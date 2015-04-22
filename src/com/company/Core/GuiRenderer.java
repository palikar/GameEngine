/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.Core;

import com.company.Components.Components2D.shaders.GuiRendererShader;
import com.company.Math.Vector2f;
import com.company.Math.Vector3f;
import com.company.Rendering.GuiTexture;
import com.company.Rendering.GuiTextureResource;
import com.company.Rendering.Mesh;
import com.company.Rendering.Rendereble;
import com.company.Rendering.RenderingEngine;
import com.company.Rendering.Shader;
import com.company.Rendering.Vertex;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class GuiRenderer extends GameComponent implements Rendereble {

    private final static Vertex[] vertices = new Vertex[]{
        new Vertex(new Vector3f(-1, 1, 0), new Vector2f(0, 0)),
        new Vertex(new Vector3f(1, -1, 0), new Vector2f(1, 1)),
        new Vertex(new Vector3f(-1, -1, 0), new Vector2f(0, 1)),
        new Vertex(new Vector3f(1, 1, 0), new Vector2f(1, 0)),};

    private final static int[] indices = new int[]{
        0, 1, 2,
        0, 3, 1,};

    private final Mesh quad;
    private GuiRendererShader shader = new GuiRendererShader();
    private ArrayList<GuiTextureResource> textures;
    private GuiTextureResource current;

    public GuiRenderer() {
        textures = new ArrayList<>();
        quad = new Mesh();
        quad.AddVerteces(vertices, indices);
        InitShader();
    }

    @Override
    public void Render(RenderingEngine renderingEngine) {
        super.Render(renderingEngine);
        GetParent().GetGame().GetRenderingEngine().BindShader(GetShader());
        for (int i = 0; i < textures.size(); i++) {
            current = textures.get(i);
            UpdateTransform();
            
        }

    }

    @Override
    public void Update(double delta) {
        super.Update(delta);
    }

    @Override
    public void UpdateUniforms() {
        GetShader().SetUniform("offSet", current.GetOffSet());
        GetShader().SetUniform("tileSize", current.GetTileSize());
        GetShader().SetUniform("transformMatrix", GetTransform().GetTransformation());
        GetShader().Set2DSamplerUniform("texture", GetParent().GetGame().GetRenderingEngine().GetSampler(current.GetTexture()));

    }

    private void UpdateTransform() {
        GetTransform().SetPosition(new Vector3f(current.GetPosition().GetX(), current.GetPosition().GetY(), 0));
        GetTransform().SetScale(new Vector3f(current.GetScale().GetX(), current.GetScale().GetY(), 1));
    }

    public void AddTexture(GuiTexture texture) {
        textures.add(texture);
    }

    @Override
    public final void InitShader() {
        shader.InitShader();
    }

    @Override
    public Shader GetShader() {
        return shader.GetBaseShader();
    }

    @Override
    public int GetVAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int GetIBO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int GetBufferSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void PreRender() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    @Override
    public void PostRender() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

}
