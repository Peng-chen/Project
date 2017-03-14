package tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by youyouza on 16-5-3.
 */
public class TimeUtil {

    //    yyyy-MM-dd HH:mm:ss

    /**
     * @param timeString like yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getHour(String timeString){

        String[]day=timeString.split(" ");
        String []hour=day[1].split(":");
        return hour[0];


    }


    /**
     * split time String and return hour
     *
     * @param timeString like yyyy-MM-dd HH:mm:ss
     * @return int HH
     */


    public static int getHourFromDateString(String timeString){

        return getHourFromString(getHour(timeString));

    }


    public static int getHourFromString(String hours){

        int hour=Integer.parseInt(hours);
        return hour;

    }


    /**
     * @param timeString like yyyy-MM-dd HH:mm:ss
     * @return String dd
     */
    public static String getDay(String timeString){

        String[]day=timeString.split(" ");
        String []hour=day[0].split("-");
        return hour[2];


    }

    public static int getDayFromString(String day){

        return Integer.parseInt(day);

    }


    /**
     * split time String and return day
     *
     * @param DateString like yyyy-MM-dd HH:mm:ss
     * @return int dd
     */

    public static int getDayFromDateString(String DateString){

        return getDayFromString(getDay(DateString));

    }




    /**
     * split time String and return Date
     *
     * return yyyy-MM-dd
     *
     * @param timeString like yyyy-MM-dd HH:mm:ss
     * @return String yyyy-MM-dd
     */
    public static String getDate(String timeString){
        String[]day=timeString.split(" ");
        return day[0];


    }

    public static  boolean isSameHour(Calendar day1,Calendar day2){

//        System.out.println(day1.toString());
//        System.out.println(day2.toString());

        return (day1.get(Calendar.ERA)==day2.get(Calendar.ERA))&&
                (day1.get(Calendar.YEAR)==day2.get(Calendar.YEAR))&&
                (day1.get(Calendar.DAY_OF_YEAR)==day2.get(Calendar.DAY_OF_YEAR))&&
                (day1.get(Calendar.HOUR_OF_DAY)==day2.get(Calendar.HOUR_OF_DAY));




    }

    public static  boolean isSameMinute(Calendar day1,Calendar day2){

//        System.out.println(day1.toString());
//        System.out.println(day2.toString());

        return (day1.get(Calendar.ERA)==day2.get(Calendar.ERA))&&
                (day1.get(Calendar.YEAR)==day2.get(Calendar.YEAR))&&
                (day1.get(Calendar.DAY_OF_YEAR)==day2.get(Calendar.DAY_OF_YEAR))&&
                (day1.get(Calendar.HOUR_OF_DAY)==day2.get(Calendar.HOUR_OF_DAY))&&
                (day1.get(Calendar.MINUTE)==day2.get(Calendar.MINUTE));




    }


    /**
     *
     * return true  if two day are same day
     *
     * @param day1 Calendar
     * @param day2 Calendar
     * @return
     */
    public static  boolean isSameDay(Calendar day1,Calendar day2){

        System.out.println(day1.toString());
        System.out.println(day2.toString());

        return (day1.get(Calendar.ERA)==day2.get(Calendar.ERA))&&(day1.get(Calendar.YEAR)==day2.get(Calendar.YEAR))
                &&(day1.get(Calendar.DAY_OF_YEAR)==day2.get(Calendar.DAY_OF_YEAR))&&
                (day1.get(Calendar.HOUR_OF_DAY)==day2.get(Calendar.HOUR_OF_DAY));




    }

    /**
     *
     * get today time <br/>
     * like :2016-05-03<br/>
     *
     * it with format:"yyyy-MM-dd"
     *
     * @return String
     */
    public static String getTodayTime(){

        Calendar calendar=getNowCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatNow= sdf.format(calendar.getTime());
//        System.out.println(formatNow);
        return formatNow;

    }



    public static int[] getWeekArray(int length){
        int []weekDay=new int[length];
        int q=0;
        for(int i=length-1;i>=0;--i){

            weekDay[q++]=TimeUtil.getBeforeDay(i+1).get(Calendar.DAY_OF_MONTH);
        }
        return weekDay;

    }

    public static Calendar getCalendar(){

        return Calendar.getInstance();

    }


    /**
     * format calendar with default format:<br/>
     *
     * yyyy-MM-dd HH:mm
     *
     * @param calendar
     * @return String
     */
    public static String formatCalendar(Calendar calendar){
//    	System.out.println(calendar.toString());

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formatNow= sdf.format(calendar.getTime());

        return formatNow;

    }

    /**
     *
     * use format to format specific calendar<br/>
     *
     * @param calendar
     * @param format like "yyyy-MM-dd HH:mm "
     * @return
     */
    public static String formatCalendar(Calendar calendar,String format){

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String formatNow= sdf.format(calendar.getTime());

        return formatNow;

    }

    public static Calendar setDate(Date date,Calendar calendar){
        calendar.setTime(date);

        return calendar;
    }


    /**
     * printf today string with default format
     */
    public static String format(){

        return formatCalendar(getNowCalendar());


    }

    /**
     *
     * get now Calendar
     *
     * @return
     */
    public static Calendar getNowCalendar(){

        return setDate(new Date(),getCalendar());

    }



    /**
     * day>=0
     *
     * return the Calendar that today before
     * @param day
     * @return
     */
    public static Calendar getBeforeDay(int day){

        Calendar calendar=getNowCalendar();
        calendar.add(Calendar.DATE, -day);
        return calendar;

    }


    /**
     *
     * get before Month of now
     *
     * @param month>=0
     * @return Calendar
     */
    public static Calendar getBeforeMonth(int month){

        Calendar calendar=getNowCalendar();
        calendar.add(Calendar.MONTH, -month);
        return calendar;


    }

    /**
     * week_index is from 0 to 6 <br/>
     * 0 represent Sunday <br/>
     *
     * 6 represent Saturday <br/>
     *
     * @param calendar
     * @return int
     */
    public static int getWeek(Calendar calendar) {

        int week_index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }

        return week_index;

    }

    /**
     *
     * weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"}<br/>
     *
     *
     * @param calendar
     * @return String
     */
    public static String getWeek(Date date){
        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return weeks[week_index];
    }




}
