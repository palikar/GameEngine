/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.Animation2D;

import com.company.Math.Transform;
import java.util.ArrayList;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class TransformTimeline extends Timeline<Transform>
{

    private ArrayList<TransformInterpolation> interpolators;
    private Transform dest;
    private float step;
    Transform start;

    public TransformTimeline(Transform start, float time, Transform dest)
    {
        super(time);
        this.start = start;
        interpolators = new ArrayList<>();
        this.dest = dest;
        AddKeyframe(new Keyframe<Transform>(0.0f).PutKeyValue("transform", start));
    }

    public void AddTransform(Transform transform, float timePosition)
    {
        AddTransform(transform, timePosition, GetKeyframesCount());

    }

    public void AddTransform(Transform transform, float timePosition, int indexPosition)
    {
        AddKeyframe(
                new Keyframe<Transform>(timePosition).PutKeyValue("transform", transform), indexPosition);
        interpolators.add(
                new TransformInterpolation(GetDataAt(indexPosition - 1, "transform"), transform));
    }

    @Override
    public void Update(double delta)
    {
        super.Update(delta);
        if (!IsRunning())
        {
            return;
        }
        Transform newTransform = GetCurrenteTransform();
        dest.SetPosition(newTransform.GetPosition());
        dest.SetRotation(newTransform.GetRotation());
        dest.SetScale(newTransform.GetScale());
    }

    public Transform GetCurrenteTransform()
    {
        int startIndex = GetLastKeyframeIndex();
       // System.out.println(startIndex);

        Keyframe<Transform> startKey = GetLastKeyframe();
        Keyframe<Transform> endKey = GetNextKeyframe();

        TransformInterpolation interpolator = interpolators.get(startIndex);

        float interpolationFactor = (GetCurrentDelta() - startKey.GetTimePosition()) / (endKey.GetTimePosition() - startKey.GetTimePosition());

        return interpolator.GetValue(interpolationFactor);
    }

    public Transform GetTranformAt(int keyframeIndex)
    {
        return GetDataAt(keyframeIndex, "transform");
    }
    
    

}
