package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector4;
import nif.niobject.NiNode;

public class BSOrderedNode extends NiNode
{
	/**
	 
	 <niobject name="BSOrderedNode" inherit="NiNode" ver1="20.2.0.7" userver="11">

	 Bethesda-Specific node.
	 
	 <add name="Alpha Sort Bound" type="Vector4">Unknown</add>
	 <add name="Is Static Bound" type="byte">Unknown</add>
	 </niobject>
	 */

	public NifVector4 alphaSortBound;

	public byte isStaticBound;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		alphaSortBound = new NifVector4(stream);
		isStaticBound = ByteConvert.readByte(stream);

		return success;
	}
}