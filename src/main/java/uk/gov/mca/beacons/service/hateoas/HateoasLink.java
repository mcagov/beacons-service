package uk.gov.mca.beacons.service.hateoas;

public class HateoasLink {

  private final String verb;

  private final String path;

  public HateoasLink(String verb, String path) {
    this.verb = verb;
    this.path = path;
  }

  public String getVerb() {
    return verb;
  }

  public String getPath() {
    return path;
  }
}
