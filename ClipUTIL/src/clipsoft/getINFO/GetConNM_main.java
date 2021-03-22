package clipsoft.getINFO;

import LogType.Each_Log;
import LogType.Total_Log;
import clipsoft.valuesBean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.clipsoft.clipreport.base.enums.UsingConnectionType;

public class GetConNM_main extends valuesBean
{
  public static void main(String[] args)
    throws Exception
  {
    if (args.length < 2) {
    	setDir("C:\\Users\\Clip-lee\\Desktop\\test\\");
    	//setDir("C:\\Users\\Clip-lee\\Desktop\\clipsoft\\crf\\crf_20200506\\1_crf_파라미터제거\\");
    	setLogType("total");
    	setSearchWord(""+UsingConnectionType.CSVFile);
    	setDeDuplicate("true");
    	//setGubun("searchConName"); //컨넥션 이름 찾기
    	//setGubun("searchQuery"); // 변환후 "?필드명" 문자열 포함된 쿼리 찾기 (엑셀의 '쿼리이상'에 해당)
    	
    	setGubun("searchConType"); // 컨넥션 타입 찾기
    	//setSearchWord(""+UsingConnectionType.CSVFile);
    }
    else {
      setDir(args[0]);
      setLogType(args[1]);
      setSearchWord(args[2]);
      setDeDuplicate(args[3]);
    }

    System.out.println("*************************************************************");
    //System.out.println("CSV Connection Name이 \"CSV\"로 시작하는 항목을 찾습니다...");
    System.out.println("CSV Connection Name이  "+getSearchWord()+" 로 시작하는 항목을 찾습니다...");
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
          GetConNM fc = new GetConNM();
          fc.readReport(f1.getPath());
          
          if (getLogType().equals("each")) {
            Each_Log t = new Each_Log();
            t.setWritelog(f1.getPath().replace(".reb", "") + ".txt");
          }

          if (getLogType().equals("total")) {
            Total_Log e = new Total_Log();
            //e.setWritelog(getDir() + "\\result_"+f_date+".txt");
			//System.out.println("로그생성 ★★★★★ : "+getDir() + "\\result_"+f_date+".txt");
          }
        }
      }
      //System.out.println("★★★★ 검색 종료 ::: "+s.length +" 개 중  "+getCnt() + " 개 발견");
      System.out.println("★★★★ 검색 종료 ★★★★");
    }
    else {
      System.out.println("찾으시는 디렉토리가 없습니다.");
    }
  }
}