package uk.gov.mca.beacons.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

  private final GetValidationErrorResponseService validationService;

  @Autowired
  public ValidationHandler(
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
    final var errorResponse = validationService.fromBindingErrors(
      ex.getBindingResult()
    );
    return super.handleExceptionInternal(
      ex,
      errorResponse,
      headers,
      status,
      request
    );
  }
}
