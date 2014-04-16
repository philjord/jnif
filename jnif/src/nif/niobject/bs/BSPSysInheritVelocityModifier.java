package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.particle.NiPSysModifier;

public class BSPSysInheritVelocityModifier extends NiPSysModifier
{
	/**
	 
	    <niobject name="BSPSysInheritVelocityModifier"  abstract="0" inherit="NiPSysModifier">
	        <add name="Unknown Int 1" type="uint">Unknown</add>
	        <add name="Unknown Float 1" type="float">Unknown</add>
	        <add name="Unknown Float 2" type="float">Unknown</add>
	        <add name="Unknown Float 3" type="float">Unknown</add>
	    </niobject>
	 */
	public float UnknownInt1;

	public float UnknownFloat1;

	public float UnknownFloat2;

	public float UnknownFloat3;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		UnknownInt1 = ByteConvert.readInt(stream);
		UnknownFloat1 = ByteConvert.readFloat(stream);
		UnknownFloat2 = ByteConvert.readFloat(stream);
		UnknownFloat3 = ByteConvert.readFloat(stream);

		return success;
	}
}
