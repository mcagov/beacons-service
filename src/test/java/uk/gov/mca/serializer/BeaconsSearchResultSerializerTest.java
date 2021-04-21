package uk.gov.mca.serializer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.BeaconStatus;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.model.BeaconsSearchResult;
import uk.gov.mca.beacons.service.model.PersonType;
import uk.gov.mca.beacons.service.model.Registration;
import uk.gov.mca.beacons.service.serializer.BeaconsSearchResultSerializer;

@ExtendWith(MockitoExtension.class)
public class BeaconsSearchResultSerializerTest {

    private static final String JSON_RESOURCE = "src/test/resources/fixtures/beaconSearch.json";

    @BeforeEach
    void init() {
    }

    @Test
    void shouldSerializeResultForZeroResultsAsExpected() throws IOException {
        final String json = new String(Files.readAllBytes(Paths.get(JSON_RESOURCE))).replaceAll("[\\n\\t ]", "");;

        // final var beaconId = UUID.randomUUID();
        // final var beacon = new Beacon();
        // beacon.setId(beaconId);
        // final var beaconsSearchResult = new BeaconsSearchResult();
        // beaconsSearchResult.setBeacons(List.of(beacon));

        final var beaconsSearchResult = new BeaconsSearchResult();
        beaconsSearchResult.setBeacons(List.of());

        Writer jsonWriter = (Writer) new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
        SerializerProvider serializerProvider = new ObjectMapper().getSerializerProvider();

        final var serializer = new BeaconsSearchResultSerializer();
        serializer.serialize(beaconsSearchResult, jsonGenerator, serializerProvider);
        jsonGenerator.flush();

        assertThat(jsonWriter.toString(), is(equalTo(json)));
    }
}
