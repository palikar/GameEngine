package com.company.Math;

/**
 * Created by Stanislav on 15.2.2015 г..
 */
public class MathUtil
{
    public static final float PI = (float)Math.PI;


    public static double Clamp(double x, double upper, double lower)
    {
        if (x > upper)
            x = upper;
        else if (x < lower)
            x = lower;
        return x;
    }

    public static double Saturate(double x)
    {
        if (x > 1.0d)
        {
            return 1.0d;
        }
        if (x < 0.0d)
        {
            return 0.0d;
        }
        return x;
    }


}
