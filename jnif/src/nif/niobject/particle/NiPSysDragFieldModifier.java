package nif.niobject.particle;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector3;

public class NiPSysDragFieldModifier extends NiPSysFieldModifier
{
	/**
	 <niobject name="NiPSysDragFieldModifier" inherit="NiPSysFieldModifier">

	 Particle system modifier, used for controlling the particle velocity in drag space warp.
	 
	 <add name="Use Direction?" type="bool">Whether to use the direction field?</add>
	 <add name="Direction" type="Vector3">Direction of the particle velocity</add>
	 </niobject>
	 */

	public boolean useDirection;

	public NifVector3 direction;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		useDirection = ByteConvert.readBool(stream, nifVer);
		direction = new NifVector3(stream);

		return success;
	}
}
