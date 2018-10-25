package com.belfry.bequank.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateHandler {
    public static boolean formerEarlier(String a, String b)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date former = format.parse(a);
            Date later = format.parse(b);
            return former.before(later);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<String> lastTwoDays() {
        List<String> pastTwoDays = new ArrayList<>();
        pastTwoDays.add(getPastDate(0));
        pastTwoDays.add(getPastDate(1));

        return pastTwoDays;
    }
    public static List<String> dateToWeek() {
        List<String> pastDaysList = new ArrayList<>();
        for (int i = 7; i >= 0; i--) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }

    private static String getPastDate(int past){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }
}
