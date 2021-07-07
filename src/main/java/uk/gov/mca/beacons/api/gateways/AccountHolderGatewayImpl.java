package uk.gov.mca.beacons.api.gateways;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.domain.PersonType;
import uk.gov.mca.beacons.api.dto.CreateAccountHolderRequest;
import uk.gov.mca.beacons.api.mappers.AccountHolderRowMapper;

@Repository
@Transactional
@Slf4j
public class AccountHolderGatewayImpl implements AccountHolderGateway {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  public AccountHolderGatewayImpl(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public AccountHolder getById(UUID id) {
    final SqlParameterSource paramMap = new MapSqlParameterSource()
      .addValue("id", id);

    try {
      return jdbcTemplate.queryForObject(
        "SELECT " +
        "account_holder.id, " +
        "account_holder.auth_id, " +
        "person.full_name, " +
        "person.email, " +
        "person.address_line_1 as address_line1, " +
        "person.address_line_2 as address_line2, " +
        "person.address_line_3 as address_line3, " +
        "person.address_line_4 as address_line4, " +
        "person.postcode, " +
        "person.county, " +
        "person.telephone_number, " +
        "person.alternative_telephone_number, " +
        "person.town_or_city " +
        "FROM account_holder " +
        "INNER JOIN person ON account_holder.person_id = person.id " +
        "WHERE account_holder.id = :id",
        paramMap,
        new AccountHolderRowMapper()
      );
    } catch (EmptyResultDataAccessException e) {
      log.info("Unable to find account holder with id {}", id);
      return null;
    }
  }

  @Override
  public AccountHolder getByAuthId(String authId) {
    final SqlParameterSource paramMap = new MapSqlParameterSource()
      .addValue("authId", authId);
    try {
      return jdbcTemplate.queryForObject(
        "SELECT " +
        "account_holder.id, " +
        "account_holder.auth_id, " +
        "person.full_name, " +
        "person.email, " +
        "person.address_line_1 as address_line1, " +
        "person.address_line_2 as address_line2, " +
        "person.address_line_3 as address_line3, " +
        "person.address_line_4 as address_line4, " +
        "person.postcode, " +
        "person.county, " +
        "person.telephone_number, " +
        "person.alternative_telephone_number, " +
        "person.town_or_city " +
        "FROM account_holder " +
        "INNER JOIN person ON account_holder.person_id = person.id " +
        "WHERE account_holder.auth_id = :authId",
        paramMap,
        new AccountHolderRowMapper()
      );
    } catch (EmptyResultDataAccessException e) {
      log.info("Unable to find account holder with auth id {}", authId);
      return null;
    }
  }

  @Override
  public AccountHolder create(
    CreateAccountHolderRequest createAccountHolderRequest
  ) {
    final var now = LocalDateTime.now();
    final var personId = UUID.randomUUID();
    final SqlParameterSource personParamMap = new MapSqlParameterSource()
      .addValue("id", personId)
      .addValue("fullName", createAccountHolderRequest.getFullName())
      .addValue(
        "telephoneNumber",
        createAccountHolderRequest.getTelephoneNumber()
      )
      .addValue(
        "alternativeTelephoneNumber",
        createAccountHolderRequest.getAlternativeTelephoneNumber()
      )
      .addValue("email", createAccountHolderRequest.getEmail())
      .addValue("personType", PersonType.OWNER.name())
      .addValue("createdDate", now)
      .addValue("lastModifiedDate", now)
      .addValue("addressLine1", createAccountHolderRequest.getAddressLine1())
      .addValue("addressLine2", createAccountHolderRequest.getAddressLine2())
      .addValue("addressLine3", createAccountHolderRequest.getAddressLine3())
      .addValue("addressLine4", createAccountHolderRequest.getAddressLine4())
      .addValue("townOrCity", createAccountHolderRequest.getTownOrCity())
      .addValue("postcode", createAccountHolderRequest.getPostcode())
      .addValue("county", createAccountHolderRequest.getCounty());

    jdbcTemplate.update(
      "INSERT INTO person " +
      "(id, " +
      "full_name, " +
      "telephone_number, " +
      "alternative_telephone_number, " +
      "email, " +
      "person_type, " +
      "created_date, " +
      "last_modified_date, " +
      "address_line_1, " +
      "address_line_2, " +
      "address_line_3, " +
      "address_line_4, " +
      "town_or_city, " +
      "postcode, " +
      "county) " +
      "VALUES " +
      "(:id, " +
      ":fullName, " +
      ":telephoneNumber, " +
      ":alternativeTelephoneNumber, " +
      ":email, " +
      ":personType, " +
      ":createdDate, " +
      ":lastModifiedDate, " +
      ":addressLine1, " +
      ":addressLine2, " +
      ":addressLine3, " +
      ":addressLine4, " +
      ":townOrCity, " +
      ":postcode, " +
      ":county)",
      personParamMap
    );

    final var accountHolderId = UUID.randomUUID();
    final SqlParameterSource accountHolderParamMap = new MapSqlParameterSource()
      .addValue("accountHolderId", accountHolderId)
      .addValue("authId", createAccountHolderRequest.getAuthId())
      .addValue("personId", personId);
    jdbcTemplate.update(
      "INSERT INTO account_holder " +
      "(id, " +
      "auth_id, " +
      "person_id) " +
      "VALUES " +
      "(:accountHolderId, " +
      ":authId, " +
      ":personId)",
      accountHolderParamMap
    );

    return AccountHolder
      .builder()
      .id(accountHolderId)
      .authId(createAccountHolderRequest.getAuthId())
      .email(createAccountHolderRequest.getEmail())
      .fullName(createAccountHolderRequest.getFullName())
      .telephoneNumber(createAccountHolderRequest.getTelephoneNumber())
      .alternativeTelephoneNumber(
        createAccountHolderRequest.getAlternativeTelephoneNumber()
      )
      .addressLine1(createAccountHolderRequest.getAddressLine1())
      .addressLine2(createAccountHolderRequest.getAddressLine2())
      .addressLine3(createAccountHolderRequest.getAddressLine3())
      .addressLine4(createAccountHolderRequest.getAddressLine4())
      .townOrCity(createAccountHolderRequest.getTownOrCity())
      .postcode(createAccountHolderRequest.getPostcode())
      .county(createAccountHolderRequest.getCounty())
      .build();
  }

  @Override
  public AccountHolder read(AccountHolder request) {
    throw new RuntimeException();
  }

  @Override
  public AccountHolder update(AccountHolder request) {
    throw new RuntimeException();
  }

  @Override
  public AccountHolder delete(AccountHolder request) {
    throw new RuntimeException();
  }
}
