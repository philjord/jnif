package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;

public abstract class NiDynamicEffect extends NiAVObject
{
	/**
	 <niobject name="NiDynamicEffect" abstract="1" inherit="NiAVObject">

	 A dynamic effect such as a light or environment map.
	 
	 <add name="Switch State" type="bool" ver1="10.1.0.106">
	 Turns effect on and off?  Switches list to list of unaffected nodes?
	 </add>
	 <add name="Num Affected Node List Pointers" type="uint" ver2="4.0.0.2">The number of affected nodes referenced.</add>
	 <add name="Num Affected Nodes" type="uint" ver1="10.1.0.0">The number of affected nodes referenced.</add>
	 <add name="Affected Node List Pointers" type="uint" arr1="Num Affected Node List Pointers" ver2="4.0.0.2">
	 This is probably the list of affected nodes. For some reason i do not know the max exporter seems to write pointers instead of links. But it doesn't matter because at least in version 4.0.0.2 the list is automagically updated by the engine during the load stage.
	 </add>
	 <add name="Affected Nodes" type="Ref" template="NiAVObject" arr1="Num Affected Nodes" ver1="10.1.0.0">The list of affected nodes?</add>
	 </niobject>
	 */

	public boolean switchState;

	public int numAffectedNodes = 0;

	public NifRef[] affectedNodes;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		switchState = ByteConvert.readBool(stream, nifVer);
		numAffectedNodes = ByteConvert.readInt(stream);
		affectedNodes = new NifRef[numAffectedNodes];
		for (int i = 0; i < numAffectedNodes; i++)
		{
			affectedNodes[i] = new NifRef(NiAVObject.class, stream);
		}

		return success;
	}
}