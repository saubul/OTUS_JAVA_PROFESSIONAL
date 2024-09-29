import testrunner.TestRunner;
import testsuite.CorrectTestSuite;
import testsuite.DisabledTestSuite;
import testsuite.MoreThanOneAnnotationTestSuite;
import testsuite.WrongTestSuite;

import java.lang.reflect.InvocationTargetException;

public class Application
{
    public static void main( String[] args ) throws InvocationTargetException, IllegalAccessException {
        System.out.println("---------------------------------------------------------------------------------\nTEST SUITE 1");
        TestRunner.run(CorrectTestSuite.class);
        System.out.println("---------------------------------------------------------------------------------\nTEST SUITE 2");
        TestRunner.run(DisabledTestSuite.class);
        System.out.println("---------------------------------------------------------------------------------\nTEST SUITE 3");
        TestRunner.run(MoreThanOneAnnotationTestSuite.class);
        System.out.println("---------------------------------------------------------------------------------\nTEST SUITE 4");
        TestRunner.run(WrongTestSuite.class);
        System.out.println("---------------------------------------------------------------------------------");
    }
}