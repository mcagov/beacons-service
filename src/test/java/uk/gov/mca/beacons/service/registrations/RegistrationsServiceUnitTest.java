package uk.gov.mca.beacons.service.registrations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import uk.gov.mca.beacons.service.model.Registration;

class RegistrationsServiceUnitTest {

  private final RegistrationsService registrationsService = new RegistrationsService();

  @Test
  void shouldReturnTheSameRegistrationObjectProvided() {
    final Registration registration = new Registration();
    final Registration expected = registrationsService.register(registration);

    assertThat(registration, is(expected));
  }
}
