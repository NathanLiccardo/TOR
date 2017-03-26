package Cryptography;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author nathan
 */
public class PublicKeyReader {

  public PublicKey get(String filename){
      try {
          byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
          X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
          KeyFactory kf = KeyFactory.getInstance("RSA");
          return kf.generatePublic(spec);
      } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
          System.err.println(ex);
      }
      return null;
  }  
}
