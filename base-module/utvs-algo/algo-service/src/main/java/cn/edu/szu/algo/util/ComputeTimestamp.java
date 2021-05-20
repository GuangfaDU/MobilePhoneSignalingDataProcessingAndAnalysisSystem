package cn.edu.szu.algo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public class ComputeTimestamp {

    public static long checkTimestamp(String time1, String time2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(time1).getTime() - simpleDateFormat.parse(time2).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
