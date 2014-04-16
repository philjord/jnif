package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifFlags;
import nif.basic.NifPtr;
import nif.compound.NifVector3;

public class NiPortal extends NiAVObject
{
	/**
	 
	 <niobject name="NiPortal" inherit="NiAVObject">

	 A Portal
	 
	 <add name="Unknown Flags" type="Flags">Unknown flags.</add>
	 <add name="Unknown Short 1" type="short">Unknown</add>
	 <add name="Num Vertices" type="ushort">Number of vertices in this polygon</add>
	 <add name="Vertices" type="Vector3" arr1="Num Vertices">Vertices</add>
	 <add name="Target" type="Ptr" template="NiNode">Target portal or room</add>
	 </niobject>
	 
	 */

	public NifFlags unknownFlags;

	public short unknownShort1b;

	public int numVertices;

	public NifVector3[] vertices;

	public NifPtr target;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownFlags = new NifFlags(stream);
		unknownShort1b = ByteConvert.readShort(stream);

		numVertices = ByteConvert.readInt(stream);
		vertices = new NifVector3[numVertices];
		for (int i = 0; i < numVertices; i++)
		{
			vertices[i] = new NifVector3(stream);
		}

		target = new NifPtr(NiNode.class, stream);

		return success;
	}
}