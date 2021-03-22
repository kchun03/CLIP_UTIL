package clipsoft;

import java.util.ArrayList;
import java.util.List;

import com.clipsoft.clipreport.base.RexObjectList;
import com.clipsoft.clipreport.base.functions.ConditionalStyle;
import com.clipsoft.clipreport.base.functions.ReplaceTextInfo;
import com.clipsoft.clipreport.base.globe.TheReportFile;

public class valuesBean {
	static String dir;
	static String rptName;
	static String gubun;
	static String logType;
	static List list = null;
	static List list2 = new ArrayList();
	protected static List getList2() {
		return list2;
	}

	protected static void setList2(List list2) {
		valuesBean.list2 = list2;
	}

	static String deDuplicate;
	static String searchWord;
	static String searchWord2;
	static int cnt;
	static TheReportFile reportFile;
	static String controlName;
	RexObjectList<ConditionalStyle> standard_ConditionalStyle;

	protected static String getControlName() {
		return controlName;
	}

	protected static void setControlName(String controlName) {
		valuesBean.controlName = controlName;
	}

	protected RexObjectList<ConditionalStyle> getStandard_ConditionalStyle() {
		return standard_ConditionalStyle;
	}

	protected void setStandard_ConditionalStyle(RexObjectList<ConditionalStyle> standard_ConditionalStyle) {
		this.standard_ConditionalStyle = standard_ConditionalStyle;
	}

	protected static TheReportFile getReportFile() {
		return reportFile;
	}

	protected static void setReportFile(TheReportFile reportFile) {
		valuesBean.reportFile = reportFile;
	}

	protected static int getCnt() {
		return cnt;
	}

	protected static void setCnt(int cnt) {
		valuesBean.cnt += cnt;
	}

	protected static String getSearchWord2() {
		return searchWord2;
	}

	protected static void setSearchWord2(String setsearchWord2) {
		searchWord2 = setsearchWord2;
	}

	protected static String getSearchWord() {
		return searchWord;
	}

	protected static void setSearchWord(String setsearchWord) {
		searchWord = setsearchWord;
	}

	protected static String getDeDuplicate() {
		return deDuplicate;
	}

	protected static void setDeDuplicate(String setdeDuplicate) {
		deDuplicate = setdeDuplicate;
	}

	protected static List getList() {
		return list;
	}

	protected static void setList(List set_list) {
		list = set_list;
	}

	protected static String getGubun() {
		return gubun;
	}

	protected static void setGubun(String setgubun) {
		gubun = setgubun;
	}

	protected static String getDir() {
		return dir;
	}

	protected static void setDir(String setdir) {
		dir = setdir;
	}

	protected static String getRptName() {
		return rptName;
	}

	protected static void setRptName(String setrptName) {
		rptName = setrptName;
	}

	protected static String getLogType() {
		return logType;
	}

	protected static void setLogType(String setlogType) {
		logType = setlogType;
	}
}