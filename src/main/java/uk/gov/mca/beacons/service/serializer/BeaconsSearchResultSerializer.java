package uk.gov.mca.beacons.service.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import uk.gov.mca.beacons.service.exceptions.BeaconsSearchResultSerializerException;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconsSearchResult;

public class BeaconsSearchResultSerializer
  extends StdSerializer<BeaconsSearchResult> {

  public BeaconsSearchResultSerializer() {
    super(BeaconsSearchResult.class);
  }

  @Override
  public Class<BeaconsSearchResult> handledType() {
    return BeaconsSearchResult.class;
  }

  @Override
  public void serialize(
    BeaconsSearchResult value,
    JsonGenerator gen,
    SerializerProvider provider
  ) {
    gen.setCodec(new ObjectMapper());
    try {
      gen.writeStartObject();
      writeMeta(value, gen);
      writeData(value, gen);
      gen.writeEndObject();
    } catch (IOException e) {
      throw new BeaconsSearchResultSerializerException(
        "failed to serialize BeaconSearchResult (IOException)",
        e
      );
    }
  }

  private void writeData(BeaconsSearchResult value, JsonGenerator gen)
    throws IOException {
    gen.writeFieldName("data");
    gen.writeStartArray();
    value
      .getBeacons()
      .forEach(
        beacon -> {
          try {
            gen.writeStartObject();
            writeBeaconDetails(gen, beacon);
            gen.writeEndObject();
          } catch (IOException e) {
            throw new BeaconsSearchResultSerializerException(
              "failed to serialize beacon data for beaconId:" + beacon.getId(),
              e
            );
          }
        }
      );
    gen.writeEndArray();
  }

  private void writeMeta(BeaconsSearchResult value, JsonGenerator gen)
    throws IOException {
    gen.writeObjectFieldStart("meta");
    gen.writeNumberField("count", value.getCount());
    gen.writeNumberField("pageSize", value.getPageSize());
    gen.writeEndObject();
  }

  private void writeEmergencyContacts(JsonGenerator gen, Beacon beacon)
    throws IOException {
    gen.writeFieldName("emergencyContacts");
    gen.writeStartArray();
    if (beacon.getEmergencyContacts() != null) {
      beacon
        .getEmergencyContacts()
        .forEach(
          contact -> {
            try {
              gen.writeStartObject();
              writeStringFieldWithNullCheck(
                gen,
                "fullName",
                contact.getFullName()
              );
              writeStringFieldWithNullCheck(
                gen,
                "telephoneNumber",
                contact.getTelephoneNumber()
              );
              writeStringFieldWithNullCheck(
                gen,
                "alternativeTelephoneNumber",
                contact.getAlternativeTelephoneNumber()
              );
              gen.writeEndObject();
            } catch (IOException e) {
              throw new BeaconsSearchResultSerializerException(
                "failed to serialize emergency contacts for beaconId:" +
                beacon.getId(),
                e
              );
            }
          }
        );
    }

    gen.writeEndArray();
  }

  private void writeBeaconDetails(JsonGenerator gen, Beacon beacon)
    throws IOException {
    writeStringFieldWithNullCheck(gen, "type", "beacon");
    writeStringFieldWithNullCheck(gen, "id", beacon.getId());

    gen.writeObjectFieldStart("attributes");

    writeStringFieldWithNullCheck(gen, "hexId", beacon.getHexId());
    writeStringFieldWithNullCheck(gen, "status", beacon.getBeaconStatus());
    writeStringFieldWithNullCheck(
      gen,
      "manufacturer",
      beacon.getManufacturer()
    );
    writeStringFieldWithNullCheck(gen, "createdDate", beacon.getCreatedDate());
    writeStringFieldWithNullCheck(gen, "model", beacon.getModel());
    writeStringFieldWithNullCheck(
      gen,
      "manufacturerSerialNumber",
      beacon.getManufacturerSerialNumber()
    );
    writeStringFieldWithNullCheck(gen, "chkCode", beacon.getChkCode());
    writeStringFieldWithNullCheck(
      gen,
      "batteryExpiryDate",
      beacon.getBatteryExpiryDate()
    );
    writeStringFieldWithNullCheck(
      gen,
      "lastServicedDate",
      beacon.getLastServicedDate()
    );

    writeUses(gen, beacon);
    writeOwner(gen, beacon);
    writeEmergencyContacts(gen, beacon);

    gen.writeEndObject();
  }

  private void writeOwner(JsonGenerator gen, Beacon beacon) throws IOException {
    final var owner = beacon.getOwner();
    if (owner != null) {
      gen.writeObjectFieldStart("owner");
      writeStringFieldWithNullCheck(gen, "fullName", owner.getFullName());
      writeStringFieldWithNullCheck(gen, "email", owner.getEmail());
      writeStringFieldWithNullCheck(
        gen,
        "telephoneNumber",
        owner.getTelephoneNumber()
      );
      writeStringFieldWithNullCheck(
        gen,
        "addressLine1",
        owner.getAddressLine1()
      );
      writeStringFieldWithNullCheck(
        gen,
        "addressLine2",
        owner.getAddressLine2()
      );
      writeStringFieldWithNullCheck(gen, "townOrCity", owner.getTownOrCity());
      writeStringFieldWithNullCheck(gen, "county", owner.getCounty());
      writeStringFieldWithNullCheck(gen, "postcode", owner.getPostcode());
      gen.writeEndObject();
    }
  }

  private void writeUses(JsonGenerator gen, Beacon beacon) throws IOException {
    gen.writeFieldName("uses");
    gen.writeStartArray();
    if (beacon.getUses() != null) {
      beacon
        .getUses()
        .forEach(
          beaconUse -> {
            try {
              gen.writeStartObject();
              writeStringFieldWithNullCheck(
                gen,
                "environment",
                beaconUse.getEnvironment()
              );
              writeStringFieldWithNullCheck(
                gen,
                "activity",
                beaconUse.getActivity()
              );
              writeStringFieldWithNullCheck(
                gen,
                "moreDetails",
                beaconUse.getMoreDetails()
              );
              gen.writeEndObject();
            } catch (IOException e) {
              throw new BeaconsSearchResultSerializerException(
                "failed to serialize uses for beaconId:" + beacon.getId(),
                e
              );
            }
          }
        );
    }
    gen.writeEndArray();
  }

  private void writeStringFieldWithNullCheck(
    JsonGenerator gen,
    String fieldName,
    Object toWrite
  ) throws IOException {
    if (toWrite == null) gen.writeStringField(
      fieldName,
      ""
    ); else gen.writeStringField(fieldName, toWrite.toString());
  }
}
