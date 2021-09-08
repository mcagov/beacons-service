package uk.gov.mca.beacons.api.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import java.io.IOException;
import java.time.OffsetDateTime;

public class OffsetDateTimeOptionalZoneDeserializer
  extends StdScalarDeserializer<OffsetDateTime> {

  public OffsetDateTimeOptionalZoneDeserializer() {
    super(OffsetDateTime.class);
  }

  @Override
  public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctx)
    throws IOException {
    return OffsetDateTimeOptionalZoneParser.parse(p.getText());
  }
}
