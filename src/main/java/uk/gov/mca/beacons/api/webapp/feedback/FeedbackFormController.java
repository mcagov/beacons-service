package uk.gov.mca.beacons.api.webapp.feedback;

import java.io.IOException;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class FeedbackFormController {

  private final FeedbackService feedbackService;

  @Autowired
  public FeedbackFormController(FeedbackService feedbackService) {
    this.feedbackService = feedbackService;
  }

  @GetMapping("/help/feedback")
  public String feedbackForm(Model model) {
    model.addAttribute("feedback", new Feedback());
    return "feedback-form";
  }

  @PostMapping("/help/feedback")
  public String feedbackSubmit(
    @Valid Feedback feedback,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) return "feedback-form";

    try {
      feedbackService.record(feedback);
    } catch (IOException e) {
      return "feedback-problem";
    }

    return "feedback-received";
  }
}
