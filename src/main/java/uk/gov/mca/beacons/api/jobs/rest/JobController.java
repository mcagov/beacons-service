package uk.gov.mca.beacons.api.jobs.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.api.jobs.JobService;

@RestController
@RequestMapping("/spring-api/job")
@Tag(name = "Job Controller")
public class JobController {

  private final JobService jobService;

  public JobController(JobService jobService) {
    this.jobService = jobService;
  }

  @PostMapping("/reindexSearch")
  public ResponseEntity<JobAcceptanceDTO> reindexSearch() throws Exception {
    Long jobId = jobService.startReindexSearchJob();
    JobAcceptanceDTO jobAcceptanceDTO = JobAcceptanceDTO
      .builder()
      .location("/spring-api/job/reindexSearch/" + jobId)
      .build();

    return ResponseEntity.accepted().body(jobAcceptanceDTO);
  }

  @GetMapping("reindexSearch/{id}")
  public ResponseEntity<JobOutputDTO> getReindexSearchOutput(
    @PathVariable("id") Long jobId
  ) {
    JobOutputDTO jobOutputDTO = JobOutputDTO
      .builder()
      .status(jobService.getJobStatus(jobId))
      .build();
    return ResponseEntity.ok().body(jobOutputDTO);
  }
}
