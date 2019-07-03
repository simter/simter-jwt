package tech.simter.jwt;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author RJ
 */
class JWTTest {
  private String SECRET_TEST = "secret";
  // {"typ":"JWT","alg":"HS256"}
  private String HEADER_ONE = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9";

  // isa=2016-01-01, exp=2017-01-01, nbf=2016-06-01
  // {"iss":"RJ","sub":"test","aud":"you","exp":1483200000,"nbf":1464710400,"iat":1451577600,"jti":"ID"}
  private String PAYLOAD_ONE = "eyJpc3MiOiJSSiIsInN1YiI6InRlc3QiLCJhdWQiOiJ5b3UiLCJleHAiOjE0ODMyMDAwMDAs" +
    "Im5iZiI6MTQ2NDcxMDQwMCwiaWF0IjoxNDUxNTc3NjAwLCJqdGkiOiJJRCJ9";

  // HEADER_ONE + PAYLOAD_ONE
  private String SIGNATURE_ONE = "GfIxwOp-DuyZ3A0bkgd7KgjeYIZPskZOTEFmYEguIv8=";

  @Test
  void encode_ONE() {
    Header header = new Header(Header.DEFAULT_TYPE, Algorithm.HS256);

    Payload payload = new Payload();
    payload.jwtId = "ID";
    payload.issuer = "RJ";
    payload.subject = "test";
    payload.audience = "you";
    payload.issuedAt = LocalDateTime.of(2016, 1, 1, 0, 0).atZone(ZoneOffset.systemDefault()).toEpochSecond();
    payload.notBefore = LocalDateTime.of(2016, 6, 1, 0, 0).atZone(ZoneOffset.systemDefault()).toEpochSecond();
    payload.expires = LocalDateTime.of(2017, 1, 1, 0, 0).atZone(ZoneOffset.systemDefault()).toEpochSecond();

    assertEquals(HEADER_ONE + "." + PAYLOAD_ONE + "." + SIGNATURE_ONE,
      new JWT(header, payload).generate(SECRET_TEST));

    assertEquals(HEADER_ONE + "." + PAYLOAD_ONE + "." + SIGNATURE_ONE,
      new JWT(header, payload).generate(SECRET_TEST.getBytes(StandardCharsets.UTF_8)));
  }

  @Test
  void encode_UNSEURED() {
    // {"alg":"NONE"} eyJhbGciOiJOT05FIn0=
    Header header = new Header(Algorithm.NONE);
    // {} e30=
    Payload payload = new Payload();

    String unsecured = "eyJhbGciOiJOT05FIn0=.e30=.";
    assertEquals(unsecured, new JWT(header, payload).generate(""));
    assertEquals(unsecured, new JWT(header, payload).generate((byte[]) null));
    assertEquals(unsecured, new JWT(header, payload).generate((String) null));
  }

  @Test
  void decode_ONE() {
    JWT jwt = JWT.verify(HEADER_ONE + "." + PAYLOAD_ONE + "." + SIGNATURE_ONE, SECRET_TEST);
    assertEquals(HEADER_ONE, jwt.header.encode());
    assertEquals(PAYLOAD_ONE, jwt.payload.encode());

    jwt = JWT.verify(HEADER_ONE + "." + PAYLOAD_ONE + "." + SIGNATURE_ONE,
      SECRET_TEST.getBytes(StandardCharsets.UTF_8));
    assertEquals(HEADER_ONE, jwt.header.encode());
    assertEquals(PAYLOAD_ONE, jwt.payload.encode());
  }
}