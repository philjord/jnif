package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifTexCoord
{
	/**
	 <compound name="TexCoord" niflibtype="TexCoord" nifskopetype="vector2">

	 Texture coordinates (u,v). As in OpenGL; image origin is in the lower left corner.
	 
	 <add name="u" type="float">First coordinate.</add>
	 <add name="v" type="float">Second coordinate.</add>
	 </compound>
	 */

	public float u;

	public float v;

	public NifTexCoord(InputStream stream) throws IOException
	{
		u = ByteConvert.readFloat(stream);
		v = ByteConvert.readFloat(stream);
	}
}
