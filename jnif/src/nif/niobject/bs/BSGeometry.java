package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.BSByteVector4;
import nif.compound.BSHalfFloatTexCoord2;
import nif.compound.NifByteColor4BGRA;
import nif.compound.NifShortVector3;
import nif.compound.NifTriangle;
import nif.compound.NifVector3;
import nif.niobject.NiAVObject;
import nif.niobject.NiAlphaProperty;
import nif.niobject.NiObject;

public class BSGeometry extends NiAVObject {
	/**
	 * BSGeometry has been part of an inheritance chain only from everything pre Starfield, but Starfield directly
	 * instantiates BSGeometry, so now I need it. Notice I am not changing any other class so the comment in NiGeometry
	 * and NiParticleSystem are still true
	 * 
	 * <niobject name="BSGeometry" inherit="NiAVObject" module="BSMain" versions="#STF#">
	 * <field name="Bounding Sphere" type="NiBound"publicv me="Bounding Box" type="BSBoundingBox" />
	 * <field name="Skin" type="Ref" template="NiObject" />
	 * <field name="Shader Property" type="Ref" template="BSShaderProperty" />
	 * <field name="Alpha Property" type="Ref" template="NiAlphaProperty" />
	 * <field name="Meshes" type="BSMeshArray" arg="Flags" length="4" /> </niobject>
	 * 
	 * 
	 */

	public NiBound			BoundingSphere;
	public BSBoundingBox	BoundingBox;
	public NifRef			Skin;
	public NifRef			ShaderProperty;
	public NifRef			AlphaProperty;
	public BSMeshArray[]	Meshes;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException {
		boolean success = super.readFromStream(stream, nifVer);

		BoundingSphere = new NiBound(stream);
		BoundingBox = new BSBoundingBox(stream);
		Skin = new NifRef(NiObject.class, stream);
		ShaderProperty = new NifRef(BSShaderProperty.class, stream);
		AlphaProperty = new NifRef(NiAlphaProperty.class, stream);
		Meshes = new BSMeshArray[4];
		for (int i = 0; i < 4; i++)
			Meshes[i] = new BSMeshArray(flags.flags, stream);

		return success;
	}

	/**
	 * <struct name="NiPlane" size="16" module="NiMain"> A plane. <field name="Normal" type="Vector3">The plane
	 * normal.</field> <field name="Constant" type="float">The plane constant.</field> </struct>
	 */
	public static class NiPlane {
		public NifVector3	Normal;
		public float		Constant;

		public NiPlane(ByteBuffer stream) throws IOException {
			Normal = new NifVector3(stream);
			Constant = ByteConvert.readFloat(stream);
		}
	}

	/**
	 * <struct name="NiBoundAABB" size="26" module="NiMain"> Divinity 2 specific NiBound extension.
	 * <field name="Num Corners" type="ushort" default="2" /> <field name="Corners" type="Vector3" length="2">Corners
	 * are only non-zero if Num Corners is 2. Hardcoded to 2.</field> </struct>
	 */
	public static class NiBoundAABB {
		public int			NumCorners;
		public NifVector3[]	Corners;

		public NiBoundAABB(ByteBuffer stream) throws IOException {
			NumCorners = ByteConvert.readUnsignedShort(stream);
			Corners = new NifVector3[2];
			for (int i = 0; i < 2; i++)
				Corners[i] = new NifVector3(stream);
		}
	}

	/**
	 * <struct name="NiBound" module="NiMain"> A sphere. <field name="Center" type="Vector3">The sphere's
	 * center.</field> <field name="Radius" type="float">The sphere's radius.</field>
	 * <field name="DIV2 AABB" type="NiBoundAABB" since="20.3.0.9" until="20.3.0.9" vercond="#DIVINITY2#" /> </struct>
	 */
	public static class NiBound {
		public NifVector3	Center;
		public float		Radius;

		public NiBound(ByteBuffer stream) throws IOException {
			Center = new NifVector3(stream);
			Radius = ByteConvert.readFloat(stream);

			//if , NifVer nifVer was in the parameter I could do this but not a biggie
			//if (nifVer.DIVINITY2()) {
			//	System.out.println(new Throwable("Unfinished NiBound").getStackTrace()[0]);
			//}
		}
	}

	/**
	 * <struct name="BSBoundingBox" module="BSMain" versions="#BETHESDA#"> Bethesda-specific bounding box.
	 * <field name="Center" type="Vector3">Center of the bounding box.</field>
	 * <field name="Dimensions" type="Vector3">Dimensions of the bounding box from center.</field> </struct>
	 */
	public static class BSBoundingBox {
		public NifVector3	Center;
		public NifVector3	Dimensions;

		public BSBoundingBox(ByteBuffer stream) throws IOException {
			Center = new NifVector3(stream);
			Dimensions = new NifVector3(stream);
		}
	}
	
	/**
	 * <struct name="BSMeshArray" module="BSMain" versions="#STF#"> <field name="Has Mesh" type="byte" />
	 * <field name="Mesh" type="BSMesh" arg="#ARG# #BITAND# 512" cond="Has Mesh #EQ# 1" /> </struct>
	 */
	public static class BSMeshArray {
		public boolean HasMesh;
		public BSMesh Mesh;
		public BSMeshArray(int flags, ByteBuffer stream) throws IOException {
			HasMesh = ByteConvert.readByte(stream) == 1;
			if (HasMesh) {
				Mesh = new BSMesh(flags & 0x200, stream);
				//System.out.println(new Throwable("Unfinished BSMeshArray").getStackTrace()[0]);
			}

		}
	}
	
	/**
    <struct name="BSMesh" module="BSMain" versions="#STF#">
        <field name="Indices Size" type="uint" />
        <field name="Num Verts" type="uint" />
        <field name="Flags" type="uint" />
        <field name="Mesh Path" type="SizedString" cond="#ARG# #EQ# 0" />
        <field name="Mesh Data" type="BSMeshData" cond="#ARG# #NEQ# 0" />
    </struct>

	 */
	public static class BSMesh {
		public int			IndicesSize;
		public int			NumVerts;
		public int			Flags;
		public String			MeshPath;
		public BSMeshData			MeshData;
		public BSMesh(int flags, ByteBuffer stream) throws IOException {			
			IndicesSize = ByteConvert.readInt(stream);
			NumVerts = ByteConvert.readInt(stream);
			Flags = ByteConvert.readInt(stream);
			if(flags == 0) {
				MeshPath =ByteConvert.readSizedString(stream);
			} else {
				MeshData = new BSMeshData(stream);
			}

		}
	}
	
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
	public static class BSMeshData {
		public int					Version;
		public int					IndicesSize;
		public NifTriangle[]			Triangles;
		public float				Scale;
		public int					WeightsPerVertex;
		public int					NumVerts;
		public NifShortVector3[]		Vertices;
		public int					NumUVs;
		public BSHalfFloatTexCoord2[]		UVs;
		public int					NumUVs2;
		public BSHalfFloatTexCoord2[]		UVs2;
		public int					NumVertexColors;
		public NifByteColor4BGRA[]		VertexColors;
		public int					NumNormals;
		public BSByteVector4[]		Normals;//Not UDecVector4, difference unclear
		public int					NumTangents;
		public BSByteVector4[]		Tangents;//Not UDecVector4, difference unclear
		public int					NumWeights;
		public BSBoneWeight[]		Weights;
		public int					NumLODs;
		public BSMeshTriangles[]	LODs;
		public int					NumMeshlets;
		public BSMeshlet[]			Meshlets;
		public int					NumCullData;
		public BSCullData			CullData;

		public BSMeshData(ByteBuffer stream) throws IOException {
			
			//<field name="Version" type="uint" />
			Version = ByteConvert.readInt(stream);
	        //<field name="Indices Size" type="uint" />
			IndicesSize = ByteConvert.readInt(stream);
	        //<field name="Triangles" type="Triangle" length="Indices Size #DIV# 3" />
			
	        //<field name="Scale" type="float">Vertex coordinate scale</field>
			
	        //<field name="Weights Per Vertex" type="uint" />
			WeightsPerVertex = ByteConvert.readInt(stream);
	        //<field name="Num Verts" type="uint" />
			NumVerts = ByteConvert.readInt(stream);
	        //<field name="Vertices" type="ShortVector3" length="Num Verts" />
			
	        //<field name="Num UVs" type="uint" />
			NumUVs = ByteConvert.readInt(stream);
	        //<field name="UVs" type="HalfTexCoord" length="Num UVs" />
			
	        //<field name="Num UVs 2" type="uint" />
			NumUVs2 = ByteConvert.readInt(stream);
	        //<field name="UVs 2" type="HalfTexCoord" length="Num UVs 2" cond="Num UVs 2 #GT# 0" />
			
	        //<field name="Num Vertex Colors" type="uint" />
			NumVertexColors = ByteConvert.readInt(stream);
	        //<field name="Vertex Colors" type="ByteColor4BGRA" length="Num Vertex Colors" cond="Num Vertex Colors #GT# 0" />
	        
			//<field name="Num Normals" type="uint" />
			NumNormals = ByteConvert.readInt(stream);
	        //<field name="Normals" type="UDecVector4" length="Num Normals" />
	        
			//<field name="Num Tangents" type="uint" />
			NumTangents = ByteConvert.readInt(stream);
	        //<field name="Tangents" type="UDecVector4" length="Num Tangents" />
	       
			//<field name="Num Weights" type="uint" />
			NumWeights = ByteConvert.readInt(stream);
	        //<field name="Weights" type="BSBoneWeight" length="Num Weights" cond="Num Weights #GT# 0" />
	        
			//<field name="Num LODs" type="uint" cond="Version #GTE# 1" />
			NumLODs = ByteConvert.readInt(stream);
	        //<field name="LODs" type="BSMeshTriangles" length="Num LODs" cond="(Version #GTE# 1) #AND# (Num LODs #GT# 0)" />
	        
			//<field name="Num Meshlets" type="uint" />
			NumMeshlets = ByteConvert.readInt(stream);
	        //<field name="Meshlets" type="BSMeshlet" length="Num Meshlets" cond="Num Meshlets #GT# 0" />
	        
			//<field name="Num Cull Data" type="uint" />
			NumCullData = ByteConvert.readInt(stream);
	        //<field name="Cull Data" type="BSCullData" arg="Version" length="Num Cull Data" cond="Num Cull Data #GT# 0" />
	              
			

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
			public BSCullData(ByteBuffer stream) throws IOException {
				BoundingSphere = new NiBound(stream);
				NormalCone = new BSByteVector4(stream);
				ApexOffset = ByteConvert.readFloat(stream);
				BoundingBox = new BSBoundingBox(stream);				
			}
		}
	}


}