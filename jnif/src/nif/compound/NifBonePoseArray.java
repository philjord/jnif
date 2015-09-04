package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifBonePoseArray
{
	/*<compound name="BonePoseArray">
	<add name="Num Unknown Array" type="int">Unknown</add>
	<add name="Unknown Floats" type="NifMatrix33" arr1="Unknown Array 2">Unknown</add>
	</compound>*/

	public int numUnknownArray;

	public int[] unknownInts;

	public NifMatrix33[] unknownArray;

	public NifBonePoseArray(InputStream stream) throws IOException
	{
		numUnknownArray = ByteConvert.readInt(stream);
		unknownInts = new int[numUnknownArray];
		unknownArray = new NifMatrix33[numUnknownArray];
		for (int i = 0; i < numUnknownArray; i++)
		{
			unknownInts[i] = ByteConvert.readInt(stream);
			unknownArray[i] = new NifMatrix33(stream);
		}
	}
}
