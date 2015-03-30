/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.Math;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public abstract class Interpolation<Type>
{

    private Type startValue;
    private Type endValue;

    public Interpolation(Type startValue, Type endValue)
    {
        this.startValue = startValue;
        this.endValue = endValue;
    }

    public abstract Type GetValue(float factor);

    public final Type GetStartValue()
    {
        return startValue;
    }

    public final Type GetEndValue()
    {
        return endValue;
    }

}
