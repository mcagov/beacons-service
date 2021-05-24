package uk.gov.mca.beacons.service.hateoas;

import java.util.ArrayList;
import java.util.List;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

@Service
public class HateoasLinkManager<T> {

  public enum SupportedMethod {
    GET, PATCH,
  }

  public List<HateoasLink> getLinksFor(T domain, IHateoasLinkStrategy<T> linkStrategy) {
    final ArrayList<HateoasLink> links = new ArrayList<HateoasLink>();

    if (linkStrategy.checkGetPermission(domain))
      addGetLink(domain, linkStrategy, links);

    if (linkStrategy.checkPatchPermission(domain))
      addPatchLink(domain, linkStrategy, links);
      
    return links;
  }

  private void addGetLink(T domain, IHateoasLinkStrategy<T> linkStrategy, ArrayList<HateoasLink> links) {
    var link = new HateoasLink(SupportedMethod.GET.toString(), linkStrategy.getGetPath(domain));
    links.add(link);
  }

  private void addPatchLink(T domain, IHateoasLinkStrategy<T> linkStrategy, ArrayList<HateoasLink> links) {
    var link = new HateoasLink(SupportedMethod.PATCH.toString(), linkStrategy.getPatchPath(domain));
    links.add(link);
  }

  public static String build(WebMvcLinkBuilder linkBuilder) {
    return linkBuilder.toUriComponentsBuilder().build().getPath();
  }
}
