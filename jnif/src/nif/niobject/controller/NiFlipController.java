package nif.niobject.controller;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiSourceTexture;

public class NiFlipController extends NiFloatInterpController
{
	/**
	 <niobject name="NiFlipController" abstract="0" inherit="NiFloatInterpController">

	 Texture flipping controller.
	 
	 <add name="Texture Slot" type="uint">Target texture slot (0=base, 4=glow).</add>
	 <add name="Unknown Int 2" type="uint" ver1="4.0.0.0" ver2="10.1.0.0">0?</add>
	 <add name="Delta" type="float" ver2="10.1.0.0">

	 Time between two flips.
	 delta = (start_time - stop_time) / sources.num_indices
	 
	 </add>
	 <add name="Num Sources" type="uint">The number of source objects.</add>
	 <add name="Sources" type="Ref" template="NiSourceTexture" arr1="Num Sources" ver1="4.0.0.0">The texture sources.</add>
	 <add name="Images" type="Ref" template="NiImage" arr1="Num Sources" ver2="3.1">The image sources</add>
	 </niobject>
	 */

	public int textureSlot;

	public int numSources;

	public NifRef[] sources;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		textureSlot = ByteConvert.readInt(stream);
		numSources = ByteConvert.readInt(stream);
		sources = new NifRef[numSources];
		for (int i = 0; i < numSources; i++)
		{
			sources[i] = new NifRef(NiSourceTexture.class, stream);
		}

		return success;
	}
}