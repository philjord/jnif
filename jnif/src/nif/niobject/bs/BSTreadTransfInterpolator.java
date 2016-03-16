package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.BSTreadTransfInfo;
import nif.niobject.interpolator.NiInterpolator;

public class BSTreadTransfInterpolator extends NiInterpolator
{
	/**
	 
	 <niobject name="BSTreadTransfInterpolator" abstract="0" inherit="NiInterpolator">

	 Bethesda-specific node.
	 
	 <add name="Num Transfers" type="int">Unknown</add>
	 <add name="Tread Transfer Info" type="BSTreadTransfInfo" arr1="Num Transfers">Unknown</add>
	 <add name="Unknown Int 1" type="int">Unknown</add>
	 </niobject>
	 */

	public int numTransfers;

	public BSTreadTransfInfo[] treadTransferInfo;

	public int unknownInt1;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numTransfers = ByteConvert.readInt(stream);

		treadTransferInfo = new BSTreadTransfInfo[numTransfers];
		for (int i = 0; i < numTransfers; i++)
		{
			treadTransferInfo[i] = new BSTreadTransfInfo(stream);
		}

		unknownInt1 = ByteConvert.readInt(stream);

		return success;
	}
}