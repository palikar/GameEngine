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

import com.company.Rendering.Shader;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public abstract class BaseShader
{

    protected static Shader shader;

    public abstract Shader GetBaseShader();

    public abstract void InitShader() throws IOException, URISyntaxException;

}