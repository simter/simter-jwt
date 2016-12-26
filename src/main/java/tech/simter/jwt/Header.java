package tech.simter.jwt;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.ByteArrayInputStream;
import java.util.Base64;

/**
 * JWT Header.
 *
 * @author RJ
 * @since JDK1.8
 */
public final class Header extends Base {
  public static final String DEFAULT_TYPE = "JWT";
  public static final Algorithm DEFAULT_ALGORITHM = Algorithm.HS256;
  public static final Header DEFAULT = new Header(DEFAULT_TYPE, DEFAULT_ALGORITHM);

  /**
   * The JWT Header type
   */
  public String type;

  /**
   * The JWT Header algorithm
   */
  public Algorithm algorithm;

  public Header() {
    super();
  }

  public Header(Algorithm algorithm) {
    super();
    this.algorithm = algorithm;
  }

  public Header(String type, Algorithm algorithm) {
    super();
    this.type = type;
    this.algorithm = algorithm;
  }

  @Override
  public JsonObject toJson() {
    JsonObjectBuilder builder = Json.createObjectBuilder();
    if (type != null && !type.isEmpty()) builder.add("typ", type);
    if (algorithm != null) builder.add("alg", algorithm.name());
    return builder.build();
  }

  /**
   * Decode the base64-string to Header.
   *
   * @param header the base64-string to be decoded
   * @return the header instance
   * @throws DecodeException if header is not a standard jwt header
   */
  public static Header decode(String header) {
    // verify to json with JAX-RS standard
    JsonObject json;
    try {
      json = Json.createReader(new ByteArrayInputStream(Base64.getUrlDecoder().decode(header))).readObject();
    } catch (JsonException e) {
      throw new DecodeException("Failed convert to JsonObject", e);
    } catch (IllegalArgumentException e) {
      throw new DecodeException("The header is not in valid Base64 scheme", e);
    }

    // validate the typ parameter
    String type = json.containsKey("typ") ? json.getString("typ") : null;
    if (type != null && !DEFAULT_TYPE.equals(type))
      throw new DecodeException("The header type '" + type + "' is not support.");

    // validate the alg parameter
    String algorithm = json.containsKey("alg") ? json.getString("alg") : null;
    if (algorithm != null && !Algorithm.HS256.name().equals(algorithm))
      throw new DecodeException("The header algorithm '" + algorithm + "' is not support.");

    // construct a header instance
    if (DEFAULT_TYPE.equals(type) && Algorithm.HS256.name().equals(algorithm)) {
      // optimise the default standard header to always the same instance
      return Header.DEFAULT;
    } else {
      // create a whole new instance
      Header h = new Header();
      h.type = type;
      h.algorithm = (algorithm != null ? Algorithm.valueOf(algorithm) : null);
      return h;
    }
  }
}