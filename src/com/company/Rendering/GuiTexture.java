/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.Rendering;

import com.company.Core.Input;
import com.company.Math.Vector2f;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class GuiTexture implements GuiTextureResource
{

    private final Texture texture;
    private final Vector2f pos;
    private final Vector2f scale;
    private final Vector2f tileSize;
    private final Vector2f offSet;

    public GuiTexture(Texture texture, Vector2f pos, Vector2f scale)
    {
        this(texture, pos, scale, new Vector2f(1, 1), new Vector2f());
    }

    public GuiTexture(Texture texture, Vector2f pos, Vector2f scale, Vector2f tileSize, Vector2f offSet)
    {
        this.texture = texture;
        this.pos = pos;
        this.scale = scale;
        this.tileSize = tileSize;
        this.offSet = offSet;
    }

    @Override
    public Texture GetTexture()
    {
        return texture;
    }

    @Override
    public Vector2f GetPosition()
    {
        return pos;
    }

    @Override
    public Vector2f GetScale()
    {
        return scale;
    }

    @Override
    public Vector2f GetTileSize()
    {
        return tileSize;
    }

    @Override
    public Vector2f GetOffSet()
    {
        return offSet;
    }

    @Override
    public void Update(double delta)
    {
    }

    @Override
    public void Input(Input input)
    {
    }

}
