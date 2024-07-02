package fullstack.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ErrorResponseFactory {
  private ErrorResponseFactory() {
    throw new ConsistencyException("Must not be instantiated.");
  }

  static ResponseEntity<ErrorInfo> buildErrorResponse(
      HttpServletRequest request, ApplicationException exc, HttpStatus status) {
    return buildErrorResponse(request, exc, exc.getCode(), status);
  }

  static ResponseEntity<ErrorInfo> buildErrorResponse(
      HttpServletRequest request, ConsistencyException exc, HttpStatus status) {
    return buildErrorResponse(request, exc, exc.getCode(), status);
  }

  static ResponseEntity<ErrorInfo> buildErrorResponse(
      HttpServletRequest request, SystemException exc, HttpStatus status) {
    return buildErrorResponse(request, exc, exc.getCode(), status);
  }

  static ResponseEntity<ErrorInfo> buildErrorResponse(
      HttpServletRequest request, Exception exc, HttpStatus status) {
    return buildErrorResponse(request, exc, null, status);
  }

  private static ResponseEntity<ErrorInfo> buildErrorResponse(
      HttpServletRequest request, Exception exc, String code, HttpStatus status) {

    return ResponseEntity.status(status)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ErrorInfo(request.getRequestURL().toString(), exc.getMessage(), code));
  }
}
