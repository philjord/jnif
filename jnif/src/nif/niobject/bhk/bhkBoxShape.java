package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector3;

public class bhkBoxShape extends bhkConvexShape
{
	/**
	 <niobject name="bhkBoxShape" abstract="0" inherit="bhkConvexShape" ver1="20.0.0.5">

	 A box.
	 
	 <add name="Unknown 8 Bytes" type="byte" arr1="8">Unknown.</add>
	 <add name="Dimensions" type="Vector3">Looks like this could be the box size.</add>
	 <add name="Minimum Size" type="float">
	 The smallest of the three sizes. Might be used for optimization.
	 </add>
	 </niobject>
	 */

	public byte[] unknown8Bytes;

	public NifVector3 dimensions;

	public float minSize;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknown8Bytes = ByteConvert.readBytes(8, stream);

		dimensions = new NifVector3(stream);

		minSize = ByteConvert.readFloat(stream);
		return success;
	}
}