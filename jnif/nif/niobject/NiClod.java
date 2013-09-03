package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class NiClod extends NiTriBasedGeom
{
	/**	 
	 <niobject name="NiClod" abstract="0" inherit="NiTriBasedGeom">

	 A shape node that holds continuous level of detail information.
	 Seems to be specific to Freedom Force.
	 
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}