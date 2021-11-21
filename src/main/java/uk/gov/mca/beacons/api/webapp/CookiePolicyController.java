package uk.gov.mca.beacons.api.webapp;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CookiePolicyController {

  @PostMapping("/help/cookies/hide-banner")
  public String setCookieToHideCookieBanner(HttpServletResponse response) {
    Cookie cookie = new Cookie("hide-cookie-banner", "true");

    int oneYear = 365 * 24 * 60 * 60;
    cookie.setMaxAge(oneYear);

    String allPaths = "/";
    cookie.setPath(allPaths);

    response.addCookie(cookie);

    return "redirect:/";
  }
}
