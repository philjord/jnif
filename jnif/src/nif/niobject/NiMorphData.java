package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifMorph;

public class NiMorphData extends NiObject
{
	/**
	 <niobject name="NiMorphData" abstract="0" inherit="NiObject">

	 Geometry morphing data.
	 
	 <add name="Num Morphs" type="uint">Number of morphing object.</add>
	 <add name="Num Vertices" type="uint">Number of vertices.</add>
	 <add name="Relative Targets" type="byte" default="1">This byte is always 1 in all official files.</add>
	 <add name="Morphs" type="Morph" arg="Num Vertices" arr1="Num Morphs">The geometry morphing objects.</add>
	 </niobject>
	 */

	public int numMorphs;

	public int numVertices;

	public byte relativeTargets;

	public NifMorph[] morphs;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numMorphs = ByteConvert.readInt(stream);
		numVertices = ByteConvert.readInt(stream);
		relativeTargets = ByteConvert.readByte(stream);

		morphs = new NifMorph[numMorphs];
		for (int i = 0; i < numMorphs; i++)
		{
			morphs[i] = new NifMorph(numVertices, stream, nifVer);
		}

		return success;
	}
}