package rga.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    public static String getCurrentTime(){
        SimpleDateFormat formatter= new SimpleDateFormat("dd MMM yyyy'('HH:mm:ss')' ");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static String resourceDirPath(){
        return baseFolder() + "src/test/resources/";
    }

    public static String baseFolder(){return System.getProperty("user.dir")+ "/";}

    public  static String xslFileLocation(){return resourceDirPath() + "configuration/Design.xsl";}

    public static String jmxFolderPath(){
        return resourceDirPath() + "jmxmodules/";
    }

    public  static String xslDataFileLocation(){return resourceDirPath() + "configuration/Data.xlsx";}

    public static String getReportPath(){
        return System.getProperty("user.dir")+ "/jmeter_reports/";
    }

    public static String getCurrentTime(String format){
        SimpleDateFormat formatter= new SimpleDateFormat(format);
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
