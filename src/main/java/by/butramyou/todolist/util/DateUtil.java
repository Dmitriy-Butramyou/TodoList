package by.butramyou.todolist.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date setTimeToMidnight(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime( date );
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getTomorrow(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }
}
