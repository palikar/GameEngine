/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.Components.Components2D;

import com.company.Animation.Animator;
import com.company.Animation.Keyframe;
import com.company.Animation.Timeline;
import com.company.Math.Vector2f;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class SpriteAnimator extends PlaneTextureRenderer
{

    Animator<Vector2f> animator;

    public SpriteAnimator(int texture, Vector2f idle) throws IOException, URISyntaxException
    {
        super(texture);
        animator = new Animator<>(idle, offSet, "texCoords");
        animator.SetToTargetAction(() ->
        {
            offSet.Set(animator.GetCurrentContent("texCoords"));
        });
    }

    public SpriteAnimator(int texture, Vector2f[] texCoords) throws IOException, URISyntaxException
    {
        super(texture, texCoords);
    }

    public void AddAnimation(String name, ArrayList<Vector2f> texCoords, float time, boolean repeating)
    {
        float timeBetweenFrames = time / texCoords.size();
        Timeline<Vector2f> newLine = new Timeline<>(time);

        for (int i = 0; i < texCoords.size(); i++)
        {
            newLine.AddKeyframe(
                    new Keyframe<Vector2f>(i * timeBetweenFrames)
                    .PutKeyValue("texCoords", texCoords.get(i)));
        }
        newLine.SetRepeat(repeating);
        animator.AddAnimation(name, newLine);
    }

    public Animator<Vector2f> GetAnimator()
    {
        return animator;
    }

    @Override
    public void Update(double delta)
    {
        super.Update(delta);
        animator.Update(delta);
    }

}
