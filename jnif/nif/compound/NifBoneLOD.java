package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NifBoneLOD
{
	/**
	     <compound name="BoneLOD">
	    Stores Bone Level of Detail info in a BSBoneLODExtraData
	    <add name="Distance" type="uint">Distance to cull?</add>
	    <add name="Bone Name" type="string">The bones name</add>
	</compound>
	 */

	public int Distance;

	public String BoneName;

	public NifBoneLOD(InputStream stream, NifVer nifVer) throws IOException
	{

		Distance = ByteConvert.readInt(stream);

		BoneName = ByteConvert.readIndexString(stream, nifVer);

	}
}
