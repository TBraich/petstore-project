package petstore.common.advice.exception;

public class SystemException extends RuntimeException {
  public SystemException(String message) {
    super("Internal Error: " + message);
  }
}
