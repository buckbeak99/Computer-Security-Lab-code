import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;

import javax.crypto.*;

public class rsa {
  public static void main(String[] args) throws Exception{
    long startTime = System.nanoTime();
    
    KeyPair pair = generateKeyPair();

    Path path = Paths.get("Samplefile.txt");
    String message = Files.readAllLines(path).get(0);

    String cipherText =  encrypt(message, pair.getPublic());

    String decipheredMessage = decrypt(cipherText, pair.getPrivate());

    System.out.println(decipheredMessage);

    String signature = sign("foobar", pair.getPrivate());

    boolean isCorrect = verify("foobar", signature, pair.getPublic());
    System.out.println("Signature correct: " + isCorrect);

    long endTime = System.nanoTime();
    long execTime = endTime - startTime;
    System.out.println("Execution time(ms) : " + execTime/1000000);
    
  }

  private static KeyPair generateKeyPair() throws Exception {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    generator.initialize(2048, new SecureRandom());
    KeyPair pair = generator.generateKeyPair();

    return pair;
  }
//   public static KeyPair getKeyPairFromKeyStore() throws Exception {
//     //Generated with:
//     //  keytool -genkeypair -alias mykey -storepass s3cr3t -keypass s3cr3t -keyalg RSA -keystore keystore.jks

//     InputStream ins = RsaExample.class.getResourceAsStream("/keystore.jks");

//     KeyStore keyStore = KeyStore.getInstance("JCEKS");
//     keyStore.load(ins, "s3cr3t".toCharArray());   //Keystore password
//     KeyStore.PasswordProtection keyPassword =       //Key password
//             new KeyStore.PasswordProtection("s3cr3t".toCharArray());

//     KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("mykey", keyPassword);

//     java.security.cert.Certificate cert = keyStore.getCertificate("mykey");
//     PublicKey publicKey = cert.getPublicKey();
//     PrivateKey privateKey = privateKeyEntry.getPrivateKey();

//     return new KeyPair(publicKey, privateKey);
// }

  public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
    Cipher encryptCipher = Cipher.getInstance("RSA");
    encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

    byte[] cipherText = encryptCipher.doFinal(plainText.getBytes("UTF-8"));

    return Base64.getEncoder().encodeToString(cipherText);
}

  public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
    byte[] bytes = Base64.getDecoder().decode(cipherText);

    Cipher decriptCipher = Cipher.getInstance("RSA");
    decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

    return new String(decriptCipher.doFinal(bytes), "UTF-8");
}

  public static String sign(String plainText, PrivateKey privateKey) throws Exception {
    Signature privateSignature = Signature.getInstance("SHA256withRSA");
    privateSignature.initSign(privateKey);
    privateSignature.update(plainText.getBytes("UTF-8"));

    byte[] signature = privateSignature.sign();

    return Base64.getEncoder().encodeToString(signature);
}

  public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
    Signature publicSignature = Signature.getInstance("SHA256withRSA");
    publicSignature.initVerify(publicKey);
    publicSignature.update(plainText.getBytes("UTF-8"));

    byte[] signatureBytes = Base64.getDecoder().decode(signature);

    return publicSignature.verify(signatureBytes);
}
}
