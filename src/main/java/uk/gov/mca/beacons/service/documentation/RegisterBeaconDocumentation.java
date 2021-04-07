package uk.gov.mca.beacons.service.documentation;

import static java.lang.annotation.ElementType.METHOD;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Operation(
  summary = "Registers new beacons",
  parameters = {
    @Parameter(
      name = "registration",
      description = "The registration object containing the beacons to be registered"
    ),
  }
)
public @interface RegisterBeaconDocumentation {
}
