/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.Rendering;

import com.company.Math.Matrix4f;
import com.company.Math.Vector2f;
import com.company.Math.Vector3f;
import com.company.Util.Util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import static org.lwjgl.opengl.GL20.*;

/**
 *
 * @author Stanislav
 */
public class Shader {

    public static final int vertPosLocation = 0;
    public static final int texCoordLocation = 1;
    public static final int vertNormalLocation = 2;

    private final int program;
    private HashMap<String, Integer> uniforms;

    public Shader() {
        program = glCreateProgram();
        if (program == 0) {
            System.err.println("Shader failed");
            System.exit(1);
        }
        uniforms = new HashMap<>();

    }

    public void Bind() {
        glUseProgram(program);
    }

    public void Unbind() {
        glUseProgram(0);
    }

    public void AddUniform(String name) {
        Bind();
        int location = glGetUniformLocation(program, name);
        if (location == 0xFFFFFFFF) {
            System.err.println("Uniform location Fucked up");
            System.exit(1);
        }
        uniforms.put(name, location);
        Unbind();
    }

    public void SetUniform(String name, int value) {
        glUniform1i(uniforms.get(name), value);
    }

    public void SetUniform(String name, float value) {
        glUniform1f(uniforms.get(name), value);
    }

    public void SetUniform(String name, Vector2f value) {
        glUniform2f(uniforms.get(name), value.GetX(), value.GetY());
    }

    public void SetUniform(String name, Vector3f value) {
        glUniform3f(uniforms.get(name), value.GetX(), value.GetY(), value.GetZ());
    }

    public void SetUniform(String name, Matrix4f value) {

        glUniformMatrix4fv(uniforms.get(name), true, Util.CreateFlippedBuffer(value));
    }

    public void Set2DSamplerUniform(String name, int samplerSlot) {
        glUniform1i(uniforms.get(name), samplerSlot);

    }

    public void compileShader() {
        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(program, 1024));
            System.exit(1);
        }
        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(program, 1024));
            System.exit(1);
        }

    }

    public void AddVertexShader(String text) {
        AddProgram(text, GL_VERTEX_SHADER);
    }

    public void AddFragmentShader(String text) {
        AddProgram(text, GL_FRAGMENT_SHADER);
    }

    private void AddProgram(String text, int type) {
        int shader = glCreateShader(type);
        if (shader == 0) {
            System.err.println("Shader fail");
            System.exit(1);
        }
        glShaderSource(shader, text);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(shader, 1024));
            System.exit(1);
        }

        glAttachShader(program, shader);
    }

    public static String LoadShader(File file) throws IOException {
        StringBuilder shaderLines = new StringBuilder();
        BufferedReader shaderReader = new BufferedReader(new FileReader(file));
        String line;

        while ((line = shaderReader.readLine()) != null) {
            shaderLines.append(line).append("\n");
        }

        shaderReader.close();

        return shaderLines.toString();
    }

    public int GetShader() {
        return program;
    }

}
