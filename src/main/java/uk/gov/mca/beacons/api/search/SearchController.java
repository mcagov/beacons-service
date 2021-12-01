package uk.gov.mca.beacons.api.search;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spring-api/search")
@Tag(name = "Search")
public class SearchController {

  public SearchController() {}

  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  public void test() {}
}
