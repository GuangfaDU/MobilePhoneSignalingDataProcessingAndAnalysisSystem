import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public class TestSplit {

    @Test
    public void testSplit() {
        Pattern pattern = Pattern.compile("\\d+[.]?\\d+");
        Matcher matcher = pattern.matcher("[[ 22.75450195 114.35973236]]");
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    @Test
    public void testSplitFile() {
        File file = new File("D:\\GuangfaDU\\data\\temp\\python\\test.csv");
        System.out.println(file.getName().split("\\.")[0]);
    }
}
