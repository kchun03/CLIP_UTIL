package clipsoft;

import java.io.File;
/*
 * 기능 : CSV DS 찾기(하위 폴더까지 reb 검색)
 * 로그 : 하나의 result.txt 에 CSV DS 결과목록 리스트업.
 */
public class FindCSVDS_main extends valuesBean{
	
	public static void main(String[] args) throws Exception {
		setDir("C:\\Users\\ChangSub.LEE\\Desktop\\레포트 폰트추출 java ver\\170425 findCSVDS_WriteLog");
		
		System.out.println("*************************************************************");
		System.out.println("CSV Connection Name이 \"CSV\"로 시작하는 항목을 찾습니다...");
		System.out.println("리포트파일 검색 경로 : "+getDir());
		System.out.println("*************************************************************");
		
        printFiles(new File(dir));
        System.out.println("success!");
    }

	// 파일을 매개 변수로 받는 메소드
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
                	FindCSVDS fc = new FindCSVDS();
                	fc.readReport(f1.getPath());            
                	fc.setWritelog(dir + "\\result.txt");
                }
    		}
    	} else {
    	// 매개변수가 파일 일 경우
    	}
    }
}
