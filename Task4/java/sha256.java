import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class sha256 {
  
  public static void main(String[] args) {
    long startTime = System.nanoTime();
    File file = new File("Samplefile.txt");
  
    String shadigest="";
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      // byte[] hash = digest.digest(base.getBytes("UTF-8"));
      try {
        shadigest = getFileChecksum(digest, file);
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println(shadigest);

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    long endTime = System.nanoTime();
    long execTime = endTime - startTime;
    System.out.println("Execution time(ms) : " + execTime/1000000);
  }

  private static String getFileChecksum(MessageDigest digest, File file) throws IOException{
    FileInputStream f = new FileInputStream(file);

    byte[] byteArray = new byte[1024];
    int bytesCount = 0;

    while((bytesCount = f.read(byteArray)) != -1){
      digest.update(byteArray, 0, bytesCount);
    };
    f.close();
    byte[] bytes = digest.digest();
    StringBuilder sb = new StringBuilder();
    for(int i=0; i< bytes.length ;i++)
    {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
    }
     
    return sb.toString();
  }

}
