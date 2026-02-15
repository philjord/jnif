package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NifExportInfo
{
	/**
	  <struct name="BSStreamHeader" versions="#BETHESDA#" module="BSMain">
    	Information about how the file was exported
        <field name="BS Version" type="ulittle32" />
        <field name="Author" type="ExportString" />
        <field name="Unknown Int" type="uint" cond="BS Version #GT# 130" />
        <field name="Process Script" type="ExportString"  cond="BS Version #LT# 131" />
        <field name="Export Script" type="ExportString" />
        <field name="Max Filepath" type="ExportString" cond="BS Version >= 103" />
    </struct>
	 </add>
	 </compound>
	 */

	public String creator;

	public String exportInfo1;

	public String exportInfo2;

	public String exportInfo3;

	public NifExportInfo(NifVer nifVer, ByteBuffer stream) throws IOException
	{
		if (nifVer.LOAD_VER <= NifVer.VER_10_0_1_2)
		{
			ByteConvert.readInt(stream);
		}
		creator = ByteConvert.readByteString(stream);
		
		if(nifVer.BS_Version > 130)
			ByteConvert.readInt(stream);
		if(nifVer.BS_Version <= 130)
			exportInfo1 = ByteConvert.readByteString(stream);

		exportInfo2 = ByteConvert.readByteString(stream);

		if(nifVer.BS_Version >= 103)
			exportInfo3 = ByteConvert.readByteString(stream);


	}
}
