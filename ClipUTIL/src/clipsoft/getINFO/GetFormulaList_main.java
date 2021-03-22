package clipsoft.getINFO;

import LogType.Each_Log;
import LogType.Total_Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import clipsoft.valuesBean;

@SuppressWarnings("unused")
public class GetFormulaList_main extends valuesBean
{
  public static void main(String[] args)
    throws Exception
  {
    if (args.length < 2)
    {
      //setDir("C:\\Users\\Clip-lee\\Desktop\\test\\");
    	setDir("C:\\Users\\Clip-lee\\Desktop\\test\\test\\");
      
      setLogType("none");//total, each, none
      //setSearchWord("\"rexpert");
      setSearchWord("formula.");
      
      
      setDeDuplicate("false");
    } else {
      setDir(args[0]);
      setLogType(args[1]);
      setSearchWord(args[2]);
      setDeDuplicate(args[3]);
    }
    System.out.println("*************************************************************");
    System.out.println("리포트파일 내에 공식필드 내용을 찾습니다...");
    System.out.println("리포트파일 검색 경로 : " + getDir());
    System.out.println("*************************************************************");
    printFiles(new File(getDir()));
  }

  public static void printFiles(File f) throws Exception
  {
    File resultPath = new File(f.getAbsolutePath() + "\\result.txt");
    String f_date = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());
    
    if (f.exists()) {
      resultPath.delete();
    }

    if (f.isDirectory()) {
      String[] s = f.list();

      for (int i = 0; i < s.length; ++i) {
        setList(null);
        File f1 = new File(f.getPath() + "/" + s[i]);

        if (f1.isDirectory()) {
          printFiles(f1);
        } else if ((f1.getName().endsWith("reb")) || (f1.getName().endsWith("crf"))) {
        	System.out.println(f1.getPath());
          GetFormulaList g = new GetFormulaList();
          g.settingGubun(getGubun());
          g.readReport(f1.getPath());
          //g.writeReport(getReportFile(), getRptName());
          
          if (getLogType().equals("each")) {
              Each_Log t = new Each_Log();
              t.setWritelog(f1.getPath().replace(".reb", "") + ".txt");
            }

            if (getLogType().equals("total")) {
              Total_Log e = new Total_Log();
              e.setWritelog(getDir() + "\\result_"+f_date+".txt");
              System.out.println("로그생성 ★★★★★ : "+getDir() + "\\result_"+f_date+".txt");
            }
        }
        
      }
      System.out.println("★★★★ 검색 종료 ★★★★");
    }
    else {
      System.out.println("찾으시는 디렉토리가 없습니다.");
    }
  }
}