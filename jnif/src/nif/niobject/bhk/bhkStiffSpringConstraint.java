package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector4;

public class bhkStiffSpringConstraint extends bhkConstraint
{
	/**
	 
	 <niobject name="bhkStiffSpringConstraint" abstract="0" inherit="bhkConstraint">

	 A spring constraint.
	 
	 <add name="Pivot A" type="Vector4">Pivot A.</add>
	 <add name="Pivot B" type="Vector4">Pivot B.</add>
	 <add name="Length" type="float">Length.</add>
	 </niobject>
	 
	 */

	public NifVector4 pivotA;

	public NifVector4 pivotB;

	public float length;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		pivotA = new NifVector4(stream);
		pivotB = new NifVector4(stream);
		length = ByteConvert.readFloat(stream);

		return success;
	}
}
