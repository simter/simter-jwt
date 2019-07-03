package tech.simter.jwt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author RJ
 */
class HeaderTest {
  // {"typ":"JWT","alg":"HS256"}
  private String HEADER_JWT_HS256 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9";
  // {"alg":"HS256"}
  private String HEADER_NULL_HS256 = "eyJhbGciOiJIUzI1NiJ9";
  // {"typ":"JWT"}
  private String HEADER_JWT_NULL = "eyJ0eXAiOiJKV1QifQ==";
  // {}
  private String HEADER_NULL_NULL = "e30=";

  @Test
  void encode_JWT_HS256() {
    assertEquals(HEADER_JWT_HS256, new Header(Header.DEFAULT_TYPE, Algorithm.HS256).encode());
  }

  @Test
  void encode_NULL_HS256() {
    assertEquals(HEADER_NULL_HS256, new Header(Algorithm.HS256).encode());
  }

  @Test
  void encode_JWT_NULL() {
    assertEquals(HEADER_JWT_NULL, new Header(Header.DEFAULT_TYPE, null).encode());
  }

  @Test
  void encode_NULL_NULL() {
    assertEquals(HEADER_NULL_NULL, new Header().encode());
  }

  @Test
  void decode_JWT_HS256() {
    Header header = Header.decode(HEADER_JWT_HS256);
    assertEquals(header, Header.DEFAULT);
    assertEquals(Header.DEFAULT_TYPE, header.type);
    assertEquals(Algorithm.HS256, header.algorithm);
  }

  @Test
  void decode_NULL_HS256() {
    Header header = Header.decode(HEADER_NULL_HS256);
    assertEquals(Algorithm.HS256, header.algorithm);
    assertNull(header.type);
  }

  @Test
  void decode_JWT_NULL() {
    Header header = Header.decode(HEADER_JWT_NULL);
    assertEquals(Header.DEFAULT_TYPE, header.type);
    assertNull(header.algorithm);
  }

  @Test
  void decode_NULL_NULL() {
    Header header = Header.decode(HEADER_NULL_NULL);
    assertNull(header.algorithm);
    assertNull(header.type);
  }
}