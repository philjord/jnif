package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifFlags;
import nif.enums.BSShaderFlags;
import nif.enums.BSShaderType;
import nif.niobject.NiProperty;

public class BSShaderProperty extends NiProperty
{
	/**
	 
	 <niobject name="BSShaderProperty" abstract="0" inherit="NiProperty" ver1="20.2.0.7">

	 Bethesda-specific Property node
	 
	 <add name="Flags" type="Flags" default="1">Unknown</add>
	 <add name="Shader Type" type="BSShaderType" default="1">
	 Unknown (Set to 0x21 for NoLighting, 0x11 for Water)
	 </add>
	 <add name="shaderFlags" type="uint" default="0x82000000">Unknown</add>
	 <add name="Unknown Int 2" type="int" default="1">Unknown</add>
	 <add name="envmapScale" type="float" default="1.0">Unknown</add>
	 </niobject>
	 */

	public NifFlags flags;

	public BSShaderType shaderType;

	public BSShaderFlags shaderFlags;

	public int unknownInt2;

	public float envmapScale;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		flags = new NifFlags(stream);
		shaderType = new BSShaderType(stream);
		shaderFlags = new BSShaderFlags(stream);
		unknownInt2 = ByteConvert.readInt(stream);
		envmapScale = ByteConvert.readFloat(stream);

		return success;
	}
}