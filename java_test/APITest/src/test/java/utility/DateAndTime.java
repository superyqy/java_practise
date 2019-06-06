package utility;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateAndTime {

    public static String getCurrentTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = df.format(new Date());

        return currentTime;
    }

    public static String getCurrentDay(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String currentDay = df.format(new Date());

        return currentDay;
    }

    public static String getFutureTime(int unit, Integer amout){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(unit, amout);


        return df.format(cal.getTime());
    }


    public static boolean compareDate(String targetDate, String beginDate, String endDate){
        Date fromDate = null;
        Date toDate = null;
        Date targetTime = null;
        boolean result = false;

        DateFormat formatWithSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            fromDate = formatWithSecond.parse(beginDate);
            toDate = formatWithSecond.parse(endDate);
            if(targetDate.indexOf("T") != -1){
                targetDate = targetDate.replace("T"," ");
            }
            targetTime = formatWithSecond.parse(targetDate);
        }catch (ParseException e){
            e.printStackTrace();
        }

        if(fromDate.before(targetTime) && toDate.after(targetTime)){
            result = true;
        }

        return result;

    }
}
