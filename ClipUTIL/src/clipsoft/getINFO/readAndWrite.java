package clipsoft.getINFO;

import clipsoft.valuesBean;

import com.clipsoft.clipreport.base.Rexpert4;
import com.clipsoft.clipreport.base.globe.TheReportFile;


public class readAndWrite extends valuesBean
{
	public static void main(String[] args)
		    throws Exception
		  {
			String rptName = "C:\\Users\\Clip-lee\\Desktop\\test\\monthPerformanceReport_rex_convert.crf";
//			setDir("C:\\Users\\Clip-lee\\Desktop\\clipsoft\\이슈\\연구소전달\\2020-04-29 쿼리이상한거붙는거2");
		readReport(rptName);
		  }

  public static void readReport(String rptName) throws Exception {
	  TheReportFile reportFile = null;
    try
    {
      reportFile = Rexpert4.read(rptName);      
    }
    catch (Exception e)
    {
      throw new Exception("can't load report file!! >>" + rptName);
    } finally {
        //writeReport(reportFile, "C:\\Users\\LEECHANGSUB\\Desktop\\미처리\\# 창원삼성병원 서식 작업\\조건스타일TEST\\test2.reb");
    	writeReport(reportFile, rptName.replace(".crf", "_convert.crf"));
    	System.out.println("finish");
    }
  }
  
  public static void writeReport(TheReportFile reportFile2, String rptName) throws Exception {
	  Rexpert4.write(reportFile2, rptName);
  }
  
}
		  