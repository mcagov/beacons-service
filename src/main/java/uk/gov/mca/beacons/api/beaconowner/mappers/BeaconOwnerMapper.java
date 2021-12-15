package uk.gov.mca.beacons.api.beaconowner.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.mca.beacons.api.beaconowner.domain.BeaconOwner;
import uk.gov.mca.beacons.api.beaconowner.rest.BeaconOwnerRegistrationDTO;
import uk.gov.mca.beacons.api.shared.mappers.person.AddressMapper;

@Component("BeaconOwnerMapperV2")
public class BeaconOwnerMapper {

  private final AddressMapper addressMapper;

  @Autowired
  public BeaconOwnerMapper(AddressMapper addressMapper) {
    this.addressMapper = addressMapper;
  }

  public BeaconOwner fromDTO(BeaconOwnerRegistrationDTO dto) {
    BeaconOwner beaconOwner = new BeaconOwner();

    beaconOwner.setFullName(dto.getFullName());
    beaconOwner.setEmail(dto.getEmail());
    beaconOwner.setTelephoneNumber(dto.getTelephoneNumber());
    beaconOwner.setAlternativeTelephoneNumber(
      dto.getAlternativeTelephoneNumber()
    );
    beaconOwner.setAddress(addressMapper.fromDTO(dto.getAddressDTO()));

    return beaconOwner;
  }

  public BeaconOwnerRegistrationDTO toBeaconOwnerRegistrationDTO(
    BeaconOwner beaconOwner
  ) {
    return BeaconOwnerRegistrationDTO
      .builder()
      .fullName(beaconOwner.getFullName())
      .email(beaconOwner.getEmail())
      .telephoneNumber(beaconOwner.getTelephoneNumber())
      .alternativeTelephoneNumber(beaconOwner.getAlternativeTelephoneNumber())
      .addressDTO(addressMapper.toDTO(beaconOwner.getAddress()))
      .build();
  }
}
