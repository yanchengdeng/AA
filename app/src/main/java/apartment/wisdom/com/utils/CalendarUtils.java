package apartment.wisdom.com.utils;


import com.blankj.utilcode.util.TimeUtils;
import com.tubb.calendarselector.library.FullDay;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {

    private static CalendarUtils tCalendarUtils = null;

    public static CalendarUtils getInstant() {
        if (tCalendarUtils == null) {
            tCalendarUtils = new CalendarUtils();
        }
        return tCalendarUtils;
    }


    //入住  离店 日期
    private FullDay STAND_IN, STAND_OUT;

    public void setSTAND_IN(FullDay stand_in) {
        STAND_IN = stand_in;
    }

    public void setSTAND_OUT(FullDay stand_out) {
        STAND_OUT = stand_out;
    }

    public FullDay getSTAND_IN() {
        return STAND_IN;
    }

    public FullDay getSTAND_OUT() {
        return STAND_OUT;
    }


    public FullDay getDefalutStandIn() {
        Calendar calendar = Calendar.getInstance();
        return new FullDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

    }

    public FullDay getDefaulStandOut() {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.add(Calendar.DAY_OF_MONTH, 1);
        return new FullDay(newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH) + 1, newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    //获取 中国 星期
    public String getWeekNameByDate(FullDay fullDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, fullDay.getYear());
        calendar.set(Calendar.MONTH, fullDay.getMonth() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, fullDay.getDay());
        return
                TimeUtils.getChineseWeek(calendar.getTime().getTime());
    }


    public String getDateForamte(FullDay fullDay) {

        String moth = String.valueOf(fullDay.getMonth());
        if (fullDay.getMonth()<10){
            moth = "0"+fullDay.getMonth();
        }


        String day = String.valueOf(fullDay.getDay());
        if (fullDay.getDay()<10){
            day = "0"+fullDay.getDay();
        }

        return fullDay.getYear() + "-" + moth+ "-" + day;
    }

    public String getDateForamteChinesMMdd(FullDay fullDay) {

        return fullDay.getMonth() + "月" + fullDay.getDay() + "日";
    }


    public String getOrderMonthAndWeek(String checkTime) {
        Date date = TimeUtils.string2Date(checkTime, new SimpleDateFormat("yyyy-MM-dd"));
        return date.getMonth()+1 + "月\n" + TimeUtils.getChineseWeek(checkTime, new SimpleDateFormat("yyyy-MM-dd"));
    }

    public String getDay(String checkInTime) {

       Date date = TimeUtils.string2Date(checkInTime,new SimpleDateFormat("yyyy-MM-dd"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }
}
