// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Each_Log.java

package LogType;

import clipsoft.valuesBean;
import java.io.*;
import java.util.*;

public class Each_Log extends valuesBean
{

    public Each_Log()
    {
        list = new ArrayList();
    }

    public void setWritelog(String logFilePath)
        throws Exception
    {
        BufferedWriter out;
        try
        {
        	if(getList()==null)
            {
                RemoveFile(logFilePath);
                System.out.println(getRptName() +" 파일에는 없습니다.");
                return;
            }
        }
        catch(Exception e)
        {
            throw new Exception("???");
        }
        if(getDeDuplicate().equals("true"))
            list = DulicateRemoveList(getList());
        else
            list = getList();
        
        System.out.println(getRptName() +" 파일에서 발견 @@@@@@@@");
        out = new BufferedWriter(new FileWriter(logFilePath));
        out.write((new StringBuilder("파일명 : ")).append(getRptName()).toString());
        out.newLine();
        for(int i = 0; i < list.size(); i++)
        {
            HashMap getMap = new HashMap();
            getMap = (HashMap)list.get(i);
            Set key = getMap.keySet();
            for(Iterator iterator = key.iterator(); iterator.hasNext(); out.newLine())
            {
                String keyName = (String)iterator.next();
                String valueName = (String)getMap.get(keyName);
                out.write(valueName);
            }

        }

        out.close();
    }

    private void RemoveFile(String FilePath)
    {
        try
        {
            File f = new File(FilePath);
            if(f.exists())
                f.delete();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private List DulicateRemoveList(List list)
    {
        List duplicateRemoveList = new ArrayList(new LinkedHashSet(list));
        return duplicateRemoveList;
    }

    private List list;
}
