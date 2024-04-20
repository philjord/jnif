package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class BSSPTranslucencyParams
{
	/**
	 <struct name="BSSPTranslucencyParams" module="BSMain" versions="#F76#">
        <field name="Subsurface Color" type="Color3" />
        <field name="Transmissive Scale" type="float"/>
        <field name="Turbulence" type="float" />
        <field name="Thick Object" type="bool" />
        <field name="Mix Albedo" type="bool" />
    </struct>
	 */

	public NifColor3 SubsurfaceColor;
	public float TransmissiveScale;
	public float Turbulence;
	public boolean ThickObject;
	public boolean MixAlbedo;

	public BSSPTranslucencyParams(ByteBuffer stream) throws IOException
	{
		SubsurfaceColor = new NifColor3(stream);
		TransmissiveScale = ByteConvert.readFloat(stream);
		Turbulence = ByteConvert.readFloat(stream);
		ThickObject = ByteConvert.readByte(stream) != 0;
		MixAlbedo = ByteConvert.readByte(stream) != 0;
	}
 
}
