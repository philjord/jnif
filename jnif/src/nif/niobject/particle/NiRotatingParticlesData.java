package nif.niobject.particle;

import java.io.IOException;
import java.io.InputStream;

import nif.NifVer;

public class NiRotatingParticlesData extends NiParticlesData
{
	/**
	 <niobject name="NiRotatingParticlesData" abstract="0" inherit="NiParticlesData">

	 Rotating particles data object.
	 
	 <add name="Has Rotations 2" type="bool" ver2="4.2.2.0">Is the particle rotation array present?</add>
	 <add name="Rotations 2" type="Quaternion" arr1="Num Vertices" cond="Has Rotations 2 != 0" ver2="4.2.2.0">The individual particle rotations.</add>
	 </niobject>
	 */

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}

}