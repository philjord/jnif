package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.controller.NiFloatInterpController;

public class BSEffectShaderPropertyFloatController extends NiFloatInterpController
{
	/**
	 <niobject name="BSEffectShaderPropertyFloatController" abstract="0" inherit="NiFloatInterpController">
	    This controller is used to animate variables in BSEffectShaderPropertyFloatController, target is a number in order they appear:
	    0: Visibility?
	    1: 
	    2: 
	    3: 
	    4: Emissive or Saturation?
	    5: Alpha Transparency
	    6: Texture Translation U
	    7: Texture Repeat U
	    8: Texture Translate V
	    9: Texture Repeat V
	        <add name="Target Variable" type="uint">Unknown</add>
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
