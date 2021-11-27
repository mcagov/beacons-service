package uk.gov.mca.beacons.api.webapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeedbackFormController {

  @GetMapping("/help/feedback")
  public String feedbackForm() {
    return "feedback-form";
  }
}
