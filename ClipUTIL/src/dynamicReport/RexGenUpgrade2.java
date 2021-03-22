package dynamicReport;

import com.clipsoft.clipreport.generator.CreateReport;
import com.clipsoft.clipreport.generator.*;
import com.clipsoft.org.json.simple.*;
import com.clipsoft.org.json.simple.parser.JSONParser;
import com.clipsoft.org.json.simple.parser.ParseException;

public class RexGenUpgrade2 {
/*
 * 사이트 : 아시아나IDT
 * 3.0 > 업그레이드로 인한 동적레포트 작업
 * 기존 파일명 : gnt1100.jsp
 */
	public static  String init() throws ParseException {
		//CreateReport report = new CreateReport("c:\\test\\gen_dummy2.crf");
		CreateReport report = new CreateReport();
		
		String jsonStr = "[{\"no\":\"7247\", \"aircraft\":\"763\", \"schedule\":[{\"flight\":\"371 SZX\", \"graph_start\":\"11\", \"graph_width\":\"49\",\"time\":\"00:55/05:00\"}]},{\"no\":\"8247\", \"aircraft\":\"863\", \"schedule\":[{\"flight\":\"471 SZX\", \"graph_start\":\"21\", \"graph_width\":\"59\",\"time\":\"02:55/07:00\"}]}]";
		if(jsonStr.isEmpty()) {
			SubSection detail = report.findSubSection("본문", 0);
			detail.getSubSection().setVisible(true);
		} else {
			JSONParser parser = new JSONParser();
			JSONArray root =(JSONArray)parser.parse(jsonStr);
			
			for(int i=0; i<root.size(); i++) { //본문 건수
				SubSection detail_new = new SubSection();
				detail_new.setHeight(130);
				detail_new.getSubSection().setName("s_0_0");
				detail_new.getSubSection().setSubSectionKeepTogether(true);
				
				System.out.println("=============  " + i + "  =============");
				JSONObject rootField =(JSONObject)root.get(i);
				JSONArray scheduleList = (JSONArray)rootField.get("schedule");	
				System.out.println(rootField.get("no"));
				System.out.println(rootField.get("aircraft"));
				
				//sectionName, controlName, value, x, y, wdith, height, linestyle, linewidth, linecolor, fontName, fontSize, Labelbackgroundcolor 
				setControl_Label(detail_new, "dID_2_0_1", rootField.get("no").toString(), 0, 35, 108, 40, 0, 100, "000000", "굴림체", 9, "");
				setControl_Label(detail_new, "dID_2_0_1", rootField.get("aircraft").toString(), 30, 70, 70, 50, 0, 100, "000000", "굴림체", 8, "");
				
				for(int j=0; j<scheduleList.size(); j++){	
					System.out.println(j);
					JSONObject scheduleListField = (JSONObject)scheduleList.get(j);	
					System.out.println(scheduleListField.get("flight"));
					System.out.println(scheduleListField.get("graph_start"));
					System.out.println(scheduleListField.get("graph_width"));
					System.out.println(scheduleListField.get("time"));
										
					int graph_start = 108 + (Integer.parseInt(scheduleListField.get("graph_start").toString()) * 9);
					int graph_width = Integer.parseInt(scheduleListField.get("graph_width").toString()) * 9;
					String flight = (String) scheduleListField.get("flight");
					String time = (String) scheduleListField.get("time");
				
					// 그래프 #1
					setControl_Label(detail_new, "dID_2_0_1", "", graph_start, 70, graph_width, 6, 1, 100, "000000", "굴림체", 7, "000000");
					// 편명도착 #1
					setControl_Label(detail_new, "dID_2_0_1", flight, graph_start, 20, 100, 30, 0, 100, "000000", "굴림체", 7, "");
					// 출발도착시간 #1
					setControl_Label(detail_new, "dID_2_0_1", time, graph_start, 90, 150, 30, 0, 100, "000000", "굴림체", 7, "");
				}
				
				report.addSubSectionDetail(detail_new);
			}
		}
		
		//System.out.println(report.saveReportToBase64String());
		//report.saveReport("c:\\test\\testgen_dummy222.crf");
		return report.saveReportToBase64String();
	}
	public static void setControl_Label(SubSection section, String name, String value, int x, int y, int width, int height, int linestyle, int linewidth, String linecolor, String fontName, int fontSize, String backgroundcolor) {
		int backStyle;
		if(backgroundcolor.equals("")) {
			backStyle = 1;
			backgroundcolor = "000000";
		} else {
			backStyle = 0;
			backgroundcolor = "000000";
		}
		LabelControl schedule_label1 = section.createLabel("dID_2_0_1");
		schedule_label1.getLabel().setName(name);
		schedule_label1.setText(value); 
		schedule_label1.setPosition(x, y, width, height); 
		schedule_label1.setFontProperty(fontName, fontSize, parseIntRGB("000000"), false, false, false, false);
			
		schedule_label1.setHorizAlign(0);
		schedule_label1.setVerticalAlign(1);
		schedule_label1.setLineProperty(linestyle, linewidth, parseIntRGB(linecolor));
		schedule_label1.setBackgroundProperty(backStyle, parseIntRGB(backgroundcolor), 0, parseIntRGB("000000"));
	}
	/*
	 * 추후 패치 예정
	public static LineControl setControl_Line(SubSection detailSection) {
	}
	*/
	public static int parseIntRGB(String hexaData) {
		int rgb = 0;
		Util util = new Util();
		
		rgb = util.getRGB(Integer.valueOf( hexaData.substring( 0, 2 ), 16 ), Integer.valueOf( hexaData.substring( 2, 4 ), 16 ), Integer.valueOf( hexaData.substring( 4, 6 ), 16 ));
		return rgb;
	}
	
	

}
