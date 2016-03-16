package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifSkinData
{
	/**
	 <compound name="SkinData">

	 Skinning data component.
	 
	 <add name="SkinTransform" type="SkinTransform">	
	 </add>
	 <add name="Bounding Sphere Offset" type="Vector3">
	 Translation offset of a bounding sphere holding all vertices. (Note that its a Sphere Containing Axis Aligned Box not a minimum volume Sphere)
	 </add>
	 <add name="Bounding Sphere Radius" type="float">Radius for bounding sphere holding all vertices.</add>
	 <add name="Num Vertices" type="ushort">Number of weighted vertices.</add>
	 <add name="Vertex Weights" type="SkinWeight" arr1="Num Vertices" ver2="4.2.1.0">The vertex weights.</add>
	 <add name="Vertex Weights" type="SkinWeight" arr1="Num Vertices" ver1="4.2.2.0" cond="ARG != 0">The vertex weights.</add>
	 </compound>
	 */
	public NifSkinTransform nifSkinTransform;

	public NifVector3 boundingSphereOffset;

	public float boundingSphereRadius;

	public short numVertices;

	public NifSkinWeight[] vertexWeights;

	public NifSkinData(boolean hasVertexWeights, ByteBuffer stream) throws IOException
	{
		nifSkinTransform = new NifSkinTransform(stream);
		boundingSphereOffset = new NifVector3(stream);
		boundingSphereRadius = ByteConvert.readFloat(stream);
		numVertices = ByteConvert.readShort(stream);
		if (hasVertexWeights)
		{
			vertexWeights = new NifSkinWeight[numVertices];
			for (int i = 0; i < numVertices; i++)
			{
				vertexWeights[i] = new NifSkinWeight(stream);
			}
		}
	}
}
