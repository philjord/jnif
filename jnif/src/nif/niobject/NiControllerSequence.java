package nif.niobject;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;
import nif.basic.NifRef;
import nif.niobject.controller.NiControllerManager;

public class NiControllerSequence extends NiSequence
{

	public static final int CYCLE_LOOP = 0;

	public static final int CYCLE_REVERSE = 1;

	public static final int CYCLE_CLAMP = 2;

	/**
	 <niobject name="NiControllerSequence" abstract="0" inherit="NiSequence" ver1="10.0.1.0">

	 Root node in .kf files (version 10.0.1.0 and up).
	 
	 <add name="Weight" type="float" default="1.0" ver1="10.1.0.106">Weight/priority of animation?</add>
	 <add name="Text Keys" type="Ref" template="NiTextKeyExtraData" ver1="10.1.0.106">
	 Link to NiTextKeyExtraData. Replaces the other Text Keys field in versions 10.1.0.106 and up.
	 </add>
	 <add name="Cycle Type" type="CycleType" ver1="10.1.0.106">
	 Anim cycle type? Is part of "Flags" in other objects?
	 </add>
	 <add name="Unknown Int 0" type="uint" ver1="10.1.0.106" ver2="10.1.0.106">Unknown.</add>
	 <add name="Frequency" type="float" ver1="10.1.0.106">The animation frequency.</add>
	 <add name="Start Time" type="float" ver1="10.1.0.106">The controller sequence start time?</add>
	 <add name="Stop Time" type="float" ver1="10.1.0.106">The controller sequence stop time?</add>
	 <add name="Unknown Float 2" type="float" ver1="10.2.0.0" ver2="10.2.0.0">Unknown.</add>
	 <add name="Unknown Byte" type="byte" ver1="10.1.0.106" ver2="10.1.0.106">Unknown.</add>
	 <add name="Manager" type="Ptr" template="NiControllerManager" ver1="10.1.0.106">
	 Refers to NiControllerManager which references this object, if any.
	 </add>
	 <add name="Target Name" type="string" ver1="10.1.0.106">Name of target node Controller acts on.</add>
	 <add name="String Palette" type="Ref" template="NiStringPalette" ver1="10.2.0.0" ver2="20.0.0.5">Refers to NiStringPalette.</add>
	 <add name="Unknown Short 1" type="short" ver1="20.2.0.7" vercond="(User Version == 11) && (User Version 2 >= 24)">Unknown</add>
	 <add name="Unknown Short 2" type="short" ver1="20.2.0.7" vercond="(User Version == 11) && (User Version 2 >= 24) && (User Version 2 <= 28)">Unknown</add>
	 </niobject>
	 */

	public float weight;

	public NifRef textKeys2;

	public int cycleType;

	public float frequency;

	public float startTime;

	public float stopTime;

	public NifPtr manager;

	public String targetName;

	public NifRef stringPalette;

	public short unknownShort1;

	public short unknownShort2;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		weight = ByteConvert.readFloat(stream);
		textKeys2 = new NifRef(NiTextKeyExtraData.class, stream);
		cycleType = ByteConvert.readInt(stream);
		if (nifVer.LOAD_VER == NifVer.VER_10_1_0_106)
		{
			ByteConvert.readInt(stream);
		}
		frequency = ByteConvert.readFloat(stream);
		startTime = ByteConvert.readFloat(stream);
		if (nifVer.LOAD_VER >= NifVer.VER_10_2_0_0 && nifVer.LOAD_VER <= NifVer.VER_10_4_0_1)
		{
			ByteConvert.readFloat(stream);
		}
		stopTime = ByteConvert.readFloat(stream);
		if (nifVer.LOAD_VER == NifVer.VER_10_1_0_106)
		{
			ByteConvert.readByte(stream);
		}
		manager = new NifPtr(NiControllerManager.class, stream);
		targetName = ByteConvert.readIndexString(stream, nifVer);
		if (nifVer.LOAD_VER >= NifVer.VER_10_2_0_0 && nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			stringPalette = new NifRef(NiStringPalette.class, stream);
		}

		if (nifVer.LOAD_VER == NifVer.VER_20_2_0_7 && (nifVer.LOAD_USER_VER >= 11) && nifVer.LOAD_USER_VER2 >= 24)
		{
			unknownShort1 = ByteConvert.readShort(stream);
			if (nifVer.LOAD_USER_VER2 <= 28)
			{
				unknownShort2 = ByteConvert.readShort(stream);
			}
		}
		if (nifVer.isBP())
		{
			ByteConvert.readInt(stream);
		}

		return success;
	}
}