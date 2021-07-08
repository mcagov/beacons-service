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
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.api.mappers.AccountHolderRowMapper;
import uk.gov.mca.beacons.api.mappers.ModelPatcherFactory;

@Repository
@Transactional
@Slf4j
public class AccountHolderGatewayImpl implements AccountHolderGateway {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final ModelPatcherFactory<AccountHolder> accountHolderPatcherFactory;

  @Autowired
  public AccountHolderGatewayImpl(
    NamedParameterJdbcTemplate jdbcTemplate,
    ModelPatcherFactory<AccountHolder> accountHolderPatcherFactory
  ) {
    this.jdbcTemplate = jdbcTemplate;
    this.accountHolderPatcherFactory = accountHolderPatcherFactory;
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
  public AccountHolder update(UUID id, AccountHolder accountHolderUpdate) {
    final AccountHolder accountHolder = this.getById(id);
    if (accountHolder == null) throw new ResourceNotFoundException();

    final var patcher = accountHolderPatcherFactory
      .getModelPatcher()
      .withMapping(AccountHolder::getFullName, AccountHolder::setFullName)
      .withMapping(
        AccountHolder::getTelephoneNumber,
        AccountHolder::setTelephoneNumber
      )
      .withMapping(
        AccountHolder::getAlternativeTelephoneNumber,
        AccountHolder::setAlternativeTelephoneNumber
      )
      .withMapping(
        AccountHolder::getAddressLine1,
        AccountHolder::setAddressLine1
      )
      .withMapping(
        AccountHolder::getAddressLine2,
        AccountHolder::setAddressLine2
      )
      .withMapping(
        AccountHolder::getAddressLine3,
        AccountHolder::setAddressLine3
      )
      .withMapping(
        AccountHolder::getAddressLine4,
        AccountHolder::setAddressLine4
      )
      .withMapping(AccountHolder::getTownOrCity, AccountHolder::setTownOrCity)
      .withMapping(AccountHolder::getPostcode, AccountHolder::setPostcode)
      .withMapping(AccountHolder::getCounty, AccountHolder::setCounty);

    final var updatedModel = patcher.patchModel(
      accountHolder,
      accountHolderUpdate
    );

    final var personParamMap = new MapSqlParameterSource()
      .addValue("accountId", id)
      .addValue("fullName", updatedModel.getFullName())
      .addValue("telephoneNumber", updatedModel.getTelephoneNumber())
      .addValue(
        "alternativeTelephoneNumber",
        updatedModel.getAlternativeTelephoneNumber()
      )
      .addValue("lastModifiedDate", LocalDateTime.now())
      .addValue("addressLine1", updatedModel.getAddressLine1())
      .addValue("addressLine2", updatedModel.getAddressLine2())
      .addValue("addressLine3", updatedModel.getAddressLine3())
      .addValue("addressLine4", updatedModel.getAddressLine4())
      .addValue("townOrCity", updatedModel.getTownOrCity())
      .addValue("postcode", updatedModel.getPostcode())
      .addValue("county", updatedModel.getCounty());

    jdbcTemplate.update(
      "UPDATE person SET " +
      "full_name = :fullName , " +
      "telephone_number = :telephoneNumber , " +
      "alternative_telephone_number = :alternative_telephone_number , " +
      "last_modified_date = :lastModifiedDate , " +
      "address_line_1 = :addressLine1 , " +
      "address_line_2 = :addressLine2 , " +
      "address_line_3 = :addressLine3 , " +
      "address_line_4 = :addressLine4 , " +
      "town_or_city = :townOrCity , " +
      "postcode = :postcode , " +
      "county = :county " +
      "FROM account_holder " +
      "WHERE person.id = account_holder.person_id " +
      "and account_holder.id = :accountId",
      personParamMap
    );

    return updatedModel;
  }
}
