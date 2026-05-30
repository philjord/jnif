package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import nif.ByteConvert;
import nif.compound.BSByteVector4;
import nif.compound.BSHalfFloatTexCoord2;
import nif.compound.NifByteColor4BGRA;
import nif.compound.NifShortVector3;
import nif.compound.NifTriangle;
import nif.compound.NifVector4;
import nif.niobject.bs.BSGeometry.BSBoundingBox;
import nif.niobject.bs.BSGeometry.NiBound;
import nif.tools.FP16;


public class BSMeshData {
	
	public static final boolean LOAD_OPTIMIZED = true;
	public static final boolean TANGENTS = true;
	
	/**
	 * 

    <struct name="BSMeshData" module="BSMain" versions="#STF#">
        <field name="Version" type="uint" />
        <field name="Indices Size" type="uint" />
        <field name="Triangles" type="Triangle" length="Indices Size #DIV# 3" />
        <field name="Scale" type="float">Vertex coordinate scale</field>
        <field name="Weights Per Vertex" type="uint" />
        <field name="Num Verts" type="uint" />
        <field name="Vertices" type="ShortVector3" length="Num Verts" />
        <field name="Num UVs" type="uint" />
        <field name="UVs" type="HalfTexCoord" length="Num UVs" />
        <field name="Num UVs 2" type="uint" />
        <field name="UVs 2" type="HalfTexCoord" length="Num UVs 2" cond="Num UVs 2 #GT# 0" />
        <field name="Num Vertex Colors" type="uint" />
        <field name="Vertex Colors" type="ByteColor4BGRA" length="Num Vertex Colors" cond="Num Vertex Colors #GT# 0" />
        <field name="Num Normals" type="uint" />
        <field name="Normals" type="UDecVector4" length="Num Normals" />
        <field name="Num Tangents" type="uint" />
        <field name="Tangents" type="UDecVector4" length="Num Tangents" />
        <field name="Num Weights" type="uint" />
        <field name="Weights" type="BSBoneWeight" length="Num Weights" cond="Num Weights #GT# 0" />
        <field name="Num LODs" type="uint" cond="Version #GTE# 1" />
        <field name="LODs" type="BSMeshTriangles" length="Num LODs" cond="(Version #GTE# 1) #AND# (Num LODs #GT# 0)" />
        <field name="Num Meshlets" type="uint" />
        <field name="Meshlets" type="BSMeshlet" length="Num Meshlets" cond="Num Meshlets #GT# 0" />
        <field name="Num Cull Data" type="uint" />
        <field name="Cull Data" type="BSCullData" arg="Version" length="Num Cull Data" cond="Num Cull Data #GT# 0" />
    </struct>

	 */
	
	public int						Version;
	public int						IndicesSize;
	public NifTriangle[]			Triangles;
	public float					Scale;
	public int						WeightsPerVertex;
	public int						NumVerts;
	public NifShortVector3[]		Vertices;
	public int						NumUVs;
	public BSHalfFloatTexCoord2[]	UVs;
	public int						NumUVs2;
	public BSHalfFloatTexCoord2[]	UVs2;
	public int						NumVertexColors;
	public NifByteColor4BGRA[]		VertexColors;
	public int						NumNormals;
	public BSByteVector4[]			Normals;
	public int						NumTangents;
	public BSByteVector4[]			Tangents;
	public int						NumWeights;
	public BSBoneWeight[]			Weights;
	public int						NumLODs;
	public BSMeshTriangles[]		LODs;
	public int						NumMeshlets;
	public BSMeshlet[]				Meshlets;
	public int						NumCullData;
	public BSCullData[]				CullData;

	//OPTIMISATION
	public FloatBuffer				verticesOptBuf;
	public FloatBuffer				normalsOptBuf;
	public FloatBuffer				tangentsOptBuf;
	public FloatBuffer				binormalsOptBuf;
	public FloatBuffer				colorsOptBuf;
	public FloatBuffer				uVSetOptBuf;
	public FloatBuffer				uVSet2OptBuf;

	//TODO: not done yet
	public float[]					BoneWeights;
	public int[]					BoneIndices;

	//OPTIMISATION
	public int[]					trianglesOpt;


	public BSMeshData(ByteBuffer stream) throws IOException {
	
		//<field name="Version" type="uint" />
		Version = ByteConvert.readInt(stream);
		//<field name="Indices Size" type="uint" />
		IndicesSize = ByteConvert.readInt(stream);

		if (!LOAD_OPTIMIZED) {

			//<field name="Triangles" type="Triangle" length="Indices Size #DIV# 3" />
			Triangles = new NifTriangle[IndicesSize / 3];
			for (int i = 0; i < (IndicesSize / 3); i++) {
				Triangles[i] = new NifTriangle(stream);
			}
			//<field name="Scale" type="float">Vertex coordinate scale</field>
			Scale = ByteConvert.readFloat(stream) / 32767f;
			//<field name="Weights Per Vertex" type="uint" />
			WeightsPerVertex = ByteConvert.readInt(stream);
			//<field name="Num Verts" type="uint" />
			NumVerts = ByteConvert.readInt(stream);
			//<field name="Vertices" type="ShortVector3" length="Num Verts" />
			Vertices = new NifShortVector3[NumVerts];
			for (int i = 0; i < NumVerts; i++) {
				Vertices[i] = new NifShortVector3(stream);
			}
			//<field name="Num UVs" type="uint" />
			NumUVs = ByteConvert.readInt(stream);
			//<field name="UVs" type="HalfTexCoord" length="Num UVs" />
			UVs = new BSHalfFloatTexCoord2[NumUVs];
			for (int i = 0; i < NumUVs; i++) {
				UVs[i] = new BSHalfFloatTexCoord2(stream);
			}
			//<field name="Num UVs 2" type="uint" />
			NumUVs2 = ByteConvert.readInt(stream);
			//<field name="UVs 2" type="HalfTexCoord" length="Num UVs 2" cond="Num UVs 2 #GT# 0" />
			if (NumUVs2 > 0) {
				UVs2 = new BSHalfFloatTexCoord2[NumUVs2];
				for (int i = 0; i < NumUVs2; i++) {
					UVs2[i] = new BSHalfFloatTexCoord2(stream);
				}
			}
			//<field name="Num Vertex Colors" type="uint" />
			NumVertexColors = ByteConvert.readInt(stream);
			//<field name="Vertex Colors" type="ByteColor4BGRA" length="Num Vertex Colors" cond="Num Vertex Colors #GT# 0" />
			if (NumVertexColors > 0) {
				VertexColors = new NifByteColor4BGRA[NumVertexColors];
				for (int i = 0; i < NumVertexColors; i++) {
					VertexColors[i] = new NifByteColor4BGRA(stream);
				}
			}
			//<field name="Num Normals" type="uint" />
			NumNormals = ByteConvert.readInt(stream);
			//<field name="Normals" type="UDecVector4" length="Num Normals" />
			Normals = new BSByteVector4[NumNormals];
			for (int i = 0; i < NumNormals; i++) {
				Normals[i] = new BSByteVector4(stream);
			}
			//<field name="Num Tangents" type="uint" />
			NumTangents = ByteConvert.readInt(stream);
			//<field name="Tangents" type="UDecVector4" length="Num Tangents" />
			Tangents = new BSByteVector4[NumTangents];
			for (int i = 0; i < NumTangents; i++) {
				Tangents[i] = new BSByteVector4(stream);
			}

		} else {
			// for pulling bytes in a bigendian fashion
			byte[] b = new byte[4];
			
			
			int numTriangles = IndicesSize / 3;
			trianglesOpt = new int[numTriangles * 3];
			for (int i = 0; i < numTriangles; i++) {
				trianglesOpt[i * 3 + 0] = ByteConvert.readUnsignedShort(stream);
				trianglesOpt[i * 3 + 1] = ByteConvert.readUnsignedShort(stream);
				trianglesOpt[i * 3 + 2] = ByteConvert.readUnsignedShort(stream);
			}

			
			// magic number from here
			//https://github.com/fo76utils/nifskope/blob/8e117d5406a561e057db37f967d84773aa8df833/src/io/MeshFile.cpp#L107C3-L108C16
			//xyz /= 32767.0f; looks like a tinyGL magically rubbish number to me
			//xyz *= scale;
			
			Scale = ByteConvert.readFloat(stream) / 1024; // Empirically proven dead accurate, note this is 1/32 of 32767, interesting
			
			WeightsPerVertex = ByteConvert.readInt(stream);

			NumVerts = ByteConvert.readInt(stream);

			verticesOptBuf = BSTriShape.createFB(NumVerts * 3);

			for (int i = 0; i < NumVerts; i++) {						
				verticesOptBuf.put(i * 3 + 0, ByteConvert.readShort(stream)  * Scale * BSTriShape.ES_TO_METERS_SCALE);
				verticesOptBuf.put(i * 3 + 2, -ByteConvert.readShort(stream) * Scale * BSTriShape.ES_TO_METERS_SCALE);
				verticesOptBuf.put(i * 3 + 1, ByteConvert.readShort(stream)  * Scale * BSTriShape.ES_TO_METERS_SCALE);
			}

			NumUVs = ByteConvert.readInt(stream);
			uVSetOptBuf = BSTriShape.createFB(NumUVs * 2);
			for (int i = 0; i < NumUVs; i++) {
				//HalfTexCoord
				//https://github.com/fo76utils/nifskope/blob/8e117d5406a561e057db37f967d84773aa8df833/src/io/MeshFile.cpp#L121
				//https://github.com/fo76utils/nifskope/blob/8e117d5406a561e057db37f967d84773aa8df833/lib/libfo76utils/src/fp32vec4_base.hpp#L69
				  
				uVSetOptBuf.put(i * 2 + 0, FP16.toFloat(ByteConvert.readShort(stream)));
				uVSetOptBuf.put(i * 2 + 1, FP16.toFloat(ByteConvert.readShort(stream)));
			}
			
			NumUVs2 = ByteConvert.readInt(stream);
			if (NumUVs2 > 0) {
				uVSet2OptBuf = BSTriShape.createFB(NumUVs2 * 2);
				for (int i = 0; i < NumUVs2; i++) {
					uVSet2OptBuf.put(i * 2 + 0, FP16.toFloat(ByteConvert.readShort(stream)));
					uVSet2OptBuf.put(i * 2 + 1, FP16.toFloat(ByteConvert.readShort(stream)));
				}
			}

			NumVertexColors = ByteConvert.readInt(stream);
			if (NumVertexColors > 0) {
				colorsOptBuf = BSTriShape.createFB(NumVertexColors * 4);
				for (int i = 0; i < NumVertexColors; i++) {
					//TODO: TEST!! BGRA!!! put into a RGBA shape
					//https://github.com/fo76utils/nifskope/blob/8e117d5406a561e057db37f967d84773aa8df833/src/io/MeshFile.cpp#L147
					colorsOptBuf.put(i * 4 + 2, ByteConvert.readUnsignedByte(stream) / 255f);
					colorsOptBuf.put(i * 4 + 1, ByteConvert.readUnsignedByte(stream) / 255f);
					colorsOptBuf.put(i * 4 + 0, ByteConvert.readUnsignedByte(stream) / 255f);
					colorsOptBuf.put(i * 4 + 3, ByteConvert.readUnsignedByte(stream) / 255f);
				}
			}

			NumNormals = ByteConvert.readInt(stream);
			normalsOptBuf = BSTriShape.createFB(NumNormals * 3);

			for (int i = 0; i < NumNormals; i++) {
				//https://github.com/fo76utils/nifskope/blob/8e117d5406a561e057db37f967d84773aa8df833/src/io/MeshFile.cpp#L158
				// a UDecVector4? https://github.com/fo76utils/nifskope/blob/develop/src/data/niftypes.h#L831				
				//UDecVector4( const std::uint32_t & v ) : Vector4( FloatVector4::convertX10Y10Z10W2( v ) )		
				// note c++ts read the bytes of an int in some damn stupid order that's unspoken, cos shh
				
				stream.get(b);				 
				int n = (((b[0] & 0xff) << 24) | ((b[1] & 0xff) << 16) | ((b[2] & 0xff) << 8) | (b[3] & 0xff));
				float x = (n & 0x000003FF) * (float)(2.0 / 1023.0) - 1.0f;
				float y = (n & 0x000FFC00) * (float)(2.0 / 1047552.0) - 1.0f;
				float z = (n & 0x3FF00000) * (float)(2.0 / 1072693248.0) - 1.0f;
				//float w = (n >> 30) * (float)(2.0 / 3.0) - 1.0f;				 
				
				normalsOptBuf.put(i * 3 + 0, x);
				normalsOptBuf.put(i * 3 + 2, -y);
				normalsOptBuf.put(i * 3 + 1, z);
			}

			NumTangents = ByteConvert.readInt(stream);
			tangentsOptBuf = BSTriShape.createFB(NumTangents * 3);
			
			for (int i = 0; i < NumTangents; i++) {
				//UDecVector4
				
				stream.get(b);				 
				int n = (((b[0] & 0xff) << 24) | ((b[1] & 0xff) << 16) | ((b[2] & 0xff) << 8) | (b[3] & 0xff));
				float x = (n & 0x000003FF) * (float)(2.0 / 1023.0) - 1.0f;
				float y = (n & 0x000FFC00) * (float)(2.0 / 1047552.0) - 1.0f;
				float z = (n & 0x3FF00000) * (float)(2.0 / 1072693248.0) - 1.0f;
				//float w = (n >> 30) * (float)(2.0 / 3.0) - 1.0f;				 
				tangentsOptBuf.put(i * 3 + 0, x);
				tangentsOptBuf.put(i * 3 + 2, -y);
				tangentsOptBuf.put(i * 3 + 1, z);
			}

		}

		// load everything else normal style, not used yet

		//<field name="Num Weights" type="uint" />
		NumWeights = ByteConvert.readInt(stream);
		//<field name="Weights" type="BSBoneWeight" length="Num Weights" cond="Num Weights #GT# 0" />
		if (NumWeights > 0) {
			Weights = new BSBoneWeight[NumWeights];
			for (int i = 0; i < NumWeights; i++) {
				Weights[i] = new BSBoneWeight(stream);
			}
		}
		
		if (Version >= 1) {
			//<field name="Num LODs" type="uint" cond="Version #GTE# 1" />
			NumLODs = ByteConvert.readInt(stream);
			//<field name="LODs" type="BSMeshTriangles" length="Num LODs" cond="(Version #GTE# 1) #AND# (Num LODs #GT# 0)" />
			if (NumLODs > 0) {
				LODs = new BSMeshTriangles[NumLODs];
				for (int i = 0; i < NumLODs; i++) {
					LODs[i] = new BSMeshTriangles(stream);
				}
			}
		}
		//<field name="Num Meshlets" type="uint" />
		NumMeshlets = ByteConvert.readInt(stream);
		//<field name="Meshlets" type="BSMeshlet" length="Num Meshlets" cond="Num Meshlets #GT# 0" />
		if (NumMeshlets > 0) {
			Meshlets = new BSMeshlet[NumMeshlets];
			for (int i = 0; i < NumMeshlets; i++) {
				Meshlets[i] = new BSMeshlet(stream);
			}
		}
		//<field name="Num Cull Data" type="uint" />
		NumCullData = ByteConvert.readInt(stream);
		//<field name="Cull Data" type="BSCullData" arg="Version" length="Num Cull Data" cond="Num Cull Data #GT# 0" />
		if (NumCullData > 0) {
			CullData = new BSCullData[NumCullData];
			for (int i = 0; i < NumCullData; i++) {
				CullData[i] = new BSCullData(stream, Version);
			}
		}

	}
	
	//https://github.com/fo76utils/nifskope/blob/develop/lib/libfo76utils/src/fp32vec4_base.hpp#L674
	public static NifVector4 convertX10Y10Z10W2(int n) {
		NifVector4 tmp = new NifVector4();
		tmp.x = (n & 0x000003FF) * (float)(2.0 / 1023.0) - 1.0f;
		tmp.y = (n & 0x000FFC00) * (float)(2.0 / 1047552.0) - 1.0f;
		tmp.z = (n & 0x3FF00000) * (float)(2.0 / 1072693248.0) - 1.0f;
		tmp.w = (n >> 30) * (float)(2.0 / 3.0) - 1.0f;
		return tmp;
	}

	/**
    <struct name="BSBoneWeight" module="BSMain" versions="#STF#">
        <field name="Bone" type="ushort" />
        <field name="Weight" type="ushort" />
    </struct>*/
	
	public static class BSBoneWeight  {
		public int bone;
		public int weight;
		public BSBoneWeight(ByteBuffer stream) throws IOException {
			bone = ByteConvert.readUnsignedShort(stream);
			weight = ByteConvert.readUnsignedShort(stream);
		}
	}
	/**
	<struct name="BSMeshTriangles" module="BSMain" versions="#STF#">
    <field name="Indices Size" type="uint" />
    <field name="Triangles" type="Triangle" length="Indices Size #DIV# 3" />
	</struct>*/
	public static class BSMeshTriangles {
		public int IndicesSize;
		public NifTriangle[] Triangles;
		public BSMeshTriangles(ByteBuffer stream) throws IOException {
			IndicesSize = ByteConvert.readInt(stream);
			Triangles = new NifTriangle[IndicesSize/3];
			for(int i = 0; i<(IndicesSize/3); i++) {
				Triangles[i] = new NifTriangle(stream);
			}
		}
	}
	/**
	<struct name="BSMeshlet" module="BSMain" versions="#STF#">
    <field name="Vertex Count" type="uint" />
    <field name="Vertex Offset" type="uint" />
    <field name="Triangle Count" type="uint" />
    <field name="Triangle Offset" type="uint" />
	</struct>*/
	public static class BSMeshlet  {
		public int VertexCount;
		public int VertexOffset;
		public int TriangleCount;
		public int TriangleOffset;
		public BSMeshlet(ByteBuffer stream) throws IOException {
			VertexCount = ByteConvert.readInt(stream);
			VertexOffset = ByteConvert.readInt(stream);
			TriangleCount = ByteConvert.readInt(stream);
			TriangleOffset = ByteConvert.readInt(stream);				
		}
	}
	
	/**
	 * 

	<struct name="BSCullData" module="BSMain" versions="#STF#">
    	<field name="Bounding Sphere" type="NiBound" cond="#ARG# #LT# 2" />
    	<field name="Normal Cone" type="ByteVector4" cond="#ARG# #LT# 2" />
    	<field name="Apex Offset" type="float" cond="#ARG# #LT# 2" />
    	<field name="Bounding Box" type="BSBoundingBox" cond="#ARG# #GTE# 2" />
	</struct>

	 */
	public static class BSCullData {
		public NiBound BoundingSphere;
		public BSByteVector4 NormalCone;
		public float ApexOffset;
		public BSBoundingBox BoundingBox;
		public BSCullData(ByteBuffer stream, int Version) throws IOException {
			if(Version < 2) {
				BoundingSphere = new NiBound(stream);
				NormalCone = new BSByteVector4(stream);
				ApexOffset = ByteConvert.readFloat(stream);
			} else {
				BoundingBox = new BSBoundingBox(stream);
			}
		}
	}
}
