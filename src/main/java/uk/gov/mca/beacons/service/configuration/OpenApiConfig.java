package uk.gov.mca.beacons.service.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI beaconsOpenApiConfig() {
    return new OpenAPI()
      .info(
        new Info()
          .title("Beacons API")
          .description("OpenAPI 3 definition for the Beacons API")
          .license(
            new License()
              .name(("MIT"))
              .url(
                "https://github.com/mcagov/beacons-service/blob/main/LICENCE"
              )
          )
      )
      .externalDocs(
        new ExternalDocumentation()
          .description("GitHub")
          .url("https://github.com/mcagov/beacons-service")
      );
  }
}
