package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifQuaternionXYZW;
import nif.niobject.interpolator.NiInterpolator;

public class BSRotAccumTransfInterpolator extends NiInterpolator
{
	/**
	 
	 <niobject name="BSRotAccumTransfInterpolator" abstract="0" inherit="NiInterpolator">

	 NO info from the niftools system!
	 Below is my mad guesses, the quats don't seem right
	 */

	public NifQuaternionXYZW q1;

	public NifQuaternionXYZW q2;

	public int i;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		q1 = new NifQuaternionXYZW(stream);
		q2 = new NifQuaternionXYZW(stream);
		i = ByteConvert.readInt(stream);
		return success;
	}
}