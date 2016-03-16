package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifLODRange
{
	/**
	 <compound name="LODRange">

	 The distance range where a specific level of detail applies.
	 
	 <add name="Near Extent" type="float">Begining of range.</add>
	 <add name="Far Extent" type="float">End of Range.</add>
	 <add name="Unknown Ints" type="uint" arr1="3" ver2="3.1">Unknown (0,0,0).</add>
	 </compound>
	 */

	public float near;

	public float far;

	public NifLODRange(ByteBuffer stream) throws IOException
	{
		near = ByteConvert.readFloat(stream);
		far = ByteConvert.readFloat(stream);
	}
}
