package uk.gov.mca.beacons.service.mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.dto.AccountHolderDTO;
import uk.gov.mca.beacons.service.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.model.AccountHolder;
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
    dto.addAttribute("fullName", domain.getFullName());
    dto.addAttribute("email", domain.getEmail());
    dto.addAttribute("telephoneNumber", domain.getTelephoneNumber());
    dto.addAttribute(
      "alternativeTelephoneNumber",
      domain.getAlternativeTelephoneNumber()
    );
    dto.addAttribute("addressLine1", domain.getAddressLine1());
    dto.addAttribute("addressLine2", domain.getAddressLine2());
    dto.addAttribute("addressLine3", domain.getAddressLine3());
    dto.addAttribute("addressLine4", domain.getAddressLine4());
    dto.addAttribute("townOrCity", domain.getTownOrCity());
    dto.addAttribute("county", domain.getCounty());
    dto.addAttribute("postcode", domain.getPostcode());
    dto.addAttribute("personType", domain.getPersonType());
    dto.addAttribute(
      "alternativeTelephoneNumber",
      domain.getAlternativeTelephoneNumber()
    );
    dto.addAttribute("telephoneNumber2", domain.getTelephoneNumber2());
    dto.addAttribute(
      "alternativeTelephoneNumber2",
      domain.getAlternativeTelephoneNumber2()
    );
    dto.addAttribute("country", domain.getCountry());
    dto.addAttribute("fax", domain.getFax());
    dto.addAttribute("companyName", domain.getCompanyName());
    dto.addAttribute("careOf", domain.getCareOf());
    dto.addAttribute("createUserId", domain.getCreateUserId());
    dto.addAttribute("updateUserId", domain.getUpdateUserId());
    dto.addAttribute("createdDate", domain.getCreatedDate());
    dto.addAttribute("lastModifiedDate", domain.getLastModifiedDate());
    dto.addAttribute("versioning", domain.getVersioning());
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
