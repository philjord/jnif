package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifMatchGroup
{
	/**
	 <compound name="MatchGroup">

	 Group of vertex indices of vertices that match.
	 
	 <add name="Num Vertices" type="ushort">Number of vertices in this group.</add>
	 <add name="Vertex Indices" type="ushort" arr1="Num Vertices">The vertex indices.</add>
	 </compound>
	 */

	public short numVertices;

	public short[] vertexIndices;

	public NifMatchGroup(InputStream stream) throws IOException
	{
		numVertices = ByteConvert.readShort(stream);
		vertexIndices = new short[numVertices];
		for (int i = 0; i < numVertices; i++)
		{
			vertexIndices[i] = ByteConvert.readShort(stream);
		}
	}
}
