package com.ftemotheos.tasks.secret_task1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.*;

public class HelloCity {

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    public static final String FILE_PATH = "src" + FILE_SEPARATOR + "main" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR;

    public static void main(String[] args) {

        String cityName;
        String timeZone;
        TimeZone targetTimeZone = TimeZone.getTimeZone("GMT");
        Locale currentLocale = getCurrentLocale();

        if (args == null) {
            throw new NullPointerException("Null instead of command line arguments has been passed");
        }

        if (args.length == 0) {
            throw new IllegalArgumentException("No one command line arguments has been passed. Must provide one or two");
        }

        if (args.length == 1 || args.length == 2) {

            if (args[0] == null) {
                throw new NullPointerException("Null first argument of command line has been passed");
            }

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

                if (args[1] == null) {
                    throw new NullPointerException("Null second argument of command line has been passed");
                }

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

        Properties en = new Properties();
        String[] template = {"Good morning", "Good afternoon", "Good evening", "Good night"};
        createMessageResources(en, template, "MessageResources.properties", "English/Default message resources");

        Properties ru = new Properties();
        template = new String[] {
                "\u0414\u043e\u0431\u0440\u043e\u0435 \u0443\u0442\u0440\u043e",
                "\u0414\u043e\u0431\u0440\u044b\u0439 \u0434\u0435\u043d\u044c",
                "\u0414\u043e\u0431\u0440\u044b\u0439 \u0432\u0435\u0447\u0435\u0440",
                "\u0414\u043e\u0431\u0440\u043e\u0439 \u043d\u043e\u0447\u0438"
        };
        createMessageResources(ru, template, "MessageResources_ru.properties", "Russian message resources");

        Properties ua = new Properties();
        template = new String[] {
                "\u0414\u043e\u0431\u0440\u043e\u0433\u043e \u0440\u0430\u043d\u043a\u0443",
                "\u0414\u043e\u0431\u0440\u043e\u0433\u043e \u0434\u043d\u044f",
                "\u0414\u043e\u0431\u0440\u043e\u0433\u043e \u0432\u0435\u0447\u043e\u0440\u0430",
                "\u0414\u043e\u0431\u0440\u043e\u0457 \u043d\u043e\u0447\u0456"
        };
        createMessageResources(ua, template, "MessageResources_ua.properties", "Ukrainian message resources");

        Calendar calendar = new GregorianCalendar(targetTimeZone);
        calendar.setTime(new Date());
        int targetHours = calendar.get(Calendar.HOUR_OF_DAY);

        System.out.println(getGreetings(currentLocale, cityName, targetHours));
    }

    public static void createMessageResources(Properties p, String[] template, String fileName, String comment) {
        if (template.length != 4) {
            throw new IllegalArgumentException("Template should consist of four elements");
        }
        p.setProperty("morning", template[0]);
        p.setProperty("afternoon", template[1]);
        p.setProperty("evening", template[2]);
        p.setProperty("night", template[3]);
        try (Writer fileOut = new FileWriter(FILE_PATH + fileName)) {
            p.store(fileOut, comment);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static String getGreetings(Locale currentLocale, String cityName, int targetHours) {
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
        ResourceBundle resourceBundle = ResourceBundle.getBundle("MessageResources", currentLocale);
        String message = resourceBundle.getString(times);
        try {
            byte[] utf8Bytes = message.getBytes("ISO-8859-1");
            message = new String(utf8Bytes,"UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return MessageFormat.format("{0}, {1}!", message, cityName);
    }

    public static Locale getCurrentLocale() {
        return Locale.getDefault();
    }

    public static void setCurrentLocale(Locale currentLocale) {
        Locale.setDefault(currentLocale);
    }
}
