/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.Rendering;

import com.company.Math.Vector2f;
import com.company.Rendering.Texture;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class GuiTexture
{

    private Texture texture;
    private Vector2f pos;
    private Vector2f scale;
    private Vector2f tileSize;
    private Vector2f offSet;

    public GuiTexture(Texture texture, Vector2f pos, Vector2f scale, Vector2f tileSize, Vector2f offSet)
    {
        this.texture = texture;
        this.pos = pos;
        this.scale = scale;
        this.tileSize = tileSize;
        this.offSet = offSet;
    }

    public Texture getTexture()
    {
        return texture;
    }

    public Vector2f getPos()
    {
        return pos;
    }

    public Vector2f getScale()
    {
        return scale;
    }

    public Vector2f getTileSize()
    {
        return tileSize;
    }

    public Vector2f getOffSet()
    {
        return offSet;
    }

}
