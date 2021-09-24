package uk.gov.mca.beacons.api.gateways;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.mappers.AccountHolderRowMapper;
import uk.gov.mca.beacons.api.mappers.ModelPatcherFactory;

@ExtendWith(MockitoExtension.class)
public class PostgresAccountHolderGatewayTest {

  private final Clock fixedClock = Clock.fixed(
    Instant.parse("1983-03-13T13:03:00Z"),
    ZoneId.of("UTC")
  );

  @Captor
  ArgumentCaptor<MapSqlParameterSource> sqlParamsCaptor;

  @Mock
  private NamedParameterJdbcTemplate jdbcMock;

  @Test
  void shouldCallUpdateWithCorrectSqlParams() {
    UUID accountId = UUID.randomUUID();
    AccountHolder accountHolder = new AccountHolder();
    AccountHolder accountHolderUpdate = AccountHolder
      .builder()
      .fullName("Fat Les")
      .telephoneNumber("1966")
      .alternativeTelephoneNumber("2021")
      .addressLine1("Wembley Stadium")
      .addressLine2("Wembley Park")
      .addressLine3("Wembley")
      .addressLine4("Brent")
      .townOrCity("London")
      .postcode("V1N6 4LO")
      .county("England")
      .build();
    given(
      jdbcMock.queryForObject(
        isA(String.class),
        isA(MapSqlParameterSource.class),
        isA(AccountHolderRowMapper.class)
      )
    )
      .willReturn(accountHolder);

    var gateway = new PostgresAccountHolderGateway(
      jdbcMock,
      new ModelPatcherFactory<>(),
      fixedClock
    );
    gateway.update(accountId, accountHolderUpdate);

    verify(jdbcMock).update(any(String.class), sqlParamsCaptor.capture());
    var sqlParams = sqlParamsCaptor.getValue().getValues();
    assertThat(
      "don't want to add or remove anything",
      sqlParams.size(),
      is(12)
    );
    assertThat(sqlParams.get("accountId"), is(accountId));
    assertThat(sqlParams.get("fullName"), is("Fat Les"));
    assertThat(sqlParams.get("telephoneNumber"), is("1966"));
    assertThat(sqlParams.get("alternativeTelephoneNumber"), is("2021"));
    assertThat(sqlParams.get("addressLine1"), is("Wembley Stadium"));
    assertThat(sqlParams.get("addressLine2"), is("Wembley Park"));
    assertThat(sqlParams.get("addressLine3"), is("Wembley"));
    assertThat(sqlParams.get("addressLine4"), is("Brent"));
    assertThat(sqlParams.get("townOrCity"), is("London"));
    assertThat(sqlParams.get("postcode"), is("V1N6 4LO"));
    assertThat(sqlParams.get("county"), is("England"));
    var modifiedDate = (OffsetDateTime) sqlParams.get("lastModifiedDate");
    assertThat(modifiedDate, is(equalTo(OffsetDateTime.now(fixedClock))));
  }

  @Test
  void shouldReturnUpdatedModel() {
    UUID accountId = UUID.randomUUID();
    AccountHolder accountHolder = AccountHolder
      .builder()
      .fullName("Toots")
      .telephoneNumber("555 54 46 54")
      .alternativeTelephoneNumber("555 999 999")
      .addressLine1(null)
      .addressLine2("")
      .build();
    AccountHolder accountHolderUpdate = AccountHolder
      .builder()
      .fullName("Bob Marley")
      .telephoneNumber("555 329 184")
      .build();

    given(
      jdbcMock.queryForObject(
        isA(String.class),
        isA(MapSqlParameterSource.class),
        isA(AccountHolderRowMapper.class)
      )
    )
      .willReturn(accountHolder);

    var gateway = new PostgresAccountHolderGateway(
      jdbcMock,
      new ModelPatcherFactory<>(),
      fixedClock
    );
    var updatedModel = gateway.update(accountId, accountHolderUpdate);

    assertThat(updatedModel.getFullName(), is("Bob Marley"));
    assertThat(updatedModel.getTelephoneNumber(), is("555 329 184"));
    assertThat(updatedModel.getAlternativeTelephoneNumber(), is("555 999 999"));
    assertThat(updatedModel.getAddressLine1(), is(nullValue()));
    assertThat(updatedModel.getAddressLine2(), is(""));
  }
}
