package uk.gov.mca.beacons.api;

import org.springframework.context.annotation.Bean;
import uk.gov.mca.beacons.api.services.GetValidationErrorResponse;
import uk.gov.mca.beacons.api.services.GetValidationErrorResponseService;

public class WebMvcTestConfiguration {

  @Bean
  public GetValidationErrorResponse getValidationErrorResponseService() {
    return new GetValidationErrorResponseService();
  }
}
