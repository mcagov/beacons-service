package uk.gov.mca.beacons.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.dto.BeaconDTO;
import uk.gov.mca.beacons.api.dto.BeaconsSearchResultDTO;
import uk.gov.mca.beacons.api.dto.DeleteBeaconRequestDTO;
import uk.gov.mca.beacons.api.dto.NoteDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.exceptions.InvalidBeaconDeleteException;
import uk.gov.mca.beacons.api.exceptions.InvalidPatchException;
import uk.gov.mca.beacons.api.mappers.BeaconMapper;
import uk.gov.mca.beacons.api.mappers.BeaconsResponseFactory;
import uk.gov.mca.beacons.api.mappers.NoteMapper;
import uk.gov.mca.beacons.api.services.BeaconsService;
import uk.gov.mca.beacons.api.services.DeleteBeaconService;
import uk.gov.mca.beacons.api.services.NoteService;

@RestController
@RequestMapping("/beacons")
@Tag(name = "Beacons Controller")
public class BeaconsController {

  private final BeaconsService beaconsService;
  private final BeaconsResponseFactory responseFactory;
  private final BeaconMapper beaconMapper;
  private final DeleteBeaconService deleteBeaconService;
  private final NoteService noteService;
  private final NoteMapper noteMapper;

  @Autowired
  public BeaconsController(
    BeaconsService beaconsService,
    BeaconsResponseFactory responseFactory,
    BeaconMapper beaconMapper,
    DeleteBeaconService deleteBeaconService,
    NoteService noteService,
    NoteMapper noteMapper
  ) {
    this.beaconsService = beaconsService;
    this.responseFactory = responseFactory;
    this.beaconMapper = beaconMapper;
    this.deleteBeaconService = deleteBeaconService;
    this.noteService = noteService;
    this.noteMapper = noteMapper;
  }

  @GetMapping
  public BeaconsSearchResultDTO findAll() {
    final var results = new BeaconsSearchResultDTO();
    results.setBeacons(beaconsService.findAll());
    return results;
  }

  @GetMapping(value = "/{uuid}")
  public WrapperDTO<BeaconDTO> findByUuid(@PathVariable("uuid") UUID uuid) {
    final var beacon = beaconsService.find(uuid);
    return responseFactory.buildDTO(beacon);
  }

  @GetMapping(value = "/{uuid}/notes")
  public WrapperDTO<List<NoteDTO>> getNotesByBeaconId(
    @PathVariable("uuid") UUID beaconId
  ) {
    final List<Note> foundNotes = noteService.findAllByBeaconId(beaconId);

    return noteMapper.toOrderedWrapperDTO(foundNotes);
  }

  @PatchMapping(value = "/{uuid}")
  @PreAuthorize("hasAuthority('APPROLE_UPDATE_RECORDS')")
  public ResponseEntity<Void> update(
    @PathVariable("uuid") UUID uuid,
    @RequestBody WrapperDTO<BeaconDTO> dto
  ) {
    final var update = beaconMapper.fromDTO(dto.getData());
    if (!uuid.equals(dto.getData().getId())) throw new InvalidPatchException();

    beaconsService.update(uuid, update);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PatchMapping(value = "{uuid}/delete")
  public ResponseEntity<Void> delete(
    @PathVariable("uuid") UUID id,
    @RequestBody @Valid DeleteBeaconRequestDTO requestDTO
  ) {
    if (
      !id.equals(requestDTO.getBeaconId())
    ) throw new InvalidBeaconDeleteException();

    deleteBeaconService.delete(requestDTO);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
