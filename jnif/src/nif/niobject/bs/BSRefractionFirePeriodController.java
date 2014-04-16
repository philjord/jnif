package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.controller.NiTimeController;
import nif.niobject.interpolator.NiInterpolator;

public class BSRefractionFirePeriodController extends NiTimeController
{
	/**
	 Name 	Type 	Arg 	Arr1 	Arr2 	Cond 	Description 	From	To
	
	Interpolator 	Ref<NiInterpolator> 					Link to Interpolator. 	20.2.0.7 	
	 <niobject name="BSRefractionFirePeriodController" abstract="0" inherit="NiTimeController" ver1="20.2.0.7" userver="11">
	 Bethesda-specific node.
	 </niobject>
	 */

	public NifRef interpolator;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7)
		{
			interpolator = new NifRef(NiInterpolator.class, stream);
		}
		return success;
	}
}