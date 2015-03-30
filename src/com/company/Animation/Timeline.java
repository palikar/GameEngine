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
package com.company.Animation;

import java.util.ArrayList;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Timeline<Type>
{

    private ArrayList<Keyframe<Type>> keyframes;
    private Keyframe lastFrame;
    private int lastFrameIndex = 0;
    private int startIndex = 0;
    private float keyDelta;
    private boolean running = false;
    private Action switchKeyframesAction;
    private float time;
    private boolean repeat = false;
    private static final Keyframe<Object> infinity = new Keyframe<>(Float.MAX_VALUE);

    public Timeline(float time)
    {
        keyframes = new ArrayList<>();
        this.time = time;
        keyDelta = 0.0f;

    }

    public Timeline AddKeyframe(Keyframe<Type> key)
    {
        return AddKeyframe(key, keyframes.size());
    }

    public Timeline AddKeyframe(Keyframe<Type> key, int position)
    {
        keyframes.add(position, key);
        if (lastFrame == null)
        {
            lastFrame = key;
            lastFrameIndex = position;
        }
        return this;
    }

    public void Update(double delta)
    {
        if (!running)
        {
            return;
        }

        keyDelta += delta;
        Keyframe nextFramekey = GetNextKeyframe();

        if (keyDelta >= nextFramekey.GetTimePosition())
        {
            lastFrame.KeyframeExitAction();
            lastFrame = nextFramekey;
            lastFrameIndex++;
            FrameSwitch();
            lastFrame.KeyframeEnterAction();
        }
        if (keyDelta >= time)
        {
            keyDelta = 0.0f;
            lastFrame = keyframes.get(0);
            lastFrameIndex = 0;
            if (!repeat)
            {
                running = false;
            }
        }

    }

    public final Timeline SetStartIndex(int startIndex)
    {
        if (startIndex >= keyframes.size())
        {
            throw new RuntimeException("Invali keyframe");
        }
        this.startIndex = startIndex;
        this.lastFrameIndex = startIndex;
        this.lastFrame = keyframes.get(this.lastFrameIndex);
        return this;
    }

    public Type GetDataAt(int index, String name)
    {
        return keyframes.get(index).GetData(name);

    }

    public final Keyframe GetLastKeyframe()
    {
        return lastFrame;
    }

    public final Keyframe GetNextKeyframe()
    {
        int index = lastFrameIndex + 1;
        if (index >= keyframes.size())
        {
            return infinity;
        }
        return keyframes.get(index);
    }

    public final int GetLastKeyframeIndex()
    {
        return lastFrameIndex;
    }

    public final int GetNextKeyframeIndex()
    {
        int index = lastFrameIndex + 1;
        return index >= keyframes.size() ? keyframes.size() - 1 : index;
    }

    public final Timeline SetRunning(boolean enable)
    {
        this.running = enable;
        return this;
    }

    public final Timeline SetRepeat(boolean enable)
    {
        this.repeat = enable;
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

    public float GetCurrentDelta()
    {
        return keyDelta;
    }

    public int GetKeyframesCount()
    {
        return keyframes.size();
    }

    public boolean IsRunning()
    {
        return running;
    }

    public Timeline Stop()
    {
        running = false;
        return this;
    }

    public Timeline Reset()
    {
        keyDelta = 0.0f;
        lastFrame = keyframes.get(0);
        lastFrameIndex = 0;
        return this;
    }

    public Timeline Start()
    {
        running = true;
        return this;
    }

}
