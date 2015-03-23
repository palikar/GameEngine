/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.Rendering;

import com.company.TestGame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;

public class RenderingUtils {

    public static void TakeScreenShot() {
        GL11.glReadBuffer(GL11.GL_FRONT);
        ByteBuffer videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int width = (GLFWvidmode.width(videoMode));
        int height = (GLFWvidmode.height(videoMode));
        int bpp = 4;
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        try {
            ImageIO.write(RenderingUtils.BufferToImage(width, height, bpp, buffer), "bmp", new File("Screen_Shot_" + System.currentTimeMillis() + ".bmp"));
        } catch (IOException ex) {
            Logger.getLogger(TestGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static BufferedImage BufferToImage(int width, int height, int bpp, ByteBuffer buffer) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int i = (x + (width * y)) * bpp;
                int r = buffer.get(i) & 0xFF;
                int g = buffer.get(i + 1) & 0xFF;
                int b = buffer.get(i + 2) & 0xFF;
                image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }

        return image;
    }

}
