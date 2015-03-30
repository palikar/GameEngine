/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company;

import com.company.Core.GameComponent;
import com.company.Core.GameObject;
import com.company.Math.Matrix4f;
import com.company.Math.Vector2f;
import com.company.Math.Vector3f;
import com.company.Rendering.Mesh;
import com.company.Rendering.RenderingEngine;
import com.company.Rendering.Texture;
import com.company.Rendering.Vertex;
import java.util.ArrayList;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class GuiRenderer extends GameComponent
{

    private final Mesh quad;
    public Texture tex;
    public Vector2f pos;
    public Vector2f scale;

    public GuiRenderer()
    {
        quad = new Mesh();
        Vertex[] vertices = new Vertex[]
        {
            new Vertex(new Vector3f(-1, 1, 0), new Vector2f(0, 0)),
            new Vertex(new Vector3f(1, -1, 0), new Vector2f(1, 1)),
            new Vertex(new Vector3f(-1, -1, 0), new Vector2f(0, 1)),
            new Vertex(new Vector3f(1, 1, 0), new Vector2f(1, 0)),

        };

        int[] indices = new int[]
        {
            0, 1, 2,
            0, 3, 1,
        };
        quad.AddVerteces(vertices, indices);
    }

    @Override
    public void Render(RenderingEngine renderingEngine)
    {
        super.Render(renderingEngine);
        Matrix4f transform = new Matrix4f().InitIdentity();
        transform.InitTranslation(pos.GetX(), pos.GetY(), 0);
        transform.InitScale(scale.GetX(), scale.GetY(), 0);
    }

    @Override
    public void Update(double delta)
    {
        super.Update(delta); //To change body of generated methods, choose Tools | Templates.
    }

}
