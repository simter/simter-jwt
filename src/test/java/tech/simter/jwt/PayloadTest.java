package tech.simter.jwt;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.Assert.*;

/**
 * @author RJ
 */
public class PayloadTest {
  // isa=2016-01-01, exp=2017-01-01, nbf=2016-06-01
  // {"iss":"RJ","sub":"test","aud":"you","exp":1483200000,"nbf":1464710400,"iat":1451577600,"jti":"ID"}
  private String PAYLOAD_FULL = "eyJpc3MiOiJSSiIsInN1YiI6InRlc3QiLCJhdWQiOiJ5b3UiLCJleHAiOjE0ODMyMDAwMDAs" +
    "Im5iZiI6MTQ2NDcxMDQwMCwiaWF0IjoxNDUxNTc3NjAwLCJqdGkiOiJJRCJ9";
  // {}
  private String PAYLOAD_EMPTY = "e30=";
  // {"publicClaim":"test"}
  private String PAYLOAD_PUBLIC = "eyJwdWJsaWNDbGFpbSI6InRlc3QifQ==";

  @Test
  public void encode_FULL() {
    Payload payload = new Payload();
    payload.jwtId = "ID";
    payload.issuer = "RJ";
    payload.subject = "test";
    payload.audience = "you";
    payload.issuedAt = LocalDateTime.of(2016, 1, 1, 0, 0).atZone(ZoneOffset.systemDefault()).toEpochSecond();
    payload.notBefore = LocalDateTime.of(2016, 6, 1, 0, 0).atZone(ZoneOffset.systemDefault()).toEpochSecond();
    payload.expires = LocalDateTime.of(2017, 1, 1, 0, 0).atZone(ZoneOffset.systemDefault()).toEpochSecond();
    assertEquals(PAYLOAD_FULL, payload.encode());
  }

  @Test
  public void encode_EMPTY() {
    Payload payload = new Payload();
    assertEquals(PAYLOAD_EMPTY, payload.encode());
  }

  @Test
  public void encode_PUBLIC() {
    Payload payload = new Payload();
    payload.add("publicClaim", "test");
    assertEquals(PAYLOAD_PUBLIC, payload.encode());
  }

  @Test
  public void decode_FULL() {
    Payload payload = Payload.decode(PAYLOAD_FULL);
    assertEquals("RJ", payload.issuer);
    assertEquals("test", payload.subject);
    assertEquals("you", payload.audience);
    assertEquals(1483200000L, (long) payload.expires);
    assertEquals(1464710400L, (long) payload.notBefore);
    assertEquals(1451577600L, (long) payload.issuedAt);
    assertEquals("ID", payload.jwtId);
    assertTrue(payload.getData().isEmpty());
  }

  @Test
  public void decode_EMPTY() {
    Payload payload = Payload.decode(PAYLOAD_EMPTY);
    assertNull(payload.issuer);
    assertNull(payload.subject);
    assertNull(payload.audience);
    assertNull(payload.expires);
    assertNull(payload.notBefore);
    assertNull(payload.issuedAt);
    assertNull(payload.jwtId);
    assertTrue(payload.getData().isEmpty());
  }

  @Test
  public void decode_PUBLIC() {
    Payload payload = Payload.decode(PAYLOAD_PUBLIC);
    assertNull(payload.issuer);
    assertNull(payload.subject);
    assertNull(payload.audience);
    assertNull(payload.expires);
    assertNull(payload.notBefore);
    assertNull(payload.issuedAt);
    assertNull(payload.jwtId);
    assertFalse(payload.getData().isEmpty());
    assertEquals(1, payload.getData().size());
    assertEquals("test", payload.get("publicClaim"));
  }
}