package tech.simter.jwt;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * The base class for JWT Header and Payload
 *
 * @author RJ
 * @since JDK1.8
 */
abstract class Base {
  /**
   * Convert this instance to JsonObject.
   *
   * @return this instance JsonObject
   */
  abstract JsonObject toJson();

  private String base64;

  /**
   * Encode this instance to base64url-string with 'UTF-8' charset.
   * <p>attention: This method's result would be cache by first invoked.
   * So each time you invoked, you get the same result.</p>
   *
   * @return The encoded base64url-string.
   */
  public String encode() {
    // System.out.println(toJson());
    if (base64 == null)
      base64 = new String(
        Base64.getUrlEncoder().encode(toJson().toString().getBytes(StandardCharsets.UTF_8)),
        StandardCharsets.UTF_8
      );
    return base64;
  }

  static JsonObject decodeToJson(String base64) {
    return Json.createReader(new ByteArrayInputStream(Base64.getUrlDecoder().decode(base64)))
      .readObject();
  }
}