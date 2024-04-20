package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;
import nif.basic.NifFlags;

public class NiShaderProperty extends NiProperty
{
	/**	 
	<niobject name="NiShadeProperty" inherit="NiProperty" module="NiMain">
        Determines whether flat shading or smooth shading is used on a shape.
        <field name="Flags" type="ShadeFlags" default="SHADING_SMOOTH" vercond="#NI_BS_LTE_FO3#" />
    </niobject>
	 */

	public NifFlags flags;
	 

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		
		if(nifVer.NI_BS_LTE_FO3()) {
			flags = new NifFlags(stream);//<enum name="ShadeFlags" storage="ushort">		
		}

		return success;
	}
}