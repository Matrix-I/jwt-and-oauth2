package fullstack.api.exception;

import static fullstack.api.exception.ErrorResponseFactory.buildErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
@Order(0)
public class ApplicationControllerAdvice {
  @ExceptionHandler(ApplicationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorInfo> toBadRequestResponse(
      HttpServletRequest req, ApplicationException exc) {
    logSpecificApplicationException(req, exc);
    return buildErrorResponse(req, exc, HttpStatus.BAD_REQUEST);
  }

  private void logSpecificApplicationException(
      HttpServletRequest request, ApplicationException exc) {
    log.info(
        "{} {} returned with a {}: {}",
        request.getMethod(),
        request.getRequestURI(),
        exc.getClass().getSimpleName(),
        exc.getMessage());
  }
}
