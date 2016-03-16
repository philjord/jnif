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
	 
	 <niobject name="BSPSysSimpleColorModifier" inherit="NiPSysModifier" ver1="20.2.0.7" userver="11">

	 Bethesda-Specific Particle node.
	 
	 <add name="Fade In Percent" type="float">Unknown</add>
	 <add name="Fade out Percent" type="float">Unknown</add>
	 <add name="Color 1 End Percent" type="int">Unknown</add>
	 <add name="Color 1 Start Percent" type="int">Unknown</add>
	 <add name="Color 2 End Percent" type="int">Unknown</add>
	 <add name="Color 2 Start Percent" type="int">Unknown</add>
	 <add name="Colors" type="Color4" arr1="3">Colors</add>
	 </niobject>
	 */

	public float fadeInPercent;

	public float fadeOutPercent;

	public int color1EndPercent;

	public int color1StartPercent;

	public int color2EndPercent;

	public int color2StartPercent;

	public NifColor4[] colors;

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

		return success;
	}
}