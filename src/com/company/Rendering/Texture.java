package com.company.Rendering;

import com.company.Util.Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 * Created by Stanislav on 16.2.2015 г..
 */
public class Texture
{

    private static HashMap<String, TextureResource> resourcesFiles = new HashMap<>();
    private static HashMap<BufferedImage, TextureResource> resourcesImages = new HashMap<>();
    TextureResource resource;
    String fileName;
    BufferedImage image;

    public Texture(String fileName) throws IOException
    {
        TextureResource oldresource = resourcesFiles.get(fileName);

        this.fileName = fileName;

        if (resource != null)
        {
            resource = oldresource;
            resource.AddReference();
        } else
        {
            resource = LoadTexture(fileName);
            resourcesFiles.put(fileName, resource);
        }
    }

    public Texture(BufferedImage img) throws IOException
    {
        TextureResource oldresource = resourcesImages.get(img);

        this.image = img;

        if (resource != null)
        {
            resource = oldresource;
            resource.AddReference();
        } else
        {
            resource = LoadTexture(img);
            resourcesImages.put(img, resource);
        }
    }

    public void Bind()
    {
        Bind(0);
    }

    public void Bind(int samplerSlot)
    {
        assert (samplerSlot >= 0 && samplerSlot <= 31);
        glActiveTexture(GL_TEXTURE0 + samplerSlot);
        glBindTexture(GL_TEXTURE_2D, resource.getId());
    }

    public int GetID()
    {
        return resource.getId();
    }

    @Override
    protected void finalize() throws Throwable
    {
        if (resource.RemoveReference() && !fileName.isEmpty())
        {
            if (fileName != null)
            {
                resourcesFiles.remove(fileName);
            }
            if (image != null)
            {
                resourcesImages.remove(image);
            }
        }
    }

    private static TextureResource LoadTexture(String fileName) throws IOException
    {
        BufferedImage image = ImageIO.read(new File(fileName));
        return LoadTexture(image);
    }

    private static TextureResource LoadTexture(BufferedImage image) throws IOException
    {

        int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        ByteBuffer buffer = Util.CreateByteBuffer(image.getHeight() * image.getWidth() * 4);
        boolean hasAlpha = image.getColorModel().hasAlpha();
        System.out.println(hasAlpha);

        for (int y = 0; y < image.getHeight(); y++)
        {
            for (int x = 0; x < image.getWidth(); x++)
            {
                int pixel = pixels[y * image.getWidth() + x];

                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) ((pixel) & 0xFF));
                if (hasAlpha)
                {
                    buffer.put((byte) ((pixel >> 24) & 0xFF));
                } else
                {
                    buffer.put((byte) (0xFF));
                }
            }
        }

        buffer.flip();

        TextureResource res = new TextureResource(image.getWidth(), image.getHeight());
        glBindTexture(GL_TEXTURE_2D, res.getId());

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        return res;
    }

    public int getWidth()
    {
        return resource.GetWidth();
    }

    public int GetHeigh()
    {
        return resource.GetHeigh();
    }

}
