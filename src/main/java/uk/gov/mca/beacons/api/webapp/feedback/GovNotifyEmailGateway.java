package uk.gov.mca.beacons.api.webapp.feedback;

import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.service.notify.NotificationClient;
import uk.gov.service.notify.NotificationClientException;

/**
 * Gateway object to the GovNotify service
 *
 * @link https://www.notifications.service.gov.uk/
 */
@Slf4j
@Component
public class GovNotifyEmailGateway implements EmailGateway {

  private final NotificationClient govNotifyClient;

  public GovNotifyEmailGateway(@Value("${govnotify.api-key}") String apiKey) {
    this.govNotifyClient = new NotificationClient(apiKey);
  }

  public void send(FeedbackEmail email) throws IOException {
    String govNotifyTemplateId = "87dc177e-942f-4484-95ba-18580e937280";
    try {
      govNotifyClient.sendEmail(
        govNotifyTemplateId,
        email.getRecipientEmailAddress(),
        personalisationFields(email),
        null
      );
      log.info("Feedback email sent: " + email.getReferenceId());
    } catch (NotificationClientException e) {
      log.error("Error sending feedback email: " + e);
      throw new IOException();
    }
  }

  private Map<String, String> personalisationFields(FeedbackEmail email) {
    return Map.of(
      "referenceId",
      email.getReferenceId().toString(),
      "satisfactionRating",
      email.getSatisfactionRating().getDisplayValue(),
      "howCouldWeImproveThisService",
      email.getHowCouldWeImproveThisService()
    );
  }
}
