package clipsoft.getINFO;

import clipsoft.valuesBean;

import com.clipsoft.clipreport.base.RexObjectList;
import com.clipsoft.clipreport.base.Rexpert4;
import com.clipsoft.clipreport.base.controls.Control;
import com.clipsoft.clipreport.base.controls.ControlLabel;
import com.clipsoft.clipreport.base.controls.ControlLine;
import com.clipsoft.clipreport.base.controls.ControlListForEachSeparatedPage;
import com.clipsoft.clipreport.base.controls.ControlSubreport;
import com.clipsoft.clipreport.base.controls.ControlTable;
import com.clipsoft.clipreport.base.controls.Tables.TableCell;
import com.clipsoft.clipreport.base.controls.Tables.TableCellNormal;
import com.clipsoft.clipreport.base.enums.GrowMethod;
import com.clipsoft.clipreport.base.functions.Condition;
import com.clipsoft.clipreport.base.functions.ConditionalStyle;
import com.clipsoft.clipreport.base.functions.FieldLink;
import com.clipsoft.clipreport.base.functions.StylePropertySetting;
import com.clipsoft.clipreport.base.functions.TextInfo;
import com.clipsoft.clipreport.base.globe.Globe;
import com.clipsoft.clipreport.base.globe.TheReportFile;
import com.clipsoft.clipreport.base.page.BackgroundPage;
import com.clipsoft.clipreport.base.page.ForegroundPage;
import com.clipsoft.clipreport.base.page.MainPage;
import com.clipsoft.clipreport.base.reports.Report;
import com.clipsoft.clipreport.base.reports.ReportData;
import com.clipsoft.clipreport.base.reports.ReportDesign;
import com.clipsoft.clipreport.base.reports.ReportObjectManager;
import com.clipsoft.clipreport.base.sections.Section;
import com.clipsoft.clipreport.base.sections.SectionBackground;
import com.clipsoft.clipreport.base.sections.SubSection;
import com.clipsoft.clipreport.base.sections.SubSectionDefault;
import com.clipsoft.clipreport.base.sections.SubSectionSubreport;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GetSectionInfo extends valuesBean {
	private Map map;
	private List list = new ArrayList();
	private int cnt_ConditionalStyleList = 0;
	private TheReportFile reportFile = null;
	private RexObjectList<ConditionalStyle> standard_ConditionalStyle = null;

	public void readReport(String rptName) throws Exception {
		setRptName(rptName);
		// TheReportFile reportFile = null;
		reportFile = null;
		try {
			reportFile = Rexpert4.read(getRptName());
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("can't load report file!! >>" + getRptName());
			//throw new Exception("can't load report file!! >>" + getRptName());
		}
		Report report = reportFile.getGlobe().getMainReport();
		findMainPage(report, "top");
	}

	private void findMainPage(Report report, String sControlPath) {
		ReportDesign design = report.getReportDesign();
		MainPage mainpage = design.getMainPage();
		RexObjectList sectionList = mainpage.getSectionList();
		findSectionList(sectionList, sControlPath + " > MainPage");
	}

	private void findSectionList(RexObjectList<Section> sectionList, String sControlPath) {
		for (int i = 0; i < sectionList.size(); ++i) {
			RexObjectList subSectionList = ((Section) sectionList.get(i)).getSubSectionList();
			//System.out.println(((Section) sectionList.get(i)).getObjectID());
			
			if(getGubun().equals("getRepeatSection_condition")){
				if(((Section) sectionList.get(i)).getObjectID() == 4050){
					//System.out.println("본문의 서브섹션 갯수 : "+subSectionList.size());
					if(subSectionList.size() == 1){
						for (int j = 0; j < subSectionList.size(); ++j) {
							for(int k = 0; k < ((SubSection) subSectionList.get(j)).getConditionalStyleList().size(); k++) {
								Condition cd = ((SubSection) subSectionList.get(j)).getConditionalStyleList().get(k).getCondition();
								//System.out.println( cd.getConditionField().getName() + " " + cd.getCompareOperator().name() + " " + cd.getCompareValue1Text());
								
								//"recordnumber or recordCount = 0"
								if(( cd.getConditionField().getName().equals("RecordNumber") || cd.getConditionField().getName().equals("RecordCount") )&& cd.getCompareOperator().name().equals("Equal") && cd.getCompareValue1Text().equals("0")){
									//System.out.println( cd.getConditionField().getName() + " " + cd.getCompareOperator().name() + " " + cd.getCompareValue1Text());
									RexObjectList<StylePropertySetting> cdStyle = ((SubSection) subSectionList.get(j)).getConditionalStyleList().get(k).getStylePropertySettingList();
									for(int l = 0; l < cdStyle.size(); l++) {
										
										//"보이기:false"
										if(cdStyle.get(l).getPropertyID() == 4800 && cdStyle.get(l).getPropertyValueText().equals("0")){
											//System.out.println(l +" : "+cdStyle.get(l).getPropertyValueText());
											//System.out.println(l +" : "+cdStyle.get(l).getPropertyID());
											System.out.println(getRptName() + "    ★★★★★★★★★★");
										}
										
									}
								}
								
								
							}
						}
					}
					
				}
			} else {
				for (int j = 0; j < subSectionList.size(); ++j) {
					if (((SubSection) subSectionList.get(j)).getObjectID() == 4250) {
						SubSectionSubreport subreportsection = (SubSectionSubreport) subSectionList.get(j);
						Report report = subreportsection.getSubreport();

						findMainPage(report, sControlPath + " > " + ((SubSection) subSectionList.get(j)).getName());
					} else {
						RexObjectList controlPageList = null;
						SubSectionDefault default1 = (SubSectionDefault) subSectionList.get(j);
						controlPageList = default1.getControlListForEachSeparatedPageList();

						for (int k = 0; k < controlPageList.size(); ++k) {
							ControlListForEachSeparatedPage pagecontrolList = (ControlListForEachSeparatedPage) controlPageList
									.get(k);
							RexObjectList controlList = pagecontrolList.getControlList();

							if (controlPageList.size() > 1)
								sControlPath = sControlPath + " > Page(" + (k + 1) + ")";

							System.out.println(((SubSection) subSectionList.get(j)).getName() + " >>>> " + ((SubSection) subSectionList.get(j)).getObjectID());
						}
					}
				}
			}
			
			
		}
	}


	public void writeReport() throws Exception {
		//Rexpert4.write(reportFile, getRptName().replace(".crf", "_convert2.crf"));
		Rexpert4.write(reportFile, getRptName());
	}
}