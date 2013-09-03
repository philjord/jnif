package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiNode;

public class BSMultiBoundNode extends NiNode
{
	/**
	 
	 
	<niobject name="BSMultiBoundNode" inherit="NiNode">
	    Bethesda-specific node.
	    <add name="Multi Bound" type="Ref" template="BSMultiBound">Unknown.</add>
		<add name="Unknown Int" type="uint" ver1="20.2.0.7" vercond="(User Version >= 12)">Unknown</add>
	</niobject>
	 */

	public NifRef MultiBound;

	public int unknownInt1;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		MultiBound = new NifRef(BSMultiBound.class, stream);
		if (nifVer.LOAD_USER_VER >= 12)
		{
			unknownInt1 = ByteConvert.readInt(stream);
		}

		return success;
	}
}