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
import nif.niobject.NiExtraData;
import tools.MiniFloat;

public class BSPackedCombinedSharedGeomDataExtra extends NiExtraData
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
	
	Precompile folder physics content
	124871 total files 	2,213,609,472 total file size	
	88477 file count 642955258 file size; ending in _OC.NIF with no physics data
	36394 file count 957196266 file size; ending in _OC.NIF with physics data	 
	4484 file count 613457948 file size; ending in _Physics.NIF
	
	
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
	
	  
	 UnknownInt1 and 2 appear are never 0 when SLSF2_Tree_Anim is set so they are texture or shader flags of some sort
	 they are not 0 in others cases 2 but it's hard to see
	 
	 
	 my thought is these ar for grid aligned gear, by grid chop, name is grid square hash 
	 each has walls of grid in them
	 dec 1390487 = 00153797_Physics.NIF
	 13 subs 
	 first sub 2 materails lots of textures
	 so for a given right name part every appears in a given grid space limit 256 apart in x y z 
	 Must parse up to check =  tried parse of combines but seems non 1 to 1
	 looks like same name = same grid space
	 ok so within a file there is never more than 256 between node x y and z
	 
	
	 
	 
	 
	 00189492_00000000_OC.NIF has havok root and named nodes in it
	 but I see plenty of havok node in any of the big OC files
	 in the 0000000_OC file I see a bstrishape at 0,0,0 then lots at big numbers
	 
	 
	 
	 
	 00172297 = dec 1516183 BostonAirPortAliasCell vhas what looks like same room 3 times in fact
	 00172297_01ADAD47_OC.NIF has same material in it and is called 01ADAD47 like the last one
	 one weird piece of concrete is up high in corner (x0-5.12y=0-5.12)
	 00172297_0105A45C_OC.NIF contains the same 5 materials in nodes too looks like identical data
	 00172297_12D2E4A8_OC.NIF same 5 materials but X are 2560/2816 (512 offset from prev)
	 00172297_26AB25B4_OC.NIF same 5 materials but X are 3072/3328 (512 offset from prev)
	 
	 so second value is hash of xyz location of the grid being parsed?
	 
	these matarials are used to create cell (26AB25B4?), but not in OC file? too small
	fileName Materials\Vehicles\Airplane\Airplane_INT_01.BGSM
	fileName Materials\Vehicles\Airplane\Airplane_EXT_01.BGSM
	 
	 
	 
	00199630_0105A45C_OC.NIF	 
	dec 1676848  (the last precombine) this is an int cell 
	CombatZoneHoldingCell
	6 flat face is all, no windows
	5 shape has materials of floor ceiling wall wallalpha panel
	1 shape file has a roof reference, so not at all interior?
	
	
	single shape has 1 data 1 combined
	shape trans 132,104,2225
	
	floor hs 2 data in it with 4 in each? 2 each of them are identical in ROTR
	
	YawPitch looks to make floor rotation for both
	
	
	shapes trans vary only on z value, x = 2176 y=2208 (for 5)
	
	first shape (floors):
	note bstrishape translation is exact center of room so for shape sounds perfect
	
	combineds in first set trans=2304/2048,2336/2080,512 4 wall planes at mid or ceiling
	512 is center of room but X= 2304/2048 are 46.08/40.96 (on grid) Y= 2336/2080 is 46.72/41.6  
	both the above are grid spaced 256 but y's are not grid aligned
	I would note the above XYs are offset by 50 or 100 from the esm location
	all bounds = match x and y (2304/2048,2336) but z is 736/50 = 14.72 or ceiling height? why ceiling for bounds?
	second set are at floor height for z and each wall again in x
	
	combineds in second set have 256 for z value, so 256/50 -5.12 (modular grid) which is floor floor height
	note floor height for both trans and bounds
	
	next shape is ceiling, which has only 4 at ceiling
	
	next shape wall 8 all on floor at walls centers but 2 types of rotation (one has roll in it??)
	
	next shape wall alpha 8 matches wall exactly
	
	next wall panel 8 matches wall exactly
	
	of 3 wall num tri num vert all vary (weird); Unk1 varies; UnkInt1/2 are same
	
	
	
	Unk1
	floor floor unk1 of 318154902
	floor ceiling combined has unk1 of 169098656 
	ceiling ceiling unk1 169097864
	3 walls = 322870316, 322870676, 322871300
	
	
	other file z 1201 and unk1 58446696
	
	Good god! it's made of 4 corner pieces! not 6 flat pieces 4 SCOL's 
	
	nif file loaded = heaps(perhaps scols and precompile have a relationship?
	first 4 incl LODs just plain concrete
	filename Interiors\Industrial\BldShellOut\IndBldShellOutMid01.nif
	filename LOD\Interiors\Industrial\BldShellOut\IndBldShellOutMid01_LOD.nif
	filename LOD\Interiors\Industrial\BldShellOut\IndBldShellOutMid01_LOD_1.nif
	filename LOD\Interiors\Industrial\BldShellOut\IndBldShellOutMid01_LOD_1.nif
	filename SCOL\Fallout4.esm\CM0001E599.NIF
	filename SCOL\Fallout4.esm\CM0001E593.NIF
	filename SCOL\Fallout4.esm\CM0001E595.NIF
	filename SCOL\Fallout4.esm\CM0001E597.NIF
	
	
	given teh 4 piece nature I now have 8 walls pieces 4 cielings 4 floors
	my file has 4 sets of loor but?
	4 cielings and 8 walls
	
	square
	0 0.5019608
	1 -1.1920929E-7
	2 0.9999999
	3 0.0
	4 -0.9999999
	5 -1.1920929E-7
	6 0.0
	7 0.0
	8 0.0
	
	
	rectangle
	
	0 0.502
	1 -1.0
	2 0.0
	3 0.0
	4 0.0
	5 -1.0
	6 0.0
	7 0.0
	8 0.0
	
	0,1,2 if 2 was Z then these look like normal (but 0 is oddly half)
	
	If the grid is parsed at 256's I could use that as a multiple for quads??
	
	ok, 4 verts for a quad
	= 10 bytes per vert in the 40 byte section
	hfloat = 3*2=6 byte leaves 4 for texcoords (and color?)
	
	normals are shared and could be in shared section Unk1 maybe?
	
	note SCOL in the example are place 256 off ground, so verts are 256 more than SCOL verts
	each vert is 128 sideways (for a 256 total)
	
	Note this supports the double grid parsing width idea of 512)
	
	it appears like every trans is on a 32 multiple (like it keeps depth buffer accurate?)
	but the bounds center is not
	
	
	notice the alpha of ceiling is 1 lower than the floor texture (736 and 735)
	
	
	so for ceiling I'm look for up by 224 on each vector
	
	
	bytes 0,1 vary based on alpha ness, color or something? 
	may 4bytes alpha rgb? orginals are just white original threshold is 98 and flags of 748, but both have alphs so no point
	
	
	of upper wall part pairs ( no normals?
	 0-7 exact 4 non zero lines
	 1-3 exact 6 non zero lines 2 Nans  happens to exactly match flat bits
	 2-5 exact 4 non zero lines
	 4-6 exact 6 non zero lines
	 
	 
	 ok ok floats and matrix, but possibly columnmajor or stored backwards or something
	 
	 
	 need to find a scol that is 256 wide and see what shape it makes
	 *
	 */

	public int UnknownInt1;
	public int UnknownInt2;
	public int NumData;
	public int[] Unk1;
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

		Unk1 = new int[NumData];
		fs = new float[NumData][];
		for (int i = 0; i < NumData; i++)
		{
			fs[i] = new float[4];
			//Unk1[i] = ByteConvert.readInt(stream);
			//ByteConvert.readInt(stream);

			//	bv[i] = new BSByteVector3(stream);
			//	System.out.println("bv= " + bv[i]);
			//	System.out.println(" " + i + "0 " + ByteConvert.readByte(stream));
			//	System.out.println(" " + i + "1 " + ByteConvert.readByte(stream));
			//	System.out.println(" " + i + "2 " + ByteConvert.readByte(stream));
			//	System.out.println(" " + i + "3 " + ByteConvert.readByte(stream));
			fs[i][0] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
			fs[i][1] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
			fs[i][2] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
			fs[i][3] = MiniFloat.toFloat(ByteConvert.readUnsignedShort(stream));
			//System.out.println(" " + i + "0 " + fs[i][0]);
			//System.out.println(" " + i + "1 " + fs[i][1]);
			//System.out.println(" " + i + "2 " + fs[i][2]);
			//System.out.println(" " + i + "3 " + fs[i][3]);
		}

		data = new Data[NumData];
		for (int i = 0; i < NumData; i++)
		{
			data[i] = new Data(stream, nifVer);
		}

		return success;
	}

	public float[][] fs;

	public static class Data
	{
		public int numTris;
		public int numLODs;
		public int[][] LODs;

		public int NumCombined;

		public Combined[] Combined;

		public int UnkInt1;
		public int UnkInt2;

		public Data(InputStream stream, NifVer nifVer) throws IOException
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
				Combined[i] = new Combined(stream, nifVer);
			}

			// No they are really similar amoungst packed that are next to each other?
			UnkInt1 = ByteConvert.readInt(stream);
			UnkInt2 = ByteConvert.readInt(stream);

		}
	}

	public static class Combined
	{
		public float f1;
		public NifMatrix33 rot;

		public NifVector3 trans;
		public float scale; // bounds matches up
		public NifSphereBV bounds;

		public Combined(InputStream stream, NifVer nifVer) throws IOException
		{
			//most often 0.5ish but sometimes 0 sometimes 1
			f1 = ByteConvert.readFloat(stream);

			rot = new NifMatrix33(stream); //reversed??inverted?? world space perhaps?

			trans = new NifVector3(stream); // world space
			scale = ByteConvert.readFloat(stream); //often one but sometime 1.5 or 0.64 or 0.84

			bounds = new NifSphereBV(stream);

		}
	}

}
