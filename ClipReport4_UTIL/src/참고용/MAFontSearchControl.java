package 참고용;

import com.clipsoft.clipreport.base.RexObjectList;
import com.clipsoft.clipreport.base.Rexpert4;
import com.clipsoft.clipreport.base.controls.Control;
import com.clipsoft.clipreport.base.controls.ControlLabel;
import com.clipsoft.clipreport.base.controls.ControlListForEachSeparatedPage;
import com.clipsoft.clipreport.base.controls.ControlSubreport;
import com.clipsoft.clipreport.base.controls.ControlTable;
import com.clipsoft.clipreport.base.controls.Tables.TableCell;
import com.clipsoft.clipreport.base.controls.Tables.TableCellNormal;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MAFontSearchControl
{
  private String sReportFile;
  private String sCheckField;
  private ArrayList alResult = new ArrayList();
  private ArrayList alResultControlValue = new ArrayList();
  private ArrayList alResultControlPath = new ArrayList();
  private ArrayList alResultFontValue = new ArrayList();

  public String getsReportFile()
  {
    return this.sReportFile;
  }

  public void setsReportFile(String sReportFile) {
    this.sReportFile = sReportFile;
  }

  public String getsCheckField() {
    return this.sCheckField;
  }

  public void setsCheckField(String sTargetField) {
    this.sCheckField = sTargetField;
  }

  public ArrayList getalResultControlValue() {
    return this.alResultControlValue;
  }

  public ArrayList getAlResult() {
    return this.alResult;
  }

  public ArrayList getAlResultControlPath() {
    return this.alResultControlPath;
  }

  public ArrayList getalResultFontValue() {
    return this.alResultFontValue;
  }

  public void findControlToExtendFormatOption()
    throws Exception
  {
    TheReportFile reportFile = null;
    try
    {
      reportFile = Rexpert4.read(this.sReportFile);
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

  private void findSectionList(RexObjectList<Section> sectionList, String sControlPath)
  {
    for (int i = 0; i < sectionList.size(); ++i) {
      RexObjectList subSectionList = ((Section)sectionList.get(i)).getSubSectionList();

      for (int j = 0; j < subSectionList.size(); ++j)
      {
        if (((SubSection)subSectionList.get(j)).getObjectID() == 4250)
        {
          SubSectionSubreport subreportsection = (SubSectionSubreport)subSectionList.get(j);

          Report report = subreportsection.getSubreport();

          findMainPage(report, sControlPath + " > " + ((SubSection)subSectionList.get(j)).getName());

          findBackgroundPage(report, sControlPath + " > " + ((SubSection)subSectionList.get(j)).getName());

          findForegroundPage(report, sControlPath + " > " + ((SubSection)subSectionList.get(j)).getName());
        }
        else
        {
          RexObjectList controlPageList = null;
          SubSectionDefault default1 = (SubSectionDefault)subSectionList.get(j);
          controlPageList = default1.getControlListForEachSeparatedPageList();

          for (int k = 0; k < controlPageList.size(); ++k)
          {
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

  private void findControlList(RexObjectList<Control> controlList, String sControlPath)
  {
    for (int m = 0; m < controlList.size(); ++m)
    {
      Control control = (Control)controlList.get(m);

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

  private void findLabel(ControlLabel control, String sControlPath)
  {
    if ((control.getApplyValueField() != null) || (control.getApplyValueText() != null))
    {
      String fontName = control.getTextInfo().getFontName();

      if (getResultValue(fontName)) {
        this.alResult.add(control);
        this.alResultControlValue.add(fontName);
        this.alResultControlPath.add(sControlPath);
      }
    }
  }

  private void findTable(ControlTable control, String sControlPath)
  {
    for (int i = 0; i < control.getRowCount(); ++i)
    {
      for (int j = 0; j < control.getColumnCount(); ++j)
      {
        TableCell tc = control.getTableCell(i, j);

        if (tc.getObjectID() == 5800) {
          TableCellNormal tn = (TableCellNormal)tc;

          if ((tn.getApplyValueField() != null) || (tn.getApplyValueText() != null)) {
            String fontName = tn.getTextInfo().getFontName();
            if (getResultValue(fontName)) {
              this.alResult.add(control);
              this.alResultControlValue.add(fontName);
              this.alResultControlPath.add(sControlPath + " > " + control.getName() + " > cell(" + i + "," + j + ")");
            }
          }
        }
      }
    }
  }

  private boolean getResultValue(String fontName)
  {
    if (this.alResultFontValue.isEmpty()) {
      this.alResultFontValue.add(fontName);
      return true;
    }
    boolean vCheck = false;
    for (int i = 0; i < this.alResultFontValue.size(); ++i)
    {
      if (this.alResultFontValue.get(i).equals(fontName)) {
        vCheck = false;
        break;
      }
      vCheck = true;
    }

    if (vCheck) {
      this.alResultFontValue.add(fontName);
      return true;
    }
    return false;
  }

  public void writeResultFile(String sFile)
  {
    Control control;
    StringBuffer sbStr = new StringBuffer("");
    StringBuffer sbStr_font = new StringBuffer("");

    for (int i = 0; i < this.alResult.size(); ++i) {
      control = (Control)this.alResult.get(i);

      sbStr.append(this.sReportFile + "\t" + 
        control.getName() + "\t" + 
        this.alResultControlValue.get(i).toString() + "\t" + 
        this.alResultControlPath.get(i).toString() + "\r\n");
    }
    for (int i = 0; i < this.alResultFontValue.size(); ++i) {
      control = (Control)this.alResult.get(i);

      sbStr_font.append(this.alResultFontValue.get(i).toString() + "\r\n");
    }

    writeFile(sFile, sbStr_font.toString());
  }

  private void writeFile(String sFile, String sContents)
  {
    PrintWriter writer = null;
    try
    {
      File file = new File(sFile);
      String filename = file.getAbsolutePath();

      FileWriter fw = new FileWriter(filename, true);
      writer = new PrintWriter(new BufferedWriter(fw), true);
    }
    catch (IOException e) {
      System.out.println("Can't open log file : " + e.getMessage());
      return;
    }
    catch (Exception e) {
      System.out.println("Can't open log file : " + e.getMessage());
      return;
    }

    try
    {
      writer.print(sContents);
    }
    catch (Exception e) {
      System.out.println("Can't write log file : " + e.getMessage());
    }

    if (writer != null) {
      try { writer.close(); } catch (Exception e) { }
      writer = null;
    }
  }
}