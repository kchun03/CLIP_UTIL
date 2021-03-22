import java.io.*;
import java.net.*;

import com.clipsoft.clipreport.oof.OOFDocument;
import com.clipsoft.clipreport.oof.OOFFile;
import com.clipsoft.clipreport.oof.connection.OOFConnectionMemo;

public class oofTest2 {

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		
		OOFDocument oof = OOFDocument.newOOF();
		
		for(int i=0; i<2; i++) {
			OOFFile file = oof.addFile("crf.root", "%root%/report/ea/FreadStat/FreportPassportPaperImg.crf");
			file.addConnectionData("*", "oracle1_jndi");
			file.addField("name1", "value1");
		}
		
		System.out.println(oof);
	}

}
