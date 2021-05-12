package uk.gov.mca.beacons.service.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "resource was not found")
public class ResourceNotFoundException extends RuntimeException {

}