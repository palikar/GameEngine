package com.company.Components.Components2D;

import com.company.Components.Components2D.shaders.PlaneTextureRenereShader;
import com.company.Core.GameComponent;
import com.company.Core.GameObject;
import com.company.Math.Vector2f;
import com.company.Math.Vector3f;
import com.company.Rendering.Mesh;
import com.company.Rendering.RenderingEngine;
import com.company.Rendering.Shader;
import com.company.Rendering.Vertex;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;

public class PlaneTextureRenderer extends GameComponent
{

    protected Vector2f texCoordMult, tileSize, offSet;
    private Vertex[] vertices;
    private int[] indices;
    private int texture;
    private Mesh mesh;
    private PlaneTextureRenereShader shader = new PlaneTextureRenereShader();
    private boolean blending = false;

    public PlaneTextureRenderer(int texture) throws IOException, URISyntaxException
    {
        this(texture, null);
    }

    public PlaneTextureRenderer(int texture, Vector2f[] texCoords) throws IOException, URISyntaxException
    {

        this.texCoordMult = new Vector2f(1f, 1f);
        this.tileSize = new Vector2f();
        this.offSet = new Vector2f();
        this.texture = texture;

        InitVerticesAndIndices(0.5f, 0.5f, 0);
        if (texCoords != null)
        {
            SetTexCoord(texCoords);
        }
        InitMesh();
        InitShader();

        SetTexCoordMult(new Vector2f(1, 1));
        SetOffSet(new Vector2f(0, 0));
        SetTileSize(new Vector2f(1f, 1f));
    }

    private void InitVerticesAndIndices(float width1, float height1, float zIndex1)
    {
        vertices = new Vertex[]
        {
            new Vertex(new Vector3f(width1, -height1, zIndex1), new Vector2f(1, 1)),
            new Vertex(new Vector3f(-width1, -height1, zIndex1), new Vector2f(0, 1)),
            new Vertex(new Vector3f(-width1, height1, zIndex1), new Vector2f(0, 0)),
            new Vertex(new Vector3f(width1, height1, zIndex1), new Vector2f(1, 0))
        };
        indices = new int[]
        {
            0, 1, 2,
            0, 2, 3
        };

    }

    @Override
    public void InitShader()
    {
        try
        {
            shader.InitShader();
        } catch (IOException | URISyntaxException ex)
        {
            Logger.getLogger(PlaneTextureRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void InitMesh()
    {
        mesh = new Mesh();
        mesh.AddVerteces(vertices, indices);
    }

    public void PutVertecesAndIndices(Vertex[] vertices, int[] indices)
    {
        this.vertices = vertices;
        this.indices = indices;
    }

    @Override
    public void Init(GameObject parent)
    {
        super.Init(parent);

    }

    public void SetTexCoordMult(Vector2f value)
    {
        this.texCoordMult.Set(value);
    }

    public void SetTexCoord(Vector2f[] texCoords)
    {
        for (int i = 0; i < 4; i++)
        {
            vertices[i].GetTexCoord().Set(texCoords[i]);
        }
        InitMesh();
    }

    @Override
    public void Render(RenderingEngine renderingEngine)
    {
        super.Render(renderingEngine);
        GetParent().GetGame().GetRenderingEngine().BindShader(GetShader());
        UpdateUniforms();
        if (blending)
        {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }

        mesh.draw();
        if (blending)
        {
            GL11.glDisable(GL11.GL_BLEND);
        }

    }

    @Override
    public void UpdateUniforms()
    {
        if (!GetParent().GetGame().GetRenderingEngine().IsShaderBinded(GetShader()))
        {
            return;
        }
        GetShader().Set2DSamplerUniform("texture", texture);
        GetShader().SetUniform("transformMatrix", GetParent().GetTransform().GetTransformation());
        GetShader().SetUniform("viewMatrix", GetParent().GetGame().GetRenderingEngine().GetCamera().GetViewProjection());
        GetShader().SetUniform("texCoordMult", texCoordMult);
        GetShader().SetUniform("offSet", offSet.Mul(tileSize));
        GetShader().SetUniform("tileSize", tileSize);

    }

    public void SetTexture(int tex)
    {
        this.texture = tex;
    }

    public void SetTileSize(Vector2f tileSize)
    {
        this.tileSize = tileSize;
    }

    public void SetOffSet(Vector2f offSet)
    {
        this.offSet = offSet;
    }

    @Override
    public void Update(double delta)
    {
        super.Update(delta);

    }

    @Override
    public Shader GetShader()
    {
        return shader.GetBaseShader();
    }

    public PlaneTextureRenderer EnableBlending()
    {
        blending = true;
        return this;
    }

}
