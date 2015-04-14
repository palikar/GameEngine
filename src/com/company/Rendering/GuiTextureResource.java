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
public interface GuiTextureResource
{

    public void Update(double delta);

    public void Input(Input input);

    public Vector2f GetPosition();

    public Texture GetTexture();


    public Vector2f GetScale();

    public Vector2f GetTileSize();

    public Vector2f GetOffSet();

}
