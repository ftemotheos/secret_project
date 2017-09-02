package com.ftemotheos.tasks.secret_task1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

public class HelloCityTest {
    private static final Logger logger = Logger.getLogger("com.ftemotheos.tasks.secret_task1");
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private int testHours1, testHours2;

    public static void main(String[] args) {
        if (System.getProperty("java.util.logging.config.class") == null
                && System.getProperty("java.util.logging.config.file") == null)
        {
            try
            {
                logger.setLevel(Level.ALL);
                final int LOG_ROTATION_COUNT = 10;
                Handler handler =
                        new FileHandler("HelloCity.log", 0, LOG_ROTATION_COUNT);
                logger.addHandler(handler);
            }
            catch (IOException e)
            {
                logger.log(Level.SEVERE, "Can't create log file handler", e);
            }
        }
        org.junit.runner.JUnitCore.main("com.ftemotheos.tasks.secret_task1.HelloCityTest");
    }

    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }

    @Before
    public void setUpData() {
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        calendar.setTime(new Date());
        testHours1 = calendar.get(Calendar.HOUR_OF_DAY);
        calendar = new GregorianCalendar(TimeZone.getTimeZone("America/New_York"));
        calendar.setTime(new Date());
        testHours2 = calendar.get(Calendar.HOUR_OF_DAY);
    }

    @Test
    public void testMain() throws Exception {
        logger.info("Test main with args = null");
        try {
            HelloCity.main(null);
            fail("Expected an NullPointerException to be thrown");
            logger.info("Failed!");
        } catch (NullPointerException e) {
            assertThat(e.getMessage(), is("Null command line argument has been passed"));
            logger.info("Passed!");
        }

        String[] args = new String[] {};
        logger.info("Test main with args = " + Arrays.toString(args));
        try {
            HelloCity.main(args);
            fail("Expected an IllegalArgumentException to be thrown");
            logger.info("Failed!");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("No one command line arguments has been passed. Must provide one or two"));
            logger.info("Passed!");
        }

        args = new String[] {""};
        logger.info("Test main with args = [ ]");
        try {
            HelloCity.main(args);
            fail("Expected an IllegalArgumentException to be thrown");
            logger.info("Failed!");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Must provide city name"));
            logger.info("Passed!");
        }

        args = new String[] {"Any city"};
        logger.info("Test main with args = " + Arrays.toString(args));
        logger.warning("This test is correct only if the program under test and the test program are in the same locale");
        outContent.reset();
        HelloCity.main(args);
        try {
            assertEquals("Failure! Message is incorrect",
                    HelloCity.getGreetings(Locale.getDefault(), args[0], testHours1),
                    outContent.toString().trim());
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        args = new String[] {"Any city", "GMT"};
        logger.info("Test main with args = " + Arrays.toString(args));
        logger.warning("This test is correct only if the program under test and the test program are in the same locale");
        outContent.reset();
        HelloCity.main(args);
        try {
            assertEquals("Failure! Message is incorrect",
                    HelloCity.getGreetings(Locale.getDefault(), args[0], testHours1),
                    outContent.toString().trim());
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        args = new String[] {"Any city", "America/New_York"};
        logger.info("Test main with args = " + Arrays.toString(args));
        logger.warning("This test is correct only if the program under test and the test program are in the same locale");
        outContent.reset();
        HelloCity.main(args);
        try {
            assertEquals("Failure! Message is incorrect",
                    HelloCity.getGreetings(Locale.getDefault(), args[0], testHours2),
                    outContent.toString().trim());
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        args = new String[] {"Any city", "ABRACADABRA"};
        logger.info("Test main with args = " + Arrays.toString(args));
        logger.warning("This test is correct only if the program under test and the test program are in the same locale");
        outContent.reset();
        HelloCity.main(args);
        try {
            assertEquals("Failure! Message is incorrect",
                    HelloCity.getGreetings(Locale.getDefault(), args[0], testHours1),
                    outContent.toString().trim());
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        args = new String[] {"New York"};
        logger.info("Test main with args = " + Arrays.toString(args));
        logger.warning("This test is correct only if the program under test and the test program are in the same locale");
        outContent.reset();
        HelloCity.main(args);
        try {
            assertEquals("Failure! Message is incorrect",
                    HelloCity.getGreetings(Locale.getDefault(), args[0], testHours2),
                    outContent.toString().trim());
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        args = new String[] {"New York", "GMT"};
        logger.info("Test main with args = " + Arrays.toString(args));
        logger.warning("This test is correct only if the program under test and the test program are in the same locale");
        outContent.reset();
        HelloCity.main(args);
        try {
            assertEquals("Failure! Message is incorrect",
                    HelloCity.getGreetings(Locale.getDefault(), args[0], testHours1),
                    outContent.toString().trim());
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        args = new String[] {"New York", "America/New_York"};
        logger.info("Test main with args = " + Arrays.toString(args));
        logger.warning("This test is correct only if the program under test and the test program are in the same locale");
        outContent.reset();
        HelloCity.main(args);
        try {
            assertEquals("Failure! Message is incorrect",
                    HelloCity.getGreetings(Locale.getDefault(), args[0], testHours2),
                    outContent.toString().trim());
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        args = new String[] {"New York", "ABRACADABRA"};
        logger.info("Test main with args = " + Arrays.toString(args));
        logger.warning("This test is correct only if the program under test and the test program are in the same locale");
        outContent.reset();
        HelloCity.main(args);
        try {
            assertEquals("Failure! Message is incorrect",
                    HelloCity.getGreetings(Locale.getDefault(), args[0], testHours2),
                    outContent.toString().trim());
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        args = new String[] {"New York", "GMT", "Anything else"};
        logger.info("Test main with args = " + Arrays.toString(args));
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
    public void testGetGreetings() throws Exception {
        String greetings;

        logger.info("Test getGreetings with language=en, city=New York, time=00:00");
        greetings = HelloCity.getGreetings(Locale.forLanguageTag("en"), "New York", 0);
        try {
            assertEquals("Failure! Message is incorrect", "Good night, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        logger.info("Test getGreetings with language=en, city=New York, time=06:00");
        greetings = HelloCity.getGreetings(Locale.forLanguageTag("en"), "New York", 6);
        try {
            assertEquals("Failure! Message is incorrect", "Good morning, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        logger.info("Test getGreetings with language=en, city=New York, time=09:00");
        greetings = HelloCity.getGreetings(Locale.forLanguageTag("en"), "New York", 9);
        try {
            assertEquals("Failure! Message is incorrect", "Good afternoon, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        logger.info("Test getGreetings with language=en, city=New York, time=19:00");
        greetings = HelloCity.getGreetings(Locale.forLanguageTag("en"), "New York", 19);
        try {
            assertEquals("Failure! Message is incorrect", "Good evening, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        logger.info("Test getGreetings with language=en, city=New York, time=23:00");
        greetings = HelloCity.getGreetings(Locale.forLanguageTag("en"), "New York", 23);
        try {
            assertEquals("Failure! Message is incorrect", "Good night, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        logger.info("Test getGreetings with language=ru, city=New York, time=00:00");
        greetings = HelloCity.getGreetings(Locale.forLanguageTag("ru"), "New York", 0);
        try {
            assertEquals("Failure! Message is incorrect", "Доброй ночи, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        logger.info("Test getGreetings with language=ru, city=New York, time=06:00");
        greetings = HelloCity.getGreetings(Locale.forLanguageTag("ru"), "New York", 6);
        try {
            assertEquals("Failure! Message is incorrect", "Доброе утро, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        logger.info("Test getGreetings with language=ru, city=New York, time=09:00");
        greetings = HelloCity.getGreetings(Locale.forLanguageTag("ru"), "New York", 9);
        try {
            assertEquals("Failure! Message is incorrect", "Добрый день, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        logger.info("Test getGreetings with language=ru, city=New York, time=19:00");
        greetings = HelloCity.getGreetings(Locale.forLanguageTag("ru"), "New York", 19);
        try {
            assertEquals("Failure! Message is incorrect", "Добрый вечер, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        logger.info("Test getGreetings with language=ru, city=New York, time=23:00");
        greetings = HelloCity.getGreetings(Locale.forLanguageTag("ru"), "New York", 23);
        try {
            assertEquals("Failure! Message is incorrect", "Доброй ночи, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        logger.info("Test getGreetings with language=ua, city=New York, time=00:00");
        greetings = HelloCity.getGreetings(Locale.forLanguageTag("ua"), "New York", 0);
        try {
            assertEquals("Failure! Message is incorrect", "Доброї ночі, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        logger.info("Test getGreetings with language=ua, city=New York, time=06:00");
        greetings = HelloCity.getGreetings(Locale.forLanguageTag("ua"), "New York", 6);
        try {
            assertEquals("Failure! Message is incorrect", "Доброго ранку, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        logger.info("Test getGreetings with language=ua, city=New York, time=09:00");
        greetings = HelloCity.getGreetings(Locale.forLanguageTag("ua"), "New York", 9);
        try {
            assertEquals("Failure! Message is incorrect", "Доброго дня, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        logger.info("Test getGreetings with language=ua, city=New York, time=19:00");
        greetings = HelloCity.getGreetings(Locale.forLanguageTag("ua"), "New York", 19);
        try {
            assertEquals("Failure! Message is incorrect", "Доброго вечора, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        logger.info("Test getGreetings with language=ua, city=New York, time=23:00");
        greetings = HelloCity.getGreetings(Locale.forLanguageTag("ua"), "New York", 23);
        try {
            assertEquals("Failure! Message is incorrect", "Доброї ночі, New York!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        logger.info("Test getGreetings with language=en, city=Moscow, time=00:00");
        greetings = HelloCity.getGreetings(Locale.forLanguageTag("en"), "Moscow", 0);
        try {
            assertEquals("Failure! Message is incorrect", "Good night, Moscow!", greetings);
        } catch (AssertionError e) {
            logger.info("Failed!");
            throw new AssertionError(e.getMessage());
        }
        logger.info("Passed!");

        logger.info("Test getGreetings with language=en, city=Kiev, time=00:00");
        greetings = HelloCity.getGreetings(Locale.forLanguageTag("en"), "Kiev", 0);
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
