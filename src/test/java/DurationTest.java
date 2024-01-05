import com.artformgames.plugin.tempflight.utils.TimeUtils;
import org.junit.Test;

public class DurationTest {

    @Test
    public void testDuration() {
        System.out.println(TimeUtils.parseDuration("1d2h3m4s"));
    }

}
