uniform mat4 uModelMatrix;
uniform mat4 uViewMatrix;
uniform mat4 uProjectionMatrix;
attribute vec4 aPosition;
attribute vec4 aColor;
attribute vec3 aNormal;
attribute vec2 aTexCoord;
varying vec3 vMVPosition; // These vec3 are multiplied without projection matrix, so they don't have w?
varying vec4 vColor;
varying vec3 vMVNormal;
varying vec2 vTexCoord;
void main()
{
	mat4 MVMatrix = uViewMatrix * uModelMatrix;
	mat4 MVPMatrix = uProjectionMatrix * MVMatrix;
	vMVPosition = vec3(MVMatrix * aPosition);
	vColor = aColor;
	vMVNormal = vec3(MVMatrix * vec4(aNormal, 0.0));
	vTexCoord = aTexCoord;
	//gl_Position = uProjectionMatrix * uViewMatrix * uModelMatrix * aPosition;
	// Oh yes, this works!
	gl_Position = MVPMatrix * aPosition;
}