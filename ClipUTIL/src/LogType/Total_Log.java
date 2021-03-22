// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Total_Log.java

package LogType;

import clipsoft.valuesBean;
import java.io.*;
import java.util.*;

public class Total_Log extends valuesBean {

	public Total_Log() {
		list = new ArrayList();
	}

	public void setWritelog(String logFilePath) throws Exception {
		if (getList() == null) {
			RemoveFile(logFilePath);
			// System.out.println(getRptName() +" 파일에는 없습니다.");
			return;
		}
		try {
			System.out.println(getRptName() + " 파일에서 발견 ★★★★★");
			// System.out.println(list);
			setCnt(1);
			if (getDeDuplicate().equals("true"))
				list = DulicateRemoveList(getList());
			else
				list = getList();

			// System.out.println(list);
			UpdateFile(logFilePath, "*************************************************************");
			UpdateFile(logFilePath, "리포트파일 검색 경로 : " + getRptName());
			UpdateFile(logFilePath, "*************************************************************\n\n");
			for (int i = 0; i < list.size(); i++) {
				/*
				 * HashMap getMap = new HashMap(); getMap = (HashMap)list.get(i);
				 */
				LinkedHashMap getMap = new LinkedHashMap();
				getMap = (LinkedHashMap) list.get(i);
				Set key = getMap.keySet();
				String valueName;
				for (Iterator iterator = key.iterator(); iterator.hasNext(); UpdateFile(logFilePath, valueName)) {
					String keyName = (String) iterator.next();
					valueName = (String) getMap.get(keyName);
					//System.out.println(valueName);
				}

			}

		} catch (Exception e) {
			System.out.println("setWriteLog() >>" + e.getMessage());
		}
		return;
	}

	private void CreateFile(String FilePath) {
		try {
			int nLast = FilePath.lastIndexOf("\\");
			String strDir = FilePath.substring(0, nLast);
			String strFile = FilePath.substring(nLast + 1, FilePath.length());
			File dirFolder = new File(strDir);
			dirFolder.mkdirs();
			File f = new File(dirFolder, strFile);
			f.createNewFile();
		} catch (Exception ex) {
			System.out.println("CreateFile() >>" + ex.getMessage());
		}
	}

	private String ReadFileText(File file) {
		String strText = "";
		try {
			BufferedReader buffRead = new BufferedReader(new FileReader(file));
			int nBuffer;
			while ((nBuffer = buffRead.read()) != -1)
				strText = (new StringBuilder(String.valueOf(strText))).append((char) nBuffer).toString();
			buffRead.close();
		} catch (Exception ex) {
			System.out.println("ReadFileText() >> " + ex.getMessage());
		}
		return strText;
	}

	private void UpdateFile(String FilePath, String Text) {
		try {
			File f = new File(FilePath);
			if (!f.exists())
				CreateFile(FilePath);
			String fileText = ReadFileText(f);
			BufferedWriter buffWrite = new BufferedWriter(new FileWriter(f));
			Text = (new StringBuilder(String.valueOf(fileText))).append("\r\n").append(Text).toString();
			buffWrite.write(Text, 0, Text.length());
			buffWrite.flush();
			buffWrite.close();
		} catch (Exception ex) {
			System.out.println("UpdateFile() >> " + ex.getMessage());
		}
	}

	private void RemoveFile(String FilePath) {
		System.out.println("RemoveFile() >>" + FilePath);
		try {
			File f = new File(FilePath);
			if (f.exists())
				f.delete();
		} catch (Exception ex) {
			System.out.println("RemoveFile() >>" + ex.getMessage());
		}
	}

	private List DulicateRemoveList(List list) {
		List duplicateRemoveList = new ArrayList(new LinkedHashSet(list));
		return duplicateRemoveList;
	}

	private List list;
}