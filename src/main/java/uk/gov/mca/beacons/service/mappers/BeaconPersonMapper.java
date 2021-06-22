package uk.gov.mca.beacons.service.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.PersonType;

@Service
public class BeaconPersonMapper {

  public BeaconPerson fromDTO(BeaconPersonDTO beaconPersonDTO) {
    final var beaconPerson = new BeaconPerson();

    final var attributes = beaconPersonDTO.getAttributes();

    beaconPerson.setEmail((String) attributes.get("email"));
    beaconPerson.setFullName((String) attributes.get("fullName"));
    beaconPerson.setTelephoneNumber((String) attributes.get("telephoneNumber"));
    beaconPerson.setAlternativeTelephoneNumber(
      (String) attributes.get("alternativeTelephoneNumber")
    );
    beaconPerson.setTelephoneNumber2(
      (String) attributes.get("telephoneNumber2")
    );
    beaconPerson.setAlternativeTelephoneNumber2(
      (String) attributes.get("alternativeTelephoneNumber2")
    );
    beaconPerson.setAddressLine1((String) attributes.get("addressLine1"));
    beaconPerson.setAddressLine2((String) attributes.get("addressLine2"));
    beaconPerson.setAddressLine3((String) attributes.get("addressLine3"));
    beaconPerson.setAddressLine4((String) attributes.get("addressLine4"));
    beaconPerson.setTownOrCity((String) attributes.get("townOrCity"));
    beaconPerson.setPostcode((String) attributes.get("postcode"));
    beaconPerson.setCounty((String) attributes.get("county"));

    beaconPerson.setCountry((String) attributes.get("country"));
    beaconPerson.setCompanyName((String) attributes.get("companyName"));
    beaconPerson.setCareOf((String) attributes.get("careOf"));
    beaconPerson.setFax((String) attributes.get("fax"));
    beaconPerson.setIsMain((String) attributes.get("isMain"));
    beaconPerson.setCreateUserId((Integer) attributes.get("createUserId"));
    beaconPerson.setUpdateUserId((Integer) attributes.get("updateUserId"));
    beaconPerson.setVersioning((Integer) attributes.get("versioning"));

    beaconPerson.setPersonType(
      PersonType.valueOf((String) attributes.get("personType"))
    );

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm:ss",
      Locale.ENGLISH
    );
    Object createdDate = attributes.get("createdDate");
    if (createdDate != null) {
      beaconPerson.setCreatedDate(
        LocalDateTime.parse((String) createdDate, formatter)
      );
    }

    Object lastModifiedDate = attributes.get("lastModifiedDate");
    if (lastModifiedDate != null) {
      beaconPerson.setLastModifiedDate(
        LocalDateTime.parse((String) lastModifiedDate, formatter)
      );
    }

    return beaconPerson;
  }

  public BeaconPersonDTO toDTO(BeaconPerson domain) {
    final var dto = new BeaconPersonDTO();
    dto.setId(domain.getId());

    final Map<String, Object> attributes = new HashMap<>();
    attributes.put("fullName", domain.getFullName());
    attributes.put("email", domain.getEmail());
    attributes.put("telephoneNumber", domain.getTelephoneNumber());
    attributes.put(
      "alternativeTelephoneNumber",
      domain.getAlternativeTelephoneNumber()
    );
    attributes.put(
      "alternativeTelephoneNumber2",
      domain.getAlternativeTelephoneNumber2()
    );
    attributes.put("addressLine1", domain.getAddressLine1());
    attributes.put("addressLine2", domain.getAddressLine2());
    attributes.put("addressLine3", domain.getAddressLine3());
    attributes.put("addressLine4", domain.getAddressLine4());
    attributes.put("townOrCity", domain.getTownOrCity());
    attributes.put("county", domain.getCounty());
    attributes.put("postcode", domain.getPostcode());
    attributes.put("country", domain.getCountry());
    attributes.put("companyName", domain.getCompanyName());
    attributes.put("careOf", domain.getCareOf());
    attributes.put("fax", domain.getFax());
    attributes.put("createUserId", domain.getCreateUserId());
    attributes.put("updateUserId", domain.getUpdateUserId());
    attributes.put("createdDate", domain.getCreatedDate());
    attributes.put("lastModifiedDate", domain.getLastModifiedDate());
    attributes.put("versioning", domain.getVersioning());
    dto.setAttributes(attributes);

    return dto;
  }

  public final WrapperDTO<BeaconPersonDTO> toWrapperDTO(
    BeaconPerson beaconPerson
  ) {
    WrapperDTO<BeaconPersonDTO> wrapperDTO = new WrapperDTO<>();

    wrapperDTO.setData(toDTO(beaconPerson));

    return wrapperDTO;
  }
}
