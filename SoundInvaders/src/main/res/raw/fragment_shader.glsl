precision mediump float;

varying vec3 lightVec[2];
varying vec3 eyeVec;
varying vec2 texCoord;

uniform sampler2D textureUnit0;
uniform sampler2D textureUnit1;

uniform vec3 diffuseColors[8];
uniform vec3 specularColors[8];

uniform vec4 ambientColor;

uniform vec3 windowSize;

uniform float beatFrac;

void main()
{
        vec2 pos = gl_FragCoord.xy;
        vec2 centre = windowSize.xy/2.0;

        float dist = length(pos - centre);
        float maxdist = 300.0;
        float bdist = beatFrac*maxdist;

        float ringwidth = 20.0;

        float cdist = dist - bdist;

        float percentage = cdist / ringwidth;

        percentage = percentage/2.0 + abs(percentage);

        float intensity = clamp(percentage, 0.0, 1.0);

        intensity = 1.0 - intensity;

        if(beatFrac < 0.5)// || beatFrac > 0.70)
        {
            gl_FragColor = vec4(1.0, 0.0, 0.0, 0.0);
        }

		//gl_FragColor = vec4(1.0*intensity, 0.0, 0.0, 0.0);
		else
		    gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);
}