package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.controller.NiFloatInterpController;

public class BSEffectShaderPropertyColorController extends NiFloatInterpController
{
	/**
	 	<niobject name="BSEffectShaderPropertyColorController" inherit="NiFloatInterpController">
	    Unkown
			<add name="Unknown Int 1" type="uint">Unknown</add>
		</niobject>
	 */
	public int UnknownInt1;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		UnknownInt1 = ByteConvert.readInt(stream);

		return success;
	}
}
