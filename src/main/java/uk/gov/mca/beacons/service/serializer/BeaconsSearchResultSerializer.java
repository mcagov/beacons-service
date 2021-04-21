package uk.gov.mca.beacons.service.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import uk.gov.mca.beacons.service.model.BeaconsSearchResult;

public class BeaconsSearchResultSerializer
  extends StdSerializer<BeaconsSearchResult> {

  public BeaconsSearchResultSerializer() {
    super(BeaconsSearchResult.class);
  }

  // @Override
  // public void serialize(BeaconsSearchResult value, JsonGenerator gen,
  // SerializerProvider serializers)
  // throws IOException {

  // gen.writeStartObject();
  // gen.writeEndObject();
  // }

  @Override
  public Class<BeaconsSearchResult> handledType() {
    return BeaconsSearchResult.class;
  }

  @Override
  public void serialize(
    BeaconsSearchResult value,
    JsonGenerator gen,
    SerializerProvider provider
  ) throws IOException {
    gen.writeStartObject();
    gen.writeObjectFieldStart("meta");
    gen.writeNumberField("count", value.getCount());
    gen.writeNumberField("pageSize", value.getPageSize());
    gen.writeEndObject();
    gen.writeFieldName("data");
    gen.writeStartArray();
    gen.writeEndArray();
    gen.writeEndObject();
  }
}
