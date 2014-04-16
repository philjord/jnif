package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifbhkCMSDBigTris
{
	/**
	 * 

    <compound name="bhkCMSDBigTris">
    Triangle indices used in pair with "Big Verts" in a bhkCompressedMeshShapeData.
        <add name="Triangle 1" type="ushort"></add>
        <add name="Triangle 2" type="ushort"></add>
        <add name="Triangle 3" type="ushort"></add>
        <add name="Unknown Int 1" type="uint">Always 0?</add>
        <add name="Unknown Short 1" type="ushort"></add>
    </compound>
	 */
	public short Triangle1;
	public short Triangle2;
	public short Triangle3;

	public int UnknownInt1;
	public short UnknownShort1;

	public NifbhkCMSDBigTris(InputStream stream) throws IOException
	{
		Triangle1  = ByteConvert.readShort(stream);
		Triangle2  = ByteConvert.readShort(stream);
		Triangle3  = ByteConvert.readShort(stream);
		UnknownInt1 = ByteConvert.readInt(stream);
		UnknownShort1  = ByteConvert.readShort(stream);
	}
}
