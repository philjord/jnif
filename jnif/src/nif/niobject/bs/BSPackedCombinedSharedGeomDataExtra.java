package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.BSVertexData;
import nif.compound.NifMatrix33;
import nif.compound.NifSphereBV;
import nif.compound.NifTriangle;
import nif.compound.NifVector3;

public class BSPackedCombinedSharedGeomDataExtra extends BSExtraData
{

	public int dwordsPerVertex;
	public int vertexFormat2;
	public int vertexFormatFlags3;
	public int vertexFormat4;
	public int vertexFormat5;
	public int vertexFormatFlags6;
	public int vertexFormatFlags7;
	public int vertexFormat8;
	public int numTriangles;
	public int numVertices;
	public BSVertexData[] vertexData;
	public NifTriangle[] triangles;
	/*
	
	
	f:\game media\fallout4\meshes\precombined\00000fc9_08744b5f_oc.nif
	1Materials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSMMaterials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSMMaterials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSM
	f:\game media\fallout4\meshes\precombined\00000fc9_0fa78d95_oc.nif
	1Materials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSMMaterials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSMMaterials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSM
	f:\game media\fallout4\meshes\precombined\00000fc9_2952ac67_oc.nif
	1Materials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSMMaterials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSMMaterials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSM
	f:\game media\fallout4\meshes\precombined\00000fc9_2bd78aa7_oc.nif
	1Materials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSMMaterials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSMMaterials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSM
	f:\game media\fallout4\meshes\precombined\00000fc9_357a5b52_oc.nif
	1Materials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSMMaterials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSMMaterials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSM
	f:\game media\fallout4\meshes\precombined\00000fd2_1716d923_oc.nif
	1Materials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSMMaterials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSMMaterials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSM
	f:\game media\fallout4\meshes\precombined\00000fda_20b9886e_oc.nif
	1Materials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSMMaterials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSMMaterials\Architecture\DiamondCity\DiamondRadioTowers01Alpha.BGSM
	
	centers offset from 0,0,0
	no translations
	tower01 t=10 v=14 r=1041.4841 c=0,0,876.17
	tower02 t=4 v=8 r=888.6927 c=0,0,872.35
	tower03 t=4 v=8 r=898.1022 c=0,0,876.16
	tower04 t=6 v=10 r=470.5269 c=0,0,438.62
	tower05 t=8 v=12 r=426.9221 c=0,0,392.90
	tower06 t=4 v=8 r=379.6253 c=0,0,365.56
	tower07 t=48 v=68 r=649.8404 c=0,-4.7,324.4
	
	all contains only one bstrishape and no texture apart from radio tower (center always 0,0,0)
	they seem to have a translation
	00000fc9_08744b5f_oc block 3 size = 280 t=8 v=16 r=1000.9674 c=0,0,0
	00000fc9_0fa78d95_oc block 3 size = 156 t=4 v=8 r=269.49237
	00000fc9_2952ac67_oc block 3 size = 156 t=48 v=68 r=617.30176
	00000fc9_2bd78aa7_oc block 3 size = 280 t=10 v=18 r=864.4895
	00000fc9_357a5b52_oc block 3 size = 156 t=48 v=68 r=617.30176
	00000fd2_1716d923_oc block 3 size = 156 t=48 v=68 r=948.6953 trans = -2629.999 2399.4658 1242.3391
	00000fda_20b9886e_oc block 3 size = 280 t=56 v=80 r=1165.8461
	7 towers
	
	
	f:\game media\fallout4\meshes\architecture\\ussconstitution\signweatherby01.nif
	1signweatherbysignweatherbysignweat
	f:\game media\fallout4\meshes\precombined\0000dbbe_3a0a7711_oc.nif -> it is a single bstrishape amoungst many in here
	1signweatherbysignweatherbysignweat
	f:\game media\fallout4\meshes\precombined\0000dbbe_3bb2127f_oc.nif
	1signweatherbysignweatherbysignweat
	
	
	given constant size perhaps always quads? certainly crazy translate would put them in place
	n rotations so just 3 verts per each  
	
	when 2 sided the bounds radius would be identical and center, I see lots of identical radius with small moves in centers
	
	
	when one then bounds match to parent, when many they are smaller and centers move around a bit
		
	weatherby is 171 in 0000DBBE_3BB2127F_OC.NIF 	
	
	0000DBBE_3BB2127F_OC.NIF object 204 has a LOD count in parent of 6006 and the LOD data shows numbers 3003 and 9009 in 2 interesting spots
	yes common pattern for BSMeshLODTriShape parent LOD[5]-LOD[1]
	
	I see when I have 2 data the number seem not to add up?
	
	
	block 138 seems to show 2 sided figures that have a implicit 0,0,0 and the quad extends out from there
	each swap out is off by 45ish in Y like that is part of the width, always matching in Z same height
	// no no centers match up!
	 * 
	 * float after trans would normally be scale wouldn't it? but also scale appears after rot normally
	 * 
	 * 
	 *UnknownInt1 and 2 appear are never 0 when SLSF2_Tree_Anim is set so they are texture or shader flags of some sort
	 *they are not 0 in others cases 2 but it's hard to see
	 */

	public int UnknownInt1;
	public int UnknownInt2;
	public int NumData;
	public int[][] Unk1;
	public Data[] data;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		dwordsPerVertex = ByteConvert.readUnsignedByte(stream);
		vertexFormat2 = ByteConvert.readUnsignedByte(stream);
		vertexFormatFlags3 = ByteConvert.readUnsignedByte(stream);
		vertexFormat4 = ByteConvert.readUnsignedByte(stream);
		vertexFormat5 = ByteConvert.readUnsignedByte(stream);
		vertexFormatFlags6 = ByteConvert.readUnsignedByte(stream);
		vertexFormatFlags7 = ByteConvert.readUnsignedByte(stream);
		vertexFormat8 = ByteConvert.readUnsignedByte(stream);
		numVertices = ByteConvert.readInt(stream);
		numTriangles = ByteConvert.readInt(stream);

		UnknownInt1 = ByteConvert.readInt(stream);// related to transparency?
		UnknownInt2 = ByteConvert.readInt(stream);
		
		
		NumData = ByteConvert.readInt(stream);

		//MUST be extents!!!!!!!
		Unk1 = new int[NumData][2];
		for (int i = 0; i < NumData; i++)
		{
			Unk1[i][0] = ByteConvert.readInt(stream); // always a fixed value
					
			Unk1[i][1] = ByteConvert.readInt(stream);		
			//for (int j = 0; j < 8; j++)
			{
				//		System.out.println(" " + j + " " + ByteConvert.readUnsignedByte(stream) / 255f);
			}
			//for (int j = 0; j < 8; j++)
			{
		//		System.out.println(" " + j + " " + ((ByteConvert.readUnsignedByte(stream) / 255.0f) * 2.0f - 1.0f));
			}
			//	for (int j = 0; j < 4; j++)
			{
				//		System.out.println(" " + j + " " + MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream)));
			}

		//	for (int j = 0; j < 4; j++)
			{
			//	System.out.println(" " + j + " " + ByteConvert.readUnsignedShort(stream));
			}
			// NOT FLOATS!

		}

		data = new Data[NumData];
		for (int i = 0; i < NumData; i++)
		{
			data[i] = new Data(stream);
		}

		return success;
	}

	public static class Data
	{
		public int numTris;
		public int numLODs;
		public int[][] LODs;

		public int NumCombined;

		public Combined[] Combined;

		public int UnkInt1;
		public int UnkInt2;

		public Data(InputStream stream) throws IOException
		{
			numTris = ByteConvert.readInt(stream);
			numLODs = ByteConvert.readInt(stream);
			// total lods only sometimes = numTris
			LODs = new int[numLODs][2];
			for (int i = 0; i < numLODs; i++)
			{
				LODs[i][0] = ByteConvert.readInt(stream);// tri count				
				LODs[i][1] = ByteConvert.readInt(stream);// distance
			}

			NumCombined = ByteConvert.readInt(stream);
			Combined = new Combined[NumCombined];
			for (int i = 0; i < NumCombined; i++)
			{
				Combined[i] = new Combined(stream);
			}

			// No they are really similar amoungst packed that are next to each other?
			UnkInt1 = ByteConvert.readInt(stream);
			UnkInt2 = ByteConvert.readInt(stream);

		}
	}

	public static class Combined
	{
		public NifMatrix33 rot;
		public float Unk1;
		public NifVector3 trans;
		public float scale; // bounds matches up
		public NifSphereBV bounds;

		public Combined(InputStream stream) throws IOException
		{
			rot = new NifMatrix33(stream);
			Unk1 = ByteConvert.readFloat(stream);
			trans = new NifVector3(stream);
			scale = ByteConvert.readFloat(stream);
			bounds = new NifSphereBV(stream);
		}
	}
}
