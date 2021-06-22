package uk.gov.mca.beacons.service.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import uk.gov.mca.beacons.service.domain.AccountHolder;

public class AccountHolderRowMapper implements RowMapper<AccountHolder> {

  @Override
  public AccountHolder mapRow(ResultSet rs, int rowNum) throws SQLException {
    return AccountHolder
      .builder()
      .id(UUID.fromString(rs.getString("id")))
      .authId(rs.getString("auth_id"))
      .fullName(rs.getString("full_name"))
      .email(rs.getString("email"))
      .addressLine1(rs.getString("address_line1"))
      .addressLine2(rs.getString("address_line2"))
      .addressLine3(rs.getString("address_line3"))
      .addressLine4(rs.getString("address_line4"))
      .postcode(rs.getString("postcode"))
      .county(rs.getString("county"))
      .telephoneNumber(rs.getString("telephone_number"))
      .alternativeTelephoneNumber(rs.getString("alternative_telephone_number"))
      .townOrCity(rs.getString("town_or_city"))
      .build();
  }
}
