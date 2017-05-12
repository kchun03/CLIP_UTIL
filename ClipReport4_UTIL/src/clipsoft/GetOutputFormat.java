package clipsoft;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clipsoft.clipreport.base.RexObjectList;
import com.clipsoft.clipreport.base.Rexpert4;
import com.clipsoft.clipreport.base.controls.Control;
import com.clipsoft.clipreport.base.controls.ControlLabel;
import com.clipsoft.clipreport.base.controls.ControlListForEachSeparatedPage;
import com.clipsoft.clipreport.base.controls.ControlSubreport;
import com.clipsoft.clipreport.base.controls.ControlTable;
import com.clipsoft.clipreport.base.controls.Tables.TableCell;
import com.clipsoft.clipreport.base.controls.Tables.TableCellNormal;
import com.clipsoft.clipreport.base.globe.TheReportFile;
import com.clipsoft.clipreport.base.page.BackgroundPage;
import com.clipsoft.clipreport.base.page.ForegroundPage;
import com.clipsoft.clipreport.base.page.MainPage;
import com.clipsoft.clipreport.base.reports.Report;
import com.clipsoft.clipreport.base.reports.ReportDesign;
import com.clipsoft.clipreport.base.sections.Section;
import com.clipsoft.clipreport.base.sections.SectionBackground;
import com.clipsoft.clipreport.base.sections.SubSection;
import com.clipsoft.clipreport.base.sections.SubSectionDefault;
import com.clipsoft.clipreport.base.sections.SubSectionSubreport;
import com.clipsoft.jsEngine.javascript.ast.Label;

public class GetOutputFormat extends valuesBean
{
	private Map map;
	private List list = new ArrayList();
	
	public void readReport(String rptName) throws Exception {
		setRptName(rptName);
	    TheReportFile reportFile = null;
	    try
	    {
	      reportFile = Rexpert4.read(getRptName());
	      //System.out.println("리포트파일 명 : "+rptName);
	    }
	    catch (Exception e)
	    {
	      throw new Exception("can't load report file!!");
	    }
	    Report report = reportFile.getGlobe().getMainReport();
	    findMainPage(report, "top");
	    findBackgroundPage(report, "top");
	    findForegroundPage(report, "top");
	}
	private void findMainPage(Report report, String sControlPath)
	{
	    ReportDesign design = report.getReportDesign();
	    MainPage mainpage = design.getMainPage();
	    RexObjectList sectionList = mainpage.getSectionList();
	    findSectionList(sectionList, sControlPath + " > MainPage");
	}
	private void findSectionList(RexObjectList<Section> sectionList, String sControlPath) {
		for (int i = 0; i < sectionList.size(); ++i) {
			RexObjectList subSectionList = ((Section)sectionList.get(i)).getSubSectionList();
			/*
			 * 섹션 ID
			 * 3650 : 보고서머리글
			 * 3950 : 페이지머리글
			 * 3750 : 데이터머리글
			 * 4050 : 본문
			 * 3800 : 데이터바닥글
			 * 3700 : 보고서바닥글
			 * 4000 : 페이지바닥글
			 */
			for (int j = 0; j < subSectionList.size(); ++j) {
				/*
				 * Sub 섹션 ID
				 * 4200 : 일반섹션
				 * 4250 : 서브리포트섹션
				 */
				if (((SubSection)subSectionList.get(j)).getObjectID() == 4250) {
					SubSectionSubreport subreportsection = (SubSectionSubreport)subSectionList.get(j);
					
					Report report = subreportsection.getSubreport();
					
					findMainPage(report, sControlPath + " > " + ((SubSection)subSectionList.get(j)).getName());
					findBackgroundPage(report, sControlPath + " > " + ((SubSection)subSectionList.get(j)).getName());
					findForegroundPage(report, sControlPath + " > " + ((SubSection)subSectionList.get(j)).getName());
				} else {
					RexObjectList controlPageList = null;
					SubSectionDefault default1 = (SubSectionDefault)subSectionList.get(j);
					controlPageList = default1.getControlListForEachSeparatedPageList();
	
					for (int k = 0; k < controlPageList.size(); ++k) {
						ControlListForEachSeparatedPage pagecontrolList = (ControlListForEachSeparatedPage)controlPageList.get(k);
						RexObjectList controlList = pagecontrolList.getControlList();
						
					if (controlPageList.size() > 1)
						sControlPath = sControlPath + " > Page(" + (k + 1) + ")";
					
					findControlList(controlList, sControlPath + " > " + ((SubSection)subSectionList.get(j)).getName());
					}
				}
			}
		}
	}
	private void findBackgroundPage(Report report, String sControlPath)
	{
	    ReportDesign design = report.getReportDesign();
	    BackgroundPage page = design.getBackgroundPage();

	    SectionBackground section = page.getSectionBackground();
	    RexObjectList subsectionlist = section.getSubSectionList();

	    if (subsectionlist.size() < 1)
	      return;

	    SubSectionDefault default1 = (SubSectionDefault)subsectionlist.get(0);

	    RexObjectList controlPageList = default1.getControlListForEachSeparatedPageList();
	    ControlListForEachSeparatedPage pagecontrolList = (ControlListForEachSeparatedPage)controlPageList.get(0);
	    RexObjectList controlList = pagecontrolList.getControlList();

	    findControlList(controlList, sControlPath + " > BackgroundPage");
	}

	private void findForegroundPage(Report report, String sControlPath)
	{
	    ReportDesign design = report.getReportDesign();
	    ForegroundPage page = design.getForegroundPage();

	    SectionBackground section = page.getSectionBackground();
	    RexObjectList subsectionlist = section.getSubSectionList();

	    if (subsectionlist.size() < 1)
	      return;

	    SubSectionDefault default1 = (SubSectionDefault)subsectionlist.get(0);

	    RexObjectList controlPageList = default1.getControlListForEachSeparatedPageList();
	    ControlListForEachSeparatedPage pagecontrolList = (ControlListForEachSeparatedPage)controlPageList.get(0);
	    RexObjectList controlList = pagecontrolList.getControlList();

	    findControlList(controlList, sControlPath + " > ForegroundPage");
	}
	private void findControlList(RexObjectList<Control> controlList, String sControlPath)
	  {
	    for (int m = 0; m < controlList.size(); ++m)
	    {
	      Control control = (Control)controlList.get(m);
	      /*
	       * ControlID
	       * 4850 : 표
	       * 4600 : 글상자
	       * 4650 : 리포트 컨트롤
	       */
	      if (control.getObjectID() == 4600)
	      {	    	  
	    	  findLabel((ControlLabel)control, sControlPath);
	      }
	      else if (control.getObjectID() == 4850)
	      {
	    	  findTable((ControlTable)control, sControlPath);
	      }
	      else if (control.getObjectID() == 4650)
	      {
	        ControlSubreport subreportcontrol = (ControlSubreport)control;
	        Report report = subreportcontrol.getSubreport();

	        findMainPage(report, sControlPath + " > " + control.getName());
	      }
	    }
	  }
	private void findTable(ControlTable control, String sControlPath) {
		
		for (int i = 0; i < control.getRowCount(); ++i)
	    {
			for (int j = 0; j < control.getColumnCount(); ++j)
		    {
				TableCell tc = control.getTableCell(i, j);
				TableCellNormal tcm = (TableCellNormal)tc;
				if(tcm.getOutputFormat() != null && tcm.getOutputFormat() != "") {
					/*
					System.out.println("========================================");
					System.out.println(sControlPath + " > " + control.getName() + " > " + (i+1) +"번째 행 / "+ (j+1) +"번째 열 (셀)");
					System.out.println("========================================");
					System.out.println("-> "+tcm.getOutputFormat());
					*/
					map = new HashMap();
					map.put("path", sControlPath + " > " + control.getName() + " > " + (i+1) +"번째 행 / "+ (j+1) +"번째 열 (셀)");
					map.put("detailValue", tcm.getOutputFormat());
					list.add(map);
				}
		    }
	    }
		
	}
	private void findLabel(ControlLabel control, String sControlPath) {
		if(control.getOutputFormat() != null && control.getOutputFormat() != "") {
			map = new HashMap();
			map.put("path", sControlPath + " > " + control.getName() +" (라벨)");
			map.put("detailValue", control.getOutputFormat());
			list.add(map);
		}		
	}
	public void setWritelog(String logFilePath) throws Exception {
		/*
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("파일명 : "+rptName);
		System.out.println("============================================");
		*/
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(logFilePath));
			out.write("파일명 : "+getRptName()); out.newLine();
			out.newLine(); 
			for(int i=0; i<list.size(); i++){
				HashMap getMap = new HashMap();
				getMap = (HashMap)list.get(i);
				
				out.newLine();
				out.write("컨트롤 위치 : "+getMap.get("path").toString()); out.newLine();
				out.write("-> "+getMap.get("detailValue").toString()); out.newLine();
			}
			out.close();
		} catch (Exception e) {
			throw new Exception("???");
		}
	}
}