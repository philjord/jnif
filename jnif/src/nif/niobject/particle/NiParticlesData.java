package nif.niobject.particle;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifQuaternion;
import nif.compound.NifVector3;
import nif.compound.NifVector4;
import nif.niobject.NiGeometryData;
import nif.niobject.NiObject;

public class NiParticlesData extends NiGeometryData
{

	/**
		    
	    <add name="Num Particles" type="ushort" ver2="4.0.0.2">The maximum number of particles (matches the number of vertices).</add>
	    <add name="Particle Radius" type="float" ver2="10.0.1.0">The particles&#039; size.</add>
	    <add name="Has Radii" type="bool" ver1="10.1.0.0">Is the particle size array present?</add>
	    <add name="Radii" type="float" arr1="Num Vertices" cond="Has Radii" ver1="10.1.0.0" vercond="!((Version >= 20.2.0.7) &amp;&amp; (User Version >= 11))">The individual particel sizes.</add>
	    <add name="Num Active" type="ushort">The number of active particles at the time the system was saved. This is also the number of valid entries in the following arrays.</add>
	    <add name="Has Sizes" type="bool">Is the particle size array present?</add>
	    <add name="Sizes" type="float" arr1="Num Vertices" cond="Has Sizes" vercond="!((Version >= 20.2.0.7) &amp;&amp; (User Version >= 11))">The individual particel sizes.</add>
	    <add name="Has Rotations" type="bool" ver1="10.0.1.0">Is the particle rotation array present?</add>
	    <add name="Rotations" type="Quaternion" arr1="Num Vertices" cond="Has Rotations" ver1="10.0.1.0" vercond="!((Version >= 20.2.0.7) &amp;&amp; (User Version >= 11))">The individual particle rotations.</add>
	    <add name="Unknown Byte 1" type="byte" vercond="((Version >= 20.2.0.7) &amp;&amp; (User Version >= 12))">Unknown, probably a boolean.</add>
	    <add name="Unknown Link" type="Ref" template="NiObject" vercond="((Version >= 20.2.0.7) &amp;&amp; (User Version >= 12))">Unknown</add>
	    <add name="Has Rotation Angles" type="bool" ver1="20.0.0.4">Are the angles of rotation present?</add>
	    <add name="Rotation Angles" type="float" arr1="Num Vertices" cond="Has Rotation Angles" vercond="!((Version >= 20.2.0.7) &amp;&amp; (User Version >= 11))">Angles of rotation</add>
	    <add name="Has Rotation Axes" type="bool" ver1="20.0.0.4">Are axes of rotation present?</add>
	    <add name="Rotation Axes" type="Vector3" arr1="Num Vertices" cond="Has Rotation Axes" ver1="20.0.0.4" vercond="!((Version >= 20.2.0.7) &amp;&amp; (User Version >= 11))">Unknown</add>
	    <add name="Has UV Quadrants" type="bool" vercond="(Version >= 20.2.0.7) &amp;&amp; (User Version == 11)">if value is no, a single image rendered</add>
	    <add name="Num UV Quadrants" type="byte" vercond="(Version >= 20.2.0.7) &amp;&amp; (User Version == 11)">2,4,8,16,32,64 are potential values. If "Has" was no then this should be 256, which represents a 16x16 framed image, which is invalid</add>
	    <add name="UV Quadrants" type="Vector4" arr1="Num UV Quadrants" cond="Has UV Quadrants >= 1" vercond="(Version >= 20.2.0.7) &amp;&amp; (User Version == 11)"></add>
	    <add name="Unknown Byte 2" type="byte" vercond="(Version == 20.2.0.7) &amp;&amp; (User Version >= 11)">Unknown</add>
	</niobject>
	 

	 */

	public short numParticles;

	public float particlesRadius;

	public boolean hasRadii;

	public float[] radii;

	public short numActive;

	public boolean hasSizes;

	public float[] sizes;

	public boolean hasRotations1;

	public NifQuaternion[] rotations1;

	public byte UnknownByte1;

	public NifRef UnknownLink;

	public boolean hasRotationAngles;

	public float[] rotationAngles;

	public boolean hasRotationAxes;

	public NifVector3[] rotationAxes;

	public boolean HasUVQuadrants;

	public short NumUVQuadrants;

	public NifVector4[] UVQuadrants;

	public byte UnknownByte2;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		if (nifVer.LOAD_VER <= NifVer.VER_4_0_0_2)
		{
			numParticles = ByteConvert.readShort(stream);
		}
		if (nifVer.LOAD_VER <= NifVer.VER_10_0_1_0)
		{
			particlesRadius = ByteConvert.readFloat(stream);
		}

		if (nifVer.LOAD_VER >= NifVer.VER_10_0_1_0)
		{
			hasRadii = ByteConvert.readBool(stream, nifVer);
			if (!(nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 11))
			{
				if (hasRadii)
				{
					radii = new float[numVertices];
					for (int i = 0; i < numVertices; i++)
					{
						radii[i] = ByteConvert.readFloat(stream);
					}
				}
			}
		}
		numActive = ByteConvert.readShort(stream);

		hasSizes = ByteConvert.readBool(stream, nifVer);
		if (!(nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 11))
		{
			if (hasSizes)
			{
				sizes = new float[numVertices];
				for (int i = 0; i < numVertices; i++)
				{
					sizes[i] = ByteConvert.readFloat(stream);
				}
			}
		}
		if (nifVer.LOAD_VER >= NifVer.VER_10_0_1_0)
		{
			hasRotations1 = ByteConvert.readBool(stream, nifVer);
			if (!(nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 11))
			{
				if (hasRotations1)
				{
					rotations1 = new NifQuaternion[numVertices];
					for (int i = 0; i < numVertices; i++)
					{
						rotations1[i] = new NifQuaternion(stream);
					}
				}
			}
		}

		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 12 && !nifVer.isBP())
		{
			UnknownByte1 = ByteConvert.readByte(stream);
			UnknownLink = new NifRef(NiObject.class, stream);
		}

		if (nifVer.LOAD_VER >= NifVer.VER_20_0_0_4)
		{
			hasRotationAngles = ByteConvert.readBool(stream, nifVer);
			//where did this spec come from??? does oblivion work with this spec???
			if (!(nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 11 && !nifVer.isBP()))
			{
				if (hasRotationAngles)
				{
					rotationAngles = new float[numVertices];
					for (int i = 0; i < numVertices; i++)
					{
						rotationAngles[i] = ByteConvert.readFloat(stream);
					}
				}
			}
		 

		hasRotationAxes = ByteConvert.readBool(stream, nifVer);
		if (!(nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 11 && !nifVer.isBP()))
		{
			if (hasRotationAxes)
			{
				rotationAxes = new NifVector3[numVertices];
				for (int i = 0; i < numVertices; i++)
				{
					rotationAxes[i] = new NifVector3(stream);
				}
			}
		}
		}

		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER == 11 && !nifVer.isBP())
		{
			HasUVQuadrants = ByteConvert.readBool(stream, nifVer);

			NumUVQuadrants = ByteConvert.readByte(stream);
			if (HasUVQuadrants)
			{
				UVQuadrants = new NifVector4[NumUVQuadrants];
				for (int i = 0; i < NumUVQuadrants; i++)
				{
					UVQuadrants[i] = new NifVector4(stream);
				}
			}
		}

		if (nifVer.LOAD_VER == NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 11 && !nifVer.isBP())
		{
			UnknownByte2 = ByteConvert.readByte(stream);
		}

		return success;
	}
}