package petstore.common.advice.exception;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super("Record not found: " + message);
  }
}
