package com.company.Rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

/**
 * Created by Stanislav on 16.2.2015 г..
 */
public class TextureResource
{

    private int id;
    private int refCount;
    int width, height;

    public TextureResource(int width, int height)
    {
        id = GL11.glGenTextures();
        refCount = 1;
        this.width = width;
        this.height = height;
    }

    @Override
    protected void finalize() throws Throwable
    {
        GL15.glDeleteBuffers(id);
    }

    public void AddReference()
    {
        refCount++;
    }

    public boolean RemoveReference()
    {
        refCount--;
        return refCount == 0;
    }

    public int getId()
    {
        return id;
    }

    public int GetWidth()
    {
        return width;
    }

    public int GetHeigh()
    {
        return height;
    }

}
