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

//uniform vec3 boxPositions[128];
uniform float boxPositionsX[32];
uniform float boxPositionsY[32];
uniform int boxNum;

float distToLine(vec2 pt1, vec2 pt2, vec2 testPt)
{
    vec2 lineDir = pt2 - pt1;
    vec2 perpDir = vec2(lineDir.y, -lineDir.x);
    vec2 dirToPt1 = pt1 - testPt;
    return abs(dot(normalize(perpDir), dirToPt1));
}

void main()
{
       /* vec2 pos = gl_FragCoord.xy;
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
            //gl_FragColor = vec4(1.0, 0.0, 0.0, 0.0);
        }

		//gl_FragColor = vec4(1.0*intensity, 0.0, 0.0, 0.0);
		//else
		    gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);*/

		if(boxNum == 0)
		{
		    gl_FragColor = vec4(0.0,0.0,0.0,0.0);
		}
		else
		{
            float maxdist = 200.0;

            int mn = -1;
            float ml = -9999999.0;

            vec2 pos = gl_FragCoord.xy;
            pos.y -= 20.0;
            pos.y = windowSize.y-pos.y;


            float ydy = -1.0;

            for(int i=0; i<boxNum; i++)
            {
                float dy = -pos.y + boxPositionsY[i];
                float dx = pos.x - boxPositionsX[i];

                if(dy > 0.0 && abs(dx) < 50.0 && abs(dy) < 200.0)
                {
                    if(dy > ml)
                    {
                        ml = dy;
                        mn = i;
                        ydy = dy;
                    }
                }

                /*float dx = pos.x - boxPositionsX[i];
                float dy = pos.y - boxPositionsY[i];

                float d = dx*dx + dy*dy;

                if(d < ml)
                {
                    ml = d;
                    mn = i;
                }*/
            }

            if(mn != -1)
            {
                /*float ringwidth = 20.0;

                float ringdist = 100.0 + 7.0*sin(beatFrac*3.141592);

                float dist = sqrt(ml);

                float dist2ring = dist - ringdist;

                float pring = dist2ring/ringwidth;

                pring = pring/2.0 + abs(pring);

                pring = clamp(pring, 0.0, 1.0);

                pring = 1.0 - pring;

                float height = pring;

                float light = height*sin(3.141592*(pos.y)/windowSize.y);*/

                //light = 1.0 - light;

                /*float linelen = 200.0;
                float lineypos = boxPositionsY[mn] - linelen;

                float dist = distToLine(vec2(boxPositionsX[mn], boxPositionsY[mn]), vec2(boxPositionsX[mn], lineypos), gl_FragCoord.xy);

                float timec = ydy / linelen;

                //timec*=sin(beatFrac*3.141592);

                //timec = sin((beatFrac)*3.141592) + timec;

                beatFrac += timec;

                dist += sin((beatFrac + timec)*3.141592/2.0)*10.0;

                float maxDist = 10.0 + abs(1.0 - sin(((linelen-ydy)/linelen)*3.141592/2.0))*40.0 + timec * 50.0;
                maxDist = maxDist / 1.5;

                float col = clamp(dist/maxDist, 0.0, 1.0);

                col = 1.0 - col;

                gl_FragColor = vec4(0.4*col, 0.0, 0.0, 0.0);*/

            }

            //gl_FragColor = vec4(0.4 * pring,0.4 * pring,0.4 * pring,0.0);
            //gl_FragColor = vec4(0.2 *light + 0.05,0.2 * light + 0.05,0.2 * light + 0.05,0.0);
        }

}