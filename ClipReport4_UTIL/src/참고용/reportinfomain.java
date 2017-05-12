package 참고용;

import java.io.File;
import java.io.PrintStream;

import jregex.util.io.PathPattern;

public class reportinfomain
{
  public static void main(String[] args)
  {
    //String sFile = "C:\\Rexpert1.reb";
	  String sFile = "C:\\Users\\ChangSub.LEE\\Desktop\\레포트 폰트추출 java ver\\test.reb";
	  String sOutput = "C:\\Users\\ChangSub.LEE\\Desktop\\레포트 폰트추출 java ver\\";
    int sNum = 0;

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

    sFile = args[0];
    sOutput = args[1];
    System.out.println(2);
    try
    {
    	System.out.println(3);
      PathPattern pp = new PathPattern(sFile);
      File[] fileList = pp.files();

      for (int i = 0; i < fileList.length; ++i) {
        File file = fileList[i];
        if (file.isFile()) {
        	System.out.println(4);
          MAFontSearchControl ibkctrl = new MAFontSearchControl();

          ibkctrl.setsReportFile(file.getPath());
          ibkctrl.findControlToExtendFormatOption();

          ibkctrl.writeResultFile(sOutput + "\\" + file.getName().replace(".reb", "") + ".txt");

          System.out.println("write file => " + sOutput + " (" + file.getPath() + ")"); } else {
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