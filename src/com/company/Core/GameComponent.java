package com.company.Core;

import com.company.Components.Components2D.shaders.ShaderedObject;
import com.company.Math.Transform;
import com.company.Rendering.RenderingEngine;
import com.company.Rendering.Shader;

/**
 * Created by Stanislav on 16.2.2015 г..
 */
public class GameComponent implements ShaderedObject
{

    private GameObject parent;
    private boolean render = true, update = true, input = true;

    public void Init(GameObject parent)
    {
        this.parent = parent;
    }

    public void Input(Input input)
    {
        if (!this.input)
        {
            return;
        }

    }

    public void Update(double delta)
    {
        if (!this.update)
        {
            return;
        }
    }

    public void Render(RenderingEngine renderingEngine)
    {
        if (!this.render)
        {
            return;
        }
    }

    protected GameObject GetParent()
    {
        return parent;
    }

    @Override
    public void InitShader()
    {
    }

    @Override
    public Shader GetShader()
    {
        return null;
    }

    protected Transform GetTransform()
    {
        return GetParent().GetTransform();
    }

    @Override
    public void UpdateUniforms()
    {
    }

    public void SetRenderEnable(boolean b)
    {
        this.render = b;
    }

    public void SetUpdateEnable(boolean b)
    {
        this.update = b;
    }

    public void SetInputEnable(boolean b)
    {
        this.input = b;

    }
}
