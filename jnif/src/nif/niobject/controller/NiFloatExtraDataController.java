package nif.niobject.controller;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NiFloatExtraDataController extends NiExtraDataController
{
	/**
	 
	 <niobject name="NiFloatExtraDataController" abstract="0" inherit="NiExtraDataController" ver1="10.1.0.0">

	 Unknown.
	 
	 <add name="Controller Data" type="string" ver1="10.2.0.0">Refers to a NiFloatExtraData name.</add>
	 <add name="Num Extra Bytes" type="byte" ver2="10.1.0.0">Number of extra bytes.</add>
	 <add name="Unknown Bytes" type="byte" arr1="7" ver2="10.1.0.0">Unknown.</add>
	 <add name="Unknown Extra Bytes" type="byte" arr1="Num Extra Bytes" ver2="10.1.0.0">Unknown.</add>
	 </niobject>
	 */

	public String controllerData2;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		controllerData2 = ByteConvert.readIndexString(stream, nifVer);
		return success;
	}
}