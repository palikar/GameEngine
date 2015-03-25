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

import java.util.HashMap;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Keyframe<Type>
{

    private float timePosition;
    private HashMap<String, Type> keyValues;
    private Action enterAction;
    private Action exitAction;

    public Keyframe(float timePosition)
    {
        this.timePosition = timePosition;
        keyValues = new HashMap<>();

    }

    public final Keyframe PutKeyValue(String name, Type data)
    {
        this.keyValues.put(name, data);
        return this;
    }

    public final float GetTimePosition()
    {
        return timePosition;
    }

    public final Type GetData(String name)
    {
        return keyValues.get(name);
    }

    public Keyframe SetOnEnterAction(Action action)
    {
        this.enterAction = action;
        return this;
    }

    public Keyframe SetOnExitAction(Action action)
    {
        this.exitAction = action;
        return this;
    }

    public void KeyframeEnterAction()
    {
        if (enterAction != null)
        {
            enterAction.Perform();
        }

    }

    public void KeyframeExitAction()
    {
        if (exitAction != null)
        {
            exitAction.Perform();
        }

    }

}
