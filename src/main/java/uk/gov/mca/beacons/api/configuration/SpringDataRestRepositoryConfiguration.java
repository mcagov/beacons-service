package uk.gov.mca.beacons.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import uk.gov.mca.beacons.api.jpa.entities.BeaconSearchEntity;

@Configuration
public class SpringDataRestRepositoryConfiguration
  implements RepositoryRestConfigurer {

  @Override
  public void configureRepositoryRestConfiguration(
    RepositoryRestConfiguration config,
    CorsRegistry cors
  ) {
    config.exposeIdsFor(BeaconSearchEntity.class);
  }
}
