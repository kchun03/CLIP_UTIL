package clipsoft;

public class valuesBean {
	static String dir;
	static String rptName;
	
	protected static String getDir() {
		return dir;
	}
	protected static void setDir(String pDir) {
		valuesBean.dir = pDir;
	}
	
	protected static String getRptName() {
		return rptName;
	}
	protected static void setRptName(String rptName) {
		valuesBean.rptName = rptName;
	}
}
