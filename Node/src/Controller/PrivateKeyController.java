package Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 *
 * @author nathan
 */
public class PrivateKeyController {
    
  public PrivateKey get(String filename){
      try {
          byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
          PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
          KeyFactory kf = KeyFactory.getInstance("RSA");
          return kf.generatePrivate(spec);
      } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
          System.err.println(ex);
      }
      return null;
  }
}
