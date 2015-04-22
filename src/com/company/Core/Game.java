package com.company.Core;

import com.company.Math.Vector2f;
import com.company.Rendering.Camera;
import com.company.Rendering.RenderingEngine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Stanislav on 15.2.2015 г..
 */
public abstract class Game {

    private GameObject rootGameObject;
    private CoreEngine engine;
    private RenderingEngine renderingEngine;
    private Camera mainCamera;
    private boolean shaderSorting = false;
    private boolean newAdded = false;
    private boolean guiEnabled = false;
    private GuiRenderer guiRenderer;
    private Vector2f size;

    public void Init(CoreEngine engine) {
        this.engine = engine;
        size = new Vector2f(engine.GetWidth(), engine.GetHeight());
        renderingEngine = RenderingEngine.getInstance();
        renderingEngine.Init(engine.GetHeight(), engine.GetHeight());
        mainCamera = new Camera();
        renderingEngine.SetCamera(mainCamera);
        AddObject(mainCamera);
        InitCamera();

    }

    protected abstract void InitCamera();

    protected void AddObject(GameObject object) {
        GetRootGameObject().AddChild(object);
        ObjectAdded();
    }

    protected void Update(double delta) {
        GetRootGameObject().UpdateAll(delta);
    }

    protected void Input(Input input) {
        GetRootGameObject().InputAll(input);

    }

    protected void Render() {
        GetRootGameObject().RenderAll(renderingEngine);
        renderingEngine.RenderAll();
    }

    public CoreEngine GetEngine() {
        return engine;
    }

    protected GameObject GetRootGameObject() {
        if (rootGameObject == null) {
            rootGameObject = new GameObject();
            rootGameObject.Init(this);
        }
        return rootGameObject;
    }

    public RenderingEngine GetRenderingEngine() {
        return renderingEngine;
    }

    public void SetShaderSorting(boolean shaderSorting) {
        this.shaderSorting = shaderSorting;
    }

    public void ObjectAdded() {
        newAdded = true;
    }

    public void EnableGui() {
        if (guiRenderer == null) {
            guiRenderer = new GuiRenderer();
            AddObject(new GameObject().AddComponent(guiRenderer));
        }
        guiRenderer.SetRenderEnable(true);
        guiRenderer.SetUpdateEnable(true);
        guiRenderer.SetInputEnable(true);
        guiEnabled = true;
    }

    public void DisableGui() {
        guiEnabled = false;
        guiRenderer.SetRenderEnable(false);
        guiRenderer.SetInputEnable(false);
        guiRenderer.SetInputEnable(false);
    }

    public GuiRenderer GetGui() {
        return guiRenderer;
    }

    public Vector2f GetSize() {
        return size;
    }
}
