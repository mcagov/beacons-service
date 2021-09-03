package uk.gov.mca.beacons.api.mappers;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

public class DateTimeParser {

  public static OffsetDateTime parse(String dateTimeString) {
    try {
      return OffsetDateTime.parse(dateTimeString);
    } catch (DateTimeParseException e) {
      return OffsetDateTime.of(
        LocalDateTime.parse(dateTimeString),
        ZoneOffset.ofHours(0)
      );
    }
  }
}
