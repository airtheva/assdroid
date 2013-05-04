precision lowp float;
uniform sampler2D uTexture;
varying vec3 vMVPosition;
varying vec4 vColor;
varying vec3 vMVNormal;
varying vec2 vTexCoord;
void main()
{
	gl_FragColor = vColor * texture2D(uTexture, vTexCoord);
}