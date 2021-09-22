package uk.gov.mca.beacons.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.dto.DeleteLegacyBeaconRequestDTO;
import uk.gov.mca.beacons.api.dto.LegacyBeaconDTO;
import uk.gov.mca.beacons.api.dto.NoteDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.api.mappers.LegacyBeaconMapper;
import uk.gov.mca.beacons.api.mappers.NoteMapper;
import uk.gov.mca.beacons.api.services.DeleteLegacyBeaconService;
import uk.gov.mca.beacons.api.services.LegacyBeaconService;
import uk.gov.mca.beacons.api.services.NoteService;

@RestController
@RequestMapping("/legacy-beacon")
@Tag(name = "Legacy Beacon Controller")
public class LegacyBeaconController {

    private final LegacyBeaconService legacyBeaconService;
    private final LegacyBeaconMapper legacyBeaconMapper;
    private final DeleteLegacyBeaconService deleteLegacyBeaconService;
    private final NoteService noteService;
    private final NoteMapper noteMapper;

    @Autowired
    public LegacyBeaconController(
            LegacyBeaconService legacyBeaconService,
            LegacyBeaconMapper legacyBeaconMapper,
            DeleteLegacyBeaconService deleteLegacyBeaconService,
            NoteService noteService,
            NoteMapper noteMapper
    ) {
        this.legacyBeaconService = legacyBeaconService;
        this.legacyBeaconMapper = legacyBeaconMapper;
        this.deleteLegacyBeaconService = deleteLegacyBeaconService;
        this.noteService = noteService;
        this.noteMapper = noteMapper;
    }

    @GetMapping(value = "/{uuid}")
    public WrapperDTO<LegacyBeaconDTO> findById(@PathVariable("uuid") UUID id) {
        final var legacyBeacon = legacyBeaconService
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        return legacyBeaconMapper.toWrapperDTO(legacyBeacon);
    }

    @PostMapping(value = "/{uuid}/claim")
    public ResponseEntity<Void> claimLegacyBeacon(@PathVariable("uuid") UUID id) {
        LegacyBeacon legacyBeaconToClaim = legacyBeaconService.findById(id).orElseThrow(ResourceNotFoundException::new);

        legacyBeaconService.claim(legacyBeaconToClaim);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{uuid}/notes")
    public WrapperDTO<List<NoteDTO>> getNotesByLegacyBeaconId(
            @PathVariable("uuid") UUID id
    ) {
        final List<Note> foundNotes = noteService.findAllByLegacyBeaconId(id);

        return noteMapper.toOrderedWrapperDTO(foundNotes);
    }

    @PatchMapping(value = "/{uuid}/delete")
    public ResponseEntity<Void> delete(
            @PathVariable("uuid") UUID id,
            @RequestBody @Valid DeleteLegacyBeaconRequestDTO requestDTO
    ) {
        deleteLegacyBeaconService.delete(requestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
