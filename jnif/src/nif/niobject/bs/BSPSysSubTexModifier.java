package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.particle.NiPSysModifier;

public class BSPSysSubTexModifier extends NiPSysModifier
{
	/**
	 <niobject name="BSPSysSubTexModifier"  abstract="0" inherit="NiPSysModifier">
	    Similar to a Flip Controller, this handles particle texture animation on a single texture atlas
	        <add name="Start Frame" type="uint">Starting frame/position on atlas</add>
	        <add name="Start Frame Fudge" type="float">Random chance to start on a different frame?</add>
	        <add name="End Frame" type="float">Ending frame/position on atlas</add>
	        <add name="Loop Start Frame" type="float">Frame to start looping</add>
	        <add name="Loop Start Frame Fudge" type="float"></add>
	        <add name="Frame Count" type="float">Unknown</add>
	        <add name="Frame Count Fudge" type="float">Unknown</add>
	    </niobject>
	 */

	public int StartFrame;

	public float StartFrameFudge;

	public float EndFrame;

	public float LoopStartFrame;

	public float LoopStartFrameFudge;

	public float FrameCount;

	public float FrameCountFudge;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		StartFrame = ByteConvert.readInt(stream);
		StartFrameFudge = ByteConvert.readFloat(stream);
		EndFrame = ByteConvert.readFloat(stream);
		LoopStartFrame = ByteConvert.readFloat(stream);
		LoopStartFrameFudge = ByteConvert.readFloat(stream);
		FrameCount = ByteConvert.readFloat(stream);
		FrameCountFudge = ByteConvert.readFloat(stream);

		return success;
	}
}
