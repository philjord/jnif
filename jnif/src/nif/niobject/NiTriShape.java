package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public class NiTriShape extends NiTriBasedGeom
{

	/**
	 <niobject name="NiTriShape" abstract="0" inherit="NiTriBasedGeom">

	 A shape node that refers to singular triangle data.
	 
	 </niobject>
	 */
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		return success;
	}
}