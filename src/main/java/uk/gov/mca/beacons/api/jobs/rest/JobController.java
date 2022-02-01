package uk.gov.mca.beacons.api.jobs.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/spring-api/job")
@Tag(name = "Job Controller")
public class JobController {

  private final JobLauncher jobLauncher;
  private final Job reindexSearchJob;
  private final JobExplorer jobExplorer;

  public JobController(
    JobLauncher jobLauncher,
    Job reindexSearchJob,
    JobExplorer jobExplorer
  ) {
    this.jobLauncher = jobLauncher;
    this.reindexSearchJob = reindexSearchJob;
    this.jobExplorer = jobExplorer;
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

    return ResponseEntity.accepted().body(jobAcceptanceDTO);
  }

  @GetMapping("reindexSearch/{id}")
  public ResponseEntity<JobOutputDTO> getReindexSearchOutput(
    @PathVariable("id") Long jobId
  ) {
    JobExecution jobExecution = jobExplorer.getJobExecution(jobId);
    if (jobExecution == null) {
      throw new ResourceNotFoundException();
    }
    JobOutputDTO jobOutputDTO = JobOutputDTO
      .builder()
      .status(jobExecution.getStatus().getBatchStatus())
      .build();
    return ResponseEntity.ok().body(jobOutputDTO);
  }
}
