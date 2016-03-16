package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;

public class NiBinaryVoxelExtraData extends NiExtraData
{
	/**
	 
	 <niobject name="NiBinaryVoxelExtraData" abstract="0" inherit="NiExtraData" ver1="3.1">

	 Voxel extra data object.
	 
	 <add name="Unknown Int" type="uint" default="0">Unknown.  0?</add>
	 <add name="Data" type="Ref" template="NiBinaryVoxelData">Link to binary voxel data.</add>
	 </niobject>
	 */

	public int unknownInt;

	public NifRef data;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknownInt = ByteConvert.readInt(stream);
		data = new NifRef(NiBinaryVoxelData.class, stream);

		return success;
	}
}