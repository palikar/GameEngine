package com.company.Rendering;

import com.company.Util.Util;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL30;

public class Mesh
{

    private int vbo;
    private int ibo;
    private int vao;
    private int size;

    public Mesh()
    {
        vbo = glGenBuffers();
        ibo = glGenBuffers();
        vao = GL30.glGenVertexArrays();

        GL30.glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        glEnableVertexAttribArray(Shader.vertPosLocation);
        glEnableVertexAttribArray(Shader.texCoordLocation);
        glEnableVertexAttribArray(Shader.vertNormalLocation);

        glVertexAttribPointer(Shader.vertPosLocation, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glVertexAttribPointer(Shader.texCoordLocation, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
        glVertexAttribPointer(Shader.vertNormalLocation, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);

        size = 0;
    }

    public void AddVerteces(Vertex[] vertices, int[] indices)
    {
        size = vertices.length * Vertex.SIZE;

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, Util.CreateFlippedBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.CreateFlippedBuffer(indices), GL_STATIC_DRAW);

    }

    public void draw()
    {

        GL30.glBindVertexArray(vao);
       // glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);

        glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);

        GL30.glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

    }

}
