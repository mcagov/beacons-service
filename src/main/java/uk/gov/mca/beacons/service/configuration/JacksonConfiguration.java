package uk.gov.mca.beacons.service.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.mca.beacons.service.model.BeaconsSearchResult;
import uk.gov.mca.beacons.service.serializer.BeaconsSearchResultSerializer;

@Configuration
public class JacksonConfiguration {
//TODO: reconcile config, which is now split between this and application.yml

  @Bean
  public ObjectMapper objectMapper() {

    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addSerializer(BeaconsSearchResult.class, new BeaconsSearchResultSerializer());
    objectMapper.registerModule(module);

    return objectMapper;
  }
}