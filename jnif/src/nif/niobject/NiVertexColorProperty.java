package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.basic.NifFlags;
import nif.enums.LightMode;
import nif.enums.VertMode;

public class NiVertexColorProperty extends NiProperty
{
	/**
	 <niobject name="NiVertexColorProperty" abstract="0" inherit="NiProperty">

	 Property of vertex colors. This object is referred to by the root object of the NIF file whenever some NiTriShapeData object has vertex colors with non-default settings; 
	 if not present, vertex colors have vertex_mode=2 and lighting_mode=1.
	 
	 <add name="Flags" type="Flags">Property flags.</add>
	 <add name="Vertex Mode" type="VertMode" ver2="20.0.0.5">

	 Determines how vertex and material colors are mixed.
	 related gl function: glColorMaterial
	 In Flags from version 20.1.0.3 onwards.
	 
	 </add>
	 <add name="Lighting Mode" type="LightMode" ver2="20.0.0.5">The light mode. In Flags from 20.1.0.3 on.</add>
	 </niobject>
	 */

	public NifFlags flags;

	public VertMode vertexMode;

	public LightMode lightingMode;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		flags = new NifFlags(stream);
		if (nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			vertexMode = new VertMode(stream);
			lightingMode = new LightMode(stream);
		}
		return success;
	}
}