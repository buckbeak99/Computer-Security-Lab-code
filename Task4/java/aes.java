import java.io.*;
import java.security.*;
import java.util.Scanner;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

public class aes {
  public static void main(String[] args) throws Exception {
    long startTime = System.nanoTime();
    
    SecretKey key = generateKey(128);
    File keyFile = new File("Generated_Key.txt");
    String algorithm = "AES/CFB/PKCS5Padding";
    IvParameterSpec ivParameterSpec = generateIv();
    // String filePath = ClassLoader.getSystemResource("Samplefile.txt").getFile();
    File inputFile = new File("Samplefile.txt");
    //BufferedReader br = new BufferedReader(new FileReader(inputFile));
    // Resource resource = new ClassPathResource("Samplefile.txt");
    // File inputFile = resource.getFile();
    File encryptedFile = new File("encrypted.txt");
   File decryptedFile = new File("decrypted.txt");
    System.out.println("Choose Operation");
    System.out.println("1. Encryption 2. Decryption");
    Scanner sc = new Scanner(System.in);
    int input = sc.nextInt();
    //Scanner ky  = new Scanner(SecretKey);
    if(input == 1){
        //File encryptedFile = new File("encrypted.txt");
        encryptFile(algorithm, key, ivParameterSpec, inputFile, encryptedFile);
        System.out.println(" Congratulations! Your file is encrypted. Please chech your encrypted.txt file.");
        

        long endTime = System.nanoTime();
        long execTime = endTime - startTime;
        System.out.println("Execution time(ms) : " + execTime/1000000);
    }
    else if(input==2){
        // File encryptedFile = new File("encrypted.txt");
        // File decryptedFile = new File("decrypted.txt");
        decryptFile(algorithm, key, ivParameterSpec, encryptedFile, decryptedFile);
        System.out.println(" Congratulations! Your file is Decrypted. Please chech your decrypted.txt file.");
      
  
        long endTime = System.nanoTime();
        long execTime = endTime - startTime;
        System.out.println("Execution time(ms) : " + execTime/1000000);
    }
    //encryptFile(algorithm, key, ivParameterSpec, inputFile, encryptedFile);
    //decryptFile(algorithm, key, ivParameterSpec, encryptedFile, decryptedFile);
    // assertThat(inputFile).hasSameTextualContentAs(decryptedFile);
    // long endTime = System.nanoTime();
    // long execTime = endTime - startTime;
    // System.out.println("Execution time(ms) : " + execTime/1000000);
    
  }

  public static void encryptFile(String algorithm, SecretKey key, IvParameterSpec iv, File inputFile, File outputFile) 
  throws Exception{

    Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
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

  public static void decryptFile(String algorithm, SecretKey key, IvParameterSpec iv, File inputFile, File outputFile) 
  throws Exception{

    Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.DECRYPT_MODE, key, iv);
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