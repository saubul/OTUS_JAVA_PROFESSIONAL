import testRunner.TestRunner;
import testSuite.CorrectTestSuite;
import testSuite.DisabledTestSuite;
import testSuite.MoreThanOneAnnotationTestSuite;
import testSuite.WrongTestSuite;

public class Application
{
    public static void main( String[] args )
    {
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