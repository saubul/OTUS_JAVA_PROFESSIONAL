package testSuite;

import annotation.Disabled;

@Disabled(reason = "Класс DisabledTestSuite отключен!")
public class DisabledTestSuite {

    public static void testMethod1() {
        System.out.println(DisabledTestSuite.class.getSimpleName() + ": testMethod1");
    }

    public static void testMethod2() {
        System.out.println(DisabledTestSuite.class.getSimpleName() + ": testMethod2");
    }

    public static void testMethod3() {
        System.out.println(DisabledTestSuite.class.getSimpleName() + ": testMethod3");
    }

}
