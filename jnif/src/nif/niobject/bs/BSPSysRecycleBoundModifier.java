package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.particle.NiPSysModifier;

public class BSPSysRecycleBoundModifier extends NiPSysModifier
{

	/**
	 <niobject name="BSPSysRecycleBoundModifier"  abstract="0" inherit="NiPSysModifier">
	        <add name="Unknown Float 1" type="float">Unknown</add>
	        <add name="Unknown Float 2" type="float">Unknown</add>
	        <add name="Unknown Float 3" type="float">Unknown</add>
	        <add name="Unknown Float 4" type="float">Unknown</add>
	        <add name="Unknown Float 5" type="float">Unknown</add>
	        <add name="Unknown Float 6" type="float">Unknown</add>
	        <add name="Unknown Int 1" type="uint">Unknown</add>
	    </niobject> 
	 */

	public float UnknownFloat1;

	public float UnknownFloat2;

	public float UnknownFloat3;

	public float UnknownFloat4;

	public float UnknownFloat5;

	public float UnknownFloat6;

	public float UnknownInt1;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		UnknownFloat1 = ByteConvert.readFloat(stream);
		UnknownFloat2 = ByteConvert.readFloat(stream);
		UnknownFloat3 = ByteConvert.readFloat(stream);
		UnknownFloat4 = ByteConvert.readFloat(stream);
		UnknownFloat5 = ByteConvert.readFloat(stream);
		UnknownFloat6 = ByteConvert.readFloat(stream);
		UnknownInt1 = ByteConvert.readInt(stream);

		return success;
	}
}
