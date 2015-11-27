package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import tools.MiniFloat;

public class BSVertexData
{
	public int vertexFormatFlags7;
	public int vertexType;

	public BSHalfFloatVector3 vertex;

	public float unknownDot;
	public float unknownShort1;

	public BSHalfFloatTexCoord2 texCoord;

	public BSByteColor4 color;

	public BSByteVector3 normal;

	public byte b1;

	public BSByteVector3 tangent;

	public byte b2;

	public float[] BoneWeights = new float[4];//half
	public int[] BoneIndices = new int[4];///surely this is unsigned byte?

	/*
	 test files 
	 F:\game media\Fallout4\Meshes\Actors\Character\FaceGenData\FaceGeom\Fallout4.esm\000B0FC4.NIF
	 F:\game media\fallout4\meshes\landscape\animated\primegroundattack01\primegroundattack01.nif
	 F:\game media\Fallout4\Meshes\Actors\Bloatfly\CharacterAssets\BloatflyDeathExplosion.nif
	 F:\game media\Fallout4\Meshes\Actors\Bloatfly\CharacterAssets\BloatflyFatGlow.nif
	 F:\game media\Fallout4\Meshes\PreCombined\00000FC9_03063B7F_OC.NIF
	 F:\game media\Fallout4\Meshes\SCOL\CM00247ABB.NIF
	 F:\game media\Fallout4\Meshes\Terrain\Commonwealth\Commonwealth.4.-8.-8.BTR
	 
	 binormals are all that's missing
	 
	 // vertexFormatFlag1 bits
	  7 & 1 == not use flags1
	  7 & 2 == has Color 
	  7 & 4 == has bones
	  7 & 8 == no exist
	  7 & 16 == 4 more 
	  7 & 32 == no exist
	  7 & 64 == 8 more
	  
	
	format7ToVertSize 1  size 20 = 1
	format7ToVertSize 3  size 24 = 1 2	
	format7ToVertSize 65 size 28 = 1          64
	format7ToVertSize 67 size 32 = 1 2        64 
		 
	format7ToVertSize 5  size 32 = 1   4
	format7ToVertSize 7  size 36 = 1 2 4  	
	format7ToVertSize 23 size 40 = 1 2 4   16
	format7ToVertSize 69 size 40 = 1   4      64
	format7ToVertSize 71 size 44 = 1 2 4      64 
	
	vertexFormatFlags1:
	just enumerated type listing?
	2=vertex+dot
	3=and texCoord
	4=and normal
	5=and tangent
	6=and color
	7=and 4 bytes (more uv sets perhaps?)
	8=and 4 bytes
	9=and 4 bytes
	10=and 4 bytes
	
	
	format1ToVertSize 2 size 8 
	format1ToVertSize 3 size 12 
	format1ToVertSize 4 size 16
	format1ToVertSize 5 size 20
	format1ToVertSize 6 size 24
	format1ToVertSize 7 size 28
	format1ToVertSize 8 size 32
	format1ToVertSize 9 size 36
	format1ToVertSize 10 size 40	
	*/

	public BSVertexData(int vertexFormatFlags7, int vertexFormatFlags1, InputStream stream) throws IOException
	{
		this.vertexFormatFlags7 = vertexFormatFlags7;
		this.vertexType = vertexFormatFlags1;

		if ((vertexFormatFlags7 & 0x01) != 0 || vertexType >= 2)
		{
			vertex = new BSHalfFloatVector3(stream);

			if (vertexFormatFlags1 != 6 && vertexFormatFlags1 != 3)
				unknownDot = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
			else
				unknownShort1 = ByteConvert.readUnsignedShort(stream);
		}

		if ((vertexFormatFlags7 & 0x01) != 0 || vertexType >= 3)
		{
			texCoord = new BSHalfFloatTexCoord2(stream);
		}

		if ((vertexFormatFlags7 & 0x01) != 0 || vertexType >= 4)
		{
			normal = new BSByteVector3(stream);
			b1 = ByteConvert.readByte(stream);
		}
		if ((vertexFormatFlags7 & 0x01) != 0 || vertexType >= 5)
		{
			tangent = new BSByteVector3(stream);
			b2 = ByteConvert.readByte(stream);
		}

		if (((vertexFormatFlags7 & 0x01) != 0 && (vertexFormatFlags7 & 0x02) != 0) || ((vertexFormatFlags7 & 0x01) == 0 && vertexType >= 6))
		{
			color = new BSByteColor4(stream);
		}

		if ((vertexFormatFlags7 & 0x01) != 0)
		{
			if ((vertexFormatFlags7 & 0x04) != 0)
			{
				for (int i = 0; i < 4; i++)
					BoneWeights[i] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
				for (int i = 0; i < 4; i++)
					BoneIndices[i] = ByteConvert.readUnsignedByte(stream);
			}

			if ((vertexFormatFlags7 & 0x10) != 0)
			{
				ByteConvert.readInt(stream);
			}

			if ((vertexFormatFlags7 & 0x40) != 0)
			{
				ByteConvert.readBytes(8, stream);
			}
		}
		else
		{
			//TODO: 9 could in fact be grabbing bone data (and 10)
			if (vertexType >= 7)
			{
				ByteConvert.readInt(stream);
			}
			if (vertexType >= 8)
			{
				ByteConvert.readInt(stream);
			}
			if (vertexType >= 9)
			{
				ByteConvert.readInt(stream);
			}
			if (vertexType >= 10)
			{
				ByteConvert.readInt(stream);
			}
		}

	}

	public String toString()
	{
		return "[BSVertexData] vertex " + vertex + " : texCoord " + texCoord;
	}

}
