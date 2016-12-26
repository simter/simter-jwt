package tech.simter.jwt;

/**
 * The Data Signed Exception.
 *
 * @author RJ
 * @since JDK1.8
 */
public class SignException extends RuntimeException {
  public SignException() {
    super();
  }

  public SignException(String message) {
    super(message);
  }

  public SignException(String message, Throwable cause) {
    super(message, cause);
  }

  public SignException(Throwable cause) {
    super(cause);
  }
}