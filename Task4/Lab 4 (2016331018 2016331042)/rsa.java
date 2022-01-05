import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;
import java.util.Scanner;

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

    String signature = sign(message, pair.getPrivate());

    boolean isCorrect = verify(message, signature, pair.getPublic());
    System.out.println("Signature correct: " + isCorrect);

    long endTime = System.nanoTime();
    long execTime = endTime - startTime;
    System.out.println("Execution time(ms) : " + execTime/1000000);
    
  }

  private static KeyPair generateKeyPair() throws Exception {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    Scanner input = new Scanner(System.in);
    System.out.println("Enter key length: ");
    // int n = 4096;
    int n = input.nextInt();
    while(n!=1024 && n!=2048 && n!=4096){
      System.out.println("Invalid Input. Try again: ");
      n = input.nextInt();
    }
    generator.initialize(n, new SecureRandom());
    KeyPair pair = generator.generateKeyPair();
    FileWriter myWriter = new FileWriter("rsakey.txt");
    myWriter.write(pair.toString());
    myWriter.close();
    input.close();
    // System.out.println(pair.getPrivate().toString());
    // System.out.println(pair.getPublic().toString());
    return pair;
  }
  
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
