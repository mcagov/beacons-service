package uk.gov.mca.beacons.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BeaconsRegistrationService {

  public static void main(String[] args) {
    SpringApplication.run(BeaconsRegistrationService.class, args);
  }
}
