package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifSkinShapeGroup
{
	/**
	 * <compound name="SkinShapeGroup" ver1="10.0.1.0">

	 Unknown.
	 
	 <add name="Num Link Pairs" type="uint">Counts unknown.</add>
	 <add name="Link Pairs" type="SkinShape" arr1="Num Link Pairs">
	 First link is a NiTriShape object.
	 Second link is a NiSkinInstance object.
	 
	 */

	public int numLinkPairs;

	public NifSkinShape[] linkPairs;

	public NifSkinShapeGroup(InputStream stream) throws IOException
	{
		numLinkPairs = ByteConvert.readInt(stream);

		linkPairs = new NifSkinShape[numLinkPairs];
		for (int i = 0; i < numLinkPairs; i++)
		{
			linkPairs[i] = new NifSkinShape(stream);
		}

	}
}
