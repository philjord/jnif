package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifVector3;
import nif.niobject.NiAVObject;
import nif.niobject.NiAlphaProperty;
import nif.niobject.NiObject;

public class BSGeometry extends NiAVObject {
	/**
	 * BSGeometry has been part of an inheritance chanin only from everything pre Starfield, but Starfiled directly
	 * instantiates BSGeometry, so now I need it. Notice I am not change any other class so teh comment in NIGeometry
	 * and NiParticleSystem are still true
	 * 
	 * <niobject name="BSGeometry" inherit="NiAVObject" module="BSMain" versions="#STF#">
	 * <field name="Bounding Sphere" type="NiBound" /> <field name="Bounding Box" type="BSBoundingBox" />
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

		BoundingSphere = new NiBound(stream, nifVer);
		BoundingBox = new BSBoundingBox(stream);
		Skin = new NifRef(NiObject.class, stream);
		ShaderProperty = new NifRef(BSShaderProperty.class, stream);
		AlphaProperty = new NifRef(NiAlphaProperty.class, stream);
		System.out.println(new Throwable("Unfinished BSMeshArray").getStackTrace()[0]);
		//Meshes = new BSMeshArray[4];
		//for (int i = 0; i < 4; i++)
		//	Meshes[i] = new BSMeshArray(stream);

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

		public NiBound(ByteBuffer stream, NifVer nifVer) throws IOException {
			Center = new NifVector3(stream);
			Radius = ByteConvert.readFloat(stream);

			if (nifVer.DIVINITY2()) {
				System.out.println(new Throwable("Unfinished NiBound").getStackTrace()[0]);
			}
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

		public BSMeshArray(ByteBuffer stream) throws IOException {
			HasMesh = ByteConvert.readByte(stream) != 0;

			System.out.println(new Throwable("Unfinished BSMeshArray").getStackTrace()[0]);

		}
	}

}