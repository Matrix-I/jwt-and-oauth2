package fullstack.api.exception;

public abstract class SystemException extends RuntimeException {
  private final String code;

  protected SystemException(String message) {
    this(message, null, null);
  }

  protected SystemException(String message, Throwable cause) {
    this(message, null, cause);
  }

  protected SystemException(String message, String code) {
    this(message, code, null);
  }

  protected SystemException(String message, String code, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
