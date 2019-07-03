package tech.simter.jwt.signer;

import org.junit.jupiter.api.Test;
import tech.simter.jwt.Algorithm;
import tech.simter.jwt.Signer;
import tech.simter.jwt.SignerFactory;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author RJ
 */
class NONETest {
  private static Signer signer = SignerFactory.get(Algorithm.NONE);

  @Test
  void sign_EMPTY() {
    // header={}, payload={}
    byte[] data = "e30=.e30=".getBytes(StandardCharsets.UTF_8);
    byte[] key = "test".getBytes(StandardCharsets.UTF_8);
    byte[] sign = signer.sign(data, key);
    assertEquals("", new String(sign, StandardCharsets.UTF_8));
  }
}