package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifColor4;
import nif.niobject.particle.NiPSysModifier;

public class BSPSysSimpleColorModifier extends NiPSysModifier
{
	/**
	<niobject name="BSPSysSimpleColorModifier" inherit="NiPSysModifier" module="BSParticle" versions="#FO3_AND_LATER#">
        Bethesda-specific particle modifier.
        <field name="Fade In Percent" type="float" default="0.1" range="#F0_1#" />
        <field name="Fade Out Percent" type="float" default="0.9" range="#F0_1#" />
        <field name="Color 1 End Percent" type="float" range="#F0_1#" />
        <field name="Color 1 Start Percent" type="float" range="#F0_1#" />
        <field name="Color 2 End Percent" type="float" range="#F0_1#" />
        <field name="Color 2 Start Percent" type="float" default="1.0" range="#F0_1#" />
        <field name="Colors" type="Color4" length="3" />
        <field name="Unknown Shorts" type="ushort" length="26" vercond="#BS_GTE_F76#" />
    </niobject>
	 */

	public float fadeInPercent;

	public float fadeOutPercent;

	public int color1EndPercent;

	public int color1StartPercent;

	public int color2EndPercent;

	public int color2StartPercent;

	public NifColor4[] colors;

	public short[] UnknownShorts;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		fadeInPercent = ByteConvert.readFloat(stream);
		fadeOutPercent = ByteConvert.readFloat(stream);
		color1EndPercent = ByteConvert.readInt(stream);
		color1StartPercent = ByteConvert.readInt(stream);
		color2EndPercent = ByteConvert.readInt(stream);
		color2StartPercent = ByteConvert.readInt(stream);
		colors = new NifColor4[3];
		for (int i = 0; i < 3; i++)
		{
			colors[i] = new NifColor4(stream);
		}

		
		//<field name="Unknown Shorts" type="ushort" length="26" vercond="#BS_GTE_F76#" />
		if(nifVer.BS_GTE_F76())
			UnknownShorts = ByteConvert.readShorts(26, stream);
		
		
		return success;
	}
}