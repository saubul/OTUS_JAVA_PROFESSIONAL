package testSuite;

import annotation.AfterSuite;
import annotation.BeforeSuite;
import annotation.Disabled;
import annotation.Test;

public class CorrectTestSuite {

    @BeforeSuite
    public static void testMethod100() {
        System.out.println("BeforeSuite testMethod100");
    }

    @Test(priority = 8)
    public static void testMethod2() {
        System.out.println("testMethod2" + " ПРИОРИТЕТ 8");
    }

    @Test(priority = 8)
    public static void testMethod3() {
        System.out.println("testMethod3" + " ПРИОРИТЕТ 8");
    }

    @Disabled(reason = "testMethod4 отключен")
    @Test(priority = 4)
    public static void testMethod4() {
        System.out.println("testMethod4" + " ПРИОРИТЕТ 4");
    }

    @Test
    public static void testMethod5() {
        throw new RuntimeException("ИСКЛЮЧЕНИЕ в testMethod5!");
    }

    @AfterSuite
    public static void testMethod0() {
        System.out.println("AfterSuite testMethod0");
    }

    public static void testMethod7() {
        System.out.println("testMethod7");
    }

}
