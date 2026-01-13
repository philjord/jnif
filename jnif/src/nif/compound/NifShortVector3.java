package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifShortVector3
{
	/**
	 

    <struct name="ShortVector3" size="6" convertible="Vector3" module="NiMain">
        A vector in 3D space (x,y,z), in signed normalized 16-bit format.
        <field name="x" type="short">First coordinate.</field>
        <field name="y" type="short">Second coordinate.</field>
        <field name="z" type="short">Third coordinate.</field>
    </struct>
	
	 */

	public float x;

	public float y;

	public float z;

	public NifShortVector3(ByteBuffer stream) throws IOException
	{
		x = ByteConvert.readUnsignedShort(stream);
		y = ByteConvert.readUnsignedShort(stream);
		z = ByteConvert.readUnsignedShort(stream);

		if (Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(z))
		{
			x = 0;
			y = 0;
			z = 0;
		}
	}

	// for NifMinFloatVector3
	protected NifShortVector3()
	{

	}

	public boolean equals(Object o)
	{
		if (o instanceof NifShortVector3)
		{
			NifShortVector3 v2 = (NifShortVector3) o;
			return v2.x == x && v2.y == y && v2.z == z;
		}
		return false;
	}

	public String toString()
	{
		return "[NifShortVector3] " + x + " " + y + " " + z;
	}
}
