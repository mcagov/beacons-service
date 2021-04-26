package uk.gov.mca.beacons.service.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

import uk.gov.mca.beacons.service.exceptions.BeaconsSearchResultSerializationException;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconsSearchResult;

public class BeaconsSearchResultSerializer extends StdSerializer<BeaconsSearchResult> {

  public BeaconsSearchResultSerializer() {
    super(BeaconsSearchResult.class);
  }

  @Override
  public Class<BeaconsSearchResult> handledType() {
    return BeaconsSearchResult.class;
  }

  @Override
  public void serialize(BeaconsSearchResult value, JsonGenerator gen, SerializerProvider provider) {
    gen.setCodec(new ObjectMapper());
    try {
      gen.writeStartObject();
      writeMeta(value, gen);
      writeData(value, gen);
      gen.writeEndObject();
    } catch (IOException e) {
      throw new BeaconsSearchResultSerializationException("failed to serialize BeaconSearchResult (IOException)", e);
    }
  }

  private void writeData(BeaconsSearchResult value, JsonGenerator gen) throws IOException {
    gen.writeFieldName("data");
    gen.writeStartArray();
    value.getBeacons().forEach(beacon -> {
      try {
        gen.writeStartObject();
        writeBeaconDetails(gen, beacon);
        gen.writeEndObject();
      } catch (IOException e) {
        throw new BeaconsSearchResultSerializationException("failed to serialize beacon data for beaconId:" + beacon.getId(), e);
      }
    });
    gen.writeEndArray();
  }

  private void writeMeta(BeaconsSearchResult value, JsonGenerator gen) throws IOException {
    gen.writeObjectFieldStart("meta");
    gen.writeNumberField("count", value.getCount());
    gen.writeNumberField("pageSize", value.getPageSize());
    gen.writeEndObject();
  }

  private void writeEmergencyContacts(JsonGenerator gen, Beacon beacon) throws IOException{
    gen.writeFieldName("emergencyContacts");
    gen.writeStartArray();
    if (beacon.getEmergencyContacts() != null) {
      beacon
        .getEmergencyContacts()
        .forEach(
          contact -> {
            try {
              gen.writeStartObject();
              gen.writeStringField("fullName", contact.getFullName());
              gen.writeStringField(
                "telephoneNumber",
                contact.getTelephoneNumber()
              );
              gen.writeStringField(
                "alternativeTelephoneNumber",
                contact.getAlternativeTelephoneNumber()
              );
              gen.writeEndObject();
            } catch (IOException e) {
              throw new BeaconsSearchResultSerializationException("failed to serialize emergency contacts for beaconId:" + beacon.getId(), e);
            }
          }
        );
    }

    gen.writeEndArray();
  }

  private void writeBeaconDetails(JsonGenerator gen, Beacon beacon) throws IOException {
    gen.writeStringField("type", "beacon");
    gen.writeStringField("id", beacon.getId().toString());
    gen.writeObjectFieldStart("attributes");

    gen.writeStringField("manufacturer", beacon.getManufacturer());
    gen.writeStringField("model", beacon.getModel());
    gen.writeStringField("manufacturerSerialNumber", beacon.getManufacturerSerialNumber());
    gen.writeStringField("chkCode", beacon.getChkCode());
    gen.writeStringField("batteryExpiryDate", beacon.getBatteryExpiryDate().toString());
    gen.writeStringField("lastServicedDate", beacon.getLastServicedDate().toString());

    writeUses(gen, beacon);
    writeOwner(gen, beacon);
    writeEmergencyContacts(gen, beacon);

    gen.writeEndObject();
  }

  private void writeOwner(JsonGenerator gen, Beacon beacon) throws IOException {
    final var owner = beacon.getOwner();
    if (owner != null) {
      gen.writeObjectFieldStart("owner");
      gen.writeStringField("fullName", owner.getFullName());
      gen.writeStringField("email", owner.getEmail());
      gen.writeStringField("telephoneNumber", owner.getTelephoneNumber());
      gen.writeStringField("addressLine1", owner.getAddressLine1());
      gen.writeStringField("addressLine2", owner.getAddressLine2());
      gen.writeStringField("townOrCity", owner.getTownOrCity());
      gen.writeStringField("county", owner.getCounty());
      gen.writeStringField("postcode", owner.getPostcode());
      gen.writeEndObject();
    }
  }

  private void writeUses(JsonGenerator gen, Beacon beacon) throws IOException {
    gen.writeFieldName("uses");
    gen.writeStartArray();
    if (beacon.getUses() != null) {
      beacon.getUses().forEach(beaconUse -> {
        try {
          gen.writeStartObject();
          gen.writeObjectField("environment", beaconUse.getEnvironment());
          gen.writeObjectField("activity", beaconUse.getActivity());
          gen.writeStringField("moreDetails", beaconUse.getMoreDetails());
          gen.writeEndObject();
        } catch (IOException e) {
          throw new BeaconsSearchResultSerializationException("failed to serialize uses for beaconId:" + beacon.getId(), e);
        }
      });
    }
    gen.writeEndArray();
  }
}
