package nif.niobject.controller;

import java.nio.ByteBuffer;

import nif.NifVer;
import nif.basic.NifRef;
import nif.enums.TargetColor;
import nif.niobject.NiPosData;

public abstract class NiPoint3InterpController extends NiSingleInterpController
{
	/**
	 <niobject name="NiPoint3InterpController" abstract="1" inherit="NiSingleInterpController" ver1="10.2.0.0">

	 A controller that interpolates point 3 data?
	 
	 <add name="Target Color" type="TargetColor" ver1="10.1.0.0">Selects which color to control.</add>
	 <add name="Data" type="Ref" template="NiPosData" ver2="10.1.0.0">
	 Material color controller data object index. Points to NiPosData.
	 </add>
	 </niobject> 	
	 */

	public TargetColor targetColor;

	public NifRef Data;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0)
		{
			targetColor = new TargetColor(stream);
		}
		if (nifVer.LOAD_VER <= NifVer.VER_10_1_0_0)
		{
			Data = new NifRef(NiPosData.class, stream);
		}
		return success;
	}
}