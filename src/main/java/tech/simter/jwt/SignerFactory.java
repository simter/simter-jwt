package tech.simter.jwt;

import tech.simter.jwt.signer.HS256;
import tech.simter.jwt.signer.NONE;

import java.util.HashMap;
import java.util.Map;

/**
 * The factory to get specific algorithm signer.
 *
 * @author RJ
 * @since JDK1.8
 */
public class SignerFactory {
  public static Signer get(Algorithm algorithm) {
    if (algorithm == null || algorithm.equals(Algorithm.NONE)) {
      return getInstance(Algorithm.NONE);
    }

    switch (algorithm) {
      case HS256:
        return new HS256();
      default:
        throw new SignException("The algorithm '" + algorithm.name() + "' is not support.");
    }
  }

  private static Map<Algorithm, Signer> signers = new HashMap<>();

  private static Signer getInstance(Algorithm algorithm) {
    if (signers.containsKey(algorithm)) {
      return signers.get(algorithm);
    } else {
      Signer signer;
      switch (algorithm) {
        case NONE:
          signer = new NONE();
          break;
        case HS256:
          signer = new HS256();
          break;
        default:
          throw new SignException("The algorithm '" + algorithm.name() + "' is not support.");
      }
      signers.put(algorithm, signer);
      return signer;
    }
  }
}