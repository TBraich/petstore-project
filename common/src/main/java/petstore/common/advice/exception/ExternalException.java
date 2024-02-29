package petstore.common.advice.exception;

public class ExternalException extends RuntimeException {
  public ExternalException(String message) {
    super("External Error: " + message);
  }
}
