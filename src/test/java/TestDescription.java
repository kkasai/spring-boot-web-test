import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class TestDescription extends TestWatcher {
    private Description description;

    @Override
    protected void starting(Description d) {
        description = d;
    }

    public Description getDescription() {
        return description;
    }

}
