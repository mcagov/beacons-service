package uk.gov.mca.beacons.service.mappers;

import java.time.LocalDateTime;
import java.util.UUID;
import uk.gov.mca.beacons.service.gateway.CreateOwnerRequest;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.PersonType;

public class CreateOwnerRequestMapper {

  public static BeaconPerson toBeaconPerson(CreateOwnerRequest request) {
    final BeaconPerson owner = new BeaconPerson();
    owner.setBeaconId(request.getBeaconId());
    owner.setFullName(request.getFullName());
    owner.setCreatedDate(request.getCreatedDate());
    owner.setLastModifiedDate(request.getLastModifiedDate());
    owner.setTelephoneNumber(request.getTelephoneNumber());
    owner.setTelephoneNumber2(request.getTelephoneNumber2());
    owner.setAlternativeTelephoneNumber(
      request.getAlternativeTelephoneNumber()
    );
    owner.setAlternativeTelephoneNumber2(
      request.getAlternativeTelephoneNumber2()
    );
    owner.setEmail(request.getEmail());
    owner.setPersonType(PersonType.OWNER);
    owner.setAddressLine1(request.getAddressLine1());
    owner.setAddressLine2(request.getAddressLine2());
    owner.setAddressLine3(request.getAddressLine3());
    owner.setAddressLine4(request.getAddressLine4());
    owner.setTownOrCity(request.getTownOrCity());
    owner.setPostcode(request.getPostcode());
    owner.setCounty(request.getCounty());
    owner.setCountry(request.getCountry());
    owner.setCompanyName(request.getCompanyName());
    owner.setCareOf(request.getCareOf());
    owner.setFax(request.getFax());
    owner.setIsMain(request.getIsMain());
    owner.setCreateUserId(request.getCreateUserId());
    owner.setUpdateUserId(request.getUpdateUserId());
    owner.setVersioning(request.getVersioning());

    return owner;
  }

  public static CreateOwnerRequest fromBeaconPerson(
    BeaconPerson owner,
    UUID beaconId
  ) {
    final var now = LocalDateTime.now();
    return CreateOwnerRequest
      .builder()
      .beaconId(beaconId)
      .fullName(owner.getFullName())
      .createdDate(
        owner.getCreatedDate() != null ? owner.getCreatedDate() : now
      )
      .lastModifiedDate(
        owner.getLastModifiedDate() != null ? owner.getLastModifiedDate() : now
      )
      .telephoneNumber(owner.getTelephoneNumber())
      .telephoneNumber2(owner.getTelephoneNumber2())
      .alternativeTelephoneNumber(owner.getAlternativeTelephoneNumber())
      .alternativeTelephoneNumber2(owner.getAlternativeTelephoneNumber2())
      .email(owner.getEmail())
      .addressLine1(owner.getAddressLine1())
      .addressLine2(owner.getAddressLine2())
      .addressLine3(owner.getAddressLine3())
      .addressLine4(owner.getAddressLine4())
      .townOrCity(owner.getTownOrCity())
      .postcode(owner.getPostcode())
      .county(owner.getCounty())
      .country(owner.getCountry())
      .companyName(owner.getCompanyName())
      .careOf(owner.getCareOf())
      .fax(owner.getFax())
      .isMain(owner.getIsMain())
      .createUserId(owner.getCreateUserId())
      .updateUserId(owner.getUpdateUserId())
      .versioning(owner.getVersioning())
      .build();
  }

  public static CreateOwnerRequest fromBeaconPerson(BeaconPerson owner) {
    return fromBeaconPerson(owner, null);
  }
}
