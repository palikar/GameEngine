package com.company.Rendering;

import com.company.Util.GlobalSpace;
import org.lwjgl.opengl.GL11;

import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Created by Stanislav on 15.2.2015 г..
 */
public class Window {

    private static Window instance = new Window();

    private GLFWErrorCallback errorCallback;

    private long window;

    private Window() {

    }

    public static Window getInstance() {
        return instance;
    }

    public void CreateWindow(int width, int height, String title) {

        Init(width, height, title);
        GLContext.createFromCurrent();
        if (GlobalSpace.DEBUG) {
            System.out.println("Maximum OpenGL texture samplers:" + GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS);
            System.out.println("OpenGL version: " + glGetString(GL_VERSION));
        }

    }

    public void Render() {
        glfwSwapBuffers(window);
        glfwPollEvents();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    private void Init(int width, int height, String title) {
        if (GlobalSpace.DEBUG) {
            System.out.println("Hello LWJGL " + Sys.getVersion() + "!");
        }
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

        if (glfwInit() != GL11.GL_TRUE) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);

        this.window = glfwCreateWindow(width, height, title, NULL, NULL);

        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        ByteBuffer videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (GLFWvidmode.width(videoMode) - width) / 2, (GLFWvidmode.height(videoMode) - height) / 2);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        glfwShowWindow(window);

    }

    public boolean isCloseRequested() {
        return glfwWindowShouldClose(window) == GL_TRUE;
    }

    public long getWindow() {
        return window;
    }

    public void Dispose() {
        glfwDestroyWindow(window);
        errorCallback.release();
        glfwTerminate();

    }

    public void SetTitle(String string) {
        glfwSetWindowTitle(window, string);
    }
}
