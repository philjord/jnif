package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifColor4;
import nif.compound.NifTexCoord;
import nif.compound.NifVector3;
import nif.enums.ConsistencyType;
import nif.niobject.particle.NiPSysData;

public abstract class NiGeometryData extends NiObject
{
	public static boolean LOAD_OPTIMIZED = true;
	public static boolean LOAD_MEGA_OPTIMIZED = true;
	public static final float ES_TO_METERS_SCALE = 0.0254f / 2f;//0.02f;

	/**
	 	
	 <niobject name="NiGeometryData" abstract="true" inherit="NiObject" module="NiMain">
        Mesh data: vertices, vertex normals, etc.
            Bethesda 20.2.0.7 NIFs: NiParticlesData no longer inherits from NiGeometryData and inherits NiObject directly. 
            "Num Vertices" is renamed to "BS Max Vertices" for Bethesda 20.2 because Vertices, Normals, Tangents, Colors, and UV arrays
            do not have length for NiPSysData regardless of "Num" or booleans.

        <field name="Group ID" type="int" since="10.1.0.114">Always zero.</field>
        <field name="Num Vertices" type="ushort" excludeT="NiPSysData">Number of vertices.</field>
        <field name="Num Vertices" type="ushort" onlyT="NiPSysData" vercond="#NI_BS_LT_FO3#">Number of vertices.</field>
        <field name="BS Max Vertices" type="ushort" onlyT="NiPSysData" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GTE_FO3#">Bethesda uses this for max number of particles in NiPSysData.</field>
        <field name="Keep Flags" type="byte" since="10.1.0.0">Used with NiCollision objects when OBB or TRI is set.</field>
        <field name="Compress Flags" type="byte" since="10.1.0.0" />
        <field name="Has Vertices" type="bool" default="true">Is the vertex array present? (Always non-zero.)</field>
        <field name="Vertices" type="Vector3" length="Num Vertices" cond="Has Vertices">The mesh vertices.</field>
        <field name="Data Flags" type="NiGeometryDataFlags" since="10.0.1.0" vercond="!#BS202#" />
        <field name="BS Data Flags" type="BSGeometryDataFlags" vercond="#BS202#" />
        <field name="Material CRC" type="uint" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GT_FO3#" />
        <field name="Has Normals" type="bool">Do we have lighting normals? These are essential for proper lighting: if not present, the model will only be influenced by ambient light.</field>
        <field name="Normals" type="Vector3" length="Num Vertices" cond="Has Normals">The lighting normals.</field>
        <field name="Tangents" type="Vector3" length="Num Vertices" cond="(Has Normals) #AND# (((Data Flags #BITOR# BS Data Flags) #BITAND# 4096) != 0)" since="10.1.0.0">Tangent vectors.</field>
        <field name="Bitangents" type="Vector3" length="Num Vertices" cond="(Has Normals) #AND# (((Data Flags #BITOR# BS Data Flags) #BITAND# 4096) != 0)" since="10.1.0.0">Bitangent vectors.</field>
        <field name="Has DIV2 Floats" type="bool" since="20.3.0.9" until="20.3.0.9" vercond="#DIVINITY2#" />
        <field name="DIV2 Floats" type="float" length="Num Vertices" since="20.3.0.9" until="20.3.0.9" vercond="#DIVINITY2#" cond="Has DIV2 Floats" />
        <field name="Bounding Sphere" type="NiBound" />
        <field name="Has Vertex Colors" type="bool">
            Do we have vertex colors? These are usually used to fine-tune the lighting of the model.

            Note: how vertex colors influence the model can be controlled by having a NiVertexColorProperty object as a property child of the root node. If this property object is not present, the vertex colors fine-tune lighting.

            Note 2: set to either 0 or 0xFFFFFFFF for NifTexture compatibility.
        </field>
        <field name="Vertex Colors" type="Color4" default="#VEC4_ONE#" length="Num Vertices" cond="Has Vertex Colors">The vertex colors.</field>
        <field name="Data Flags" type="NiGeometryDataFlags" until="4.2.2.0">The lower 6 bits of this field represent the number of UV texture sets. The rest is unused.</field>
        <field name="Has UV" type="bool" until="4.0.0.2">
            Do we have UV coordinates?

            Note: for compatibility with NifTexture, set this value to either 0x00000000 or 0xFFFFFFFF.
        </field>
        <field name="UV Sets" type="TexCoord" length="((Data Flags #BITAND# 63) #BITOR# (BS Data Flags #BITAND# 1))" width="Num Vertices">The UV texture coordinates. They follow the OpenGL standard: some programs may require you to flip the second coordinate.</field>
        <field name="Consistency Flags" type="ConsistencyType" since="10.0.1.0" default="CT_MUTABLE">Consistency Flags</field>
        <field name="Additional Data" type="Ref" template="AbstractAdditionalGeometryData" since="20.0.0.4" />
    </niobject>
	 */

	public int GroupID;

	public int numVertices;

	public int BSMaxVertices;

	public byte keepFlags;

	public byte compressFlags;

	public boolean hasVertices;

	public NifVector3[] vertices;
	//OPTIMIZISATION
	public FloatBuffer verticesOptBuf;

	public int DataFlags;
	
	public int BSDataFlags;

	public boolean hasUV;//not used

	public int actNumUVSets;// calculated for clarity

	public int MaterialCRC;

	public boolean hasNormals;

	public NifVector3[] normals;
	//OPTIMIZISATION
	public FloatBuffer normalsOptBuf;

	public NifVector3[] binormals;
	//OPTIMIZISATION
	public FloatBuffer binormalsOptBuf;

	public NifVector3[] tangents;
	//OPTIMIZISATION
	public FloatBuffer tangentsOptBuf;

	public NifVector3 center;
	public float radius;

	public boolean hasVertexColors;

	public NifColor4[] vertexColors;
	//OPTIMIZISATION
	public FloatBuffer vertexColorsOptBuf;

	public NifTexCoord[][] uVSets;
	//OPTIMIZISATION
	public FloatBuffer[] uVSetsOptBuf;

	public ConsistencyType ConsistencyFlags;

	public NifRef additionalData;

	//OPTIMIZISATION
	public boolean buffersFilled = false;

	//MEGA_OPTIMIZISATION
	public int interleavedStride = -1;
	public int geoToCoordOffset = -1;
	public int geoToColorsOffset = -1;
	public int geoToNormalsOffset = -1;
	public int[] geoToTexCoordOffset = new int[1];
	public int[] geoToVattrOffset = new int[2];

	public ByteBuffer interleavedBuffer;
	public ByteBuffer coordBuffer;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		
		
		
		
        
        
        //<field name="Group ID" type="int" since="10.1.0.114">Always zero.</field>
		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_114)
			GroupID = ByteConvert.readInt(stream);

		//<field name="Num Vertices" type="ushort" excludeT="NiPSysData">Number of vertices.</field>
		if (!(this instanceof NiPSysData)) {
			numVertices = ByteConvert.readUnsignedShort(stream);
		} else {
			//<field name="Num Vertices" type="ushort" onlyT="NiPSysData" vercond="#NI_BS_LT_FO3#">Number of vertices.</field>
		    if (nifVer.NI_BS_LT_FO3())
				numVertices = ByteConvert.readUnsignedShort(stream);
		    //<field name="BS Max Vertices" type="ushort" onlyT="NiPSysData" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GTE_FO3#">Bethesda uses this for max number of particles in NiPSysData.</field>
			if (nifVer.LOAD_VER == NifVer.VER_20_2_0_7 && nifVer.BS_GTE_FO3())
				BSMaxVertices = ByteConvert.readUnsignedShort(stream);
		}
		
		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0) {
			//<field name="Keep Flags" type="byte" since="10.1.0.0">Used with NiCollision objects when OBB or TRI is set.</field>
			keepFlags = ByteConvert.readByte(stream);
	        //<field name="Compress Flags" type="byte" since="10.1.0.0" />
			compressFlags = ByteConvert.readByte(stream);
		}

		//<field name="Has Vertices" type="bool" default="true">Is the vertex array present? (Always non-zero.)</field>
		hasVertices = ByteConvert.readBool(stream, nifVer);
		//<field name="Vertices" type="Vector3" length="Num Vertices" cond="Has Vertices">The mesh vertices.</field>
		if (hasVertices) {

			if (LOAD_OPTIMIZED) {
				/*	if (LOAD_MEGA_OPTIMIZED)
					{
						//TODO: is reading off and allocating a smaller set of data worthwhile?
						// For mega optimized we assume coord, normals, and uv
						// if we meet colors then we need to dump and rebuild the data
						// or keep color in it's own buffer? seems reasonable
						// if we are oblivion the bitans are already known
						// for fallout the bitans only are known at the uv and has normal stage
						// so honestly I'm going to be rebuilding it anyone, might as well do it
						// at the J3dNiTriBasedGeom stage and discard?
						if (nifVer.niGeometryDataToLoadMorphably.contains(new Integer(this.refId)))
						{
							//blah blah
						}
				
						if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_101 && nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
						{
							if (nifVer.niGeometryDataExtraDataArriving.contains(new Integer(this.refId)))
							{
								//blah
							}
						}
					}
					else*/
				{
					verticesOptBuf = createFB(numVertices * 3);
					for (int i = 0; i < numVertices; i++) {
						verticesOptBuf.put(i * 3 + 0, ByteConvert.readFloat(stream) * ES_TO_METERS_SCALE);
						verticesOptBuf.put(i * 3 + 2, -ByteConvert.readFloat(stream) * ES_TO_METERS_SCALE);
						verticesOptBuf.put(i * 3 + 1, ByteConvert.readFloat(stream) * ES_TO_METERS_SCALE);
					}
				}
			} else {
				vertices = new NifVector3[numVertices];
				for (int i = 0; i < numVertices; i++)
				{
					vertices[i] = new NifVector3(stream);
				}
			}
		}
		
		//<field name="Data Flags" type="NiGeometryDataFlags" since="10.0.1.0" vercond="!#BS202#" />
		if (nifVer.LOAD_VER >= NifVer.VER_10_0_1_0 && !nifVer.BS202()) 
			DataFlags = ByteConvert.readUnsignedShort(stream);
 
		//<field name="BS Data Flags" type="BSGeometryDataFlags" vercond="#BS202#" />
		if (nifVer.BS202()) 
			BSDataFlags = ByteConvert.readUnsignedShort(stream);
		
		
		//<field name="Material CRC" type="uint" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GT_FO3#" />
		if (nifVer.LOAD_VER == NifVer.VER_20_2_0_7 && nifVer.BS_GT_FO3())
			MaterialCRC = ByteConvert.readInt(stream);

        
       
        //<field name="Has Normals" type="bool">Do we have lighting normals? These are essential for proper lighting: if not present, the model will only be influenced by ambient light.</field>
		hasNormals = ByteConvert.readBool(stream, nifVer);
		if (hasNormals)
		{
			//<field name="Normals" type="Vector3" length="Num Vertices" cond="Has Normals">The lighting normals.</field>
			
			if (LOAD_OPTIMIZED) {
				normalsOptBuf = createFB(numVertices * 3);
				for (int i = 0; i < numVertices; i++) {
					normalsOptBuf.put(i * 3 + 0, ByteConvert.readFloat(stream));
					normalsOptBuf.put(i * 3 + 2, -ByteConvert.readFloat(stream));
					normalsOptBuf.put(i * 3 + 1, ByteConvert.readFloat(stream));
				}
			} else {
				normals = new NifVector3[numVertices];
				for (int i = 0; i < numVertices; i++) {
					normals[i] = new NifVector3(stream);
				}
			}
			
			//<field name="Tangents" type="Vector3" length="Num Vertices" cond="(Has Normals) #AND# (((Data Flags #BITOR# BS Data Flags) #BITAND# 4096) != 0)" since="10.1.0.0">Tangent vectors.</field>
			//<field name="Bitangents" type="Vector3" length="Num Vertices" cond="(Has Normals) #AND# (((Data Flags #BITOR# BS Data Flags) #BITAND# 4096) != 0)" since="10.1.0.0">Bitangent vectors.</field>
			if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0) {
				if (((DataFlags | BSDataFlags) & 4096) != 0) {
					if (LOAD_OPTIMIZED) {
						tangentsOptBuf = createFB(numVertices * 3);
						for (int i = 0; i < numVertices; i++) {
							tangentsOptBuf.put(i * 3 + 0, ByteConvert.readFloat(stream));
							tangentsOptBuf.put(i * 3 + 2, -ByteConvert.readFloat(stream));
							tangentsOptBuf.put(i * 3 + 1, ByteConvert.readFloat(stream));
						}

						binormalsOptBuf = createFB(numVertices * 3);
						for (int i = 0; i < numVertices; i++) {
							binormalsOptBuf.put(i * 3 + 0, ByteConvert.readFloat(stream));
							binormalsOptBuf.put(i * 3 + 2, -ByteConvert.readFloat(stream));
							binormalsOptBuf.put(i * 3 + 1, ByteConvert.readFloat(stream));
						}

					} else {
						tangents = new NifVector3[numVertices];
						for (int i = 0; i < numVertices; i++) {
							tangents[i] = new NifVector3(stream);
						}

						binormals = new NifVector3[numVertices];
						for (int i = 0; i < numVertices; i++) {
							binormals[i] = new NifVector3(stream);
						}

					}
				}
			}
		}
		//SKIPPED
		//<field name="Has DIV2 Floats" type="bool" since="20.3.0.9" until="20.3.0.9" vercond="#DIVINITY2#" />
	    //<field name="DIV2 Floats" type="float" length="Num Vertices" since="20.3.0.9" until="20.3.0.9" vercond="#DIVINITY2#" cond="Has DIV2 Floats" />

		//<field name="Bounding Sphere" type="NiBound" />
		center = new NifVector3(stream);
		radius = ByteConvert.readFloat(stream);
		
		if (LOAD_OPTIMIZED) {
			center.x = center.x * ES_TO_METERS_SCALE;
			float y = center.y;
			center.y = center.z * ES_TO_METERS_SCALE;
			center.z = -y * ES_TO_METERS_SCALE;
			radius *= ES_TO_METERS_SCALE;
		}

		//<field name="Has Vertex Colors" type="bool">
		hasVertexColors = ByteConvert.readBool(stream, nifVer);
		if (hasVertexColors) {
			//<field name="Vertex Colors" type="Color4" default="#VEC4_ONE#" length="Num Vertices" cond="Has Vertex Colors">The vertex colors.</field>	        
			if (LOAD_OPTIMIZED) {
				vertexColorsOptBuf = createFB(numVertices * 4);
				for (int i = 0; i < numVertices; i++) {
					vertexColorsOptBuf.put(i * 4 + 0, ByteConvert.readFloat(stream));
					vertexColorsOptBuf.put(i * 4 + 1, ByteConvert.readFloat(stream));
					vertexColorsOptBuf.put(i * 4 + 2, ByteConvert.readFloat(stream));
					vertexColorsOptBuf.put(i * 4 + 3, ByteConvert.readFloat(stream));
				}

			} else {
				vertexColors = new NifColor4[numVertices];
				for (int i = 0; i < numVertices; i++) {
					vertexColors[i] = new NifColor4(stream);
				}
			}
		}
		
		//<field name="Data Flags" type="NiGeometryDataFlags" until="4.2.2.0">The lower 6 bits of this field represent the number of UV texture sets. The rest is unused.</field>
        if (nifVer.LOAD_VER <= NifVer.VER_4_2_2_0)
			DataFlags = ByteConvert.readUnsignedShort(stream);

        //<field name="Has UV" type="bool" until="4.0.0.2"</field>
		if (nifVer.LOAD_VER <= NifVer.VER_4_0_0_2)
			hasUV = ByteConvert.readBool(stream, nifVer);

		
		
		//<field name="UV Sets" type="TexCoord" length="((Data Flags #BITAND# 63) #BITOR# (BS Data Flags #BITAND# 1))" width="Num Vertices">The UV texture coordinates. They follow the OpenGL standard: some programs may require you to flip the second coordinate.</field>
		//calculated actual value based on version
		actNumUVSets = (DataFlags & 63) | (BSDataFlags & 1);
	 
		if (LOAD_OPTIMIZED) {
			uVSetsOptBuf = new FloatBuffer[actNumUVSets];
			for (int j = 0; j < actNumUVSets; j++) {
				uVSetsOptBuf[j] = createFB(numVertices * 2);
				for (int i = 0; i < numVertices; i++) {
					uVSetsOptBuf[j].put(i * 2 + 0, ByteConvert.readFloat(stream));
					uVSetsOptBuf[j].put(i * 2 + 1, ByteConvert.readFloat(stream));
				}
			}

		} else {
			uVSets = new NifTexCoord[actNumUVSets][numVertices];
			for (int j = 0; j < actNumUVSets; j++) {
				for (int i = 0; i < numVertices; i++) {
					uVSets[j][i] = new NifTexCoord(stream);
				}
			}
		}
	       
        //<field name="Consistency Flags" type="ConsistencyType" since="10.0.1.0" default="CT_MUTABLE">Consistency Flags</field>
		if (nifVer.LOAD_VER >= NifVer.VER_10_0_1_0)
			ConsistencyFlags = new ConsistencyType(stream);

		//<field name="Additional Data" type="Ref" template="AbstractAdditionalGeometryData" since="20.0.0.4" />
		if (nifVer.LOAD_VER >= NifVer.VER_20_0_0_4)
			additionalData = new NifRef(NiAdditionalGeometryData.class, stream);

		return success;

	}

	public void loadTangentAndBinormalsFromExtraData(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		if (tangentsOptBuf != null)
			new Throwable("ALERT tangents already set!!!1!").printStackTrace();

		if (hasNormals) {
			if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0) {
				if (LOAD_OPTIMIZED) {
					tangentsOptBuf = createFB(numVertices * 3);
					for (int i = 0; i < numVertices; i++) {
						tangentsOptBuf.put(i * 3 + 0, ByteConvert.readFloat(stream));
						tangentsOptBuf.put(i * 3 + 2, -ByteConvert.readFloat(stream));
						tangentsOptBuf.put(i * 3 + 1, ByteConvert.readFloat(stream));
					}

					binormalsOptBuf = createFB(numVertices * 3);
					for (int i = 0; i < numVertices; i++) {
						binormalsOptBuf.put(i * 3 + 0, ByteConvert.readFloat(stream));
						binormalsOptBuf.put(i * 3 + 2, -ByteConvert.readFloat(stream));
						binormalsOptBuf.put(i * 3 + 1, ByteConvert.readFloat(stream));
					}

				} else {
					tangents = new NifVector3[numVertices];
					for (int i = 0; i < numVertices; i++) {
						tangents[i] = new NifVector3(stream);
					}

					binormals = new NifVector3[numVertices];
					for (int i = 0; i < numVertices; i++) {
						binormals[i] = new NifVector3(stream);
					}

				}
			}
		}
	}

	protected static FloatBuffer createFB(int l)
	{
		ByteBuffer bb = ByteBuffer.allocateDirect(l * 4);
		bb.order(ByteOrder.nativeOrder());
		return bb.asFloatBuffer();

	}

}