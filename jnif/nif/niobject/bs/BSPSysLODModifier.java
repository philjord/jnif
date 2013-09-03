package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.particle.NiPSysModifier;

public class BSPSysLODModifier extends NiPSysModifier
{
/** 

    <niobject name="BSPSysLODModifier" inherit="NiPSysModifier">
        <add name="Uknown Float 1" type="float">Unknown</add>
        <add name="Uknown Float 2" type="float">Unknown</add>
        <add name="Uknown Float 3" type="float">Unknown</add>
        <add name="Uknown Float 4" type="float">Unknown</add>
    </niobject>
 */
	
	
	public float UnknownFloat1;
	public float UnknownFloat2;
	public float UnknownFloat3;
	public float UnknownFloat4;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		UnknownFloat1 = ByteConvert.readFloat(stream);
		UnknownFloat2 = ByteConvert.readFloat(stream);
		UnknownFloat3 = ByteConvert.readFloat(stream);
		UnknownFloat4 = ByteConvert.readFloat(stream);

		return success;
	}
}
