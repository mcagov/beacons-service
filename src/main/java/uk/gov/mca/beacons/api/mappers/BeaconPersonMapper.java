package uk.gov.mca.beacons.api.mappers;

import static uk.gov.mca.beacons.api.dto.BeaconPersonDTO.Attributes;

import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.jpa.entities.Person;

@Service
public class BeaconPersonMapper {

    public BeaconPersonDTO toDTO(Person domain) {
        final var dto = new BeaconPersonDTO();
        dto.setId(domain.getId());

        final Attributes attributes = Attributes
                .builder()
                .fullName(domain.getFullName())
                .email(domain.getEmail())
                .createdDate(domain.getCreatedDate())
                .lastModifiedDate(domain.getLastModifiedDate())
                .telephoneNumber(domain.getTelephoneNumber())
                .telephoneNumber2(domain.getTelephoneNumber2())
                .alternativeTelephoneNumber(domain.getAlternativeTelephoneNumber())
                .alternativeTelephoneNumber2(domain.getAlternativeTelephoneNumber2())
                .addressLine1(domain.getAddressLine1())
                .addressLine2(domain.getAddressLine2())
                .addressLine3(domain.getAddressLine3())
                .addressLine4(domain.getAddressLine4())
                .townOrCity(domain.getTownOrCity())
                .postcode(domain.getPostcode())
                .county(domain.getCounty())
                .country(domain.getCountry())
                .companyName(domain.getCompanyName())
                .careOf(domain.getCareOf())
                .fax(domain.getFax())
                .isMain(domain.getIsMain())
                .createUserId(domain.getCreateUserId())
                .updateUserId(domain.getUpdateUserId())
                .versioning(domain.getVersioning())
                .build();
        dto.setAttributes(attributes);

        return dto;
    }

    public Person fromDTO(BeaconPersonDTO personDTO) {
        final var beaconPerson = new Person();
        final var attributes = personDTO.getAttributes();

        beaconPerson.setEmail(attributes.getEmail());
        beaconPerson.setFullName(attributes.getFullName());
        beaconPerson.setCreatedDate(attributes.getCreatedDate());
        beaconPerson.setLastModifiedDate(attributes.getLastModifiedDate());
        beaconPerson.setTelephoneNumber(attributes.getTelephoneNumber());
        beaconPerson.setTelephoneNumber2(attributes.getTelephoneNumber2());
        beaconPerson.setAlternativeTelephoneNumber(
                attributes.getAlternativeTelephoneNumber()
        );
        beaconPerson.setAlternativeTelephoneNumber2(
                attributes.getAlternativeTelephoneNumber2()
        );
        beaconPerson.setAddressLine1(attributes.getAddressLine1());
        beaconPerson.setAddressLine2(attributes.getAddressLine2());
        beaconPerson.setAddressLine3(attributes.getAddressLine3());
        beaconPerson.setAddressLine4(attributes.getAddressLine4());
        beaconPerson.setTownOrCity(attributes.getTownOrCity());
        beaconPerson.setPostcode(attributes.getPostcode());
        beaconPerson.setCounty(attributes.getCounty());
        beaconPerson.setCountry(attributes.getCountry());
        beaconPerson.setCompanyName(attributes.getCompanyName());
        beaconPerson.setCareOf(attributes.getCareOf());
        beaconPerson.setFax(attributes.getFax());
        beaconPerson.setIsMain(attributes.getIsMain());
        beaconPerson.setCreateUserId(attributes.getCreateUserId());
        beaconPerson.setUpdateUserId(attributes.getUpdateUserId());
        beaconPerson.setVersioning(attributes.getVersioning());

        return beaconPerson;
    }

    public final WrapperDTO<BeaconPersonDTO> toWrapperDTO(
            Person person
    ) {
        final WrapperDTO<BeaconPersonDTO> wrapperDTO = new WrapperDTO<>();

        wrapperDTO.setData(toDTO(person));

        return wrapperDTO;
    }
}
