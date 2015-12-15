package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import tools.MiniFloat;

public class BSVertexData
{
	public int vertexFormatFlags7;
	public int vertexFormatFlags6;
	public int vertexFormatFlags3;
	public int dwordsPerVertex;// * 4 = num bytes per vertex

	public NifVector3 vertex;
	public float bitangentX;

	public float unknownShort1;

	public BSHalfFloatTexCoord2 texCoord;

	public BSByteColor4 color;

	public BSByteVector3 normal;
	public float bitangentY;

	public BSByteVector3 tangent;
	public float bitangentZ;

	public float[] BoneWeights = new float[4];
	public int[] BoneIndices = new int[4];

	/*
	 test files 
	 F:\game media\Fallout4\Meshes\Actors\Character\FaceGenData\FaceGeom\Fallout4.esm\000B0FC4.NIF
	 F:\game media\fallout4\meshes\landscape\animated\primegroundattack01\primegroundattack01.nif
	 F:\game media\Fallout4\Meshes\Actors\Bloatfly\CharacterAssets\BloatflyDeathExplosion.nif
	 F:\game media\Fallout4\Meshes\Actors\Bloatfly\CharacterAssets\BloatflyFatGlow.nif
	 F:\game media\Fallout4\Meshes\PreCombined\00000FC9_03063B7F_OC.NIF
	 F:\game media\Fallout4\Meshes\SCOL\CM00247ABB.NIF
	 F:\game media\Fallout4\Meshes\Terrain\Commonwealth\Commonwealth.4.-8.-8.BTR
	 
	 //////////////////////////////////////////////////figment tests
	// 0 Meshes\Actors\Vertibird\CharacterAssets\VbirdDeathExplosion.nif
	// 2 Meshes\Terrain\Commonwealth\Commonwealth.4.24.52.BTR
	// 3 Meshes\Actors\LibertyPrime\LibertyPrimeHSBodyLights.nif
	// 4 Meshes\Actors\SuperMutantBehemoth\SupterMutantBehemothSwan.nif
	// 5 Meshes\Actors\Vertibird\CharacterAssets\VbirdDeathExplosion.nif
	// 6 Meshes\Actors\Character\CharacterAssets\FaceParts\MaleEyesShade.nif
	// 7 Meshes\Actors\Character\CharacterAssets\FaceParts\MaleEyesShade.nif
	// 8 Meshes\Actors\Character\CharacterAssets\FemaleHands.nif
	// 9 Meshes\Actors\Character\CharacterAssets\Hair\Female\FemaleHair05.nif
	//10 Meshes\Actors\Character\CharacterAssets\FaceParts\MaleEyes.nif 
	
	 
	// vertexFormatFlag1 bits
	7 & 1 == has Vertices (==0 has other issues)
	7 & 2 == has Color 
	7 & 4 == has Bones
	7 & 8 == does not exist
	7 & 16 == unknown 4 bytes 
	7 & 32 == does not exist
	7 & 64 == normal precision vertex floats
	  
	
	format7ToVertSize 1  size 20 = 1
	format7ToVertSize 3  size 24 = 1 2			 
	format7ToVertSize 5  size 32 = 1   4
	format7ToVertSize 7  size 36 = 1 2 4  	
	format7ToVertSize 23 size 40 = 1 2 4 16
	format7ToVertSize 65 size 28 = 1        64
	format7ToVertSize 67 size 32 = 1 2      64 
	format7ToVertSize 69 size 40 = 1   4    64
	format7ToVertSize 71 size 44 = 1 2 4    64 
	
	
		
	
	
	lack1 drop by 8, yes so 7&1  = first 8
	format7ToVertSize 2 size 16 = 2 (not unique)
	format7ToVertSize 4 size 24 = 4
	format7ToVertSize 6 size 28 = 2 4
	
	format7ToVertSize 0 size  8, 12, 16 so flag7==0 needs others to dictate, same for 7&2
	 
	so in case of dwordsPerVertex == 3 4 5 the bitangent data is funny (ignored?)	
	
	
	format dwpv 2 	f2:0 	f3:0 	f4:0 	f5:0 	f6:16 	f7:0	count 4230	
	format dwpv 3 	f2:2 	f3:0 	f4:0 	f5:0 	f6:48 	f7:0	count 6206
	format dwpv 3 	f2:0 	f3:2 	f4:0 	f5:0 	f6:144 	f7:0	count 1059
	format dwpv 4 	f2:2 	f3:0 	f4:3 	f5:0 	f6:48 	f7:2	count 2
	format dwpv 4 	f2:0 	f3:2 	f4:3 	f5:0 	f6:144 	f7:2	count 441	
	format dwpv 4 	f2:2 	f3:3 	f4:0 	f5:0 	f6:176 	f7:0	count 3	
	format dwpv 5 	f2:2 	f3:3 	f4:4 	f5:0 	f6:176 	f7:2	count 1
	format dwpv 6 	f2:2 	f3:0 	f4:48 	f5:0 	f6:48 	f7:4	count 8
	format dwpv 7 	f2:2 	f3:0 	f4:67 	f5:0 	f6:48 	f7:6	count 50
	
	f7&0x01 below
	format dwpv 5 	f2:2 	f3:67 	f4:0 	f5:0 	f6:176 	f7:1	count 53844
	format dwpv 6 	f2:2 	f3:67 	f4:5 	f5:0 	f6:176 	f7:3	count 22115
	format dwpv 7 	f2:4 	f3:101 	f4:0 	f5:0 	f6:176 	f7:65	count 6852	
	format dwpv 8 	f2:4 	f3:101 	f4:7 	f5:0 	f6:176 	f7:67	count 7094	
	format dwpv 8 	f2:2 	f3:67 	f4:80 	f5:0 	f6:176 	f7:5	count 2569
	format dwpv 8 	f2:2 	f3:67 	f4:80 	f5:32 	f6:176 	f7:5	count 5557		
	format dwpv 9 	f2:2 	f3:67 	f4:101 	f5:0 	f6:176 	f7:7	count 1522	
	format dwpv 9 	f2:2 	f3:67 	f4:101 	f5:32 	f6:176 	f7:7	count 5601	
	format dwpv 10 	f2:2 	f3:67 	f4:101 	f5:144 	f6:176 	f7:23	count 604		
	format dwpv 10 	f2:4 	f3:101 	f4:112 	f5:0 	f6:176 	f7:69	count 3
	format dwpv 11 	f2:4 	f3:101 	f4:135 	f5:0 	f6:176 	f7:71	count 3	
	
	
	
	f2 = 0, 2, 4 (bit maskish?)
	f3 = 0, 2, 3, 67, 101
	f4 = 0, 3, 4, 5, 7, 48, 67, 80, 101, 112, 135
	f5 = 0, 32, 144
	f6 = 16, 48, 144, 176
	
	
	Other notes:
	f3&64 is true when f7&1 is true
	f8=0 in all cases
	precombined dataSize==0 (is data in BSPackedCombinedSharedGeomDataExtra)
	format dwpv 8 f2:4	f3:101	f4:7	f5:0	f6:176 	f7:67	count 86788
	format dwpv 7 f2:4	f3:101	f4:0	f5:0	f6:176 	f7:65	count 94491
	
	
	flag5  ==32 ==144 (likely to alter meaning of bone weights)
	f5 == 0 occurs regularly
	f7 == 5 	f5 == 32 occurs only under actors facegendata	
	f7 == 7 	f5 == 32 occurs only under actors facegendata
	f7 == 23	f5 == 144 occurs only under actors facegendata
	
	 */

	public BSVertexData(int vertexFormatFlags7, int vertexFormatFlags6, int vertexFormatFlags3, int dwordsPerVertex, InputStream stream)
			throws IOException
	{
		this.vertexFormatFlags7 = vertexFormatFlags7;
		this.vertexFormatFlags6 = vertexFormatFlags6;
		this.vertexFormatFlags3 = vertexFormatFlags3;
		this.dwordsPerVertex = dwordsPerVertex;

		if ((vertexFormatFlags7 & 0x40) != 0)
		{
			vertex = new NifVector3(stream);
			if ((vertexFormatFlags6 & 0x80) != 1 && (vertexFormatFlags3 & 0x40) != 1)
				bitangentX = ByteConvert.readFloat(stream);
			else
				unknownShort1 = ByteConvert.readFloat(stream);
		}
		else //if ((vertexFormatFlags7 & 0x01) != 0 || bytesPerVertex >= 2)
		{
			vertex = new BSHalfFloatVector3(stream);

			if ((vertexFormatFlags6 & 0x80) != 1 && (vertexFormatFlags3 & 0x40) != 1)
				bitangentX = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
			else
				unknownShort1 = ByteConvert.readUnsignedShort(stream);
		}

		//if ((vertexFormatFlags7 & 0x01) != 0 || bytesPerVertex >= 3)
		if ((vertexFormatFlags6 & 0x20) != 0)
		{
			texCoord = new BSHalfFloatTexCoord2(stream);
		}

		//if ((vertexFormatFlags7 & 0x01) != 0 || bytesPerVertex >= 4)
		if ((vertexFormatFlags6 & 0x80) != 0)
		{
			normal = new BSByteVector3(stream);
			bitangentY = ((ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f);
		}

		//if ((vertexFormatFlags7 & 0x01) != 0 || bytesPerVertex >= 5)
		if ((vertexFormatFlags3 & 0x40) != 0)
		{
			tangent = new BSByteVector3(stream);
			bitangentZ = ((ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f);
		}

		//if (((vertexFormatFlags7 & 0x01) != 0 && (vertexFormatFlags7 & 0x02) != 0)
		//		|| ((vertexFormatFlags7 & 0x01) == 0 && bytesPerVertex >= 6))
		if ((vertexFormatFlags7 & 0x02) != 0)
		{
			color = new BSByteColor4(stream);
		}

		//if ((vertexFormatFlags7 & 0x01) != 0)
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
		}
		/*	else
			{
				//TODO: 9 could in fact be grabbing bone data (and 10)
				if (bytesPerVertex >= 7)
				{
					ByteConvert.readInt(stream);
				}
				if (bytesPerVertex >= 8)
				{
					ByteConvert.readInt(stream);
				}
				if (bytesPerVertex >= 9)
				{
					ByteConvert.readInt(stream);
				}
				if (bytesPerVertex >= 10)
				{
					ByteConvert.readInt(stream);
				}
			}*/

	}

	public String toString()
	{
		return "[BSVertexData] vertex " + vertex + " : texCoord " + texCoord;
	}

}
