package com.company.Util;

/**
 * Created by Stanislav on 15.2.2015 г..
 */
public class Time {
    private static long SECOND = 1000000000L;

    public static double GetTime() {
        return (double) System.nanoTime() / (double) SECOND;
    }

}
