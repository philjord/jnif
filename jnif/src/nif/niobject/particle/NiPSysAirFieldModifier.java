package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector3;

public class NiPSysAirFieldModifier extends NiPSysFieldModifier
{
	/**
	 <niobject name="NiPSysAirFieldModifier" inherit="NiPSysFieldModifier">

	 Particle system modifier, used for controlling the particle velocity in a field like wind.
	 
	 <add name="Direction" type="Vector3">Direction of the particle velocity</add>
	 <add name="Unknown Float 2" type="float">Unknown</add>
	 <add name="Unknown Float 3" type="float">Unknown</add>
	 <add name="Unknown Boolean 1" type="bool">Unknown</add>
	 <add name="Unknown Boolean 2" type="bool">Unknown</add>
	 <add name="Unknown Boolean 3" type="bool">Unknown</add>
	 <add name="Unknown Float 4" type="float">Unknown</add>
	 </niobject>
	 */

	public NifVector3 direction;

	public float unknownFloat2;

	public float unknownFloat3;

	public boolean unknownBool1;

	public boolean unknownBool2;

	public boolean unknownBool3;

	public float unknownFloat4;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		direction = new NifVector3(stream);
		unknownFloat2 = ByteConvert.readFloat(stream);
		unknownFloat3 = ByteConvert.readFloat(stream);
		unknownBool1 = ByteConvert.readBool(stream, nifVer);
		unknownBool2 = ByteConvert.readBool(stream, nifVer);
		unknownBool3 = ByteConvert.readBool(stream, nifVer);
		unknownFloat4 = ByteConvert.readFloat(stream);
		return success;
	}
}
