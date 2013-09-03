package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class BSTreadTransfInfo
{
	/**
	 <compound name="BSTreadTransfInfo">

	 Bethesda-specific node.
	 
	 <add name="Unknown Float 1" type="float">Unknown Flag</add>
	 <add name="Data" type="BSTreadTransfSubInfo" arr1="2">Data</add>
	 </compound>	
	 */

	public float unknownFloat1;

	public BSTreadTransfSubInfo[] data;

	public BSTreadTransfInfo(InputStream stream) throws IOException
	{
		unknownFloat1 = ByteConvert.readFloat(stream);
		data = new BSTreadTransfSubInfo[2];
		data[0] = new BSTreadTransfSubInfo(stream);
		data[1] = new BSTreadTransfSubInfo(stream);

	}
}
