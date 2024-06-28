package fullstack.api.exception;

public abstract class ApplicationException extends Exception {
  private final String code;

  protected ApplicationException(String message) {
    this(message, null, null);
  }

  protected ApplicationException(String message, Throwable cause) {
    this(message, null, cause);
  }

  protected ApplicationException(String message, String code) {
    this(message, code, null);
  }

  protected ApplicationException(String message, String code, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
