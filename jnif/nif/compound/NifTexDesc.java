package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifFlags;
import nif.basic.NifRef;
import nif.enums.FilterMode;
import nif.enums.TexClampMode;
import nif.niobject.NiSourceTexture;

public class NifTexDesc
{
	/**
	 Texture description.
	 
	 <add name="Source" type="Ref" template="NiSourceTexture">NiSourceTexture object index.</add>
	 <add name="Clamp Mode" type="TexClampMode" default="WRAP_S_WRAP_T" ver2="20.0.0.5">
	 0=clamp S clamp T, 1=clamp S wrap T, 2=wrap S clamp T, 3=wrap S wrap T
	 </add>
	 <add name="Filter Mode" type="TexFilterMode" default="FILTER_TRILERP" ver2="20.0.0.5">
	 0=nearest, 1=bilinear, 2=trilinear, 3=..., 4=..., 5=...
	 </add>
	 <add name="Flags" type="Flags" ver1="20.1.0.3">Texture mode flags.</add>
	 <add name="UV Set" type="uint" default="0" ver2="20.0.0.5">
	 The texture coordinate set in NiGeometryData that this texture slot will use.
	 </add>
	 <add name="PS2 L" type="short" default="0" ver2="10.2.0.0">0?</add>
	 <add name="PS2 K" type="short" default="-75" ver2="10.2.0.0">-75?</add>
	 <add name="Unknown1" type="ushort" ver2="4.1.0.12">Unknown, 0 or 0x0101?</add>
	 <add name="Has Texture Transform" type="bool" default="false" ver1="10.1.0.0">
	 Determines whether or not the texture's coordinates are transformed.
	 </add>
	 <add name="Translation" type="TexCoord" cond="Has Texture Transform != 0" ver1="10.1.0.0">
	 The amount to translate the texture coordinates in each direction?
	 </add>
	 <add name="Tiling" type="TexCoord" cond="Has Texture Transform != 0" ver1="10.1.0.0">
	 The number of times the texture is tiled in each direction?
	 </add>
	 <add name="W Rotation" type="float" default="0.0" cond="Has Texture Transform != 0" ver1="10.1.0.0">
	 2D Rotation of texture image around third W axis after U and V.
	 </add>
	 <add name="Transform Type?" type="uint" default="0" cond="Has Texture Transform != 0" ver1="10.1.0.0">
	 The texture transform type?  Doesn't seem to do anything.
	 </add>
	 <add name="Center Offset" type="TexCoord" cond="Has Texture Transform != 0" ver1="10.1.0.0">The offset from the origin?</add>
	 </compound>
	 */

	public NifRef source;

	public TexClampMode clampMode;

	public FilterMode filterMode;

	public NifFlags flags;

	public int uvSet;

	public boolean hasTextureTransform;

	public NifTexCoord translation;

	public NifTexCoord tiling;

	public float wRotation;

	public int transformType;

	public NifTexCoord centerOffset;

	public int PS2L;

	public int PS2K;

	public NifTexDesc(InputStream stream, NifVer nifVer) throws IOException
	{
		source = new NifRef(NiSourceTexture.class, stream);
		if (nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			clampMode = new TexClampMode(stream);
			filterMode = new FilterMode(stream);
		}
		if (nifVer.LOAD_VER >= NifVer.VER_20_1_0_3)
		{
			flags = new NifFlags(stream);
		}
		if (nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			uvSet = ByteConvert.readInt(stream);
		}

		if (nifVer.LOAD_VER <= NifVer.VER_10_4_0_1)
		{
			PS2L = ByteConvert.readShort(stream);
			PS2K = ByteConvert.readShort(stream);
		}

		hasTextureTransform = ByteConvert.readBool(stream, nifVer);
		if (hasTextureTransform)
		{
			translation = new NifTexCoord(stream);
			tiling = new NifTexCoord(stream);
			wRotation = ByteConvert.readFloat(stream);
			transformType = ByteConvert.readInt(stream);
			centerOffset = new NifTexCoord(stream);
		}
	}
}
