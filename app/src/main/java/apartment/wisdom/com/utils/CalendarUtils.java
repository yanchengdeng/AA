package apartment.wisdom.com.utils;


import com.tubb.calendarselector.library.FullDay;

import java.util.Calendar;

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

    public String getWeekNameByDate(FullDay fullDay){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,fullDay.getWeekOf());
        calendar.set(Calendar.MONTH,fullDay.getMonth()-1);
        calendar.set(Calendar.DAY_OF_MONTH,fullDay.getDay());
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case 0:
                return "周一";
            case 1:
                return "周二";
            case 2:
                return "周三";
            case 3:
                return "周四";
            case 4:
                return "周五";
            case 5:
                return "周六";
            default:
                return "周日";
        }
    }
}
