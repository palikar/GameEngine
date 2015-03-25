/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.Animation2D;

import com.company.Math.Quaternion;
import com.company.Math.Transform;
import com.company.Math.Vector3f;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class TransformInterpolation extends Interpolation<Transform>
{

    private Transform dest;
    private boolean rotationShortestPath = true;

    public TransformInterpolation(Transform startValue, Transform endValue)
    {
        super(startValue, endValue);
        dest = null;
    }

    public TransformInterpolation(Transform startValue, Transform endValue, Transform dest)
    {
        super(startValue, endValue);
        this.dest = dest;
    }

    @Override
    public Transform GetValue(float factor)
    {
        if (!(factor >= 0.0f || factor <= 1.0f))
        {
            throw new IllegalArgumentException("Foctor must be between 0.0f and 1.0f");
        }
        Vector3f newPosition = GetPosition(factor);
        Quaternion newRotation = GetRotation(factor);
        Vector3f newScale = GetScale(factor);
        if (dest != null)
        {
            dest.SetPosition(newPosition).SetRotation(newRotation).SetScale(newScale);
            return dest;
        }
        return new Transform().SetPosition(newPosition).SetRotation(newRotation).SetScale(newScale);
    }

    private Vector3f GetPosition(float factor)
    {
        if (GetStartValue().GetPosition().equals(GetEndValue().GetPosition()))
        {
            return GetStartValue().GetPosition();
        }
        return GetStartValue().GetPosition().Lerp(GetEndValue().GetPosition(), factor);
    }

    private Quaternion GetRotation(float factor)
    {
        if (GetStartValue().GetRotation().equals(GetEndValue().GetRotation()))
        {
            return GetStartValue().GetRotation();
        }
        return GetStartValue().GetRotation().NLerp(GetEndValue().GetRotation(), factor, rotationShortestPath);
    }

    private Vector3f GetScale(float factor)
    {
        if (GetStartValue().GetScale().equals(GetEndValue().GetScale()))
        {
            return GetStartValue().GetScale();
        }
        return GetStartValue().GetScale().Lerp(GetEndValue().GetScale(), factor);
    }

    public TransformInterpolation SetRotationShortestPath(boolean enable)
    {
        rotationShortestPath = enable;
        return this;
    }

}
