package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifMatrix33;
import nif.compound.NifVector3;

public class NiTextureEffect extends NiDynamicEffect
{
	/**
	 
	 <niobject name="NiTextureEffect" abstract="0" inherit="NiDynamicEffect">

	 Enables environment mapping. Should be in both the children list and effects list of the NiTriShape object. 
	 For Morrowind: the bump map can be used to bump the environment map (note that the bump map is ignored if no 
	 NiTextureEffect object is present).
	 
	 <add name="Model Projection Matrix" type="Matrix33">Model projection matrix.  Always identity?</add>
	 <add name="Model Projection Transform" type="Vector3">Model projection transform.  Always (0,0,0)?</add>
	 <add name="Texture Filtering" type="TexFilterMode">Texture Filtering mode.</add>
	 <add name="Texture Clamping" type="TexClampMode">Texture Clamp mode.</add>
	 <add name="Texture Type" default="2" type="EffectType">The type of effect that the texture is used for.</add>
	 <add name="Coordinate Generation Type" default="2" type="CoordGenType">
	 The method that will be used to generate UV coordinates for the texture effect.
	 </add>
	 <add name="Image" type="Ref" template="NiImage" ver2="3.1">Image index.</add>
	 <add name="Source Texture" type="Ref" template="NiSourceTexture" ver1="4.0.0.0">Source texture index.</add>
	 <add name="Clipping Plane" default="0" type="byte">
	 Determines whether a clipping plane is used.  0 means that a plane is not used.
	 </add>
	 <add name="Unknown Vector" type="Vector3">Unknown: (1,0,0)?</add>
	 <add name="Unknown Float" type="float">Unknown. 0?</add>
	 <add name="PS2 L" type="short" default="0" ver2="10.2.0.0">0?</add>
	 <add name="PS2 K" type="short" default="-75" ver2="10.2.0.0">-75?</add>
	 <add name="Unknown Short" type="ushort" ver2="4.1.0.12">Unknown: 0.</add>
	 </niobject>
	 
	 */

	public NifMatrix33 modelProjectionMatrix;

	public NifVector3 modelProjectionTransform;

	public int textureFiltering;

	public int textureClamping;

	public short unknownShort2;

	public int textureType;

	public int coordinateGenerationType;

	public NifRef sourceTexture;

	public byte clippingPlane;

	public NifVector3 unknownVector;

	public float unknownFloat;

	public short PS2L;

	public short PS2K;

	public short unknownShort3;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		modelProjectionMatrix = new NifMatrix33(stream);
		modelProjectionTransform = new NifVector3(stream);
		textureFiltering = ByteConvert.readInt(stream);
		textureClamping = ByteConvert.readInt(stream);
		if (nifVer.LOAD_VER >= NifVer.VER_20_6_0_0)
		{
			unknownShort2 = ByteConvert.readShort(stream);
		}
		textureType = ByteConvert.readInt(stream);
		coordinateGenerationType = ByteConvert.readInt(stream);
		sourceTexture = new NifRef(NiSourceTexture.class, stream);
		clippingPlane = ByteConvert.readByte(stream);
		unknownVector = new NifVector3(stream);
		unknownFloat = ByteConvert.readFloat(stream);

		if (nifVer.LOAD_VER <= NifVer.VER_10_2_0_0)
		{
			PS2L= ByteConvert.readShort(stream);
			PS2K= ByteConvert.readShort(stream);
		}
		
		if (nifVer.LOAD_VER <= NifVer.VER_4_1_0_12)
		{
			unknownShort3= ByteConvert.readShort(stream);
		}
		return success;
	}
}