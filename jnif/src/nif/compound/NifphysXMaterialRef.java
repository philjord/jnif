package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.basic.NifRef;
import nif.niobject.NiPhysXMaterialDesc;

public class NifphysXMaterialRef
{
	/**
	 <compound name="physXMaterialRef">
	 <add name="Number" type="byte">Unknown</add>
	 <add name="Unknown Byte 1" type="byte">Unknown</add>
	 <add name="Material Desc" type="Ref" template="NiPhysXMaterialDesc">PhysX Material Description</add>
	 </compound>
	 */

	public byte number;

	public byte unknownByte1;

	public NifRef materialDesc;

	public NifphysXMaterialRef(InputStream stream) throws IOException
	{

		number = ByteConvert.readByte(stream);
		unknownByte1 = ByteConvert.readByte(stream);

		materialDesc = new NifRef(NiPhysXMaterialDesc.class, stream);

	}
}
