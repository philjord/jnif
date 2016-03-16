package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.particle.NiPSysModifier;

public class BSPSysHavokUpdateModifier extends NiPSysModifier
{
	/**
	 
	    <niobject name="BSPSysHavokUpdateModifier"  abstract="0" inherit="NiPSysModifier">
	        <add name="Unknown Int 1" type="uint">Unknown</add>
	        <add name="Unknown Int 2" type="uint">Unknown</add>
	        <add name="Unknown Int 3" type="uint">Unknown</add>
	    </niobject> 
	 */

	public int UnknownInt1;

	public int UnknownInt2;

	public int UnknownInt3;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		UnknownInt1 = ByteConvert.readInt(stream);
		UnknownInt2 = ByteConvert.readInt(stream);
		UnknownInt3 = ByteConvert.readInt(stream);

		return success;
	}
}
