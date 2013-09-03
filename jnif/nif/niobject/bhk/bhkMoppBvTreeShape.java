package nif.niobject.bhk;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifVector3;
import nif.enums.HavokMaterial;

public class bhkMoppBvTreeShape extends bhkBvTreeShape
{
	/**
	 
	    <niobject name="bhkMoppBvTreeShape" abstract="0" inherit="bhkBvTreeShape">
        Memory optimized partial polytope bounding volume tree shape (not an entity).
        <add name="Shape" type="Ref" template="bhkShape">The shape.</add>
        <add name="Material" type="HavokMaterial">The shape's material.</add>
        <!--<add name="Unknown 8 Bytes" type="byte" arr1="8">Unknown bytes, probably MOPP Header.</add>-->
        <add name="Unknown Int 1" type="uint">Unknown</add>
        <add name="Unknown Int 2" type="uint">Unknown</add>
        <add name="Unknown Float" type="float" default="1.0">Unknown float, might be scale.</add>
        <add name="MOPP Data Size" type="uint">Number of bytes for MOPP data.</add>
        <add name="Origin" type="Vector3">Origin of the object in mopp coordinates. This is the minimum of all vertices in the packed shape along each axis, minus 0.1.</add>
        <add name="Scale" type="float">The scaling factor to quantize the MOPP: the quantization factor is equal to 256*256 divided by this number. In Oblivion files, scale is taken equal to 256*256*254 / (size + 0.2) where size is the largest dimension of the bounding box of the packed shape.</add>
        <add name="Old MOPP Data" ver2="10.0.1.0" type="byte" nifskopetype="blob" arr1="MOPP Data Size - 1">The tree of bounding volume data (old style, contains more than just the mopp script).</add>
        <add name="MOPP Data" ver1="10.0.1.2" type="byte" nifskopetype="blob" arr1="MOPP Data Size">The tree of bounding volume data.</add>
        <add name="Unknown Byte 1" type="byte" ver1="20.2.0.7" vercond="User Version >= 12">Unknown</add>
    </niobject>
	 
	 */

	public NifRef shape;

	public HavokMaterial material;

	public int unknownInt1;

	public int unknownInt2;

	public float unknownFloat;

	public int mOPPDataSize;

	public NifVector3 origin;

	public float scale;

	public byte[] mOPPData;

	public byte unknownByte1;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		shape = new NifRef(bhkShape.class, stream);
		material = new HavokMaterial(stream);
		unknownInt1 = ByteConvert.readInt(stream);
		unknownInt2 = ByteConvert.readInt(stream);
		unknownFloat = ByteConvert.readFloat(stream);
		mOPPDataSize = ByteConvert.readInt(stream);
		origin = new NifVector3(stream);
		scale = ByteConvert.readFloat(stream);
		mOPPData = ByteConvert.readBytes(mOPPDataSize, stream);

		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 12)
		{
			unknownByte1 = ByteConvert.readByte(stream);
		}

		return success;
	}
}