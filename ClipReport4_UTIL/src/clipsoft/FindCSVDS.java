package clipsoft;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clipsoft.clipreport.base.RexObjectList;
import com.clipsoft.clipreport.base.Rexpert4;
import com.clipsoft.clipreport.base.datas.conections.Connection;
import com.clipsoft.clipreport.base.globe.TheReportFile;


public class FindCSVDS extends valuesBean
{
	private Map map;
	private List list = new ArrayList();
	
	public void readReport(String rptName) throws Exception {	
		setRptName(rptName);
	    TheReportFile reportFile = null;
	    try
	    {
	      reportFile = Rexpert4.read(getRptName());
	    }
	    catch (Exception e)
	    {
	      throw new Exception("can't load report file!!");
	    }
	    RexObjectList<Connection> report = reportFile.getGlobe().getGlobalObjectManager().getConnectionList();
	    for(int i=0; i<report.size(); i++) {
	    	if(report.get(i).getName().contains("CSV") || report.get(i).getName().contains("csv")) {	    		
	    		System.out.println("리포트파일 명 : "+rptName+" \n\t 커넥션명 : "+report.get(i).getName());
	    		map = new HashMap();
				map.put("rptName",  rptName);
				map.put("ConnectionName", "Connection Name : "+report.get(i).getName());
				list.add(map);
	    	}
	    }	    
	}
	
	/*
	 * ================================================================================================================
	 * 하단부는 로그용 
	 * ================================================================================================================	
	*/
	
	public void setWritelog(String logFilePath) throws Exception {
		try {
			for(int i=0; i<list.size(); i++){
				HashMap getMap = new HashMap();
				getMap = (HashMap)list.get(i);

				UpdateFile(logFilePath, getMap.get("rptName").toString());
				UpdateFile(logFilePath, getMap.get("ConnectionName").toString());
			}
		} catch (Exception e) {
			throw new Exception("???");
		}
	}
	
    // 파일 생성  
	private void CreateFile(String FilePath)  
	{  
	    try  
	    {  
	        int nLast = FilePath.lastIndexOf("\\");  
	        String strDir = FilePath.substring(0, nLast);  
	        String strFile = FilePath.substring(nLast+1, FilePath.length());  
	          
	        File dirFolder = new File(strDir);  
	        dirFolder.mkdirs();  
	        File f = new File(dirFolder, strFile);  
	        f.createNewFile();  
	    }  
	    catch (Exception ex)  
	    {  
	        System.out.println(ex.getMessage());  
	    }  
	}  
  
	// 파일 테스트 읽기  
	private String ReadFileText(File file)  
	{  
	    String strText = "";  
	    int nBuffer;  
	    try   
	    {  
	        BufferedReader buffRead = new BufferedReader(new FileReader(file));  
	        while ((nBuffer = buffRead.read()) != -1)  
	        {  
	            strText += (char)nBuffer;  
	        }  
	        buffRead.close();  
	    }  
	    catch (Exception ex)  
	    {  
	        System.out.println(ex.getMessage());  
	    }  
	      
	    return strText;  
	}  
  
	// 파일 수정  
	private void UpdateFile(String FilePath, String Text)  
	{  
		
	    try   
	    {  
	        File f = new File(FilePath);  
	        if (f.exists() == false)  
	        {  
	            // 파일이 존재하지 않는 경우 파일을 만든다.  
	            CreateFile(FilePath);  
	        }  
	          
	        // 파일 읽기  
	        String fileText = ReadFileText(f);  
	        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(f));  
	        Text = fileText + "\r\n" + Text;  
	        // 파일 쓰기  
	        buffWrite.write(Text, 0, Text.length());  
	        // 파일 닫기  
	        buffWrite.flush();  
	        buffWrite.close();  
	    }  
	    catch (Exception ex)  
	    {  
	        System.out.println(ex.getMessage());  
	    }  
	}
}