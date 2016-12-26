package tech.simter.jwt.signer;

import tech.simter.jwt.Signer;

import java.nio.charset.StandardCharsets;

/**
 * The None Signature.
 *
 * @author RJ
 * @since JDK1.8
 */
public final class NONE implements Signer {
  private static final byte[] EMPTY = "".getBytes(StandardCharsets.UTF_8);

  @Override
  public byte[] sign(byte[] data, byte[] key) {
    // https://tools.ietf.org/html/rfc7519#section-6 Unsecured JWTs:
    // An Unsecured JWT is a JWS using the "alg" Header Parameter value "none"
    // and with the empty string for its JWS Signature value.
    return EMPTY;
  }
}