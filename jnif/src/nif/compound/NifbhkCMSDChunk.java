package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifbhkCMSDChunk
{
	/**
	 * 

	    <compound name="bhkCMSDChunk">
    Defines subshape chunks in bhkCompressedMeshShapeData
        <add name="Translation" type="Vector4">Chunk individual translation</add>
        <add name="Chunk Material index" type="uint">Selects the material for chunk. It is numeric index of array item in Chunk Materials.</add>
        <add name="Unknown Short 1" type="ushort">Always 0xFFFF?</add>
        <add name="Chunk Transform index" type="ushort">Selects the transform for chunk. It is numeric index to Chunk Transforms.</add>
        <add name="Num Vertices" type="uint">Number of compressed vertices</add>
        <add name="Vertices" type="ushort" arr1="Num Vertices">Compressed vertices</add>
        <add name="Num Indices" type="uint"></add>
        <add name="Indices" type="ushort" arr1="Num Indices"></add>
        <add name="Num Strips" type="uint">Number of compressed strips</add>
        <add name="Strips" type="ushort" arr1="Num Strips">Compressed strips</add>
        <add name="Num Indices 2" type="uint">Number of </add>
        <add name="Indices 2" type="ushort" arr1="Num Indices 2">Compressed </add>
    </compound>

	 */

	public NifVector4 translation;

	public int materialIndex;

	public int unknownShort;

	public short transformIndex;

	public int NumVertices;

	public int[] Vertices;

	public int NumIndices;

	public int[] Indices;

	public int NumStrips;

	public int[] Strips;

	public int NumIndices2;

	public short[] Indices2WeldInfo;

	public NifbhkCMSDChunk(ByteBuffer stream) throws IOException
	{
		translation = new NifVector4(stream);
		materialIndex = ByteConvert.readInt(stream);
		unknownShort = ByteConvert.readUnsignedShort(stream);
		transformIndex = ByteConvert.readShort(stream);

		NumVertices = ByteConvert.readInt(stream);
		Vertices = new int[NumVertices];
		for (int i = 0; i < NumVertices; i++)
		{
			Vertices[i] = ByteConvert.readUnsignedShort(stream);
		}
		NumIndices = ByteConvert.readInt(stream);
		Indices = new int[NumIndices];
		for (int i = 0; i < NumIndices; i++)
		{
			Indices[i] = ByteConvert.readUnsignedShort(stream);
		}
		NumStrips = ByteConvert.readInt(stream);
		Strips = new int[NumStrips];
		for (int i = 0; i < NumStrips; i++)
		{
			Strips[i] = ByteConvert.readUnsignedShort(stream);
		}
		NumIndices2 = ByteConvert.readInt(stream);
		Indices2WeldInfo = new short[NumIndices2];
		for (int i = 0; i < NumIndices2; i++)
		{
			Indices2WeldInfo[i] = ByteConvert.readShort(stream);
		}

	}
}
