package com.company.Rendering;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL15;
import static org.lwjgl.opengl.GL20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_SRGB;

/**
 * Created by Stanislav on 21.2.2015 г..
 */
public class RenderingEngine
{

    private static final RenderingEngine instance = new RenderingEngine();
    private Camera camera = null;
    private Texture[] textures;
    private final int maxTextureCnt = GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS;
    private int currentShader;
    private Queue<Rendereble> objectsToRender;

    private RenderingEngine()
    {
        objectsToRender = new LinkedList<>();
        textures = new Texture[maxTextureCnt];
    }

    public void Init(int canvasHeight, int canvasWidth)
    {
        glClearColor(0, 0, 0, 0);
        glEnable(GL_CULL_FACE);
        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        //glEnable(GL_FRAMEBUFFER_SRGB);

    }

    public void BindShader(Shader shader)
    {
        if (currentShader == shader.GetShader())
        {
            return;
        }
        shader.Bind();
        currentShader = shader.GetShader();
    }

    public boolean IsShaderBinded(Shader shader)
    {
        return shader.GetShader() == currentShader;
    }

    public int GetSampler(Texture tex)
    {
        int firstFree = -1;
        for (int i = 0; i < 32; i++)
        {
            if (textures[i] == null)
            {
                firstFree = firstFree == -1 ? i : firstFree;
                continue;
            }
            if (textures[i].GetID() == tex.GetID())
            {
                return i;
            }
        }
        if (firstFree == -1)
        {
            firstFree = 0;
        }

        textures[firstFree] = tex;
        tex.Bind(firstFree);

        return firstFree;
    }

    public int SetSampler(Texture tex, int samplerSlot)
    {
        assert (samplerSlot >= 0 && samplerSlot <= 31);
        textures[samplerSlot] = tex;
        tex.Bind(samplerSlot);
        return samplerSlot;

    }

    public RenderingEngine RenderAll()
    {

        objectsToRender.forEach((Rendereble object) ->
        {
            BindShader(object.GetShader());
            object.UpdateUniforms();
            glBindVertexArray(object.GetVAO());
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, object.GetIBO());
            object.PreRender();
            glDrawElements(GL_TRIANGLES, object.GetBufferSize(), GL_UNSIGNED_INT, 0);
            object.PostRender();
            glBindVertexArray(0);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        });
        objectsToRender.clear();

        return this;
    }

    public RenderingEngine Submit(Rendereble object)
    {
        object.InitShader();
        objectsToRender.offer(object);
        return this;
    }

    public RenderingEngine Submit(ArrayList<Rendereble> objects)
    {
        objects.forEach((Rendereble object) ->
        {
            Submit(object);
        });
        return this;
    }

    public Camera GetCamera()
    {
        return camera;
    }

    public void SetCamera(Camera camera)
    {
        this.camera = camera;
    }

    public static RenderingEngine getInstance()
    {
        return instance;
    }

}
