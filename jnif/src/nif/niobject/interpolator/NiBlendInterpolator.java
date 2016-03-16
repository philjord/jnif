package nif.niobject.interpolator;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public abstract class NiBlendInterpolator extends NiInterpolator
{
	/**
	 
	 <niobject name="NiBlendInterpolator" abstract="1" inherit="NiInterpolator" ver1="20.0.0.5">

	 An extended type of interpolater.
	 
	 <add name="Unknown Short" type="ushort">Unknown.</add>
	 <add name="Unknown Int" type="uint">Unknown.</add>
	 </niobject>
	 */

	public short unknownShort;

	public int unknownInt;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknownShort = ByteConvert.readShort(stream);
		unknownInt = ByteConvert.readInt(stream);

		return success;
	}
}