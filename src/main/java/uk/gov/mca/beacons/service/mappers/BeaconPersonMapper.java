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
}
