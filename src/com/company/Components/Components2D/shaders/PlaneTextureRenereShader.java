/*
 * Copyright (C) 2015 Sammy Guergachi <sguergachi at gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.company.Components.Components2D.shaders;

import com.company.Core.GameComponent;
import com.company.Rendering.Shader;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaneTextureRenereShader extends BaseShader
{

    public void InitShader() throws IOException, URISyntaxException
    {
        
        if (shader == null)
        {
            shader = new Shader();

            shader.AddVertexShader(Shader.LoadShader(new File(getClass().getResource(".\\planeRendererShader.vs").toURI())));
            shader.AddFragmentShader(Shader.LoadShader(new File(getClass().getResource(".\\planeRendererShader.fs").toURI())));
            shader.compileShader();
            shader.AddUniform("transformMatrix");
            shader.AddUniform("viewMatrix");
            shader.AddUniform("texCoordMult");
            shader.AddUniform("texture");
            shader.AddUniform("tileSize");
            shader.AddUniform("offSet");
        }
    }

    public Shader GetBaseShader()
    {
        if (shader == null)
        {
            try
            {
                InitShader();
            } catch (IOException | URISyntaxException ex)
            {
                Logger.getLogger(PlaneTextureRenereShader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return shader;
    }

}
