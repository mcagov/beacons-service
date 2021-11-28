package uk.gov.mca.beacons.api.webapp;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uk.gov.mca.beacons.api.webapp.feedback.GovNotifyEmail;
import uk.gov.service.notify.NotificationClient;
import uk.gov.service.notify.NotificationClientException;

/**
 * Gateway object to the GovNotify service
 *
 * @link https://www.notifications.service.gov.uk/
 */
@Slf4j
@Component
public class GovNotifyGateway {

  private final NotificationClient govNotifyClient;

  public GovNotifyGateway() {
    this.govNotifyClient = new NotificationClient("TODO apiKey");
  }

  public void sendEmail(GovNotifyEmail email) throws IOException {
    try {
      govNotifyClient.sendEmail(
        email.getTemplateId(),
        email.getTo(),
        email.getPersonalisation(),
        null
      );
    } catch (NotificationClientException e) {
      log.error(e.toString());
      throw new IOException();
    }
  }
}
