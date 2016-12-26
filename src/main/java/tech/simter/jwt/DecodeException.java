package tech.simter.jwt;

/**
 * The Data Decode Exception.
 *
 * @author RJ
 * @since JDK1.8
 */
public class DecodeException extends RuntimeException {
  public DecodeException() {
    super();
  }

  public DecodeException(String message) {
    super(message);
  }

  public DecodeException(String message, Throwable cause) {
    super(message, cause);
  }

  public DecodeException(Throwable cause) {
    super(cause);
  }
}