package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifSkinData
{
	/**
	 <compound name="SkinData">

	 Skinning data component.
	 
	 <add name="Rotation" type="Matrix33">
	 Rotation offset of the skin from this bone in bind position.
	 </add>
	 <add name="Translation" type="Vector3">
	 Translation offset of the skin from this bone in bind position.
	 </add>
	 <add name="Scale" type="float">
	 Scale offset of the skin from this bone in bind position. (Assumption - this is always 1.0 so far)
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
	public NifMatrix33 rotation;

	public NifVector3 translation;

	public float scale;

	public NifVector3 boundingSphereOffset;

	public float boundingSphereRadius;

	public short numVertices;

	public NifSkinWeight[] vertexWeights;

	public NifSkinData(boolean hasVertexWeights, InputStream stream) throws IOException
	{
		rotation = new NifMatrix33(stream);
		translation = new NifVector3(stream);
		scale = ByteConvert.readFloat(stream);
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
