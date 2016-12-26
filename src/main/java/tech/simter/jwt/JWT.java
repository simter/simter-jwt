package tech.simter.jwt;

import java.nio.charset.StandardCharsets;

/**
 * JWT
 *
 * @author RJ
 * @since JDK1.8
 */
public final class JWT {
  public Header header;
  public Payload payload;

  public JWT(Header header, Payload payload) {
    this.header = header;
    this.payload = payload;
  }

  /**
   * Encode this instance to JWT standard format.
   *
   * @param secret the signature key
   * @return [header].[payload].[signature]
   */
  public String generate(byte[] secret) {
    String hp = header.encode() + "." + payload.encode();
    return hp + "." + new String(
      SignerFactory.get(header.algorithm).sign(hp.getBytes(StandardCharsets.UTF_8), secret),
      StandardCharsets.UTF_8
    );
  }

  /**
   * Encode this instance to JWT standard format.
   *
   * @param secret the signature key
   * @return [header].[payload].[signature]
   */
  public String generate(String secret) {
    return generate(secret == null ? null : secret.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Verify whether the JWT is valid. If valid return the decoded instance.
   *
   * @param jwt    jwt
   * @param secret signature key
   * @return the decoded jwt instance
   * @throws DecodeException if jwt is illegal
   */
  public static JWT verify(String jwt, String secret) {
    return verify(jwt, secret == null ? null : secret.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Validate whether the JWT is valid. If valid return the decoded instance.
   * <p>Follow <code>rfc7519#section-7</code> to validating a JWT</p>
   *
   * @param jwt    jwt
   * @param secret signature key
   * @return the decoded jwt instance
   * @throws DecodeException if jwt is illegal.
   */
  public static JWT verify(String jwt, byte[] secret) {
    // 1. verify that the jwt contains at least one period ('.') character
    if (jwt.indexOf(".") <= 0)
      throw new DecodeException("Illegal jwt: no '.' character inner");

    // verify and verify header, payload
    String[] hps = jwt.split("\\.");
    Header header = Header.decode(hps[0]);

    // a unsecured JWT is not supported this time
    if (header.algorithm == Algorithm.NONE && hps.length == 2)
      throw new DecodeException("'none' algorithm is not support.");

    // secured jwt
    if (hps.length != 3)
      throw new DecodeException("Illegal jwt: not [header].[payload].[signature] format");
    Payload payload = Payload.decode(hps[1]);

    // verify the signature
    byte[] signature = SignerFactory.get(header.algorithm)
      .sign((hps[0] + "." + hps[1]).getBytes(StandardCharsets.UTF_8), secret);
    if (!hps[2].equals(new String(signature, StandardCharsets.UTF_8))) {
      throw new DecodeException("Illegal jwt: signature not match");
    } else {
      return new JWT(header, payload);
    }
  }
}