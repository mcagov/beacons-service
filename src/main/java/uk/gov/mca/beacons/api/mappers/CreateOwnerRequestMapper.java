package uk.gov.mca.beacons.api.mappers;

import java.util.UUID;
import uk.gov.mca.beacons.api.db.Person;
import uk.gov.mca.beacons.api.dto.CreateOwnerRequest;
import uk.gov.mca.beacons.api.entities.PersonType;

public class CreateOwnerRequestMapper {

    public static Person toBeaconPerson(CreateOwnerRequest request) {
        final Person owner = new Person();
        owner.setBeaconId(request.getBeaconId());
        owner.setFullName(request.getFullName());
        owner.setTelephoneNumber(request.getTelephoneNumber());
        owner.setAlternativeTelephoneNumber(
                request.getAlternativeTelephoneNumber()
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

        return owner;
    }

    public static CreateOwnerRequest fromBeaconPerson(
            Person owner,
            UUID beaconId
    ) {
        return CreateOwnerRequest
                .builder()
                .beaconId(beaconId)
                .fullName(owner.getFullName())
                .telephoneNumber(owner.getTelephoneNumber())
                .alternativeTelephoneNumber(owner.getAlternativeTelephoneNumber())
                .email(owner.getEmail())
                .addressLine1(owner.getAddressLine1())
                .addressLine2(owner.getAddressLine2())
                .addressLine3(owner.getAddressLine3())
                .addressLine4(owner.getAddressLine4())
                .townOrCity(owner.getTownOrCity())
                .postcode(owner.getPostcode())
                .county(owner.getCounty())
                .build();
    }
}
