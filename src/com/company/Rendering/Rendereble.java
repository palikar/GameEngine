/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.Rendering;

/**
 *
 * @author User
 */
public interface Rendereble
{

    public void InitShader();

    public Shader GetShader();

    public void UpdateUniforms();

    public int GetVAO();

    public int GetIBO();

    public int GetBufferSize();

    public default void PreRender()
    {
    }

    public default void PostRender()
    {
    }
;
}
