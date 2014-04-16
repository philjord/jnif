package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NiVertWeightsExtraData extends NiExtraData
{
	/**
	 
	 <niobject name="NiVertWeightsExtraData" abstract="0" inherit="NiExtraData">

	 Not used in skinning.
	 Unsure of use - perhaps for morphing animation or gravity.
	 
	 <add name="Num Bytes" type="uint">Number of bytes in this data object.</add>
	 <add name="Num Vertices" type="ushort">Number of vertices.</add>
	 <add name="Weight" type="float" arr1="Num Vertices">The vertex weights.</add>
	 </niobject>
	 
	 */

	public int numBytes;

	public short numVertices;

	public float[] data;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numBytes = ByteConvert.readInt(stream);
		numVertices = ByteConvert.readShort(stream);
		data = ByteConvert.readFloats(numVertices, stream);
		return success;
	}
}