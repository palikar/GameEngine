package com.company.Core;

import com.company.Components.Components2D.shaders.ShaderedObject;
import com.company.Rendering.Camera;
import com.company.Rendering.RenderingEngine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Stanislav on 15.2.2015 г..
 */
public abstract class Game
{

    private GameObject rootGameObject;
    private CoreEngine engine;
    private RenderingEngine renderingEngine;
    private Camera mainCamera;
    private boolean shaderSorting = false;

    public void Init(CoreEngine engine)
    {
        this.engine = engine;
        renderingEngine = RenderingEngine.getInstance();
        renderingEngine.Init(engine.GetHeight(), engine.GetHeight());
        mainCamera = new Camera();
        renderingEngine.SetCamera(mainCamera);
        AddObject(mainCamera);
        InitCamera();

    }

    protected abstract void InitCamera();

    protected void AddObject(GameObject object)
    {
        GetRootGameObject().AddChild(object);
    }

    protected void Update(double delta)
    {
        GetRootGameObject().UpdateAll(delta);
    }

    protected void Input(Input input)
    {
        GetRootGameObject().InputAll(input);

    }

    protected void Render()
    {
        if (shaderSorting)
        {
            ArrayList<ShaderedObject> objs = GetAllObjectsToRender(rootGameObject);

            for (ShaderedObject obj : objs)
            {
                if (obj instanceof GameComponent)
                {
                    ((GameComponent) obj).Render(renderingEngine);
                }
                if (obj instanceof GameObject)
                {
                    ((GameObject) obj).Render(renderingEngine);
                }
            }
        } else
        {
            GetRootGameObject().RenderAll(renderingEngine);
        }
    }

    public CoreEngine GetEngine()
    {
        return engine;
    }

    protected GameObject GetRootGameObject()
    {
        if (rootGameObject == null)
        {
            rootGameObject = new GameObject();
            rootGameObject.Init(this);
        }
        return rootGameObject;
    }

    public RenderingEngine GetRenderingEngine()
    {
        return renderingEngine;
    }

    private ArrayList<ShaderedObject> GetAllObjectsToRender(GameObject root)
    {
        ArrayList<ShaderedObject> objects = new ArrayList<>();
        for (GameObject obj : root.children)
        {
            objects.add(obj);
            objects.addAll(obj.components);
            if (!obj.children.isEmpty())
            {
                objects.addAll(GetAllObjectsToRender(obj));

            }

        }
        
        

        Collections.sort(objects, new Comparator<ShaderedObject>()
        {

            @Override
            public int compare(ShaderedObject o1, ShaderedObject o2)
            {
                if (o1.GetShader() != null && o2.GetShader() != null)
                {
                    if (o1.GetShader().GetShader() == o2.GetShader().GetShader())
                    {
                        return 0;
                    } else
                    {
                        return 1;
                    }

                }
                return 0;
            }
        });
        return objects;
    }

    public void SetShaderSorting(boolean shaderSorting)
    {
        this.shaderSorting = shaderSorting;
    }
}
