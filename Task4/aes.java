import java.io.*;
import java.security.*;
import java.util.Scanner;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

public class aes {
  public static void main(String[] args) throws Exception {
    int n,alg;
    String algorithm;
    Scanner input = new Scanner(System.in);
    long startTime = System.nanoTime();
    System.out.println("Bit Key Length (128/192/256): ");
    n = input.nextInt();
    while(n!=128 && n!=192 && n!=256){
      System.out.println("Invalid Input. Try again: ");
      n = input.nextInt();
    }
    SecretKey key = generateKey(n);
    System.out.println("(1)ECB or (2)CFB? Answer with 1 or 2: ");
    alg = input.nextInt();
    while(alg!=1 && alg!=2){
      System.out.println("Invalid Input. Try again: ");
      alg = input.nextInt();
    }
    if(n==1){
      algorithm = "AES/ECB/PKCS5Padding";
    }else algorithm = "AES/CFB/PKCS5Padding";
    // if(n==1){
    //   algorithm = "AES/CFB/PKCS5Padding";
    // }
    // if(n==2){
    //   algorithm = "AES/CFB/PKCS5Padding";
    // }
      IvParameterSpec ivParameterSpec = generateIv();
    // String filePath = ClassLoader.getSystemResource("Samplefile.txt").getFile();
    File inputFile = new File("Samplefile.txt");
    // Resource resource = new ClassPathResource("Samplefile.txt");
    // File inputFile = resource.getFile();
    File encryptedFile = new File("encrypted.txt");
    File decryptedFile = new File("decrypted.txt");
    encryptFile(algorithm, key, ivParameterSpec, inputFile, encryptedFile,alg);
    decryptFile(algorithm, key, ivParameterSpec, encryptedFile, decryptedFile,alg);
    // assertThat(inputFile).hasSameTextualContentAs(decryptedFile);
    long endTime = System.nanoTime();
    long execTime = endTime - startTime;
    input.close();
    System.out.println("Execution time(ms) : " + execTime/1000000);
    
  }

  public static void encryptFile(String algorithm, SecretKey key, IvParameterSpec iv, File inputFile, File outputFile, int alg) 
  throws Exception{

    Cipher cipher = Cipher.getInstance(algorithm);
    if(alg==1){
      cipher.init(Cipher.ENCRYPT_MODE, key);
     }else cipher.init(Cipher.ENCRYPT_MODE, key, iv);
    FileInputStream inputStream = new FileInputStream(inputFile);
    FileOutputStream outputStream = new FileOutputStream(outputFile);
    byte[] buffer = new byte[64];
    int bytesRead;
    while ((bytesRead = inputStream.read(buffer)) != -1) {
        byte[] output = cipher.update(buffer, 0, bytesRead);
        if (output != null) {
            outputStream.write(output);
        }
    }
    byte[] outputBytes = cipher.doFinal();
    if (outputBytes != null) {
        outputStream.write(outputBytes);
    }
    inputStream.close();
    outputStream.close();
  }

  public static void decryptFile(String algorithm, SecretKey key, IvParameterSpec iv, File inputFile, File outputFile, int alg) 
  throws Exception{

    Cipher cipher = Cipher.getInstance(algorithm);
    if(alg==1){
      cipher.init(Cipher.DECRYPT_MODE, key);
     }else cipher.init(Cipher.DECRYPT_MODE, key, iv);
    FileInputStream inputStream = new FileInputStream(inputFile);
    FileOutputStream outputStream = new FileOutputStream(outputFile);
    byte[] buffer = new byte[64];
    int bytesRead;
    while ((bytesRead = inputStream.read(buffer)) != -1) {
        byte[] output = cipher.update(buffer, 0, bytesRead);
        if (output != null) {
            outputStream.write(output);
        }
    }
    byte[] outputBytes = cipher.doFinal();
    if (outputBytes != null) {
        outputStream.write(outputBytes);
    }
    inputStream.close();
    outputStream.close();
  }

  // Initialization vector (IV) for CFB
  public static IvParameterSpec generateIv() {
    byte[] iv = new byte[16];
    new SecureRandom().nextBytes(iv);
    return new IvParameterSpec(iv);
  }

  // Key generator
  public static SecretKey generateKey(int n) throws Exception {
  KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
  keyGenerator.init(n);
  SecretKey key = keyGenerator.generateKey();
  return key;
  }
}