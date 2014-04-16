package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NifExportInfo
{
	/**
	 <compound name="ExportInfo" ver1="10.0.1.2">
	 <add name="Unknown" type="uint" ver2="10.0.1.2" default="3">Probably the number of strings that follow.</add>
	 <add name="Creator" type="ShortString">Could be the name of the creator of the NIF file?</add>
	 <add name="Export Info 1" type="ShortString">
	 Unknown. Can be something like 'TriStrip Process Script'.
	 </add>
	 <add name="Export Info 2" type="ShortString">
	 Unknown. Possibly the selected option of the export script. Can be something like 'Default Export Script'.
	 </add>
	 </compound>
	 */

	public String creator;

	public String exportInfo1;

	public String exportInfo2;

	public NifExportInfo(int version, InputStream stream) throws IOException
	{
		if (version == NifVer.VER_10_0_1_2)
		{
			ByteConvert.readInt(stream);
		}
		creator = ByteConvert.readShortString(stream);
		exportInfo1 = ByteConvert.readShortString(stream);
		exportInfo2 = ByteConvert.readShortString(stream);
	}
}
