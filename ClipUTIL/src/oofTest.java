import java.io.*;
import java.net.*;

import com.clipsoft.clipreport.oof.OOFDocument;
import com.clipsoft.clipreport.oof.OOFFile;
import com.clipsoft.clipreport.oof.connection.OOFConnectionMemo;

public class oofTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
		

		String rptname = "test";
		String reportDataType = "XML";
		String reportData = "test";
		String reportDataInfo = "CSVDS1|^|CSVDS3|^|CSVDS4";
		String barcodeYN = "N";
		//String paramlist = "a|=|aV|^|b|=|bV|^|c|=|cV"; // �Ķ���� Set (a|=|aV|^|b|=|bV|^|c|=|cV)
		String paramlist = "test1=testValue1&test2=testValue2&test3=testValue3"; // �Ķ���� Set (a|=|aV|^|b|=|bV|^|c|=|cV)
		String v_rexOption = "";
/*
		System.out.println("rptname : "+rptname);
		System.out.println("reportDataType : "+reportDataType);
		System.out.println("reportData : "+reportData);
		System.out.println("reportDataInfo : "+reportDataInfo);
		System.out.println("barcodeYN : "+barcodeYN);
		System.out.println("paramlist : "+paramlist);
		System.out.println("v_rexOption : "+v_rexOption);
*/
		//CSVDS1|=|ds_insreqResult|^|CSVDS3|=|ds_clinicResult|^|CSVDS4|=|ds_inspctReq
		String[] paramlistArr = null;
		
		OOFDocument oof = OOFDocument.newOOF();
		OOFFile file = oof.addFile("crf", rptname+ ".crf");

		OOFConnectionMemo ocm = file.addConnectionMemo("*", reportData);
		if(reportDataType.contains("CSV") ) {
			ocm.addContentParamCSV("*", "utf-8", reportDataInfo.split(",")[0], reportDataInfo.split(",")[1], "|@|", "{%dataset.index%}");	
		} else if(reportDataType.contains("XML") ) {
			paramlistArr = reportDataInfo.split("\\|\\^\\|");
			for(int i=0; i<paramlistArr.length; i++) {
				ocm.addContentParamXML(paramlistArr[i], "utf-8", "gubun/"+paramlistArr[i]+"/Datasets/Dataset/Contents/record");
			}
		}

		//paramlistArr = paramlist.split("\\|\\^\\|");
		paramlistArr = paramlist.split("&");
		//�Ķ���� ����
		for(int i=0; i<paramlistArr.length; i++) {
			//String[] paramlistArr2 = paramlistArr[i].split("\\|\\=\\|");
			String[] paramlistArr2 = paramlistArr[i].split("=");
			if(paramlistArr2.length == 1){
				oof.addField(paramlistArr2[0], "");
			}	else if(paramlistArr2.length != 1){
				oof.addField(paramlistArr2[0], paramlistArr2[1]);
			}		
		}
		System.out.println(oof);
	}

}
