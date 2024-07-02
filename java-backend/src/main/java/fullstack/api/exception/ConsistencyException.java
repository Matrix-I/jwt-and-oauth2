package fullstack.api.exception;

import java.io.Serial;

public class ConsistencyException extends RuntimeException {
  @Serial private static final long serialVersionUID = -1712915898257230879L;

  private final String code;

  public ConsistencyException(String message) {
    this(message, null, null);
  }

  public ConsistencyException(String message, Throwable cause) {
    this(message, null, cause);
  }

  public ConsistencyException(String message, String code) {
    this(message, code, null);
  }

  public ConsistencyException(String message, String code, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
