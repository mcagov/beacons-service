package uk.gov.mca.beacons.api.jobs.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring-api/job")
@Tag(name = "Job Controller")
public class JobController {

  private final JobLauncher jobLauncher;
  private final Job reindexSearchJob;

  public JobController(JobLauncher jobLauncher, Job reindexSearchJob) {
    this.jobLauncher = jobLauncher;
    this.reindexSearchJob = reindexSearchJob;
  }

  @PostMapping("/reindexSearch")
  public ResponseEntity<JobAcceptanceDTO> reindexSearch()
    throws JobExecutionException {
    JobExecution jobExecution = jobLauncher.run(
      reindexSearchJob,
      new JobParameters()
    );

    JobAcceptanceDTO jobAcceptanceDTO = JobAcceptanceDTO
      .builder()
      .location("/spring-api/job/reindexSearch/" + jobExecution.getJobId())
      .build();

    return ResponseEntity.accepted().build();
  }
}
