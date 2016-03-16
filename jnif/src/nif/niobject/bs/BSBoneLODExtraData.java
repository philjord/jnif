package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifBoneLOD;
import nif.niobject.NiExtraData;

public class BSBoneLODExtraData extends NiExtraData
{
	/**
	<niobject name="BSBoneLODExtraData" inherit="NiExtraData">
	Unknown
		<add name="BoneLOD Count" type="uint">Number of bone entries</add>
	    <add name="BoneLOD Info" type="BoneLOD" arr1="BoneLOD Count">Bone Entry</add>
	</niobject>
	*/

	public int BoneLODCount;

	public NifBoneLOD[] BoneLODInfo;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		BoneLODCount = ByteConvert.readInt(stream);
		BoneLODInfo = new NifBoneLOD[BoneLODCount];
		for (int i = 0; i < BoneLODCount; i++)
		{
			BoneLODInfo[i] = new NifBoneLOD(stream, nifVer);
		}

		return success;
	}
}
