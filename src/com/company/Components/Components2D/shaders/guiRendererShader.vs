#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoordIn;

out vec4 color;
out vec2 texCoord;

uniform mat4 transformMatrix;

void main()
{
	gl_Position = transformMatrix*vec4(position,1.0);
	texCoord = texCoordIn;

}