package clipsoft.getINFO;

import LogType.Each_Log;
import LogType.Total_Log;
import clipsoft.valuesBean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetSectionInfo_main extends valuesBean {
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			setDir("C:\\Users\\Clip-lee\\Desktop\\test");
			setLogType("None");
			setSearchWord("");
			setDeDuplicate("true");
			setGubun("getRepeatSection_condition"); //본문서브섹션이 1개면서, 조건스타일 "recordnumber or recordCount = 0" / "보이기:false" 인것 찾기
			
			
		} else {
			setDir(args[0]);
			setLogType(args[1]);
			setSearchWord(args[2]);
			setDeDuplicate(args[3]);
			setGubun(args[4]);
		}

		System.out.println("*************************************************************");
		System.out.println("리포트파일 내에 섹션 값을 찾습니다...(" + getGubun() + ")");
		System.out.println("리포트파일 검색 경로 : " + getDir());
		System.out.println("*************************************************************");
		printFiles(new File(getDir()));
		System.out.println("★★★★ 검색 종료 ★★★★");
	}

	public static void printFiles(File f) throws Exception {
		File resultPath = new File(f.getAbsolutePath() + "\\result.txt");
		String f_date = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());
		
		if (f.exists()) {
			resultPath.delete();
		}

		if (f.isDirectory()) {
			String[] s = f.list();
			int cnt = 0;
			for (int i = 0; i < s.length; ++i) {
				setList(null);
				File f1 = new File(f.getPath() + "/" + s[i]);

				if (f1.isDirectory()) {
					printFiles(f1);
				} else if ((f1.getName().endsWith("reb"))  || (f1.getName().endsWith("crf"))  ) {
					System.out.println("리포트 파일명 : " + f1.getPath());
					/*System.out.println("*************************************************************");
					System.out.println("리포트 파일명 : " + s[i]);
					System.out.println("*************************************************************");*/
					GetSectionInfo g = new GetSectionInfo();

						g.readReport(f1.getPath());
						//g.writeReport();
					

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
		} else {
			System.out.println("찾으시는 디렉토리가 없습니다.");
		}
	}
}