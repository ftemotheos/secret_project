package com.ftemotheos.tasks.secret_task1;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

public class HelloCityTest {

    private static final Logger logger = Logger.getLogger("com.ftemotheos.tasks.secret_task1");

    private static Map<String, String[]> testArgs = new HashMap<>();

    private static Map<String, Integer> testHours = new HashMap<>();

    private static Map<String, Locale> testLocales = new HashMap<>();

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private String[] args;

    private String greetings;

    private Locale targetLocale;

    public static void main(String[] args) {
        Properties loggingConfig = new Properties();
        loggingConfig.setProperty("handlers", "java.util.logging.FileHandler");
        loggingConfig.setProperty(".level", "ALL");
        loggingConfig.setProperty("java.util.logging.FileHandler.level", "ALL");
        loggingConfig.setProperty("java.util.logging.FileHandler.formatter", "java.util.logging.SimpleFormatter");
        loggingConfig.setProperty("java.util.logging.FileHandler.limit", "0");
        loggingConfig.setProperty("java.util.logging.FileHandler.count", "10");
        loggingConfig.setProperty("java.util.logging.FileHandler.pattern", "HelloCity%g.log");
        loggingConfig.setProperty("java.util.logging.ConsoleHandler.level", "ALL");
        loggingConfig.setProperty("java.util.logging.ConsoleHandler.formatter", "java.util.logging.SimpleFormatter");
        try (Writer fileOut = new FileWriter(HelloCity.FILE_PATH + "logging.properties")) {
            loggingConfig.store(fileOut, "Project logging configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            LogManager.getLogManager().readConfiguration(HelloCityTest.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        org.junit.runner.JUnitCore.main("com.ftemotheos.tasks.secret_task1.HelloCityTest");
    }

    public static int getHour(String timeZone) {
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone(timeZone));
        calendar.setTime(new Date());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    @BeforeClass
    public static void setUpData() {
        testArgs.put("Null", null);
        testArgs.put("Empty", new String[] {});
        testArgs.put("FirstNull", new String[] {null});
        testArgs.put("FirstZeroLength", new String[] {""});
        testArgs.put("FirstAny", new String[] {"Any city"});
        testArgs.put("FirstNewYork", new String[] {"New York"});
        testArgs.put("FirstAnyCitySecondNull", new String[] {"Any city", null});
        testArgs.put("FirstAnySecondGMT", new String[] {"Any city", "GMT"});
        testArgs.put("FirstNewYorkSecondGMT", new String[] {"New York", "GMT"});
        testArgs.put("FirstAnySecondAmericaNewYork", new String[] {"Any city", "America/New_York"});
        testArgs.put("FirstNewYorkSecondAmericaNewYork", new String[] {"New York", "America/New_York"});
        testArgs.put("FirstAnySecondABRACADABRA", new String[] {"Any city", "ABRACADABRA"});
        testArgs.put("FirstNewYorkSecondABRACADABRA", new String[] {"New York", "ABRACADABRA"});
        testArgs.put("FirstNewYorkSecondGMTThirdAny", new String[] {"New York", "GMT", "Anything else"});
        testHours.put("GMT", getHour("GMT"));
        testHours.put("NewYork", getHour("America/New_York"));
        testHours.put("LosAngeles", getHour("America/Los_Angeles"));
        testLocales.put("en", Locale.forLanguageTag("en"));
        testLocales.put("ru", Locale.forLanguageTag("ru"));
        testLocales.put("ua", Locale.forLanguageTag("ua"));
    }

    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testNull() {
        logger.info("Test main with args[] = null");
        try {
            HelloCity.main(testArgs.get("Null"));
            fail("Expected an NullPointerException to be thrown");
            logger.info("Failed!");
        } catch (NullPointerException e) {
            assertThat(e.getMessage(), is("Null instead of command line arguments has been passed"));
            logger.info("Passed!");
        }
    }

    @Test
    public void testEmpty() {
        logger.info("Test main with args[] = " + Arrays.toString(testArgs.get("Empty")));
        try {
            HelloCity.main(testArgs.get("Empty"));
            fail("Expected an IllegalArgumentException to be thrown");
            logger.info("Failed!");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("No one command line arguments has been passed. Must provide one or two"));
            logger.info("Passed!");
        }
    }

    @Test
    public void testFirstNull() {
        logger.info("Test main with args[] = " + Arrays.toString(testArgs.get("FirstNull")));
        try {
            HelloCity.main(testArgs.get("FirstNull"));
            fail("Expected an NullPointerException to be thrown");
            logger.info("Failed!");
        } catch (NullPointerException e) {
            assertThat(e.getMessage(), is("Null first argument of command line has been passed"));
            logger.info("Passed!");
        }
    }

    @Test
    public void testFirstZeroLength() {
        logger.info("Test main with args[] = [\"\"]");
        try {
            HelloCity.main(testArgs.get("FirstZeroLength"));
            fail("Expected an IllegalArgumentException to be thrown");
            logger.info("Failed!");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Must provide city name"));
            logger.info("Passed!");
        }
    }

    @Test
    public void testFirstAny() {
        args = testArgs.get("FirstAny");
        logger.info("Test main with args[] = " + Arrays.toString(args));
        outContent.reset();
        HelloCity.main(args);
        try {
            assertEquals("Failure! Message is incorrect",
                    HelloCity.getGreetings(HelloCity.getCurrentLocale(), args[0], testHours.get("GMT")),
                    outContent.toString().trim());
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testFirstNewYork() {
        args = testArgs.get("FirstNewYork");
        logger.info("Test main with args[] = " + Arrays.toString(args));
        outContent.reset();
        HelloCity.main(args);
        try {
            assertEquals("Failure! Message is incorrect",
                    HelloCity.getGreetings(HelloCity.getCurrentLocale(), args[0], testHours.get("NewYork")),
                    outContent.toString().trim());
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testFirstAnyCitySecondNull() {
        args = testArgs.get("FirstAnyCitySecondNull");
        logger.info("Test main with args[] = " + Arrays.toString(args));
        try {
            HelloCity.main(args);
            fail("Expected an NullPointerException to be thrown");
            logger.info("Failed!");
        } catch (NullPointerException e) {
            assertThat(e.getMessage(), is("Null second argument of command line has been passed"));
            logger.info("Passed!");
        }
    }

    @Test
    public void testFirstAnySecondGMT() {
        args = testArgs.get("FirstAnySecondGMT");
        logger.info("Test main with args[] = " + Arrays.toString(args));
        outContent.reset();
        HelloCity.main(args);
        try {
            assertEquals("Failure! Message is incorrect",
                    HelloCity.getGreetings(HelloCity.getCurrentLocale(), args[0], testHours.get("GMT")),
                    outContent.toString().trim());
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testFirstNewYorkSecondGMT() {
        args = testArgs.get("FirstNewYorkSecondGMT");
        logger.info("Test main with args[] = " + Arrays.toString(args));
        outContent.reset();
        HelloCity.main(args);
        try {
            assertEquals("Failure! Message is incorrect",
                    HelloCity.getGreetings(HelloCity.getCurrentLocale(), args[0], testHours.get("GMT")),
                    outContent.toString().trim());
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testFirstAnySecondAmericaNewYork() {
        args = testArgs.get("FirstAnySecondAmericaNewYork");
        logger.info("Test main with args[] = " + Arrays.toString(args));
        outContent.reset();
        HelloCity.main(args);
        try {
            assertEquals("Failure! Message is incorrect",
                    HelloCity.getGreetings(HelloCity.getCurrentLocale(), args[0], testHours.get("NewYork")),
                    outContent.toString().trim());
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testFirstNewYorkSecondAmericaNewYork() {
        args = testArgs.get("FirstNewYorkSecondAmericaNewYork");
        logger.info("Test main with args[] = " + Arrays.toString(args));
        outContent.reset();
        HelloCity.main(args);
        try {
            assertEquals("Failure! Message is incorrect",
                    HelloCity.getGreetings(HelloCity.getCurrentLocale(), args[0], testHours.get("NewYork")),
                    outContent.toString().trim());
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testFirstAnySecondABRACADABRA() {
        args = testArgs.get("FirstAnySecondABRACADABRA");
        logger.info("Test main with args[] = " + Arrays.toString(args));
        outContent.reset();
        HelloCity.main(args);
        try {
            assertEquals("Failure! Message is incorrect",
                    HelloCity.getGreetings(HelloCity.getCurrentLocale(), args[0], testHours.get("GMT")),
                    outContent.toString().trim());
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testFirstNewYorkSecondABRACADABRA() {
        args = testArgs.get("FirstNewYorkSecondABRACADABRA");
        logger.info("Test main with args[] = " + Arrays.toString(args));
        outContent.reset();
        HelloCity.main(args);
        try {
            assertEquals("Failure! Message is incorrect",
                    HelloCity.getGreetings(HelloCity.getCurrentLocale(), args[0], testHours.get("NewYork")),
                    outContent.toString().trim());
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testFirstNewYorkSecondGMTThirdAny() {
        args = testArgs.get("FirstNewYorkSecondGMTThirdAny");
        logger.info("Test main with args[] = " + Arrays.toString(args));
        try {
            HelloCity.main(args);
            fail("Expected an IllegalArgumentException to be thrown");
            logger.info("Failed!");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Incorrect number of incoming command line arguments. Must provide one or two"));
            logger.info("Passed!");
        }
    }

    @Test
    public void testEnNewYork0() {
        targetLocale = testLocales.get("en");
        logger.info("Test getGreetings with language=en, city=New York, time=00:00");
        HelloCity.setCurrentLocale(targetLocale);
        greetings = HelloCity.getGreetings(targetLocale, "New York", 0);
        try {
            assertEquals("Failure! Message is incorrect", "Good night, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testEnNewYork6() {
        targetLocale = testLocales.get("en");
        logger.info("Test getGreetings with language=en, city=New York, time=06:00");
        HelloCity.setCurrentLocale(targetLocale);
        greetings = HelloCity.getGreetings(targetLocale, "New York", 6);
        try {
            assertEquals("Failure! Message is incorrect", "Good morning, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testEnNewYork9() {
        targetLocale = testLocales.get("en");
        logger.info("Test getGreetings with language=en, city=New York, time=09:00");
        HelloCity.setCurrentLocale(targetLocale);
        greetings = HelloCity.getGreetings(targetLocale, "New York", 9);
        try {
            assertEquals("Failure! Message is incorrect", "Good afternoon, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testEnNewYork19() {
        targetLocale = testLocales.get("en");
        logger.info("Test getGreetings with language=en, city=New York, time=19:00");
        HelloCity.setCurrentLocale(targetLocale);
        greetings = HelloCity.getGreetings(targetLocale, "New York", 19);
        try {
            assertEquals("Failure! Message is incorrect", "Good evening, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testEnNewYork23() {
        targetLocale = testLocales.get("en");
        logger.info("Test getGreetings with language=en, city=New York, time=23:00");
        HelloCity.setCurrentLocale(targetLocale);
        greetings = HelloCity.getGreetings(targetLocale, "New York", 23);
        try {
            assertEquals("Failure! Message is incorrect", "Good night, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testRuNewYork0() {
        targetLocale = testLocales.get("ru");
        logger.info("Test getGreetings with language=ru, city=New York, time=00:00");
        HelloCity.setCurrentLocale(targetLocale);
        greetings = HelloCity.getGreetings(targetLocale, "New York", 0);
        try {
            assertEquals("Failure! Message is incorrect", "Доброй ночи, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testRuNewYork6() {
        targetLocale = testLocales.get("ru");
        logger.info("Test getGreetings with language=ru, city=New York, time=06:00");
        HelloCity.setCurrentLocale(targetLocale);
        greetings = HelloCity.getGreetings(targetLocale, "New York", 6);
        try {
            assertEquals("Failure! Message is incorrect", "Доброе утро, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testRuNewYork9() {
        targetLocale = testLocales.get("ru");
        logger.info("Test getGreetings with language=ru, city=New York, time=09:00");
        HelloCity.setCurrentLocale(targetLocale);
        greetings = HelloCity.getGreetings(targetLocale, "New York", 9);
        try {
            assertEquals("Failure! Message is incorrect", "Добрый день, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testRuNewYork19() {
        targetLocale = testLocales.get("ru");
        logger.info("Test getGreetings with language=ru, city=New York, time=19:00");
        HelloCity.setCurrentLocale(targetLocale);
        greetings = HelloCity.getGreetings(targetLocale, "New York", 19);
        try {
            assertEquals("Failure! Message is incorrect", "Добрый вечер, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testRuNewYork23() {
        targetLocale = testLocales.get("ru");
        logger.info("Test getGreetings with language=ru, city=New York, time=23:00");
        HelloCity.setCurrentLocale(targetLocale);
        greetings = HelloCity.getGreetings(targetLocale, "New York", 23);
        try {
            assertEquals("Failure! Message is incorrect", "Доброй ночи, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testUaNewYork0() {
        targetLocale = testLocales.get("ua");
        logger.info("Test getGreetings with language=ua, city=New York, time=00:00");
        HelloCity.setCurrentLocale(targetLocale);
        greetings = HelloCity.getGreetings(targetLocale, "New York", 0);
        try {
            assertEquals("Failure! Message is incorrect", "Доброї ночі, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testUaNewYork6() {
        targetLocale = testLocales.get("ua");
        logger.info("Test getGreetings with language=ua, city=New York, time=06:00");
        HelloCity.setCurrentLocale(targetLocale);
        greetings = HelloCity.getGreetings(targetLocale, "New York", 6);
        try {
            assertEquals("Failure! Message is incorrect", "Доброго ранку, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testUaNewYork9() {
        targetLocale = testLocales.get("ua");
        logger.info("Test getGreetings with language=ua, city=New York, time=09:00");
        HelloCity.setCurrentLocale(targetLocale);
        greetings = HelloCity.getGreetings(targetLocale, "New York", 9);
        try {
            assertEquals("Failure! Message is incorrect", "Доброго дня, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testUaNewYork19() {
        targetLocale = testLocales.get("ua");
        logger.info("Test getGreetings with language=ua, city=New York, time=19:00");
        HelloCity.setCurrentLocale(targetLocale);
        greetings = HelloCity.getGreetings(targetLocale, "New York", 19);
        try {
            assertEquals("Failure! Message is incorrect", "Доброго вечора, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testUaNewYork23() {
        targetLocale = testLocales.get("ua");
        logger.info("Test getGreetings with language=ua, city=New York, time=23:00");
        HelloCity.setCurrentLocale(targetLocale);
        greetings = HelloCity.getGreetings(targetLocale, "New York", 23);
        try {
            assertEquals("Failure! Message is incorrect", "Доброї ночі, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testEnMoscow0() {
        targetLocale = testLocales.get("en");
        logger.info("Test getGreetings with language=en, city=Moscow, time=00:00");
        HelloCity.setCurrentLocale(targetLocale);
        greetings = HelloCity.getGreetings(targetLocale, "Moscow", 0);
        try {
            assertEquals("Failure! Message is incorrect", "Good night, Moscow!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @Test
    public void testEnKiev0() {
        targetLocale = testLocales.get("en");
        logger.info("Test getGreetings with language=en, city=Kiev, time=00:00");
        HelloCity.setCurrentLocale(targetLocale);
        greetings = HelloCity.getGreetings(targetLocale, "Kiev", 0);
        try {
            assertEquals("Failure! Message is incorrect", "Good night, Kiev!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");
    }

    @After
    public void cleanUpStream() {
        System.setOut(null);
    }
}
