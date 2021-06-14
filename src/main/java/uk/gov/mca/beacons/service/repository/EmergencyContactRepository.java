package uk.gov.mca.beacons.service.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.model.EmergencyContact;

@Repository
public interface EmergencyContactRepository
  extends JpaRepository<EmergencyContact, UUID> {}
