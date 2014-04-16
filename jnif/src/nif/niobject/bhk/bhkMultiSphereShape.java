package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifSphereBV;

public class bhkMultiSphereShape extends bhkSphereRepShape
{
	/**
	 <niobject name="bhkMultiSphereShape" abstract="0" inherit="bhkSphereRepShape" ver1="20.0.0.5">

	 Unknown.
	 
	 <add name="Unknown Float 1" type="float">Unknown.</add>
	 <add name="Unknown Float 2" type="float">Unknown.</add>
	 <add name="Num Spheres" type="uint">The number of spheres in this multi sphere shape.</add>
	 <add name="Spheres" type="SphereBV" arr1="Num Spheres">
	 This array holds the spheres which make up the multi sphere shape.
	 </add>
	 </niobject>
	 */

	public float unknownFloat1;

	public float unknownFloat2;

	public int numSpheres;

	public NifSphereBV[] spheres;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknownFloat1 = ByteConvert.readFloat(stream);
		unknownFloat2 = ByteConvert.readFloat(stream);
		numSpheres = ByteConvert.readInt(stream);
		spheres = new NifSphereBV[numSpheres];
		for (int i = 0; i < numSpheres; i++)
		{
			spheres[i] = new NifSphereBV(stream);
		}

		return success;
	}
}