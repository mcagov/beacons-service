package uk.gov.mca.beacons.api.domain;

import uk.gov.mca.beacons.api.jpa.entities.NoteEntity;

/**
 * An enumeration of the note types used by {@link NoteEntity}
 */
public enum NoteType {
  INCIDENT,
  GENERAL,
  RECORD_HISTORY,
}
