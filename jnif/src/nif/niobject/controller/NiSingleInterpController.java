package nif.niobject.controller;

import java.io.InputStream;

import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.interpolator.NiInterpolator;

public abstract class NiSingleInterpController extends NiInterpController
{
	/**
	 <niobject name="NiSingleInterpController" abstract="1" inherit="NiInterpController">

	 A controller referring to a single interpolator.
	 
	 <add name="Interpolator" type="Ref" template="NiInterpolator" ver1="10.2.0.0">Link to interpolator.</add>
	 </niobject>
	 */

	public NifRef interpolator;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		interpolator = new NifRef(NiInterpolator.class, stream);

		return success;
	}
}