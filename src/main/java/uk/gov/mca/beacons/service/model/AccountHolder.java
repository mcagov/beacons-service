package uk.gov.mca.beacons.service.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "beacon_account_holder")
@EntityListeners(AuditingEntityListener.class)
public class AccountHolder {

  @Id
  @GeneratedValue
  private UUID id;

  private String authId;

  private String email;

  private String fullName;

  private String telephoneNumber;

  private String alternativeTelephoneNumber;

  @Column(name = "address_line_1")
  private String addressLine1;

  @Column(name = "address_line_2")
  private String addressLine2;

  @Column(name = "address_line_3")
  private String addressLine3;

  @Column(name = "address_line_4")
  private String addressLine4;

  private String townOrCity;

  private String postcode;

  private String county;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getAuthId() {
    return authId;
  }

  public void setAuthId(String authId) {
    this.authId = authId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getTelephoneNumber() {
    return telephoneNumber;
  }

  public void setTelephoneNumber(String telephoneNumber) {
    this.telephoneNumber = telephoneNumber;
  }

  public String getAlternativeTelephoneNumber() {
    return alternativeTelephoneNumber;
  }

  public void setAlternativeTelephoneNumber(String alternativeTelephoneNumber) {
    this.alternativeTelephoneNumber = alternativeTelephoneNumber;
  }

  public String getAddressLine1() {
    return addressLine1;
  }

  public void setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  public String getAddressLine2() {
    return addressLine2;
  }

  public void setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  public String getAddressLine3() {
    return addressLine3;
  }

  public void setAddressLine3(String addressLine3) {
    this.addressLine3 = addressLine3;
  }

  public String getAddressLine4() {
    return addressLine4;
  }

  public void setAddressLine4(String addressLine4) {
    this.addressLine4 = addressLine4;
  }

  public String getTownOrCity() {
    return townOrCity;
  }

  public void setTownOrCity(String townOrCity) {
    this.townOrCity = townOrCity;
  }

  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public String getCounty() {
    return county;
  }

  public void setCounty(String county) {
    this.county = county;
  }
}
