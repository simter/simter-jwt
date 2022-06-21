package tech.simter.jwt;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT Payload.
 *
 * @author RJ
 * @since JDK1.8
 */
public final class Payload extends Base {
  /**
   * The JWT issuer
   */
  public String issuer;
  /**
   * The JWT subject.
   */
  public String subject;
  /**
   * The JWT audience.
   */
  public String audience;
  /**
   * The JWT expiration time. Identifies the expiration time on or after which the JWT MUST NOT be accepted for processing.
   * The value is a Unix epoch. It is the seconds between 1970-01-01T00:00:00Z and it.
   */
  public Long expires;
  /**
   * The JWT Not Before. Identifies the time before which the JWT MUST NOT be accepted for processing.
   * The value is a Unix epoch. It is the seconds between 1970-01-01T00:00:00Z and it.
   */
  public Long notBefore;
  /**
   * The JWT Issued At. Identifies the time at which the JWT was issued.
   * The value is a Unix epoch. It is the seconds between 1970-01-01T00:00:00Z and it.
   */
  public Long issuedAt;
  /**
   * The JWT ID. Provides a unique identifier for the JWT.
   */
  public String jwtId;

  private Map<String, String> data; // public/private claims

  public Payload() {
    super();
  }

  /**
   * Get the public/private claims.
   *
   * @return the public/private claims
   */
  public Map<String, String> getData() {
    if (data == null) data = new HashMap<>();
    return data;
  }

  /**
   * Add one public/private claim.
   *
   * @param key   the claim's key
   * @param value the claim's value
   * @return this instance
   */
  public Payload add(String key, String value) {
    getData().put(key, value);
    return this;
  }

  /**
   * Remove one public/private claim.
   *
   * @param key the claim's key
   * @return the remove value
   */
  public String remove(String key) {
    return getData().remove(key);
  }

  /**
   * Get the public/private claim value.
   *
   * @param key the claim's key
   * @return the claim's value
   */
  public String get(String key) {
    return getData().get(key);
  }

  @Override
  JsonObject toJson() {
    JsonObjectBuilder builder = Json.createObjectBuilder();
    if (issuer != null) builder.add("iss", issuer);
    if (subject != null) builder.add("sub", subject);
    if (audience != null) builder.add("aud", audience);
    if (expires != null) builder.add("exp", expires);
    if (notBefore != null) builder.add("nbf", notBefore);
    if (issuedAt != null) builder.add("iat", issuedAt);
    if (jwtId != null) builder.add("jti", jwtId);

    if (data != null) data.forEach(builder::add);
    return builder.build();
  }

  /**
   * Decode the base64-string to Payload.
   *
   * @param base64 the base64-string to be decoded
   * @return the payload instance
   */
  public static Payload decode(String base64) {
    JsonObject json = decodeToJson(base64);
    Payload payload = new Payload();
    json.keySet().forEach(key -> {
      switch (key) {
        case "iss" -> payload.issuer = json.getString(key);
        case "sub" -> payload.subject = json.getString(key);
        case "aud" -> payload.audience = json.getString(key);
        case "exp" -> payload.expires = Long.valueOf(json.get(key).toString());
        case "nbf" -> payload.notBefore = Long.valueOf(json.get(key).toString());
        case "iat" -> payload.issuedAt = Long.valueOf(json.get(key).toString());
        case "jti" -> payload.jwtId = json.getString(key);
        default -> payload.add(key, json.getString(key));
      }
    });
    return payload;
  }
}