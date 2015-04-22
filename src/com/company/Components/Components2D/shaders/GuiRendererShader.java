/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.Components.Components2D.shaders;

import com.company.Rendering.Shader;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class GuiRendererShader extends BaseShader
{

    public void InitShader()
    {

       
        if (shader == null)
        {
            try {
                shader = new Shader();
                
                shader.AddVertexShader(Shader.LoadShader(new File(getClass().getResource(".\\guiRendererShader.vs").toURI())));
                shader.AddFragmentShader(Shader.LoadShader(new File(getClass().getResource(".\\guiRedererShader.fs").toURI())));
                shader.compileShader();
                shader.AddUniform("transformMatrix");
                shader.AddUniform("texture");
                shader.AddUniform("tileSize");
                shader.AddUniform("offSet");
            } catch (IOException ex) {
                Logger.getLogger(GuiRendererShader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(GuiRendererShader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Shader GetBaseShader()
    {
        if (shader == null)
        {
            InitShader();
        }
        return shader;
    }

}
