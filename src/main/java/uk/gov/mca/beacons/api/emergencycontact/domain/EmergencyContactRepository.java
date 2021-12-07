package uk.gov.mca.beacons.api.emergencycontact.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyContactRepository
  extends JpaRepository<EmergencyContact, EmergencyContactId> {}
