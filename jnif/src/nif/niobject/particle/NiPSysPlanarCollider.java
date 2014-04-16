package nif.niobject.particle;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector3;

public class NiPSysPlanarCollider extends NiPSysCollider
{
	/**
	 
	 <niobject name="NiPSysPlanarCollider" abstract="0" inherit="NiPSysCollider" ver1="10.2.0.0">

	 Particle Collider object which particles will interact with.
	 
	 <add name="Width" type="float">Defines the width of the plane.</add>
	 <add name="Height" type="float">Defines the height of the plane.</add>
	 <add name="X Axis" type="Vector3">Defines Orientation.</add>
	 <add name="Y Axis" type="Vector3">Defines Orientation.</add>
	 </niobject>
	 */

	public float width;

	public float height;

	public NifVector3 xAxis;

	public NifVector3 yAxis;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		width = ByteConvert.readFloat(stream);
		height = ByteConvert.readFloat(stream);
		xAxis = new NifVector3(stream);
		yAxis = new NifVector3(stream);

		return success;
	}
}