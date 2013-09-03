package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.interpolator.NiInterpolator;
import nif.niobject.particle.NiPSysModifierCtlr;

public class BSPSysMultiTargetEmitterCtlr extends NiPSysModifierCtlr
{
	/**
	 
	 <niobject name="BSPSysMultiTargetEmitterCtlr" inherit="NiPSysModifierCtlr" ver1="20.2.0.7" userver="11">

	 Particle system (multi?) emitter controller.
	 
	 <add name="Data" type="Ref" template="NiPSysEmitterCtlrData" ver2="10.1.0.0">This controller's data</add>
	 <add name="Visibility Interpolator" type="Ref" template="NiInterpolator" ver1="10.2.0.0">
	 Links to a bool interpolator. Controls emitter's visibility status?
	 </add>
	 <add name="Unknown Short 1" type="short">Unknown</add>
	 <add name="Unknown Int 1" type="int">Unknown</add>
	 </niobject>
	 */

	public NifRef visibilityInterpolator;

	public short unknownShort1;

	public int unknownInt1;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		visibilityInterpolator = new NifRef(NiInterpolator.class, stream);

		unknownShort1 = ByteConvert.readShort(stream);
		unknownInt1 = ByteConvert.readInt(stream);

		return success;
	}
}