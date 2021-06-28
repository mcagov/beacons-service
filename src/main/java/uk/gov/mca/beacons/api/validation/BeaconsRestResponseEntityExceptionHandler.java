package uk.gov.mca.beacons.api.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uk.gov.mca.beacons.api.services.GetValidationErrorResponseService;

@ControllerAdvice
public class BeaconsRestResponseEntityExceptionHandler
  extends ResponseEntityExceptionHandler {

  private final GetValidationErrorResponseService validationService;

  @Autowired
  public BeaconsRestResponseEntityExceptionHandler(
    GetValidationErrorResponseService validationService
  ) {
    this.validationService = validationService;
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex,
    HttpHeaders headers,
    HttpStatus status,
    WebRequest request
  ) {
    final var errorResponseDTO = validationService.fromBindingErrors(
      ex.getBindingResult()
    );
    return super.handleExceptionInternal(
      ex,
      errorResponseDTO,
      headers,
      status,
      request
    );
  }
}
