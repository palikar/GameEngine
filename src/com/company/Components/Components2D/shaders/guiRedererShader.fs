#version 330

out vec4 fragColor;

in vec2 texCoord;

uniform sampler2D texture;
uniform vec2 offSet;
uniform vec2 tileSize;

void main()
{       
        vec4 col = texture2D(texture,fract(texCoord)*tileSize+offSet);
        if(col.a == 0){
            discard;
        }
	fragColor = col;
}