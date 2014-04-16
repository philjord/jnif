package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NifhkTriangle
{
	/**
	 <compound name="hkTriangle">

	 A triangle with extra data used for physics.
	 
	 <add name="Triangle" type="Triangle">The triangle.</add>
	 <add name="Welding Information?" type="ushort">
	 Additional welding info computed when subshapes are in use.
	 </add>
	 <add name="Normal" type="Vector3" ver2="20.0.0.5">This is the triangle's normal.</add>
	 </compound>
	 */

	public NifTriangle triangle;

	public short unknownShort;

	public NifVector3 normal;

	public NifhkTriangle(InputStream stream, NifVer nifVer) throws IOException
	{
		triangle = new NifTriangle(stream);
		unknownShort = ByteConvert.readShort(stream);
		if (nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			normal = new NifVector3(stream);
		}
	}
}
