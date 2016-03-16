package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.controller.NiObjectNET;

public class NiPhysXProp extends NiObjectNET
{
	/**
	 
	 <niobject name="NiPhysXProp" abstract="0" inherit="NiObjectNET" ver1="20.1.0.7">

	 Unknown PhysX node.
	 
	 <add name="Unknown Float 1" type="float">Unknown</add>
	 <add name="Unknown Int 1" type="uint">Unknown</add>
	 <add name="Unknown Refs 1" type="Ref" template="NiObject" arr1="Unknown Int 1">Unknown</add>
	 <add name="Num Dests" type="int">Number of NiPhysXTransformDest references</add>
	 <add name="Transform Dests" type="Ref" template="NiPhysXTransformDest" arr1="Num Dests">Unknown</add>
	 <add name="Unknown Byte" type="byte">Unknown</add>
	 <add name="Prop Description" type="Ref" template="NiPhysXPropDesc">PhysX Property Description.</add>
	 </niobject>
	 */

	public float unknownFloat1;

	public int unknownInt1;

	public NifRef[] unknownRefs1;

	public int numDests;

	public NifRef[] transformDests;

	public byte unknownByte;

	public NifRef propDescription;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownFloat1 = ByteConvert.readFloat(stream);
		unknownInt1 = ByteConvert.readInt(stream);
		unknownRefs1 = new NifRef[unknownInt1];
		for (int i = 0; i < unknownInt1; i++)
		{
			unknownRefs1[i] = new NifRef(NiObject.class, stream);
		}

		numDests = ByteConvert.readInt(stream);
		transformDests = new NifRef[numDests];
		for (int i = 0; i < numDests; i++)
		{
			transformDests[i] = new NifRef(NiPhysXTransformDest.class, stream);
		}
		unknownByte = ByteConvert.readByte(stream);

		propDescription = new NifRef(NiPhysXPropDesc.class, stream);
		return success;
	}
}