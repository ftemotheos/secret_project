package com.ftemotheos.tasks.secret_task1;

import java.text.MessageFormat;
import java.util.*;

public class HelloCity {
    public static void main(String[] args) {
        String cityName;
        String timeZone;
        TimeZone targetTimeZone = TimeZone.getTimeZone("GMT");
        Locale currentLocale = Locale.getDefault();
        if (args == null) {
            throw new NullPointerException("Null command line argument has been passed");
        }
        if (args.length == 0) {
            throw new IllegalArgumentException("No one command line arguments has been passed. Must provide one or two");
        }
        if (args.length == 1 || args.length == 2) {
            if (args[0].length() == 0) {
                throw new IllegalArgumentException("Must provide city name");
            }
            cityName = args[0];
            String[] timeZones = TimeZone.getAvailableIDs();
            for (String item: timeZones) {
                if (item.contains(cityName.trim().replace(' ', '_'))) {
                    targetTimeZone = TimeZone.getTimeZone(item);
                }
            }
            if (args.length == 2) {
                timeZone = args[1];
                targetTimeZone = TimeZone.getTimeZone(timeZone);
                if (!(targetTimeZone.getID()).equalsIgnoreCase(timeZone)) {
                    HelloCity.main(new String[] {cityName});
                    return;
                }
            }
        }
        else {
            throw new IllegalArgumentException("Incorrect number of incoming command line arguments. Must provide one or two");
        }
        Calendar calendar = new GregorianCalendar(targetTimeZone);
        calendar.setTime(new Date());
        int targetHours = calendar.get(Calendar.HOUR_OF_DAY);
        System.out.println(getGreetings(currentLocale, cityName, targetHours));
    }

    public static String getGreetings(Locale currentLocale, String cityName, int targetHours) {
        Locale.setDefault(currentLocale);
        String times;
        if (targetHours > 22) {
            times = "night";
        }
        else if (targetHours > 18) {
            times = "evening";
        }
        else if (targetHours > 8) {
            times = "afternoon";
        }
        else if (targetHours > 5) {
            times = "morning";
        }
        else {
            times = "night";
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com.ftemotheos.tasks.secret_task1.MessageResources", currentLocale);
        return MessageFormat.format("{0}, {1}!", resourceBundle.getString(times), cityName);
    }
}
