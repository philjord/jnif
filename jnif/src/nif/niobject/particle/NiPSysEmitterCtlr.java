package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.interpolator.NiInterpolator;

public class NiPSysEmitterCtlr extends NiPSysModifierCtlr
{
	/**
	 <niobject name="NiPSysEmitterCtlr" abstract="0" inherit="NiPSysModifierCtlr" ver1="10.1.0.0">

	 Particle system emitter controller.
	 
	 <add name="Data" type="Ref" template="NiPSysEmitterCtlrData" ver2="10.1.0.0">This controller's data</add>
	 <add name="Visibility Interpolator" type="Ref" template="NiInterpolator" ver1="10.2.0.0">
	 Links to a bool interpolator. Controls emitter's visibility status?
	 PJ - from looking at the nif I think this is an active controller, to deicde when the emitter can emit
	 </add>
	 </niobject>
	 */

	public NifRef visibilityInterpolator;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		visibilityInterpolator = new NifRef(NiInterpolator.class, stream);

		return success;
	}
}