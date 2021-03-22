import java.io.*;
import java.net.URL;

public class streamTEST {
 public static void main(String[] args) throws IOException {
	 FileOutputStream fos = null;
	 InputStream in = null;
	 try {
		 System.out.println("opening connection");
		 URL url = new URL("http://tech.clipsoft.co.kr/CLIPreport4/export/exportForPDF3.jsp");
		 in = url.openStream();
		 fos = new FileOutputStream(new File("c:\\someFile3.pdf"));

		 System.out.println("reading from resource and writing to file...");
		 int length = -1;
		 byte[] buffer = new byte[1024];// buffer for portion of data from connection
		 while ((length = in.read(buffer)) > -1) {
		     fos.write(buffer, 0, length);
		 }
	 } catch(Exception e) { 
	 		e.printStackTrace(); 
	 } finally {
		 fos.close();
		 in.close();
		 System.out.println("File downloaded");
	 }
	 
	 /* JAVA 1.7
	 URL url = new URL("http://localhost:8081/ClipReport4-275/export/exportForPDF.jsp");
	 try (InputStream in = url.openStream()) {
	    Files.copy(in, Paths.get("c:\\someFile.pdf"), StandardCopyOption.REPLACE_EXISTING);
	 } catch (IOException e) {
	    // handle exception
	 }*/
 }
}
