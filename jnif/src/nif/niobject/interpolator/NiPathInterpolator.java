package nif.niobject.interpolator;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiFloatData;
import nif.niobject.NiPosData;

public class NiPathInterpolator extends NiKeyBasedInterpolator
{
	/**
	 <niobject name="NiPathInterpolator" abstract="0" inherit="NiKeyBasedInterpolator" ver1="20.0.0.5">

	 Unknown interpolator.
	 
	 <add name="Unknown Short" type="ushort">Unknown.</add>
	 <add name="Unknown Int" type="uint">Unknown.</add>
	 <add name="Unknown Float 1" type="float">Unknown.</add>
	 <add name="Unknown Float 2" type="float">Unknown.</add>
	 <add name="Unknown Short 2" type="ushort">Unknown. Zero.</add>
	 <add name="Pos Data" type="Ref" template="NiPosData">Links to NiPosData.</add>
	 <add name="Float Data" type="Ref" template="NiFloatData">Links to NiFloatData.</add>
	 </niobject>
	 */

	public short unknownShort;

	public int unknownInt;

	public float unknownFloat1;

	public float unknownFloat2;

	public short unknownShort2;

	public NifRef posData;

	public NifRef floatData;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownShort = ByteConvert.readShort(stream);
		unknownInt = ByteConvert.readInt(stream);
		unknownFloat1 = ByteConvert.readFloat(stream);
		unknownFloat2 = ByteConvert.readFloat(stream);
		unknownShort2 = ByteConvert.readShort(stream);
		posData = new NifRef(NiPosData.class, stream);
		floatData = new NifRef(NiFloatData.class, stream);

		return success;
	}
}