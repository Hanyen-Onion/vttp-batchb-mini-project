package vttp5b.ssf.TravelPlanner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Util {

    public static final String USER_INFO = "userInfo";

//Date & Time parsers
    public static String parseToDate(String string) {
        
        //string = "2024-12-19";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date localDate = df.parse(string);

            DateFormat print = new SimpleDateFormat("EEE, dd MMM yyyy");
            String formattedDate = print.format(localDate);
    
            return formattedDate;
        } catch (Exception ex) {
            System.out.println("parse date error");
            //ex.printStackTrace();
        }
        return null;
    }

    public static String parseToTime(String time, String timezone) {
        
        // string = "2024-12-19T08:30:00+00:00";

        //might not need the zone
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mma");

        if (timezone == null) { 
            ZoneId foreignZ = ZoneId.systemDefault();
            ZonedDateTime zdtForeign = ZonedDateTime.parse(time).withZoneSameInstant(foreignZ);

            //change to sg time
            ZoneId sgZ = ZoneId.of("Asia/Singapore");
            ZonedDateTime zdtSG = zdtForeign.withZoneSameInstant(sgZ);

            return zdtSG.format(dtf); 
        } 
        ZoneId foreignZ = ZoneId.of(timezone);
        ZonedDateTime zdtForeign = ZonedDateTime.parse(time).withZoneSameInstant(foreignZ);

        //might not need to change to sg time
        ZoneId sgZ = ZoneId.of("Asia/Singapore");
        ZonedDateTime zdtSG = zdtForeign.withZoneSameInstant(sgZ);
        
        return zdtSG.format(dtf);
    }

    public static String parseBackTime(String time) {
       
        DateFormat df = new SimpleDateFormat("YYYY-MM-DD'T'HH:mm");
        if (time == null)
            return null;
        try {
            Date localDate = df.parse(time);

            DateFormat print = new SimpleDateFormat("HH:mm");
            String formattedDate = print.format(localDate);
    
            return formattedDate;
        } catch (Exception ex) {
            System.out.println("parse back time error");
            //ex.printStackTrace();
        }
        return null;
    }

    public static String parseTime(String date, String time) {
        if (time !=null && date != null) {
            String str = date + "T" + time;
            return str;
        }
        return null;
    }

    public static String DateToString(Date date) {
        DateFormat print = new SimpleDateFormat("dd MMM yyyy");
        String dateAsString = print.format(date);

        return dateAsString;
    }

    public static Date stringToDate(String string) {
        
        //string = "2024-12-19";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date localDate = df.parse(string);

            DateFormat print = new SimpleDateFormat("dd MMM yyyy");
            String formattedDate = print.format(localDate);
            Date date = print.parse(formattedDate);
    
            return date;
        } catch (Exception ex) {
            System.out.println("parse date error");
            //ex.printStackTrace();
        }
        return null;
    }

    public static ZonedDateTime stringToZDT(String time, String timezone) {
        
        // string = "2024-12-19T08:30:00+00:00";
        
        if (timezone == null) { 
            ZoneId foreignZ = ZoneId.systemDefault();
            ZonedDateTime zdtForeign = ZonedDateTime.parse(time).withZoneSameInstant(foreignZ);

            return zdtForeign; 
        } 
        ZoneId foreignZ = ZoneId.of(timezone);
        ZonedDateTime zdtForeign = ZonedDateTime.parse(time).withZoneSameInstant(foreignZ);
        
        return zdtForeign;
    }
}
