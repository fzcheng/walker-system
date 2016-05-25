package com.walkersoft.appmanager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil
{

//几种日期样式
    private static final SimpleDateFormat formatter1 = new SimpleDateFormat ("yyyy年MM月dd日 HH时mm分ss秒");
    private static final SimpleDateFormat formatter2 = new SimpleDateFormat ("yyyy年MM月dd日");
    private static final SimpleDateFormat formatter3 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat formatter4 = new SimpleDateFormat ("yyyy-MM-dd");
    private static final SimpleDateFormat formatter5 = new SimpleDateFormat ("yyyy/MM/dd");
    private static final SimpleDateFormat formatter6 = new SimpleDateFormat ("MM-dd HH:mm");
    public static final SimpleDateFormat FORMATER_YMDHMSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    
    public DateTimeUtil()
    {
    }
    //得到现在时间后几天的时间.如days=20,结果20070725093258656
    public static String getRelativeDate(int days)
    {
        Calendar c = Calendar.getInstance();
        c.set(5, c.get(5) + days);
        StringBuffer sb = new StringBuffer(17);
        sb.append(c.get(1));
        int tmp[] = {
            c.get(2) + 1, c.get(5), c.get(11), c.get(12), c.get(13), c.get(14)
        };
        for(int i = 0; i < tmp.length - 1; i++)
            sb.append(tmp[i] >= 10 ? "" : "0").append(tmp[i]);

        if(tmp[tmp.length - 1] < 10)
            sb.append("0");
        if(tmp[tmp.length - 1] < 100)
            sb.append("0");
        sb.append(tmp[tmp.length - 1]);
        return sb.toString();
    }

//t1和t2的差
    public static long compare(String t1, String t2)
    {
        return Long.valueOf(t1).longValue() - Long.valueOf(t2).longValue();
    }
//得到Year，time是Date类型
    public static int getYear(String time)
    {
        return Integer.valueOf(time.substring(0, 4)).intValue();
    }
//  得到Month，time是Date类型
    public static int getMonth(String time)
    {
        return Integer.valueOf(time.substring(4, 6)).intValue();
    }
//  得到Date，time是Date类型
    public static int getDate(String time)
    {
        return Integer.valueOf(time.substring(6, 8)).intValue();
    }
//  得到Hour，time是Date类型
    public static int getHour(String time)
    {
        return Integer.valueOf(time.substring(8, 10)).intValue();
    }
//  得到Minute，time是Date类型
    public static int getMinute(String time)
    {
        return Integer.valueOf(time.substring(10, 12)).intValue();
    }
//  得到Second，time是Date类型
    public static int getSecond(String time)
    {
        return Integer.valueOf(time.substring(12, 14)).intValue();
    }
//得到MilliSencond，time是Date类型
    public static int getMilliSencond(String time)
    {
        return Integer.valueOf(time.substring(14, 17)).intValue();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
//  将time(13位)格式化成指定string样式;如：1183605002016l,"yyyy年MM月dd日 HH时mm分ss秒"
    public static String getLongToString(long time, String string)
    {
        return (new SimpleDateFormat(string)).format(new Date(time));
    }  
   

//  把myDate(Thu Jul 05 10:06:37 CST 2007格式)转化成指定string如(yyyy年MM月dd日 HH时mm分ss秒)
    public static String formatDate(Date myDate, String string) {
     SimpleDateFormat formatter = new SimpleDateFormat (string);
     String time=formatter.format(myDate);
     return time;
    }
    
    
    // 将time指定格式string转化成long类型
    public static long getStrToLong(String time,String string)
    {
        try {
         return (new SimpleDateFormat(string)).parse(time).getTime();
          }
          catch (ParseException ex) {
           ex.getStackTrace();
            return 0L;
          }  
    }

//  将time指定string样式输出,两参数要匹配.如"2007-07-25 09:26:57","yyyy-MM-dd HH:mm:ss"
    public static String getShowFormat(String time,String string){
     SimpleDateFormat temp = new SimpleDateFormat (string);
        try
        {
            if(time == null || time.equals(""))
                time = temp.format(new Date());
            else
            {
             time = temp.format(temp.parse(time));
            }
                
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        return time;

    }
}