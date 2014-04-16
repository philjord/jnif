package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector3;

public class NiPhysXMeshDesc extends NiObject
{
	/**
	 
	 <niobject name="NiPhysXMeshDesc" abstract="0" inherit="NiObject" ver1="20.1.0.7">

	 Unknown PhysX node.
	 
	 <add name="Unknown Short 1" type="short">Unknown</add>
	 <add name="Unknown Float 1" type="float">Unknown</add>
	 <add name="Unknown Short 2" type="short">Unknown</add>
	 <add name="Unknown Bytes 1" type="byte" arr1="3">NXS</add>
	 <add name="Unknown Byte 1" type="byte">Unknown</add>
	 <add name="Unknown Bytes 1" type="byte" arr1="4">MESH</add>
	 <add name="Unknown Bytes 2" type="byte" arr1="8">Unknown</add>
	 <add name="Unknown Float 2" type="float">Unknown</add>
	 <add name="Unknown Int 1" type="int">Unknown</add>
	 <add name="Unknown Int 2" type="int">Unknown</add>
	 <add name="Num Vertices" type="int">Number of mesh vertices</add>
	 <add name="Unknown Int 4" type="int">Unknown</add>
	 <add name="Vertices" type="Vector3" arr1="Num Vertices">Vertices</add>
	 <add name="Unknown Bytes 3" type="byte" arr1="982">Unknown</add>
	 <add name="Unknown Shorts 1" type="short" arr1="368">Unknown</add>
	 <add name="Unknown Ints 1" type="uint" arr1="3328">Unknown</add>
	 <add name="Unknown Byte 2" type="byte">Unknown</add>
	 </niobject>
	 */

	public short unknownShort1;

	public float unknownFloat1;

	public short unknownShort2;

	public byte[] unknownBytes1;

	public byte unknownByte1;

	public byte[] unknownBytes2;

	public byte[] unknownBytes3;

	public float unknownFloat2;

	public int unknownInt1;

	public int unknownInt2;

	public int numVertices;

	public int unknownInt4;

	public NifVector3[] vertices;

	public byte[] unknownBytes4;

	public short[] unknownShorts1;

	public int[] unknownInts1;

	public byte unknownByte2;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownShort1 = ByteConvert.readShort(stream);
		unknownFloat1 = ByteConvert.readFloat(stream);
		unknownShort2 = ByteConvert.readShort(stream);
		unknownBytes1 = ByteConvert.readBytes(3, stream);
		unknownByte1 = ByteConvert.readByte(stream);
		unknownBytes2 = ByteConvert.readBytes(4, stream);
		unknownBytes3 = ByteConvert.readBytes(8, stream);
		unknownFloat2 = ByteConvert.readFloat(stream);
		unknownInt1 = ByteConvert.readInt(stream);
		unknownInt2 = ByteConvert.readInt(stream);
		numVertices = ByteConvert.readInt(stream);
		unknownInt4 = ByteConvert.readInt(stream);

		vertices = new NifVector3[numVertices];
		for (int i = 0; i < numVertices; i++)
		{
			vertices[i] = new NifVector3(stream);
		}

		unknownBytes4 = ByteConvert.readBytes(982, stream);
		unknownShorts1 = ByteConvert.readShorts(368, stream);
		unknownInts1 = ByteConvert.readInts(3328, stream);
		unknownByte2 = ByteConvert.readByte(stream);

		return success;
	}
}