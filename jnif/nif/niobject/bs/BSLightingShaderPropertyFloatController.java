package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.controller.NiFloatInterpController;

public class BSLightingShaderPropertyFloatController extends NiFloatInterpController
{
	/**
	 

	<niobject name="BSLightingShaderPropertyFloatController" abstract="0" inherit="NiFloatInterpController">
	This controller is used to animate variables in BSLightingShaderPropertyFloatController, target is a number in order they appear:
	5: Texture Translation U
	6: Texture Translation V
	7: Texture Repeat U
	8: Texture Repeat V
	    <add name="Target Variable" type="uint">Which variable in the shader to animate.</add>
	</niobject>
	 */

	public int TargetVariable;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		TargetVariable = ByteConvert.readInt(stream);

		return success;
	}
}
