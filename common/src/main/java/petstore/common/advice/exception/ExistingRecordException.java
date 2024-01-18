package petstore.common.advice.exception;

public class ExistingRecordException extends RuntimeException {

  public ExistingRecordException(String message) {
    super("Already existing record: " + message);
  }
}
