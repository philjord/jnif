package nif.niobject.particle;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifParticleDesc;
import nif.compound.NifVector4;

public class NiPSysData extends NiRotatingParticlesData
{
	/**
	     <niobject name="NiPSysData" abstract="0" inherit="NiRotatingParticlesData">
	    Particle system data.
	    <add name="Particle Descriptions" type="ParticleDesc" arr1="Num Vertices" vercond="!((Version >= 20.2.0.7) &amp;&amp; (User Version >= 11))">Unknown.</add>
	    <add name="Has Unknown Floats 3" type="bool" ver1="20.0.0.4" vercond="!((Version >= 20.2.0.7) &amp;&amp; (User Version >= 11))">Unknown.</add>
	    <add name="Unknown Floats 3" type="float" arr1="Num Vertices" cond="Has Unknown Floats 3" ver1="20.0.0.4" vercond="!((Version >= 20.2.0.7) &amp;&amp; (User Version >= 11))">Unknown.</add>
	    <add name="Unknown Short 1" type="ushort" vercond="!((Version >= 20.2.0.7) &amp;&amp; (User Version == 11))">Unknown.</add>
	    <add name="Unknown Short 2" type="ushort" vercond="!((Version >= 20.2.0.7) &amp;&amp; (User Version == 11))">Unknown.</add>

	    <add name="Has Subtexture Offset UVs" type="bool" vercond="((Version >= 20.2.0.7) &amp;&amp; (User Version >= 12))">Boolean for Num Subtexture Offset UVs</add>
	    <add name="Num Subtexture Offset UVs" type="uint" vercond="((Version >= 20.2.0.7) &amp;&amp; (User Version >= 12))">How many quads to use in BSPSysSubTexModifier for texture atlasing</add>
	    <add name="Aspect Ratio" type="float" vercond="((Version >= 20.2.0.7) &amp;&amp; (User Version >= 12))">Sets aspect ratio for Subtexture Offset UV quads</add>
	    <add name="Subtexture Offset UVs" type="Vector4" arr1="Num Subtexture Offset UVs" cond="Has Subtexture Offset UVs == 1" vercond="((Version >= 20.2.0.7) &amp;&amp; (User Version >= 12))">Defines UV offsets</add>
	    <add name="Unknown Int 4" type="uint" vercond="((Version >= 20.2.0.7) &amp;&amp; (User Version >= 12))">Unknown</add>
	    <add name="Unknown Int 5" type="uint" vercond="((Version >= 20.2.0.7) &amp;&amp; (User Version >= 12))">Unknown</add>
	    <add name="Unknown Int 6" type="uint" vercond="((Version >= 20.2.0.7) &amp;&amp; (User Version >= 12))">Unknown</add>
	    <add name="Unknown Short 3" type="ushort" vercond="((Version >= 20.2.0.7) &amp;&amp; (User Version >= 12))">Unknown</add>
	    <add name="Unknown Byte 4" type="byte" vercond="((Version >= 20.2.0.7) &amp;&amp; (User Version >= 12))">Unknown</add>
	</niobject>
	 */

	public NifParticleDesc[] particleDescriptions;

	public boolean hasUnknownFloats3;

	public float[] unknownFloats3;

	public short unknownShort1;

	public short unknownShort2;

	public boolean HasSubtextureOffsetUVs;

	public int NumSubtextureOffsetUVs;

	public float AspectRatio;

	public NifVector4[] SubtextureOffsetUVs;

	public int UnknownInt4;

	public int UnknownInt5;

	public int UnknownInt6;

	public short UnknownShort3;

	public byte UnknownByte4;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		if (!(nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 11) && !nifVer.isBP())
		{
			particleDescriptions = new NifParticleDesc[numVertices];
			for (int i = 0; i < numVertices; i++)
			{
				particleDescriptions[i] = new NifParticleDesc(stream);
			}

			hasUnknownFloats3 = ByteConvert.readBool(stream, nifVer);
			if (hasUnknownFloats3)
			{
				unknownFloats3 = new float[numVertices];
				for (int i = 0; i < numVertices; i++)
				{
					unknownFloats3[i] = ByteConvert.readFloat(stream);
				}
			}
		}

		if (!(nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER == 11) && !nifVer.isBP())
		{
			unknownShort1 = ByteConvert.readShort(stream);
			unknownShort2 = ByteConvert.readShort(stream);
		}

		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 12 && !nifVer.isBP())
		{
			HasSubtextureOffsetUVs = ByteConvert.readBool(stream, nifVer);

			NumSubtextureOffsetUVs = ByteConvert.readInt(stream);

			AspectRatio = ByteConvert.readFloat(stream);

			if (HasSubtextureOffsetUVs)
			{
				SubtextureOffsetUVs = new NifVector4[NumSubtextureOffsetUVs];
				for (int i = 0; i < NumSubtextureOffsetUVs; i++)
				{
					SubtextureOffsetUVs[i] = new NifVector4(stream);
				}
			}

			UnknownInt4 = ByteConvert.readInt(stream);

			UnknownInt5 = ByteConvert.readInt(stream);

			UnknownInt6 = ByteConvert.readInt(stream);

			UnknownShort3 = ByteConvert.readShort(stream);

			UnknownByte4 = ByteConvert.readByte(stream);
		}

		return success;
	}
}