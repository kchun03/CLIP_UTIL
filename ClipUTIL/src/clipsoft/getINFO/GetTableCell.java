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
import com.clipsoft.clipreport.base.datas.DataSet;
import com.clipsoft.clipreport.base.datas.conections.Connection;
import com.clipsoft.clipreport.base.enums.ApplyValueType;
import com.clipsoft.clipreport.base.enums.DataType;
import com.clipsoft.clipreport.base.enums.GrowMethod;
import com.clipsoft.clipreport.base.functions.ConditionalStyle;
import com.clipsoft.clipreport.base.functions.FieldLink;
import com.clipsoft.clipreport.base.functions.ReplaceTextInfo;
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
import com.clipsoft.clipreport.common.enums.LinkType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GetTableCell extends valuesBean {
	private Map map;
	private List list = new ArrayList();
	private int cnt_ConditionalStyleList = 0;
	private TheReportFile reportFile = null;
	private RexObjectList<ConditionalStyle> standard_ConditionalStyle = null;
	private boolean flag = false;

	public void readReport(String rptName) throws Exception {
		flag = false;
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
		//System.out.println("getActiveDataSet >>> "+report.getReportData().getActiveDataSet().getName());
		if(!getGubun().equals("imageVisable"))
			findMainPage(report, "top");
		findBackgroundPage(report, "top");
		findForegroundPage(report, "top");
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
			for (int j = 0; j < subSectionList.size(); ++j) {
				if (((SubSection) subSectionList.get(j)).getObjectID() == 4250) {
					SubSectionSubreport subreportsection = (SubSectionSubreport) subSectionList.get(j);
					Report report = subreportsection.getSubreport();

					findMainPage(report, sControlPath + " > " + ((SubSection) subSectionList.get(j)).getName());
					findBackgroundPage(report, sControlPath + " > " + ((SubSection) subSectionList.get(j)).getName());
					findForegroundPage(report, sControlPath + " > " + ((SubSection) subSectionList.get(j)).getName());
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

						findControlList(controlList, sControlPath + " > " + ((SubSection) subSectionList.get(j)).getName());
					}
				}
			}
		}
	}

	private void findBackgroundPage(Report report, String sControlPath) {
		ReportDesign design = report.getReportDesign();
		BackgroundPage page = design.getBackgroundPage();

		SectionBackground section = page.getSectionBackground();
		RexObjectList subsectionlist = section.getSubSectionList();

		if (subsectionlist.size() < 1)
			return;

		SubSectionDefault default1 = (SubSectionDefault) subsectionlist.get(0);

		RexObjectList controlPageList = default1.getControlListForEachSeparatedPageList();
		ControlListForEachSeparatedPage pagecontrolList = (ControlListForEachSeparatedPage) controlPageList.get(0);
		RexObjectList controlList = pagecontrolList.getControlList();

		findControlList(controlList, sControlPath + " > BackgroundPage");
	}

	private void findForegroundPage(Report report, String sControlPath) {
		ReportDesign design = report.getReportDesign();
		ForegroundPage page = design.getForegroundPage();

		SectionBackground section = page.getSectionBackground();
		RexObjectList subsectionlist = section.getSubSectionList();

		if (subsectionlist.size() < 1)
			return;

		SubSectionDefault default1 = (SubSectionDefault) subsectionlist.get(0);

		RexObjectList controlPageList = default1.getControlListForEachSeparatedPageList();
		ControlListForEachSeparatedPage pagecontrolList = (ControlListForEachSeparatedPage) controlPageList.get(0);
		RexObjectList controlList = pagecontrolList.getControlList();

		findControlList(controlList, sControlPath + " > ForegroundPage");
	}

	private void findControlList(RexObjectList<Control> controlList, String sControlPath) {
		if(getGubun().contains("ConditionalStyleList")) {
			if(sControlPath.contains(getSearchWord().split(",")[2]) || sControlPath.contains(getSearchWord().split(",")[3])) {
				for (int m = 0; m < controlList.size(); ++m) {
					Control control = (Control) controlList.get(m);
					if (control.getObjectID() == 4600) {
						findLabel((ControlLabel) control, sControlPath);
					} else if (control.getObjectID() == 4850) {
						findTable((ControlTable) control, sControlPath);
					} else if (control.getObjectID() == 4650) {
						ControlSubreport subreportcontrol = (ControlSubreport) control;
						Report report = subreportcontrol.getSubreport();

						findMainPage(report, sControlPath + " > " + control.getName());
					} else if (control.getObjectID() == 4450) {
						findLine((ControlLine) control, sControlPath);
					}
				}
			}
		} else if(getGubun().contains("reportControl_prop")) {
				for (int m = 0; m < controlList.size(); ++m) {
					Control control = (Control) controlList.get(m);
					if (control.getObjectID() == 4650) { //리포트 컨트롤
						ControlSubreport subreportcontrol = (ControlSubreport) control;
						Report report = subreportcontrol.getSubreport();

						findMainPage(report, sControlPath + " > " + control.getName());
						findControlsubport((ControlSubreport) control, sControlPath);
					}
				}
		} else {
			for (int m = 0; m < controlList.size(); ++m) {
				Control control = (Control) controlList.get(m);
				
				if (control.getObjectID() == 4600) {
					findLabel((ControlLabel) control, sControlPath);
				} else if (control.getObjectID() == 4850) {
					findTable((ControlTable) control, sControlPath);
				} else if (control.getObjectID() == 4650) {
					ControlSubreport subreportcontrol = (ControlSubreport) control;
					Report report = subreportcontrol.getSubreport();

					findMainPage(report, sControlPath + " > " + control.getName());
				} else if (control.getObjectID() == 4450) {
					findLine((ControlLine) control, sControlPath);
				}
			}
		}
	}
	private void findControlsubport(ControlSubreport control, String sControlPath) {
		if(control.getGrowMethod().name().equals("Clone")) {
			//System.out.println("★"+sControlPath + " > " + control.getName()+ "/////" +control.getObjectID() + " / "+ control.getName());
			control.setGrowMethod(GrowMethod.None);
			//control.setGrowMethod("None");
		}
	}
	private void findLine(ControlLine control, String sControlPath) {
		if (getGubun().equals("getConditionalStyleList")) {
			if (control.getName().equals(getControlName())) {
				System.out.println("컨트롤 위치 : " + sControlPath + " > " + control.getName());
				setStandard_ConditionalStyle(control.getConditionalStyleList());
				System.out.println("getted Style Size >> " + getStandard_ConditionalStyle().size());

				this.map = new LinkedHashMap();
				this.map.put("info1", "************************************************************");
				this.map.put("info2", "조건스타일 Get");
				this.map.put("info3", "************************************************************");
				this.map.put("path", "컨트롤 위치 : " + sControlPath + " > " + control.getName());
				this.map.put("getted Style Size", "조건스타일 cnt : " + getStandard_ConditionalStyle().size() + "\n");
				this.list.add(this.map);
				setList(this.list);
			}
		} else if (getGubun().equals("setConditionalStyleList")) {

			this.map = new LinkedHashMap();
			
			this.map.put("info", "▶▶▶▶ 조건스타일 Set!!");
			

			// 적용
			if (control.getName().equals(getControlName())) {
				System.out.println("findLine 컨트롤 위치 : " + sControlPath + " > " + control.getName());
				this.map.put("path", "컨트롤 위치 : " + sControlPath + " > " + control.getName());
				this.map.put("getted Style Size", "조건스타일 cnt : " + getStandard_ConditionalStyle().size() + "\n");
				this.list.add(this.map);
				setList(this.list);

				if (control.getConditionalStyleList().size() > 0) {
					control.getConditionalStyleList().removeAll();
				}

				for (int z = 0; z < getStandard_ConditionalStyle().size(); z++) {
					control.getConditionalStyleList().add(getStandard_ConditionalStyle().get(z));
					//System.out.println(getStandard_ConditionalStyle().get(z).getCondition().getCompareValue1Text());
				}
				control.setName("");
			}
		}
	}

	private void findTable(ControlTable control, String sControlPath) {
		for (int i = 0; i < control.getRowCount(); ++i) {
			for (int j = 0; j < control.getColumnCount(); ++j) {
				TableCell tc = control.getTableCell(i, j);
				if (tc.getObjectID() == 5800) {
					TableCellNormal tcm = (TableCellNormal) tc;
					
					if (getGubun().equals("getOutputFormat")) {
						if ((tcm.getOutputFormat() != null) && (tcm.getOutputFormat() != ""))
							if (getSearchWord().isEmpty()) {
								this.map = new LinkedHashMap();
								this.map.put("path", "컨트롤 위치 : " + sControlPath + " > " + control.getName() + " > "
										+ (i + 1) + "번째 행 / " + (j + 1) + "번째 열 (셀)");
								this.map.put("detailValue", "-> " + tcm.getOutputFormat());
								this.list.add(this.map);
								setList(this.list);
							} else if (tcm.getOutputFormat().contains(getSearchWord())) {
								this.map = new LinkedHashMap();
								this.map.put("path", "컨트롤 위치 : " + sControlPath + " > " + control.getName() + " > "
										+ (i + 1) + "번째 행 / " + (j + 1) + "번째 열 (셀)");
								this.map.put("detailValue", "-> " + tcm.getOutputFormat());
								this.list.add(this.map);
								setList(this.list);
							}

					} else if (getGubun().equals("getFontList")) {
						String fontName = tcm.getTextInfo().getFontName();
						if (getSearchWord().isEmpty()) {
							this.map = new LinkedHashMap();
							if (!getDeDuplicate().equals("true"))
								this.map.put("path", "폰트명 : " + fontName + "\t\t컨트롤 위치 : " + sControlPath + " > "
										+ control.getName() + " > " + (i + 1) + "번째 행 / " + (j + 1) + "번째 열 (셀)");
							else
								this.map.put("detailValue", fontName);
							this.list.add(this.map);
							setList(this.list);
						} else if (fontName.equals(getSearchWord())) {
							this.map = new LinkedHashMap();
							if (!getDeDuplicate().equals("true"))
								this.map.put("path", "폰트명 : " + fontName + "\t\t컨트롤 위치 : " + sControlPath + " > "
										+ control.getName() + " > " + (i + 1) + "번째 행 / " + (j + 1) + "번째 열 (셀)");
							else
								this.map.put("detailValue", fontName);
							this.list.add(this.map);
							setList(this.list);
						}
					} else if (getGubun().equals("getFontList_OnlyFont")) {
						String fontName = tcm.getTextInfo().getFontName();
						list = getList2();
						if(!list.contains(fontName)){
								list.add(fontName);
								setList(list);
						} 
					} else if (getGubun().equals("getWordSpace")) {
						Float wordSpace = tcm.getTextInfo().getWordSpace();
						if (getSearchWord().isEmpty()) {

						} else if (wordSpace.equals(Float.parseFloat(getSearchWord()))) {
							setList(this.list);
						}
					} else if (getGubun().equals("getConditionalStyleList")) {
						// 기준
						if (tcm.getName().equals(getControlName())) {
							System.out.println("컨트롤 위치 : " + sControlPath + " > " + control.getName() + " > " + (i + 1) + "번째 행 / " + (j + 1) + "번째 열 (셀)");
							setStandard_ConditionalStyle(tcm.getConditionalStyleList());
							System.out.println("getted Style Size >> " + getStandard_ConditionalStyle().size());

							this.map = new LinkedHashMap();
							this.map.put("info1", "************************************************************");
							this.map.put("info2", "조건스타일 Get");
							this.map.put("info3", "************************************************************");
							this.map.put("path", "컨트롤 위치 : " + sControlPath + " > " + control.getName() + " > " + (i + 1) + "번째 행 / " + (j + 1) + "번째 열 (셀)");
							this.map.put("getted Style Size", "조건스타일 cnt : " + getStandard_ConditionalStyle().size() + "\n");
							this.list.add(this.map);
							setList(this.list);
						}

					} else if (getGubun().equals("setConditionalStyleList")) {
						this.map = new LinkedHashMap();
						
						this.map.put("info", "▶▶▶▶ 조건스타일 Set!!");
						

						// 적용
						if (tcm.getName().equals(getControlName())) {
							System.out.println("findTable 컨트롤 위치 : " + sControlPath + " > " + control.getName() + " > "
									+ (i + 1) + "번째 행 / " + (j + 1) + "번째 열 (셀)");

							this.map.put("path", "컨트롤 위치 : " + sControlPath + " > " + control.getName() + " > "
									+ (i + 1) + "번째 행 / " + (j + 1) + "번째 열 (셀)");
							this.map.put("getted Style Size",
									"조건스타일 cnt : " + getStandard_ConditionalStyle().size() + "\n");
							this.list.add(this.map);
							setList(this.list);

							if (tcm.getConditionalStyleList().size() > 0) {
								tcm.getConditionalStyleList().removeAll();
							}

							for (int z = 0; z < getStandard_ConditionalStyle().size(); z++) {
								tcm.getConditionalStyleList().add(getStandard_ConditionalStyle().get(z));
							}
							tcm.setName("");
						}
					} else if(getGubun().equals("getFieldType_getReplaceTextInfoList")){
						//System.out.println("findTable 컨트롤 위치 : " + sControlPath + " > " + control.getName() + " > "+ (i + 1) + "번째 행 / " + (j + 1) + "번째 열 (셀)");
						if(tcm.getApplyValueType().equals(ApplyValueType.Field)) {
							RexObjectList<Connection> report = reportFile.getGlobe().getGlobalObjectManager().getConnectionList();
							RexObjectList<DataSet> reportDS = reportFile.getGlobe().getGlobalObjectManager().getDataSetList();
							//System.out.println("getActiveDataSet >>> "+reportFile.getGlobe().getMainReport().getReportData().getActiveDataSet().getName());
							for (int a = 0; a < report.size(); ++a)
								for(int a2 = 0; a2 < reportDS.size(); a2++){
									//System.out.println(reportDS.get(a2).getName());
									if(reportFile.getGlobe().getMainReport().getReportData().getActiveDataSet() != null) {
										if(reportDS.get(a2).getName().equals(reportFile.getGlobe().getMainReport().getReportData().getActiveDataSet().getName())) //현재 연결된 데이터셋이름과 일치한 데이터셋
											for(int b = 0; b < reportDS.get(a).getFieldDataList().size(); b++) //일치한 데이터셋의 필드 반복문
												if(reportDS.get(a).getFieldDataList().get(b).getDataType().equals(DataType.Number)) //데이터셋에 Number타입이 있는 경우
													if(reportDS.get(a).getFieldDataList().get(b).getName().equals(tcm.getApplyValueField().getName())){
														//System.out.println("findTable 컨트롤 위치 : " + sControlPath + " > " + control.getName() + " > "+ (i + 1) + "번째 행 / " + (j + 1) + "번째 열 (셀)");
														/*if(tcm.getReplaceTextInfoList().size() > 0)
															System.out.println("★★★★★★★★★");*/
														ReplaceTextInfo o = new ReplaceTextInfo();
														o.setReplaceTextOld("");
														o.setReplaceTextNew("0");
														tcm.getReplaceTextInfoList().add(o);
														flag = true;
													}
									}
								}
						}
					} else if (getGubun().equals("modify_text")) {
						String[] splitedWord = getSearchWord().split(",");
						if(tcm.getApplyValueText().equals(splitedWord[0])){
							tcm.setApplyValueText(splitedWord[1]);
							System.out.println("findTable 컨트롤 위치 : " + sControlPath + " > " + control.getName() + " > "+ (i + 1) + "번째 행 / " + (j + 1) + "번째 열 (셀)");
							flag = true;
						}
					} else if (getGubun().equals("findHyperLinkReport")) {
						if(!(tcm.getWebLinkInfo().getLinkType().equals(LinkType.None))) {
							System.out.println(tcm.getWebLinkInfo().getLinkType());
							System.out.println("findTable 컨트롤 위치 : " + sControlPath + " > " + control.getName() + " > "+ (i + 1) + "번째 행 / " + (j + 1) + "번째 열 (셀)");
							flag = true;
						}						
					}
				}
			}
		}

		// Table 속성 (셀 말고)
		if (getGubun().equals("getConditionalStyleList")) {
			if (control.getName().equals(getControlName())) {
				System.out.println("컨트롤 위치 : " + sControlPath + " > " + control.getName());
				setStandard_ConditionalStyle(control.getConditionalStyleList());
				System.out.println("getted Style Size >> " + getStandard_ConditionalStyle().size());

				this.map = new LinkedHashMap();
				this.map.put("info1", "************************************************************");
				this.map.put("info2", "조건스타일 Get");
				this.map.put("info3", "************************************************************");
				this.map.put("path", "컨트롤 위치 : " + sControlPath + " > " + control.getName());
				this.map.put("getted Style Size", "조건스타일 cnt : " + getStandard_ConditionalStyle().size() + "\n");
				this.list.add(this.map);
				setList(this.list);
			}
		} else if (getGubun().equals("setConditionalStyleList")) {
			this.map = new LinkedHashMap();
			this.map.put("info", "▶▶▶▶ 조건스타일 Set!!");

			// 적용
			if (control.getName().equals(getControlName())) {
				System.out.println("FindTableLabel 컨트롤 위치 : " + sControlPath + " > " + control.getName());

				this.map.put("path", "컨트롤 위치 : " + sControlPath + " > " + control.getName());
				this.map.put("getted Style Size", "조건스타일 cnt : " + getStandard_ConditionalStyle().size() + "\n");
				this.list.add(this.map);
				setList(this.list);
				if (control.getConditionalStyleList().size() > 0) {
					control.getConditionalStyleList().removeAll();
				}

				for (int z = 0; z < getStandard_ConditionalStyle().size(); z++) {
					control.getConditionalStyleList().add(getStandard_ConditionalStyle().get(z));
				}
				control.setName("");
			}
		}
	}

	private void findLabel(ControlLabel control, String sControlPath) {
		if (getGubun().equals("getOutputFormat")) {
			if ((control.getOutputFormat() != null) && (control.getOutputFormat() != ""))
				if (getSearchWord().isEmpty()) {
					this.map = new LinkedHashMap();
					this.map.put("path", "컨트롤 위치 : " + sControlPath + " > " + control.getName() + " (라벨)");
					this.map.put("detailValue", "-> " + control.getOutputFormat());
					this.list.add(this.map);
					setList(this.list);
				} else if (control.getOutputFormat().contains(getSearchWord())) {
					this.map = new LinkedHashMap();
					this.map.put("path", "컨트롤 위치 : " + sControlPath + " > " + control.getName() + " (라벨)");
					this.map.put("detailValue", "-> " + control.getOutputFormat());
					this.list.add(this.map);
					setList(this.list);
				}
		} else if (getGubun().equals("getFontList")) {
			String fontName = control.getTextInfo().getFontName();
			if (getSearchWord().isEmpty()) {
				this.map = new LinkedHashMap();
				if (!getDeDuplicate().equals("true"))
					this.map.put("path",
							"폰트명 : " + fontName + "\t\t컨트롤 위치 : " + sControlPath + " > " + control.getName() + " (라벨)");
				else
					this.map.put("detailValue", fontName);
				this.list.add(this.map);
				setList(this.list);
			} else if (fontName.equals(getSearchWord())) {
				this.map = new LinkedHashMap();
				if (!getDeDuplicate().equals("true"))
					this.map.put("path",
							"폰트명 : " + fontName + "\t\t컨트롤 위치 : " + sControlPath + " > " + control.getName() + " (라벨)");
				else
					this.map.put("detailValue", fontName);
				this.list.add(this.map);
				setList(this.list);
			}
		}  else if (getGubun().equals("getFontList_OnlyFont")) {
			String fontName = control.getTextInfo().getFontName();
			list = getList2();
			if(!list.contains(fontName)){
					list.add(fontName);
					setList(list);
			} 
		}  else if (getGubun().equals("getWordSpace")) {

			Float wordSpace = control.getTextInfo().getWordSpace();
			if (getSearchWord().isEmpty()) {

			} else if (wordSpace.equals(Float.parseFloat(getSearchWord()))) {
				setList(this.list);
			}
		} else if (getGubun().equals("getConditionalStyleList")) {
			if (control.getName().equals(getControlName())) {
				System.out.println("컨트롤 위치 : " + sControlPath + " > " + control.getName());
				setStandard_ConditionalStyle(control.getConditionalStyleList());
				System.out.println("getted Style Size >> " + getStandard_ConditionalStyle().size());

				this.map = new LinkedHashMap();
				this.map.put("info1", "************************************************************");
				this.map.put("info2", "조건스타일 Get");
				this.map.put("info3", "************************************************************");
				this.map.put("path", "컨트롤 위치 : " + sControlPath + " > " + control.getName());
				this.map.put("getted Style Size", "조건스타일 cnt : " + getStandard_ConditionalStyle().size() + "\n");
				this.list.add(this.map);
				setList(this.list);
			}
		} else if (getGubun().equals("setConditionalStyleList")) {
			this.map = new LinkedHashMap();
			this.map.put("info", "▶▶▶▶ 조건스타일 Set!!");

			// 적용
			if (control.getName().equals(getControlName())) {
				System.out.println("findLabel 컨트롤 위치 : " + sControlPath + " > " + control.getName());

				this.map.put("path", "컨트롤 위치 : " + sControlPath + " > " + control.getName());
				this.map.put("getted Style Size", "조건스타일 cnt : " + getStandard_ConditionalStyle().size() + "\n");
				this.list.add(this.map);
				setList(this.list);
				if (control.getConditionalStyleList().size() > 0) {
					control.getConditionalStyleList().removeAll();
				}

				for (int z = 0; z < getStandard_ConditionalStyle().size(); z++) {
					control.getConditionalStyleList().add(getStandard_ConditionalStyle().get(z));
				}
				control.setName("");
			}
		} else if (getGubun().equals("imageVisable")) {
			if (control.getName().contains("그림")) {
				//System.out.println("findLabel 컨트롤 위치 : " + sControlPath + " > " + control.getName() + " ****** " +getRptName() );
				/*
				if(!(control.getWidth() == 904 && control.getHeight() == 923)){
					System.out.println("findLabel 컨트롤 위치 : " + sControlPath + " > " + control.getName() + " ****** " +getRptName() );
					System.out.println(control.getWidth() + " / " + control.getHeight());
				}
				*/
				
				
				control.getVisibleOption().setVisibleBMP(false);
				control.getVisibleOption().setVisibleDOC(false);
				control.getVisibleOption().setVisibleHML(false);
				control.getVisibleOption().setVisibleHTM(false);
				control.getVisibleOption().setVisibleHWP(false);
				control.getVisibleOption().setVisibleJPG(false);
				control.getVisibleOption().setVisiblePDF(false);
				control.getVisibleOption().setVisiblePPT(false);
				//control.getVisibleOption().setVisiblePrinter(false);
				control.getVisibleOption().setVisibleRTF(false);
				control.getVisibleOption().setVisibleTIF(false);
				control.getVisibleOption().setVisibleTXT(false);
				control.getVisibleOption().setVisibleViewer(false);
				control.getVisibleOption().setVisibleXLS(false);
				flag = true;
			}
		}  else if (getGubun().equals("modify_text")) {
			String[] splitedWord = getSearchWord().split(",");
			if(control.getName().equals(splitedWord[0])){
				control.setName(splitedWord[1]);
				//control.get
				flag = true;
			}
		} else if (getGubun().equals("findHyperLinkReport")) {
			if(!(control.getWebLinkInfo().getLinkType().equals(LinkType.None))) {
				System.out.println(control.getWebLinkInfo().getLinkType());
				System.out.println("findLabel 컨트롤 위치 : " + sControlPath + " > " + control.getName());
				flag = true;
			}
		}
	}

	public void writeReport() throws Exception {
		if(flag) {
			System.out.println(getRptName() + "     ★★★★★★");
			//Rexpert4.write(reportFile, getRptName());
			//Rexpert4.write(reportFile, getRptName().replace(".crf", "_convert2.crf"));
		}
			
	}
}