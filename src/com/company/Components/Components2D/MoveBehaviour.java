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
package com.company.Components.Components2D;

import com.company.Core.GameComponent;
import com.company.Math.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class MoveBehaviour extends GameComponent
{

    private float dX = 0, dY = 0;
    private float accX = 0, accY = 0;
    private float maxSpeed = 5;
    private boolean accEnabled = false;
    private float speed, acc;
    private int UP_KEY = GLFW.GLFW_KEY_W;
    private int LEFT_KEY = GLFW.GLFW_KEY_A;
    private int DOWN_KEY = GLFW.GLFW_KEY_S;
    private int RIGHT_KEY = GLFW.GLFW_KEY_D;

    public MoveBehaviour()
    {
        this(1);
    }

    public MoveBehaviour(float speed)
    {
        this(speed, false);
    }

    public MoveBehaviour(float speed, boolean accEnabled)
    {
        this(speed, accEnabled, 5, 0.1f);
    }

    public MoveBehaviour(float speed, boolean accEnabled, float maxSpeed, float acc)
    {
        this.speed = speed;
        this.accEnabled = accEnabled;
        this.maxSpeed = maxSpeed;
        this.acc = acc;
    }

    @Override
    public void Update(double delta)
    {
        super.Update(delta);
        if (accEnabled)
        {
            dX += dX >= maxSpeed || dX <= -maxSpeed || dX == 0 ? 0 : accX;
            dY += dY >= maxSpeed || dY <= -maxSpeed || dY == 0 ? 0 : accY;
        }

        GetTransform().SetPosition(GetParent().GetTransform().GetPosition().Add(new Vector3f(0, dY * (float) delta, 0)));
        GetTransform().SetPosition(GetParent().GetTransform().GetPosition().Add(new Vector3f(dX * (float) delta, 0, 0)));

    }

    @Override
    public void Input(com.company.Core.Input input)
    {
        super.Input(input);
        if (input.IsClicked(UP_KEY))
        {
            dY = speed;
        }
        if (input.IsClicked(DOWN_KEY))
        {
            dY = -speed;
        }
        if (input.IsKeyPressed(UP_KEY))
        {
            dY += dY == 0 ? speed : 0;
            accY = 0.1f;
        }
        if (input.IsKeyPressed(DOWN_KEY))
        {
            dY += dY == 0 ? -speed : 0;
            accY = -0.1f;
        }
        if (input.IsLet(UP_KEY) || input.IsLet(DOWN_KEY))
        {
            dY = 0f;
            accY = 0;
        }
        if (input.IsClicked(LEFT_KEY))
        {
            dX = -speed;
        }
        if (input.IsClicked(RIGHT_KEY))
        {
            dX = +speed;
        }

        if (input.IsKeyPressed(LEFT_KEY))
        {
            dX += dX == 0 ? -speed : 0;
            accX = -0.1f;
        }
        if (input.IsKeyPressed(RIGHT_KEY))
        {
            dX += dX == 0 ? speed : 0;
            accX = 0.1f;
        }
        if (input.IsLet(LEFT_KEY) || input.IsLet(RIGHT_KEY))
        {
            dX = 0f;
            accX = 0;
        }

    }

    public void SetSpeed(float speed)
    {
        this.speed = speed;
    }

    public void SetUP_KEY(int UP_KEY)
    {
        this.UP_KEY = UP_KEY;
    }

    public void SetLEFT_KEY(int LEFT_KEY)
    {
        this.LEFT_KEY = LEFT_KEY;
    }

    public void SetDOWN_KEY(int DOWN_KEY)
    {
        this.DOWN_KEY = DOWN_KEY;
    }

    public void SetRIGHT_KEY(int RIGHT_KEY)
    {
        this.RIGHT_KEY = RIGHT_KEY;
    }

    public void SetMaxSpeed(int speed)
    {
        this.maxSpeed = speed;
    }

}
