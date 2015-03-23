/*
 * Copyright (C) 2015 Sammy Guergachi <sguergachi at gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.company.Animation2D;

import java.util.ArrayList;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Timeline<Type>
{

    private ArrayList<Keyframe<Type>> keyframes;
    private Keyframe currentFrame;
    private int currentFrameIndex;
    private int startIndex = 0;
    private double keyDelta;
    private boolean running = true;
    private Action switchKeyframesAction;

    public Timeline()
    {
        keyframes = new ArrayList<>();

    }

    public Timeline AddKeyframe(Keyframe<Type> key)
    {
        keyframes.add(key);
        if (currentFrame == null)
        {
            currentFrame = key;
        }
        return this;
    }

    public final void Update(double delta)
    {
        if (!running)
        {
            return;
        }
        keyDelta += delta;
        if (keyDelta > currentFrame.GetDuration())
        {
            currentFrame.KeyframeExitAction();
            currentFrame = GetNextFrame();
            FrameSwitch();
            currentFrame.KeyframeEnterAction();
            keyDelta = 0.0d;
        }
    }

    public final Timeline SetStartIndex(int startIndex)
    {
        if (startIndex >= keyframes.size())
        {
            throw new RuntimeException("Invali keyframe");
        }
        this.startIndex = startIndex;
        this.currentFrameIndex = startIndex;
        this.currentFrame = keyframes.get(this.currentFrameIndex);
        return this;
    }

    private final Keyframe GetNextFrame()
    {
        int nextIndex = currentFrameIndex + 1;
        if (nextIndex >= keyframes.size())
        {
            nextIndex = 0;
        }
        currentFrameIndex = nextIndex;
        return keyframes.get(currentFrameIndex);
    }

    public final Object GetCurrentKeyValue(String name)
    {
        return currentFrame.GetData(name);
    }

    public final Timeline SetRunning(boolean enable)
    {
        this.running = enable;
        return this;
    }

    public final Timeline SetSwitchKeysAction(Action action)
    {
        this.switchKeyframesAction = action;
        return this;
    }

    private final void FrameSwitch()
    {
        if (switchKeyframesAction != null)
        {
            switchKeyframesAction.Perform();
        }
    }

}
