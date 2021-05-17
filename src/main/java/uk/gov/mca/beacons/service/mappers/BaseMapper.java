package uk.gov.mca.beacons.service.mappers;

abstract class BaseMapper {

  protected <T extends Enum<T>> T parseEnumValueOrNull(
    Object value,
    Class<T> enumType
  ) {
    if (value == null) return null;

    return Enum.valueOf(enumType, value.toString());
  }
}
