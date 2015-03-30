#version 330

out vec4 fragColor;

in vec4 color;
in vec2 texCoord;

uniform sampler2D texture;
uniform vec2 texCoordMult;
uniform vec2 offSet;
uniform vec2 tileSize;

void main()
{       
	vec2 sa = texCoordMult;
        vec4 col = texture2D(texture,fract(texCoord*texCoordMult)*tileSize+offSet);
        if(col.a == 0){
            discard;
        }
	fragColor = col;
}