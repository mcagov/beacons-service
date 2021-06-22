package uk.gov.mca.beacons.service.accounts;

import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.domain.AccountHolder;

@Repository
@Transactional
public class AccountHolderGatewayImpl implements AccountHolderGateway {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  public AccountHolderGatewayImpl(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public AccountHolder getById(UUID id) {
    final SqlParameterSource params = new MapSqlParameterSource()
      .addValue("id", id);
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
      params,
      AccountHolder.class
    );
  }

  @Override
  public AccountHolder getByAuthId(String authId) {
    final SqlParameterSource params = new MapSqlParameterSource()
      .addValue("authId", authId);
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
      params,
      AccountHolder.class
    );
  }

  @Override
  public AccountHolder save(AccountHolder accountHolder) {
    return null;
  }
}
