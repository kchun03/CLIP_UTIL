import java.io.*;
import java.net.URL;

public class URLCallback {
 public static void main(String[] args) throws IOException {
	 URL url = null;
     String requestMsg = "";
     String line="";
     String requesturl = "http://localhost:28080/ClipReport5-59/export/exportForPDF.jsp";
     BufferedReader input = null;
     try {
        // Request
        url = new URL(requesturl);

        // Response
        input = new BufferedReader(new InputStreamReader(url.openStream()));

        while((line=input.readLine()) != null){
           requestMsg += line;
        }
     } catch (Exception e) {
        e.printStackTrace();
     }
     System.out.println(requestMsg);
 }
}
