package uk.gov.mca.beacons.service.dto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import uk.gov.mca.beacons.service.hateoas.HateoasLink;

public class DomainDTOTest {

  @Test
  void ShouldAddLinksCorrectly() {
    var links = new ArrayList<HateoasLink>();
    links.add(new HateoasLink("verb1", "path1"));
    links.add(new HateoasLink("verb2", "path2"));
    links.add(new HateoasLink("verb3", "path3"));

    var dto = new BeaconDTO();
    dto.addLinks(links);
    var result = dto.getLinks();

    assertThat(result.size(), is(3));
    assertThat(result.get(0).getVerb(), is("verb1"));
    assertThat(result.get(0).getPath(), is("path1"));
    assertThat(result.get(1).getVerb(), is("verb2"));
    assertThat(result.get(1).getPath(), is("path2"));
    assertThat(result.get(2).getVerb(), is("verb3"));
    assertThat(result.get(2).getPath(), is("path3"));
  }
}
