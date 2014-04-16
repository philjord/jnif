package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiExtraData;

public class BSBehaviorGraphExtraData extends NiExtraData
{
/**
 	<niobject name="BSBehaviorGraphExtraData" inherit="NiExtraData">
    Links a nif with a Havok Behavior .hkx animation file
		<add name="Behaviour Graph File" type="string">Name of the hkx file.</add>
		<add name="Controls Base Skeleton" type="byte">Unknown, has to do with blending appended bones onto an actor.</add>
	</niobject>
 */
	
	public String BehaviourGraphFile;
	public byte ControlsBaseSkeleton;
	
	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		BehaviourGraphFile = ByteConvert.readIndexString(stream, nifVer);
		ControlsBaseSkeleton = ByteConvert.readByte(stream);

		return success;
	}
}
