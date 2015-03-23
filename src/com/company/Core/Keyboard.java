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
package com.company.Core;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Keyboard extends GLFWKeyCallback
{

    private static final Keyboard keybord = new Keyboard();
    private long window;

    private final List<Integer> pressed = new ArrayList<>();
    private final List<Integer> released = new ArrayList<>();
    private GLFWKeyCallback callback;

    private Keyboard()
    {
    }

    public void Init(long window)
    {
        this.window = window;
        GLFW.glfwSetKeyCallback(window, this);
    }

    public boolean IsReleased(int key)
    {
        return !IsPressed(key);
    }

    public boolean IsPressed(int key)
    {
        return GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS || IsClicked(key);
    }

    public boolean IsClicked(int key)
    {
        return pressed.contains(key);
    }

    public boolean IsLet(int key)
    {

        return released.contains(key);
    }

    public static Keyboard GetInsatnce()
    {
        return keybord;
    }

    private void SetKeyPressed(int key, boolean isPressed)
    {
        if (isPressed && !pressed.contains(key))
        {
            pressed.add(key);

        }
        if (!isPressed && pressed.contains(key))
        {
            pressed.remove(key);

        }
    }

    private void SetKeyReleased(int key, boolean isRealesed)
    {
        if (isRealesed && !released.contains(key))
        {
            released.add(key);

        }
        if (!isRealesed && released.contains(key))
        {
            released.remove(key);

        }
    }

    public void Update()
    {
        pressed.clear();
        released.clear();
    }

    @Override
    public void invoke(long window, int key, int scanCode, int action, int mods)
    {

        SetKeyPressed(key, GLFW.GLFW_PRESS == action);
        SetKeyReleased(key, GLFW.GLFW_RELEASE == action);

    }

}
