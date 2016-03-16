package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiAVObject;

public class NiPSysFieldModifier extends NiPSysModifier
{
	/**
	 <niobject name="NiPSysFieldModifier" abstract="1" inherit="NiPSysModifier">

	 Base for all force field particle modifiers.
	 
	 <add name="Field Object" type="Ref" template="NiAVObject">Force Field Object</add>
	 <add name="Magnitude" type="float">Magnitude of the force</add>
	 <add name="Attenuation" type="float">Controls how quick the field diminishes</add>
	 <add name="Unknown Boolean 1" type="bool">Unknown</add>
	 <add name="Unknown Float 1" type="float">Unknown value, perhaps maximum effect distance</add>
	 </niobject>
	 */

	public NifRef fieldObject;

	public float magnitude;

	public float attenuation;

	public boolean unknownBoolean1;

	public float unknownFloat1;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		fieldObject = new NifRef(NiAVObject.class, stream);
		magnitude = ByteConvert.readFloat(stream);
		attenuation = ByteConvert.readFloat(stream);
		unknownBoolean1 = ByteConvert.readBool(stream, nifVer);
		unknownFloat1 = ByteConvert.readFloat(stream);

		return success;
	}
}
