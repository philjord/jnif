package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class bhkBlendCollisionObject extends bhkCollisionObject
{
	/**
	 
	 <niobject name="bhkBlendCollisionObject" abstract="0" inherit="bhkCollisionObject" ver1="20.0.0.5">

	 Unknown.
	 
	 <add name="Unknown Float 1" type="float">Blending parameter?</add>
	 <add name="Unknown Float 2" type="float">Another blending parameter?</add>
	 </niobject>
	 
	 */

	public float unknownFloat1;

	public float unknownFloat2;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownFloat1 = ByteConvert.readFloat(stream);
		unknownFloat2 = ByteConvert.readFloat(stream);

		return success;
	}
}