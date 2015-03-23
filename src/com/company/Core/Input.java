package com.company.Core;

import com.company.Math.Vector2f;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

/**
 * Created by Stanislav on 15.2.2015 г..
 */
public class Input
{

    private static final Input intance = new Input();
    private GLFWScrollCallback scrollCallback;
    private GLFWCursorPosCallback cursorPosCallback;

    private long window;

    private float scrollPos = 0;
    private float lastScrollPos = 0;
    private float lastScrollDirection = 1;
    private final float maxScroll = 10;
    private final float minScroll = -maxScroll;

    private final Vector2f mousePos = new Vector2f();
    private final Vector2f oldMousePos = new Vector2f();

    private final Keyboard keyboard = Keyboard.GetInsatnce();

    private Input()
    {

    }

    public void Inint(long window)
    {
        this.window = window;
        keyboard.Init(window);
        GLFW.glfwSetScrollCallback(window, scrollCallback = new GLFWScrollCallback()
        {
            @Override
            public void invoke(long l, double v, double v1)
            {
                UpdateScroll((float) v1);
            }
        });
        GLFW.glfwSetCursorPosCallback(window, cursorPosCallback = new GLFWCursorPosCallback()
        {
            @Override
            public void invoke(long l, double v, double v1)
            {
                oldMousePos.Set(mousePos.GetX(), mousePos.GetY());
                mousePos.Set((float) v, (float) v1);
            }
        });

    }

    public void Update()
    {
        keyboard.Update();
    }

    private void UpdateScroll(float offSet)
    {
        lastScrollPos = scrollPos;
        lastScrollDirection = Math.signum(offSet);
        scrollPos += offSet;
    }

    public float getScrollPos()
    {
        return scrollPos;
    }

    public float getLastScrollPos()
    {
        return lastScrollPos;
    }

    public float getLastScrollDirection()
    {
        return lastScrollDirection;
    }

    public Vector2f getMousePos()
    {
        return mousePos;
    }

    public Vector2f getLastMousePos()
    {
        return oldMousePos;
    }

    public void SetMousePos(Vector2f vec)
    {
        mousePos.Set(vec);
        oldMousePos.Set(vec);
        GLFW.glfwSetCursorPos(window, vec.GetX(), vec.GetY());
    }

    public void HideCursor()
    {
        glfwSetInputMode(window, GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
    }

    public void ShowCursor()
    {
        glfwSetInputMode(window, GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
    }

    public boolean IsKeyPressed(int key)
    {
        return keyboard.IsPressed(key);
    }

    public boolean IsClicked(int key)
    {
        return keyboard.IsClicked(key);
    }

    public boolean IsLet(int key)
    {
        return keyboard.IsLet(key);
    }

    public boolean isKeyRealsed(int key)
    {
        return keyboard.IsReleased(key);
    }

    public boolean isMouseButtonPressed(int key)
    {
        return GLFW.glfwGetMouseButton(window, key) == GLFW.GLFW_PRESS;
    }

    public boolean isMouseButtonRealsed(int key)
    {
        return GLFW.glfwGetMouseButton(window, key) == GLFW.GLFW_RELEASE;
    }

    public boolean isMouseButtonRepeated(int key)
    {
        return GLFW.glfwGetMouseButton(window, key) == GLFW.GLFW_REPEAT;
    }

    public static Input getIntance()
    {
        return intance;
    }

    public void Dispose()
    {
        cursorPosCallback.release();
        scrollCallback.release();
    }
}
