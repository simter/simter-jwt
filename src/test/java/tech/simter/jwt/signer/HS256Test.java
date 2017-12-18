package tech.simter.jwt.signer;

import org.junit.Test;
import tech.simter.jwt.Algorithm;
import tech.simter.jwt.Signer;
import tech.simter.jwt.SignerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

/**
 * @author RJ
 */
public class HS256Test {
  static Signer signer = SignerFactory.get(Algorithm.HS256);

  @Test
  public void sign_EMPTY() {
    // header={}, payload={}
    byte[] data = "e30=.e30=".getBytes(StandardCharsets.UTF_8);
    byte[] key = "test".getBytes(StandardCharsets.UTF_8);
    byte[] sign = signer.sign(data, key);
    assertEquals("8Yr6vjx78DFGAaXJ_j6Wa5kq82wa1AFyZzyMXtOs91Y=", new String(sign, StandardCharsets.UTF_8));
  }

  @Test
  public void string2sha256() throws NoSuchAlgorithmException {
    String text = "simter-demo";
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    Charset utf8 = StandardCharsets.UTF_8;
    byte[] hash = digest.digest(text.getBytes(utf8));

    // byte to hex
    System.out.println(bytesToHex(hash).toUpperCase());
  }

  private static String bytesToHex(byte[] bytes) {
    StringBuilder result = new StringBuilder();
    for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
    return result.toString();
  }

  private static String bytesToHex2(byte[] bytes) {
    StringBuilder hexString = new StringBuilder();
    for (byte b : bytes) {
      String hex = Integer.toHexString(0xff & b);
      if (hex.length() == 1) hexString.append('0');
      hexString.append(hex);
    }
    return hexString.toString();
  }
}