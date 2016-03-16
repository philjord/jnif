package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector3;

public class bhkCapsuleShape extends bhkConvexShape
{
	/**
	 <niobject name="bhkCapsuleShape" abstract="0" inherit="bhkConvexShape" ver1="20.0.0.5">

	 A capsule.
	 
	 <add name="Unknown 8 Bytes" type="byte" arr1="8">Unknown.</add>
	 <add name="First Point" type="Vector3">First point on the capsule's axis.</add>
	 <add name="Radius 1" type="float">Matches first capsule radius.</add>
	 <add name="Second Point" type="Vector3">Second point on the capsule's axis.</add>
	 <add name="Radius 2" type="float">Matches second capsule radius.</add>
	 </niobject>
	 */

	public byte[] unknown8Bytes;

	public NifVector3 firstPoint;

	public float radius1;

	public NifVector3 secondPoint;

	public float radius2;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknown8Bytes = ByteConvert.readBytes(8, stream);

		firstPoint = new NifVector3(stream);
		radius1 = ByteConvert.readFloat(stream);
		secondPoint = new NifVector3(stream);
		radius2 = ByteConvert.readFloat(stream);

		return success;
	}
}