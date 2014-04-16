package nif.niobject.controller;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiFloatData;
import nif.niobject.NiPosData;

public class NiPathController extends NiTimeController
{
	/**
	 <niobject name="NiPathController" abstract="0" inherit="NiTimeController">

	 Time controller for a path.
	 
	 <add name="Unknown Short 2" type="ushort" ver1="10.1.0.0">Unknown.</add>
	 <add name="Unknown Int 1" type="uint">Unknown, always 1?</add>
	 <add name="Unknown Int 2" type="uint">Unknown, always 0?</add>
	 <add name="Unknown Int 3" type="uint">Unknown, always 0?</add>
	 <add name="Unknown Short" type="ushort">Unknown, always 0?</add>
	 <add name="Pos Data" type="Ref" template="NiPosData">Path controller data index (position data). ?</add>
	 <add name="Float Data" type="Ref" template="NiFloatData">Path controller data index (float data). ?</add>
	 </niobject>	
	 */

	public short unknownShort2;

	public int unknownInt1;

	public int unknownInt2;

	public int unknownInt3;

	public short unknownShort;

	public NifRef posData;

	public NifRef floatData;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownShort2 = ByteConvert.readShort(stream);
		unknownInt1 = ByteConvert.readInt(stream);
		unknownInt2 = ByteConvert.readInt(stream);
		unknownInt3 = ByteConvert.readInt(stream);
		unknownShort = ByteConvert.readShort(stream);

		posData = new NifRef(NiPosData.class, stream);
		floatData = new NifRef(NiFloatData.class, stream);

		return success;
	}
}