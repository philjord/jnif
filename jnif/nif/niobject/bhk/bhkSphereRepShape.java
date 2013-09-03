package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.enums.HavokMaterial;

public abstract class bhkSphereRepShape extends bhkShape
{
	/**
	 <niobject name="bhkSphereRepShape" abstract="1" inherit="bhkShape">

	 A havok shape, perhaps with a bounding sphere for quick rejection in addition to more detailed shape data?
	 
	 <add name="Material" type="HavokMaterial">The shape's material.</add>
	 <add name="Radius" type="float">The radius of the sphere that encloses the shape.</add>
	 </niobject>
	 */

	public HavokMaterial material;

	public float radius;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		material = new HavokMaterial(stream);
		radius = ByteConvert.readFloat(stream);

		return success;
	}
}