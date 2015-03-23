package com.company.Core;

import com.company.Components.Components2D.shaders.ShaderedObject;
import com.company.Math.Transform;
import com.company.Rendering.RenderingEngine;
import com.company.Rendering.Shader;

import java.util.ArrayList;

/**
 * Created by Stanislav on 16.2.2015 г..
 */
public class GameObject implements ShaderedObject
{

    protected final ArrayList<GameComponent> components;
    protected final ArrayList<GameObject> children;
    protected Game game;
    private Transform transform;
    private boolean updateEnable = true, inputEnable = true, renderEneble = true;

    public GameObject()
    {
        children = new ArrayList<>();
        components = new ArrayList<>();
        transform = new Transform();
    }

    public GameObject AddChild(GameObject child)
    {
        child.Init(game);
        child.GetTransform().SetParent(transform);
        children.add(child);
        return this;
    }

    public GameObject AddComponent(GameComponent component)
    {
        component.Init(this);
        components.add(component);
        return this;
    }

    public Game GetGame()
    {
        return game;
    }

    public void Init(Game game)
    {
        this.game = game;
    }

    public final void RenderAll(RenderingEngine renderingEngine)
    {
        if (renderEneble)
        {
            Render(renderingEngine);
        }

        for (GameObject child : children)
        {
            child.RenderAll(renderingEngine);
        }

    }

    public final void UpdateAll(double delta)
    {
        if (updateEnable)
        {
            Update(delta);
        }
        for (GameObject child : children)
        {
            child.UpdateAll(delta);
        }
    }

    public final void InputAll(Input input)
    {
        if (inputEnable)
        {
            Input(input);
        }
        for (GameObject child : children)
        {
            child.InputAll(input);
        }

    }

    public void Render(RenderingEngine renderingEngine)
    {
        for (GameComponent component : components)
        {
            component.Render(renderingEngine);
        }

    }

    public void Update(double delta)
    {
        for (GameComponent component : components)
        {
            component.Update(delta);
        }

    }

    public void Input(Input input)
    {
        for (GameComponent component : components)
        {
            component.Input(input);
        }

    }

    public Transform GetTransform()
    {
        return transform;
    }

    public boolean IsUpdateEnable()
    {
        return updateEnable;
    }

    public void SetUpdateEnable(boolean updateEnable)
    {
        this.updateEnable = updateEnable;
    }

    public boolean IsInputEnable()
    {
        return inputEnable;
    }

    public void SetInputEnable(boolean inputEnable)
    {
        this.inputEnable = inputEnable;
    }

    public boolean IsRenderEneble()
    {
        return renderEneble;
    }

    public void SetRenderEneble(boolean renderEneble)
    {
        this.renderEneble = renderEneble;
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

    @Override
    public void UpdateUniforms()
    {
    }

}