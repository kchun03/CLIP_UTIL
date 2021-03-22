package clipsoft.getINFO;

import LogType.Each_Log;
import LogType.Total_Log;
import clipsoft.valuesBean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTableCell_main extends valuesBean {
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			//setDir("C:\\Users\\Clip-lee\\Desktop\\test\\test");
			//setDir("C:\\Users\\Clip-lee\\Desktop\\test");
			setDir("C:\\Users\\LEECHANGSUB\\Desktop\\test\\KIAT\\test");
			
			//setDir("C:\\Users\\Clip-lee\\Desktop\\clipsoft\\crf\\crf_20200506\\3_crf_배경이미지출력옵션변경\\");
			
			//setDir("C:\\Users\\Clip-lee\\Desktop\\clipsoft\\이슈\\연구소전달\\2020-04-29 쿼리이상한거붙는거2");
			//setLogType("total");
			setLogType("");
			//setSearchWord("기준0618,적용0618,5칸,칸");
			//setSearchWord("서울일자리플러스센터,서울일자리센터");
			setDeDuplicate("true");
			//setGubun("getConditionalStyleList");
			
			//setGubun("findHyperLinkReport"); //hyperlink 걸린 리포트 찾기
			//setGubun("modify_text"); //문구 변경 ( setSearchWord 필요 setSearchWord("변경전,변경후"); )
			//setGubun("reportControl_prop"); //리포트컨트롤 속성 변경 ( 3번째 > 1번째 )
			//setGubun("imageVisable"); //배경,전경 에서 사용한 이미지 출력옵션
			setGubun("getFontList_OnlyFont"); //전체 서식에서 사용하는 폰트목록 추리기(중복되지 않은 값)
			//setGubun("getFieldType_getReplaceTextInfoList"); //필드타입이 Number면, 데이터속성-데이터변경 처리 빈값->0
		} else {
			setDir(args[0]);
			setLogType(args[1]);
			setSearchWord(args[2]);
			setDeDuplicate(args[3]);
			setGubun(args[4]);
		}

		System.out.println("*************************************************************");
		System.out.println("리포트파일 내에 셀 속성 값을 찾습니다...(" + getGubun() + ")");
		System.out.println("리포트파일 검색 경로 : " + getDir());
		System.out.println("*************************************************************");
		printFiles(new File(getDir()));
		System.out.println("★★★★ 검색 종료 ★★★★");
		for(int k=0; k<getList2().size(); k++){
			System.out.println(getList2().get(k));
		}
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
					GetTableCell g = new GetTableCell();

					if( getGubun().equals("getConditionalStyleList")) {
						setGubun("getConditionalStyleList");
						setControlName(getSearchWord().split(",")[0]);
						g.readReport(f1.getPath());
						
						System.out.println("*************************************************************");
						System.out.println("*************************************************************");
						
						setGubun("setConditionalStyleList");
						setControlName(getSearchWord().split(",")[1]);
						g.readReport(f1.getPath());

						g.writeReport();
					} else {
						g.readReport(f1.getPath());

						g.writeReport();
					}
					

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