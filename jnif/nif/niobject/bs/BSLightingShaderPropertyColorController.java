package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.controller.NiFloatInterpController;

public class BSLightingShaderPropertyColorController extends NiFloatInterpController
{
	/**
	 	<niobject name="BSLightingShaderPropertyColorController" abstract="0" inherit="NiFloatInterpController">
			<add name="Target Variable" type="uint">Which variable in the shader to animate.</add>
		</niobject>
	 */

	public float TargetVariable;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		TargetVariable = ByteConvert.readInt(stream);

		return success;
	}
}
