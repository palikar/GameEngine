#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoordIn;
layout (location = 2) in vec3 normal;

out vec4 color;
out vec2 texCoord;

uniform mat4 transformMatrix;
uniform mat4 viewMatrix;

void main()
{
	color = vec4(clamp(position,0.0,1.0),1.0);
	gl_Position = viewMatrix*transformMatrix*vec4(position,1.0);
	texCoord = texCoordIn;

}