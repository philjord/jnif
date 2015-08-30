package nif.niobject.controller;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifFlags;
import nif.basic.NifRef;
import nif.compound.NifMorphWeight;
import nif.niobject.NiMorphData;
import nif.niobject.interpolator.NiInterpolator;

public class NiGeomMorpherController extends NiInterpController
{
	/**
	 <niobject name="NiGeomMorpherController" abstract="0" inherit="NiInterpController">

	 Time controller for geometry morphing.
	 
	 <add name="Extra Flags" type="Flags" ver1="10.0.1.2">Unknown.</add>
	 <add name="Unknown 2" type="byte" ver1="10.1.0.106" ver2="10.1.0.106">Unknown.</add>
	 <add name="Data" type="Ref" template="NiMorphData">Geometry morphing data index.</add>
	 <add name="Always Update" type="byte">Always Update</add>
	 <add name="Num Interpolators" type="uint" ver1="10.1.0.106">The number of interpolator objects.</add>
	 <add name="Interpolators" type="Ref" template="NiInterpolator" arr1="Num Interpolators" ver1="10.1.0.106" ver2="20.2.0.6">List of interpolators.</add>
	 <add name="Interpolator Weights" type="MorphWeight" arr1="Num Interpolators" ver1="20.2.0.7">Weighted Interpolators?</add>
	 <add name="Num Unknown Ints" type="uint" ver1="20.0.0.4" ver2="20.0.0.5" vercond="(User Version == 10) || (User Version == 11)">A count.</add>
	 <add name="Unknown Ints" type="uint" arr1="Num Unknown Ints" ver1="20.0.0.4" ver2="20.0.0.5" vercond="(User Version == 10) || (User Version == 11)">Unknown.</add>
	 </niobject>
	 */

	public NifFlags extraFlags;

	public NifRef data;

	public byte alwaysUpdate;

	public int numInterpolators;

	public NifRef[] interpolators;

	public NifMorphWeight[] interpolatorWeights;

	public int numUnknownInts;

	public int[] unknownInts;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (nifVer.LOAD_VER >= NifVer.VER_10_0_1_2)
		{
			extraFlags = new NifFlags(stream);
		}
		if (nifVer.LOAD_VER == NifVer.VER_10_1_0_106)
		{
			ByteConvert.readByte(stream);
		}
		data = new NifRef(NiMorphData.class, stream);
		alwaysUpdate = ByteConvert.readByte(stream);
		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_106)
		{
			numInterpolators = ByteConvert.readInt(stream);
			if (nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
			{
				interpolators = new NifRef[numInterpolators];
				for (int i = 0; i < numInterpolators; i++)
				{
					interpolators[i] = new NifRef(NiInterpolator.class, stream);
				}
			}

			if (nifVer.LOAD_VER >= NifVer.VER_20_1_0_3)
			{
				interpolatorWeights = new NifMorphWeight[numInterpolators];
				for (int i = 0; i < numInterpolators; i++)
				{
					interpolatorWeights[i] = new NifMorphWeight(stream);
				}
			}
		}

		if (nifVer.LOAD_VER >= NifVer.VER_20_0_0_4 && nifVer.LOAD_VER <= NifVer.VER_20_0_0_5
				&& (nifVer.LOAD_USER_VER == 10 || (nifVer.LOAD_USER_VER == 11 || nifVer.LOAD_USER_VER == 12)))
		{
			numUnknownInts = ByteConvert.readInt(stream);
			unknownInts = ByteConvert.readInts(numUnknownInts, stream);
		}

		return success;
	}
}