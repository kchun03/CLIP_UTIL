package clipsoft;

import java.io.File;
/*
 * 기능 : 출력양식 찾기 
 */
public class GetOutputFormat_main extends valuesBean
{
  public static void main(String[] args) throws Exception
  {
	setDir("C:\\Users\\ChangSub.LEE\\Desktop\\레포트 폰트추출 java ver\\170421 setWriteLog\\");
	
	System.out.println("*************************************************************");
	System.out.println("리포트파일 내에 출력양식을 찾습니다...");
	System.out.println("리포트파일 검색 경로 : "+getDir());
	System.out.println("*************************************************************");
		
	printFiles(new File(dir));
	System.out.println("success!");
  }
  
  //파일을 매개 변수로 받는 메소드
  public static void printFiles(File f) throws Exception {    	
  	File resultPath = new File(getDir() + "\\result.txt");
  	if(f.exists()) {
  		resultPath.delete();
  	}
  	// 매개변수가 디렉토리 일경우
  	if(f.isDirectory()) {
  		String[] s = f.list();  // 디렉토리 내의 모든 파일의 list를 확인
  		
  		// 파일 목록에서 디렉토리일 경우 
  		//printFiles 메소드 호출 그리고 파일일 경우 단순 출력을 수행
  		for(int i=0; i<s.length;i++) {
  			File f1 = new File(f.getPath() + "/" + s[i]);
  			
  			if(f1.isDirectory()) {
  				printFiles(f1);
              } else if(f1.getName().endsWith("reb") ) {
				GetOutputFormat g = new GetOutputFormat();
				              	
				g.readReport(f1.getPath());
				g.setWritelog(f1.getPath().replace(".reb", "") + ".txt");
              }
  		}
  	} else {
  	// 매개변수가 파일 일 경우
  	}
  }
}