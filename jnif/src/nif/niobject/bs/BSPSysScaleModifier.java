package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.particle.NiPSysModifier;

public class BSPSysScaleModifier extends NiPSysModifier
{
	/**
	 
	    <niobject name="BSPSysScaleModifier" inherit="NiPSysModifier">
	        <add name="Num Floats" type="uint"></add>
	        <add name="Floats" type="float" arr1="Num Floats">Unknown</add>
	    </niobject>
	    
	 */

	public int numFloats;

	public float[] floats;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		numFloats = ByteConvert.readInt(stream);
		floats = ByteConvert.readFloats(numFloats, stream);

		return success;
	}
}
