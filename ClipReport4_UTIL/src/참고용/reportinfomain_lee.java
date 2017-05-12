package 참고용;

import java.io.File;
import java.io.PrintStream;
import jregex.util.io.PathPattern;
/*
 * 기능 : 출력양식 찾기 
 */
public class reportinfomain_lee
{
  public static void main(String[] args)
  {
	String sFile = "C:\\Users\\ChangSub.LEE\\Desktop\\레포트 폰트추출 java ver\\170421 setWriteLog\\*.reb";
    String sOutput = "C:\\Users\\ChangSub.LEE\\Desktop\\레포트 폰트추출 java ver\\170421 setWriteLog\\";
    int sNum = 0;
/*
    if (args.length < 2) {
      System.out.println("======================================================");
      System.out.println("usage : reportinfomain reportfile fieldname outputfile");
      System.out.println("======================================================");

      //sFile = "C:\\code\\reportinfo\\Rexpert*.reb";
      sFile = "C:\\Users\\ChangSub.LEE\\Desktop\\test*.reb";
      sOutput = "C:\\Users\\ChangSub.LEE\\Desktop\\result.txt";
      System.out.println(1);
      return;
    }
*/
    /*
    sFile = args[0];
    sOutput = args[1];
    */
    try
    {
      PathPattern pp = new PathPattern(sFile);
      File[] fileList = pp.files();
      System.out.println("리포트파일 갯수 ::: "+pp.files().length);      
      for (int i = 0; i < fileList.length; ++i) {
        File file = fileList[i];
        if (file.isFile()) {
        	reportinfoControl ric = new reportinfoControl();
        	
        	ric.setRptName(file.getPath());
        	ric.readReport();
        	ric.setWritelog(sOutput + "\\" + file.getName().replace(".reb", "") + ".txt");
        	System.out.println("create result log : "+sOutput + "\\" + file.getName().replace(".reb", "") + ".txt");
          } else {
        	  file.isDirectory();
        }
      }
    }
    catch (Exception e)
    {
      System.out.println("error : " + e.getMessage());
    }
  }
}