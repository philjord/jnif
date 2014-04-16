package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifDecalVectorArray
{
	/**
	 <compound name="DecalVectorArray">

	 Array of Vectors for Decal placement in BSDecalPlacementVectorExtraData.
	 
	 <add name="Num Vectors" type="short">Number of sets</add>
	 <add name="Points" type="Vector3" arr1="Num Vectors">Vector XYZ coords</add>
	 <add name="Normals" type="Vector3" arr1="Num Vectors">Vector Normals</add>
	 </compound>
	 */

	public int numVectors;

	public NifVector3[] points;

	public NifVector3[] normals;

	public NifDecalVectorArray(InputStream stream) throws IOException
	{

		numVectors = ByteConvert.readShort(stream);

		points = new NifVector3[numVectors];
		normals = new NifVector3[numVectors];
		for (int i = 0; i < numVectors; i++)
		{
			points[i] = new NifVector3(stream);
			normals[i] = new NifVector3(stream);
		}

	}
}
