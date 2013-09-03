package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector4;

public class bhkConvexVerticesShape extends bhkConvexShape
{
	/**
	 <niobject name="bhkConvexVerticesShape" abstract="0" inherit="bhkConvexShape" ver1="20.0.0.5">

	 A convex shape built from vertices. Note that if the shape is used in
	 a non-static object (such as clutter), then they will simply fall
	 through ground when they are under a bhkListShape.
	 
	 <add name="Unknown 6 Floats" type="float" arr1="6">
	 Unknown. Must be (0.0,0.0,-0.0,0.0,0.0,-0.0) for arrow detection to work (mind the minus signs, -0.0 is 0x80000000 in hex).
	 </add>
	 <add name="Num Vertices" type="uint">Number of vertices.</add>
	 <add name="Vertices" type="Vector4" arr1="Num Vertices">
	 Vertices. Fourth component is 0. Lexicographically sorted.
	 </add>
	 <add name="Num Normals" type="uint">The number of half spaces.</add>
	 <add name="Normals" type="Vector4" arr1="Num Normals">
	 Half spaces as determined by the set of vertices above. First three components define the normal pointing to the exterior, fourth component is the signed distance of the separating plane to the origin: it is minus the dot product of v and n, where v is any vertex on the separating plane, and n is the normal. Lexicographically sorted.
	 </add>
	 </niobject> 	 
	 */

	public float[] unknown6Floats;

	public int numVertices;

	public NifVector4[] vertices;

	public int numNormals;

	public NifVector4[] normals;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknown6Floats = ByteConvert.readFloats(6, stream);

		numVertices = ByteConvert.readInt(stream);
		vertices = new NifVector4[numVertices];
		for (int i = 0; i < numVertices; i++)
		{
			vertices[i] = new NifVector4(stream);
		}
		numNormals = ByteConvert.readInt(stream);
		normals = new NifVector4[numNormals];
		for (int i = 0; i < numNormals; i++)
		{
			normals[i] = new NifVector4(stream);
		}

		return success;
	}
}