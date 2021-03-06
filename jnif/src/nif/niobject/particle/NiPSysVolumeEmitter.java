package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.NifVer;
import nif.basic.NifPtr;
import nif.niobject.NiNode;

public class NiPSysVolumeEmitter extends NiPSysEmitter
{
	/**
	 <niobject name="NiPSysVolumeEmitter" abstract="1" inherit="NiPSysEmitter" ver1="10.1.0.0">

	 An emitter that emits from a mesh?
	 
	 <add name="Emitter Object" type="Ptr" template="NiNode" ver1="10.1.0.0">Node parent of this modifier?</add>
	 </niobject>
	 */

	public NifPtr emitterObject;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		emitterObject = new NifPtr(NiNode.class, stream);

		return success;
	}
}
