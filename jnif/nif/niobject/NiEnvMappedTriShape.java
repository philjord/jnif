package nif.niobject;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifMatrix44;
import nif.niobject.controller.NiObjectNET;

public class NiEnvMappedTriShape extends NiObjectNET
{
	/**
	 <niobject name="NiEnvMappedTriShape" abstract="0" ver1="3.1" ver2="3.1" inherit="NiObjectNET">

	 Unknown
	 
	 <add name="Unknown 1" type="ushort">unknown (=4 - 5)</add>
	 <add name="Unknown Matrix" type="Matrix44">unknown</add>
	 <add name="Num Children" type="uint">The number of child objects.</add>
	 <add name="Children" type="Ref" template="NiAVObject" arr1="Num Children">List of child node object indices.</add>
	 <add name="Child 2" type="Ref" template="NiObject">unknown</add>
	 <add name="Child 3" type="Ref" template="NiObject">unknown</add>
	 </niobject>
	 */
	public short unknown1;

	public NifMatrix44 unknownMatrix;

	public int numChildren;

	public NifRef[] children;

	public NifRef child2;

	public NifRef child3;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknown1 = ByteConvert.readShort(stream);
		unknownMatrix = new NifMatrix44(stream);

		numChildren = ByteConvert.readInt(stream);

		children = new NifRef[numChildren];
		for (int i = 0; i < numChildren; i++)
		{

			children[i] = new NifRef(NiAVObject.class, stream);
		}

		child2 = new NifRef(NiObject.class, stream);
		child3 = new NifRef(NiObject.class, stream);

		return success;
	}
}