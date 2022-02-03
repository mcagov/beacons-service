package uk.gov.mca.beacons.api.jobs;

import javax.batch.runtime.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;

@Service
public class JobService {

  private final JobLauncher jobLauncher;
  private final Job reindexSearchJob;
  private final JobExplorer jobExplorer;
  private final JobOperator jobOperator;

  public JobService(
    @Qualifier("simpleAsyncJobLauncher") JobLauncher jobLauncher,
    Job reindexSearchJob,
    JobExplorer jobExplorer,
    JobOperator jobOperator
  ) {
    this.jobLauncher = jobLauncher;
    this.reindexSearchJob = reindexSearchJob;
    this.jobExplorer = jobExplorer;
    this.jobOperator = jobOperator;
  }

  public Long startReindexSearchJob()
    throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
    JobExecution jobExecution = jobLauncher.run(
      reindexSearchJob,
      new JobParameters()
    );

    return jobExecution.getJobId();
  }

  public BatchStatus getJobStatus(Long jobId) {
    JobExecution jobExecution = jobExplorer.getJobExecution(jobId);
    if (jobExecution == null) {
      throw new ResourceNotFoundException();
    }

    return jobExecution.getStatus().getBatchStatus();
  }

  /**
   * Cancel a running job.
   *
   * @param jobId the id of the job
   * @return true if the message was successfully sent (does not guarantee that the job has stopped)
   * @throws NoSuchJobExecutionException if the job is not found
   * @throws JobExecutionNotRunningException if the job is found but not running
   */
  public boolean cancel(Long jobId)
    throws NoSuchJobExecutionException, JobExecutionNotRunningException {
    return jobOperator.stop(jobId);
  }
}
