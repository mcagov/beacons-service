package uk.gov.mca.beacons.api.webapp;

public enum SatisfactionRating {
  VERY_SATISFIED("Very satisfied"),
  SATISFIED("Satisfied"),
  NEITHER_SATISFIED_OR_DISSATISFIED("Neither satisfied or dissatisfied"),
  DISSATISFIED("Dissatisfied"),
  VERY_DISSATISFIED("Very dissatisfied");

  private final String displayValue;

  SatisfactionRating(String displayValue) {
    this.displayValue = displayValue;
  }

  public String getDisplayValue() {
    return displayValue;
  }
}
