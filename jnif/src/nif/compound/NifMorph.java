package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.enums.KeyType;

public class NifMorph
{
	public NifVer nVer;

	/**
	 <compound name="Morph">

	 Geometry morphing data component.
	 
	 <add name="Frame Name" type="string" ver1="10.1.0.106">Name of the frame.</add>
	 <add name="Num Keys" type="uint" ver2="10.1.0.0">The number of morph keys that follow.</add>
	 <add name="Interpolation" type="KeyType" ver2="10.1.0.0">
	 Unlike most objects, the presense of this value is not conditional on there being keys.
	 </add>
	 <add name="Keys" type="Key" arg="Interpolation" template="float" arr1="Num Keys" ver2="10.1.0.0">The morph key frames.</add>
	 <add name="Unknown Int" type="uint" ver1="10.1.0.106" ver2="10.2.0.0">Unknown.</add>
	 <add name="Unknown Int" type="uint" ver1="20.0.0.4" ver2="20.1.0.3" userver="0">Unknown.</add>
	 <add name="Vectors" type="Vector3" arr1="ARG">Morph vectors.</add>
	 </compound>
	 */

	public int numVertices;//arguement

	public String frameName;

	public int numKeys;

	public KeyType Interpolation;

	public NifKey[] Keys;

	public int UnknownInt1;

	public int UnknownInt2;

	public NifVector3[] vectors;

	public NifMorph(int numVertices, InputStream stream, NifVer nifVer) throws IOException
	{
		this.nVer = nifVer;

		this.numVertices = numVertices;
		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_106)
		{
			frameName = ByteConvert.readIndexString(stream, nifVer);
		}

		if (nifVer.LOAD_VER <= NifVer.VER_10_1_0_0)
		{
			numKeys = ByteConvert.readInt(stream);
			Interpolation = new KeyType(stream);
			Keys = new NifKey[numKeys];
			for (int i = 0; i < numKeys; i++)
			{
				Keys[i] = new NifKey(Interpolation, Float.class, stream, nifVer);
			}
		}

		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_106 && nifVer.LOAD_VER <= NifVer.VER_10_2_0_0)
		{
			UnknownInt1 = ByteConvert.readInt(stream);
		}
		if (nifVer.LOAD_VER >= NifVer.VER_20_0_0_4 && nifVer.LOAD_VER <= NifVer.VER_20_0_0_5 && nifVer.LOAD_USER_VER == 0)
		{
			UnknownInt2 = ByteConvert.readInt(stream);
		}
		vectors = new NifVector3[numVertices];
		for (int i = 0; i < numVertices; i++)
		{
			vectors[i] = new NifVector3(stream);
		}
	}
}
