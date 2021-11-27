package uk.gov.mca.beacons.api.webapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class FeedbackFormController {

  @GetMapping("/help/feedback")
  public String feedbackForm(Model model) {
    model.addAttribute("feedback", new Feedback());
    return "feedback-form";
  }

  @PostMapping("/help/feedback")
  public String feedbackSubmit(@ModelAttribute Feedback feedback, Model model) {
    model.addAttribute("feedback", feedback);
    return "feedback-submitted";
  }
}
