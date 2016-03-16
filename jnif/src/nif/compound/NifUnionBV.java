package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifUnionBV
{
	/**
	 <compound name="UnionBV">
	 <add name="Num BV" type="uint">Number of Bounding Volumes.</add>
	 <add name="Bounding Volumes" type="BoundingVolume" arr1="Num BV">Bounding Volume.</add>
	 </compound>
	 */

	public int numBV;

	public NifBoundingVolume[] boundingVolumes;

	public NifUnionBV(ByteBuffer stream) throws IOException
	{
		numBV = ByteConvert.readInt(stream);
		boundingVolumes = new NifBoundingVolume[numBV];
		for (int i = 0; i < numBV; i++)
		{
			boundingVolumes[i] = new NifBoundingVolume(stream);
		}

	}
}
