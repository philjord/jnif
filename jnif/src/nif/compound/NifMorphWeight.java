package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.basic.NifRef;
import nif.niobject.interpolator.NiInterpolator;

public class NifMorphWeight
{
	/**
	 <compound name="MorphWeight">
	 <add name="Interpolator" type="Ref" template="NiInterpolator">Interpolator</add>
	 <add name="Weight?" type="float">Weight</add>
	 </compound>
	 */

	public NifRef interpolator;

	public float weight;

	public NifMorphWeight(ByteBuffer stream) throws IOException
	{
		interpolator = new NifRef(NiInterpolator.class, stream);
		weight = ByteConvert.readInt(stream);
	}
}
