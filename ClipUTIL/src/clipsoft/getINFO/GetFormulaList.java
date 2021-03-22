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
import com.clipsoft.clipreport.base.datas.fields.FieldFormula;
import com.clipsoft.clipreport.base.functions.ConditionalStyle;
import com.clipsoft.clipreport.base.functions.TextInfo;
import com.clipsoft.clipreport.base.globe.Globe;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetFormulaList extends valuesBean
{
  private Map map;
  private List list = new ArrayList();
  
  TheReportFile reportFile = null;

  public void settingGubun(String gubun)
  {
    setGubun(gubun);
  }

  public void readReport(String rptName) throws Exception {
    setRptName(rptName);
    try
    {
      reportFile = Rexpert4.read(getRptName());
      setReportFile(reportFile);
    }
    catch (Exception e)
    {
      throw new Exception("can't load report file!!");
    }
    Report report = reportFile.getGlobe().getMainReport();
    findMainPage(report, "top");    
  }

  private void findMainPage(Report report, String sControlPath) throws Exception
  {
    ReportDesign design = report.getReportDesign();
    MainPage mainpage = design.getMainPage();
    RexObjectList sectionList = mainpage.getSectionList();

    findFieldFormulaList(report, sControlPath + " > MainPage");

    findSectionList(sectionList, sControlPath + " > MainPage"); }

  private void findSectionList(RexObjectList<Section> sectionList, String sControlPath) throws Exception {
    for (int i = 0; i < sectionList.size(); ++i) {
      RexObjectList subSectionList = ((Section)sectionList.get(i)).getSubSectionList();

      for (int j = 0; j < subSectionList.size(); ++j)
      {
        if (((SubSection)subSectionList.get(j)).getObjectID() == 4250) {
          SubSectionSubreport subreportsection = (SubSectionSubreport)subSectionList.get(j);

          Report report = subreportsection.getSubreport();

          findMainPage(report, sControlPath + " > " + ((SubSection)subSectionList.get(j)).getName());
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

  private void findControlList(RexObjectList<Control> controlList, String sControlPath) throws Exception
  {
    for (int m = 0; m < controlList.size(); ++m)
    {
      Control control = (Control)controlList.get(m);

      if (control.getObjectID() == 4650)
      {
        ControlSubreport subreportcontrol = (ControlSubreport)control;
        Report report = subreportcontrol.getSubreport();

        findMainPage(report, sControlPath + " > " + control.getName()); }
    }
  }

  private void findFieldFormulaList(Report report, String sControlPath) throws Exception {
    RexObjectList<FieldFormula> formulalist = report.getReportObjectManager().getFieldFormulaList();
    int cnt = 0;
    for (int i = 0; i < formulalist.size(); ++i) {
      if (((formulalist.get(i)).getScript().contains(getSearchWord())) && (getSearchWord().length() != 0)) {
    	  cnt ++;
    	  if(cnt == 1) System.out.println(getRptName()+"     ★★★★"); 
       /* this.map = new HashMap();
        this.map.put("path", "공식필드 위치 : " + sControlPath);
        this.map.put("fileName", "공식필드 명 : " + (formulalist.get(i)).getName());
        this.list.add(this.map);
        setList(this.list);*/
        //System.out.println("공식필드 명 : " + (formulalist.get(i)).getName());
        //System.out.println("공식필드 내용1 : " + (formulalist.get(i)).getScript() );
        //System.out.println("공식필드 내용2 : " + (formulalist.get(i)).getScript().replaceAll(getSearchWord(), "rexpert.field") );
        //formulalist.get(i).setScript( (formulalist.get(i)).getScript().replaceAll(getSearchWord(), "rexpert.field") );
        System.out.println("공식필드 위치 : " + sControlPath);
        
        System.out.println( "공식필드 명 : " + (formulalist.get(i)).getName());
        System.out.println("공식필드 내용1 : " + (formulalist.get(i)).getScript() );
      }
    }
  }
  public void writeReport(TheReportFile report, String filepath) throws Exception {
	  //Rexpert4.write(report, filepath);
  }
}