package uk.gov.mca.beacons.service.mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;

abstract class BaseMapper {

  protected <T extends Enum<T>> T parseEnumValueOrNull(
    Object value,
    Class<T> enumType
  ) {
    if (value == null) return null;

    return Enum.valueOf(enumType, value.toString());
  }

  protected LocalDateTime getDateTimeOrNull(Object value) {
    if (value == null) return null;

    return LocalDateTime.parse((String) value);
  }

  protected LocalDate getDateOrNull(Object value) {
    if (value == null) return null;

    return LocalDate.parse(removeTime((String) value));
  }

  private static String removeTime(String dateTimeString) {
    final var isoDateStringLength = 10; // YYYY-MM-DD
    return dateTimeString.substring(0, isoDateStringLength);
  }
}
