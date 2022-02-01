package uk.gov.mca.beacons.api.jobs.configuration;

import javax.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.mca.beacons.api.beacon.domain.Beacon;
import uk.gov.mca.beacons.api.search.documents.BeaconSearchDocument;

@Configuration
@EnableBatchProcessing
public class BeaconBatchJobConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;
  private static final int chunkSize = 256;

  @Autowired
  public BeaconBatchJobConfiguration(
    JobBuilderFactory jobBuilderFactory,
    StepBuilderFactory stepBuilderFactory,
    EntityManagerFactory entityManagerFactory
  ) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
    this.entityManagerFactory = entityManagerFactory;
  }

  @Bean
  public JpaPagingItemReader<Beacon> itemReader() {
    return new JpaPagingItemReaderBuilder<Beacon>()
      .name("beaconReader")
      .entityManagerFactory(entityManagerFactory)
      .queryString("select b from beacon b order by lastModifiedDate")
      .pageSize(chunkSize)
      .build();
  }

  @Bean
  public Step sendBeaconSearchDocumentStep(
    ItemWriter<BeaconSearchDocument> beaconSearchDocumentWriter,
    ItemProcessor<Beacon, BeaconSearchDocument> beaconBatchJobProcessor
  ) {
    return stepBuilderFactory
      .get("sendBeaconSearchDocumentStep")
      .<Beacon, BeaconSearchDocument>chunk(chunkSize)
      .reader(itemReader())
      .processor(beaconBatchJobProcessor)
      .writer(beaconSearchDocumentWriter)
      .build();
  }

  @Bean(value = "sendBeaconSearchDocumentJob")
  public Job sendBeaconSearchDocumentJob(Step sendBeaconSearchDocumentStep) {
    return jobBuilderFactory
      .get("sendBeaconSearchDocumentStep")
      .incrementer(new RunIdIncrementer())
      .flow(sendBeaconSearchDocumentStep)
      .end()
      .build();
  }
}
