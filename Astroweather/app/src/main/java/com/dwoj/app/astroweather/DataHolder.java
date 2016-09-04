package com.dwoj.app.astroweather;

/**
 * Created by Lenovo on 2016-06-29.
 */
public class DataHolder {
    public static double longitude = 51.770;
    public static double latitude = 19.459;
    public static int frequencyOfRefreshing = 15;

    public static String getLatitude() {
        return String.valueOf(latitude);
    }

    public static String getLongitude() {
        return String.valueOf(longitude);
    }

    public static String getFrequency() { return String.valueOf(frequencyOfRefreshing);}

}
