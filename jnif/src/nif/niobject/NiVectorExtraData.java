package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector3;

public class NiVectorExtraData extends NiExtraData
{
	/**
	 
	 <niobject name="NiVectorExtraData" abstract="0" inherit="NiExtraData" ver1="10.1.0.0">

	 Extra vector data.
	 
	 <add name="Vector Data" type="Vector3">The vector data.</add>
	 <add name="Unknown Float" type="float">
	 Not sure whether this comes before or after the vector data.
	 </add>
	 </niobject>
	 
	 */

	public NifVector3 vectorData;

	public float unknownFloat;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		vectorData = new NifVector3(stream);
		unknownFloat = ByteConvert.readFloat(stream);

		return success;
	}
}