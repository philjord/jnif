package nif.niobject.bhk;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector4;
import nif.compound.NifbhkCMSDBigTris;
import nif.compound.NifbhkCMSDChunk;
import nif.compound.NifbhkCMSDMaterial;
import nif.compound.NifbhkCMSDTransform;
import nif.niobject.NiObject;

public class bhkCompressedMeshShapeData extends NiObject
{
	/**
	 *         <niobject name="bhkCompressedMeshShapeData" inherit="NiObject">
	         A compressed mesh shape for collision in Skyrim.
	        <add name="Unknown Int 1" type="uint">Unknown</add>
	        <add name="Unknown Int 2" type="uint">Unknown</add>
	        <add name="Unknown Short 1" type="Flags">Unknown</add>
	        <add name="Unknown Short 2" type="Flags">Unknown</add>
	        <add name="Unknown Short 3" type="Flags">Unknown</add>
	        <add name="Unknown Short 4" type="Flags">Unknown</add>
	        <add name="Unknown Float 1" type="float">Unknown.</add>
	        <add name="Unknown Floats 1" type="Vector4">Seems to define bounding shape, this is lower left corner?</add>
	        <add name="Unknown Floats 2" type="Vector4">see above, upper right?</add>
	        <add name="Unknown Byte 1" type="byte">Unknown</add>
	        <add name="Unknown Int 3" type="uint">Unknown</add>
	        <add name="Unknown Int 4" type="uint">Unknown</add>
	        <add name="Unknown Int 5" type="uint">Unknown</add>
	        <add name="Unknown Byte 2" type="byte">Unknown</add>
	        <add name="Num Materials" type="uint">Number of chunk materials</add>
	        <add name="Chunk Materials" type="bhkCMSDMaterial" arr1="Num Materials">Table (array) with sets of materials. Chunks refers to this table by index.</add>
	        <add name="Unknown Int 6" type="uint">Unknown</add>
	        <add name="Num Transforms" type="uint">Number of chunk transformations</add>
	        <add name="Chunk Transforms" type="bhkCMSDTransform" arr1="Num Transforms" cond="Num Transforms >= 1">Table (array) with sets of transformations. Chunks refers to this table by index.</add>
	        <add name="Num Big Verts" type="uint">Unknown</add>
	        <add name="Big Verts" type="Vector4" arr1="Num Big Verts" cond="Num Big Verts >= 1">Compressed Vertices?</add>
	        <add name="Num Big Tris" type="uint">Unknown</add>
	        <add name="Big Tris" type="bhkCMSDBigTris" arr1="Num Big Tris" cond="Num Big Tris >= 1">Unknown</add>
	        <add name="Num Chunks" type="uint">Unknown</add>
	        <add name="Chunks" type="bhkCMSDChunk" arr1="Num Chunks"></add>
	        <add name="Unknown Int 12" type="uint">Unknown, end of block.</add>
	   </niobject>


	 */

	public int BitsPerIndex;

	public int BitsPerWindingIndex;

	public int WindIndexMask;

	public int IndexMask;

	public float radius;

	public NifVector4 boundsMin;

	public NifVector4 boundsMax;

	public byte UnknownByte1;

	public int UnknownInt3;

	public int UnknownInt4;

	public int UnknownInt5;

	public byte UnknownByte2;

	public int NumMaterials;

	public NifbhkCMSDMaterial[] ChunkMaterials;

	public int UnknownInt6;

	public int NumTransforms;

	public NifbhkCMSDTransform[] ChunkTransforms;

	public int NumBigVerts;

	public NifVector4[] BigVerts;

	public int NumBigTris;

	public NifbhkCMSDBigTris[] BigTris;

	public int NumChunks;

	public NifbhkCMSDChunk[] Chunks;

	public int UnknownInt12;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		BitsPerIndex = ByteConvert.readInt(stream);

		BitsPerWindingIndex = ByteConvert.readInt(stream);

		WindIndexMask = ByteConvert.readInt(stream);

		IndexMask = ByteConvert.readInt(stream);

		radius = ByteConvert.readFloat(stream);

		boundsMin = new NifVector4(stream);

		boundsMax = new NifVector4(stream);

		UnknownByte1 = ByteConvert.readByte(stream);

		UnknownInt3 = ByteConvert.readInt(stream);

		UnknownInt4 = ByteConvert.readInt(stream);

		UnknownInt5 = ByteConvert.readInt(stream);

		UnknownByte2 = ByteConvert.readByte(stream);

		NumMaterials = ByteConvert.readInt(stream);

		ChunkMaterials = new NifbhkCMSDMaterial[NumMaterials];
		for (int i = 0; i < NumMaterials; i++)
		{
			ChunkMaterials[i] = new NifbhkCMSDMaterial(stream);
		}

		UnknownInt6 = ByteConvert.readInt(stream);

		NumTransforms = ByteConvert.readInt(stream);

		ChunkTransforms = new NifbhkCMSDTransform[NumTransforms];
		for (int i = 0; i < NumTransforms; i++)
		{
			ChunkTransforms[i] = new NifbhkCMSDTransform(stream);
		}

		NumBigVerts = ByteConvert.readInt(stream);

		BigVerts = new NifVector4[NumBigVerts];
		for (int i = 0; i < NumBigVerts; i++)
		{
			BigVerts[i] = new NifVector4(stream);
		}

		NumBigTris = ByteConvert.readInt(stream);

		BigTris = new NifbhkCMSDBigTris[NumBigTris];
		for (int i = 0; i < NumBigTris; i++)
		{
			BigTris[i] = new NifbhkCMSDBigTris(stream);
		}
		NumChunks = ByteConvert.readInt(stream);

		Chunks = new NifbhkCMSDChunk[NumChunks];
		for (int i = 0; i < NumChunks; i++)
		{
			Chunks[i] = new NifbhkCMSDChunk(stream);
		}
		UnknownInt12 = ByteConvert.readInt(stream);

		return success;
	}
}
