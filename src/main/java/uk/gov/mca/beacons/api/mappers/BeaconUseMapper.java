package uk.gov.mca.beacons.api.mappers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.db.BeaconUse;
import uk.gov.mca.beacons.api.dto.BeaconUseDTO;
import uk.gov.mca.beacons.api.entities.Activity;
import uk.gov.mca.beacons.api.entities.Environment;
import uk.gov.mca.beacons.api.entities.Purpose;
import uk.gov.mca.beacons.api.hateoas.BeaconUseLinkStrategy;
import uk.gov.mca.beacons.api.hateoas.HateoasLinkManager;

@Service
public class BeaconUseMapper extends BaseMapper {

    final HateoasLinkManager<BeaconUse> linkManager;
    final BeaconUseLinkStrategy linkStrategy;

    @Autowired
    public BeaconUseMapper(
            HateoasLinkManager<BeaconUse> linkManager,
            BeaconUseLinkStrategy linkStrategy
    ) {
        this.linkManager = linkManager;
        this.linkStrategy = linkStrategy;
    }

    public BeaconUseDTO toDTO(BeaconUse domain) {
        final var dto = new BeaconUseDTO();
        dto.setId(domain.getId());

        final Map<String, Object> attributes = new HashMap<>();
        attributes.put("environment", domain.getEnvironment());
        attributes.put("activity", domain.getActivity());
        attributes.put("otherActivity", domain.getOtherActivity());
        attributes.put("moreDetails", domain.getMoreDetails());
        attributes.put("purpose", domain.getPurpose());
        attributes.put("callSign", domain.getCallSign());
        attributes.put("vhfRadio", domain.getVhfRadio());
        attributes.put("fixedVhfRadio", domain.getFixedVhfRadio());
        attributes.put("fixedVhfRadioValue", domain.getFixedVhfRadioValue());
        attributes.put("portableVhfRadio", domain.getPortableVhfRadio());
        attributes.put("portableVhfRadioValue", domain.getPortableVhfRadioValue());
        attributes.put("satelliteTelephone", domain.getSatelliteTelephone());
        attributes.put(
                "satelliteTelephoneValue",
                domain.getSatelliteTelephoneValue()
        );
        attributes.put("mobileTelephone", domain.getMobileTelephone());
        attributes.put("mobileTelephone1", domain.getMobileTelephone1());
        attributes.put("mobileTelephone2", domain.getMobileTelephone2());
        attributes.put("otherCommunication", domain.getOtherCommunication());
        attributes.put(
                "otherCommunicationValue",
                domain.getOtherCommunicationValue()
        );
        attributes.put("maxCapacity", domain.getMaxCapacity());
        attributes.put("vesselName", domain.getVesselName());
        attributes.put("portLetterNumber", domain.getPortLetterNumber());
        attributes.put("homeport", domain.getHomeport());
        attributes.put("areaOfOperation", domain.getAreaOfOperation());
        attributes.put("beaconLocation", domain.getBeaconLocation());
        attributes.put("imoNumber", domain.getImoNumber());
        attributes.put("ssrNumber", domain.getSsrNumber());
        attributes.put("rssNumber", domain.getRssNumber());
        attributes.put("officialNumber", domain.getOfficialNumber());
        attributes.put("rigPlatformLocation", domain.getRigPlatformLocation());
        attributes.put("mainUse", domain.getMainUse());
        attributes.put("aircraftManufacturer", domain.getAircraftManufacturer());
        attributes.put("principalAirport", domain.getPrincipalAirport());
        attributes.put("secondaryAirport", domain.getSecondaryAirport());
        attributes.put("registrationMark", domain.getRegistrationMark());
        attributes.put("hexAddress", domain.getHexAddress());
        attributes.put("cnOrMsnNumber", domain.getCnOrMsnNumber());
        attributes.put("dongle", domain.getDongle());
        attributes.put("beaconPosition", domain.getBeaconPosition());
        attributes.put(
                "workingRemotelyLocation",
                domain.getWorkingRemotelyLocation()
        );
        attributes.put(
                "workingRemotelyPeopleCount",
                domain.getWorkingRemotelyPeopleCount()
        );
        attributes.put("windfarmLocation", domain.getWindfarmLocation());
        attributes.put("windfarmPeopleCount", domain.getWindfarmPeopleCount());
        attributes.put("otherActivityLocation", domain.getOtherActivityLocation());
        attributes.put(
                "otherActivityPeopleCount",
                domain.getOtherActivityPeopleCount()
        );
        dto.setAttributes(attributes);

        dto.addLinks(linkManager.getLinksFor(domain, linkStrategy));
        return dto;
    }

    public BeaconUse fromDTO(BeaconUseDTO beaconUseDto) {
        final var attributes = beaconUseDto.getAttributes();

        final var beaconUse = new BeaconUse();

        beaconUse.setEnvironment(
                parseEnumValueOrNull(attributes.get("environment"), Environment.class)
        );
        beaconUse.setPurpose(
                parseEnumValueOrNull(attributes.get("purpose"), Purpose.class)
        );
        beaconUse.setActivity(
                parseEnumValueOrNull(attributes.get("activity"), Activity.class)
        );

        beaconUse.setOtherActivity((String) attributes.get("otherActivity"));
        beaconUse.setCallSign((String) attributes.get("callSign"));
        beaconUse.setVhfRadio(parseBooleanOrNull(attributes.get("vhfRadio")));
        beaconUse.setFixedVhfRadio(
                parseBooleanOrNull(attributes.get("fixedVhfRadio"))
        );
        beaconUse.setFixedVhfRadioValue(
                (String) attributes.get("fixedVhfRadioValue")
        );
        beaconUse.setPortableVhfRadio(
                parseBooleanOrNull(attributes.get("portableVhfRadio"))
        );
        beaconUse.setPortableVhfRadioValue(
                (String) attributes.get("portableVhfRadioValue")
        );
        beaconUse.setSatelliteTelephone(
                parseBooleanOrNull(attributes.get("satelliteTelephone"))
        );
        beaconUse.setSatelliteTelephoneValue(
                (String) attributes.get("satelliteTelephoneValue")
        );
        beaconUse.setMobileTelephone(
                parseBooleanOrNull(attributes.get("mobileTelephone"))
        );
        beaconUse.setMobileTelephone1((String) attributes.get("mobileTelephone1"));
        beaconUse.setMobileTelephone2((String) attributes.get("mobileTelephone2"));
        beaconUse.setOtherCommunication(
                parseBooleanOrNull(attributes.get("otherCommunication"))
        );
        beaconUse.setOtherCommunicationValue(
                (String) attributes.get("otherCommunicationValue")
        );
        beaconUse.setMaxCapacity(parseIntegerOrNull(attributes.get("maxCapacity")));
        beaconUse.setVesselName((String) attributes.get("vesselName"));
        beaconUse.setPortLetterNumber((String) attributes.get("portLetterNumber"));
        beaconUse.setHomeport((String) attributes.get("homeport"));
        beaconUse.setAreaOfOperation((String) attributes.get("areaOfOperation"));
        beaconUse.setBeaconLocation((String) attributes.get("beaconLocation"));
        beaconUse.setImoNumber((String) attributes.get("imoNumber"));
        beaconUse.setSsrNumber((String) attributes.get("ssrNumber"));
        beaconUse.setRssNumber((String) attributes.get("rssNumber"));
        beaconUse.setOfficialNumber((String) attributes.get("officialNumber"));
        beaconUse.setRigPlatformLocation(
                (String) attributes.get("rigPlatformLocation")
        );
        beaconUse.setMainUse(parseBooleanOrNull(attributes.get("mainUse")));
        beaconUse.setAircraftManufacturer(
                (String) attributes.get("aircraftManufacturer")
        );
        beaconUse.setPrincipalAirport((String) attributes.get("principalAirport"));
        beaconUse.setSecondaryAirport((String) attributes.get("secondaryAirport"));
        beaconUse.setRegistrationMark((String) attributes.get("registrationMark"));
        beaconUse.setHexAddress((String) attributes.get("hexAddress"));
        beaconUse.setCnOrMsnNumber((String) attributes.get("cnOrMsnNumber"));
        beaconUse.setDongle(parseBooleanOrNull(attributes.get("dongle")));
        beaconUse.setBeaconPosition((String) attributes.get("beaconPosition"));
        beaconUse.setWorkingRemotelyLocation(
                (String) attributes.get("workingRemotelyLocation")
        );
        beaconUse.setWorkingRemotelyPeopleCount(
                (String) attributes.get("workingRemotelyPeopleCount")
        );
        beaconUse.setWindfarmLocation((String) attributes.get("windfarmLocation"));
        beaconUse.setWindfarmPeopleCount(
                (String) attributes.get("windfarmPeopleCount")
        );
        beaconUse.setOtherActivityLocation(
                (String) attributes.get("otherActivityLocation")
        );
        beaconUse.setOtherActivityPeopleCount(
                (String) attributes.get("otherActivityPeopleCount")
        );
        beaconUse.setMoreDetails((String) attributes.get("moreDetails"));
        beaconUse.setCreatedDate(getDateTimeOrNull(attributes.get("createdDate")));

        return beaconUse;
    }
}
