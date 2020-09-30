import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class Main {

    public static void main(String[] args) {
        JUnitCore junit = new JUnitCore();
        Result result = junit.run(AfishaTesting.class);
        if (result.getFailures().size() != 0) {
            System.out.println(result.getFailures());
        }
    }

}