package rga.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    public static String getCurrentTime(){
        SimpleDateFormat formatter= new SimpleDateFormat("dd MMM yyyy'('HH:mm:ss')' ");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
