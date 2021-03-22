package clipsoft.getINFO;

import clipsoft.valuesBean;

import com.clipsoft.clipreport.base.RexObjectList;
import com.clipsoft.clipreport.base.Rexpert4;
import com.clipsoft.clipreport.base.controls.Control;
import com.clipsoft.clipreport.base.controls.ControlLabel;
import com.clipsoft.clipreport.base.controls.ControlListForEachSeparatedPage;
import com.clipsoft.clipreport.base.controls.ControlSubreport;
import com.clipsoft.clipreport.base.controls.ControlTable;
import com.clipsoft.clipreport.base.controls.Tables.TableCell;
import com.clipsoft.clipreport.base.controls.Tables.TableCellNormal;
import com.clipsoft.clipreport.base.datas.DataSet;
import com.clipsoft.clipreport.base.datas.conections.Connection;
import com.clipsoft.clipreport.base.enums.UsingConnectionType;
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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetConNM extends valuesBean
{
  private Map map;
  private List pList = new ArrayList();

  public void readReport(String rptName)
    throws Exception
  {
    setRptName(rptName);
    TheReportFile reportFile = null;
    try
    {
      reportFile = Rexpert4.read(getRptName());
    }
    catch (Exception e)
    {
      throw new Exception("can't load report file!!");
    }
    RexObjectList<Connection> report = reportFile.getGlobe().getGlobalObjectManager().getConnectionList();
    
    RexObjectList<DataSet> reportDS = reportFile.getGlobe().getGlobalObjectManager().getDataSetList();
    //System.out.println(rptName.substring(rptName.lastIndexOf("\\")+1, rptName.length())+" \n >> 리포트 파일내에 Connection 총 갯수 : "+report.size());
    if(getGubun().equals("searchConName")){
    	int cnt = 0;
        for (int i = 0; i < report.size(); ++i) {
        	
          if ( ( (report.get(i)).getName().contains(getSearchWord())) && (getSearchWord().length() != 0)) {
        	  cnt ++;
        	  if(cnt == 1) System.out.println(getRptName()+"     ★★★★");
            this.map = new HashMap();
            this.map.put("ConnectionName", "Connection Name : " + (report.get(i)).getName());
            this.pList.add(this.map);
            setList(this.pList);
            
            //Connection Name 변경
            //((Connection)report.get(i)).setName("test");
          }    
        }
    } else if(getGubun().equals("searchQuery")){
    	for (int i = 0; i < reportDS.size(); ++i) {
    		String querySTR = reportDS.get(i).getDataSetItemNormal().getDataAccessMethodSQL().getQueryString();
    		String scriptType = reportDS.get(i).getDataSetItemNormal().getDataAccessMethodSQL().getScriptType().name();
    		
    		//if(scriptType.equals("JavaScript") && querySTR.contains("'{?")) {
			//if(!(scriptType.equals("JavaScript") ) &&  querySTR.contains("{?")) {
    		if(querySTR.contains("{?")) {
    			
        		System.out.println(getRptName() + " >>> " + reportDS.get(i).getName());
        		String querySTR2 = "";
        		
        		//System.out.println("변경 전 : "+querySTR);
        		
        		String[] gubun = querySTR.split("\\{\\?");
        		querySTR2 = gubun[0];
    			for(int j = 1; j < gubun.length; j++) {
    				//System.out.println(gubun.length + " ::: "  + j + " ::: " +gubun[j].split("\\}")[1]);
    				querySTR2 += gubun[j].split("\\}",2)[1];
    			}
    			
        		//System.out.println("변경 후 : "+querySTR2);
        		
        		//쿼리변경
        		reportDS.get(i).getDataSetItemNormal().getDataAccessMethodSQL().setQueryString(querySTR2);
    		}
    		
          }
    } else  if(getGubun().equals("searchConType")){
    	int cnt = 0;
        for (int i = 0; i < report.size(); ++i) {
          if (  report.get(i).getUsingConnectionType().toString().equals(getSearchWord()) ) {
        	  cnt ++;
        	  if(cnt == 1) System.out.println(getRptName()+"     ★★★★");
         /*   this.map = new HashMap();
            this.map.put("ConnectionName", "Connection Name : " + (report.get(i)).getName());
            this.pList.add(this.map);
            setList(this.pList);*/
        	  System.out.println(report.get(i).getName() + " / " + report.get(i).getUsingConnectionType());
            
          }    
        }
    }
    
    //변경된 내용 저장
    //Rexpert4.write(reportFile, getRptName().replace(".crf", "_convert.crf"));
    //Rexpert4.write(reportFile, getRptName());
  }
}