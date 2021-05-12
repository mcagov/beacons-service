package uk.gov.mca.beacons.service.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "resource identifier does not match model identifier")
public class InvalidPatchException extends RuntimeException {

}