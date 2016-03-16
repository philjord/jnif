package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiPSysGrowFadeModifier extends NiPSysModifier
{
	/**
	 <niobject name="NiPSysGrowFadeModifier" abstract="0" inherit="NiPSysModifier" ver1="10.1.0.0">

	 Particle modifier that controls the time it takes to grow a particle from Size=0 to the specified Size in the emitter, and then back to 0.  This modifer has no control over alpha settings.
	 
	 <add name="Grow Time" type="float">Time in seconds to fade in.</add>
	 <add name="Grow Generation" type="ushort">Unknown.</add>
	 <add name="Fade Time" type="float">Time in seconds to fade out.</add>
	 <add name="Fade Generation" type="ushort">Unknown.</add>
	 <add name="Base Scale" type="float" ver1="20.2.0.7" userver="11">Unknown</add>
	 </niobject>
	 */

	public float growTime;

	public short growGeneration;

	public float fadeTime;

	public short fadeGeneration;

	public float baseScale;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		growTime = ByteConvert.readFloat(stream);
		growGeneration = ByteConvert.readShort(stream);
		fadeTime = ByteConvert.readFloat(stream);
		fadeGeneration = ByteConvert.readShort(stream);
		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && (nifVer.LOAD_USER_VER == 11||nifVer.LOAD_USER_VER == 12))
		{
			baseScale = ByteConvert.readFloat(stream);
		}
		return success;
	}
}
