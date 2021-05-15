package cn.edu.szu.algo.util;

/**
 * @author GuangfaDU
 * @version 1.0
 */
public class ComputeDistance {

    public static final double EARTH_RADIUS = 6371.393;

    public static double rad(double num) {
        return num * Math.PI / 180.0;
    }
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;
    }
}
