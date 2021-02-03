package uk.gov.mca.beacons.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uk.gov.mca.beacons.service.beacon.Beacon;
import uk.gov.mca.beacons.service.beacon.BeaconRepository;

@SpringBootApplication
public class BeaconsRegistrationService {

  private static final Logger log = LoggerFactory.getLogger(
    BeaconsRegistrationService.class
  );

  public static void main(String[] args) {
    SpringApplication.run(BeaconsRegistrationService.class, args);
  }

  @Bean
  public CommandLineRunner demo(BeaconRepository repository) {
    return args -> {
      repository.save(new Beacon());
      repository.save(new Beacon());
      repository.save(new Beacon());

      log.info("Beacons found with findAll");
      log.info("--------------------------------");
      for (Beacon beacon : repository.findAll()) {
        log.info(beacon.toString());
      }
      log.info("");
    };
  }
}
