package nif.niobject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifFlags;
import nif.basic.NifRef;
import nif.compound.NifBoundingBox;
import nif.compound.NifMatrix33;
import nif.compound.NifVector3;
import nif.niobject.controller.NiObjectNET;

public abstract class NiAVObject extends NiObjectNET
{

	// reverse lookup of NiNode children references
	public NiNode parent;

	/**
	 <niobject name="NiAVObject" abstract="1" inherit="NiObjectNET">
	    Generic node object.
	    <add name="Flags" type="Flags" ver1="3.0">Some flags; commonly 0x000C or 0x000A.</add>
	    <add name="Unknown Short 1" type="ushort" default="8" ver1="20.2.0.7" vercond="(User Version >= 11) &amp;&amp; (User Version 2 > 26)" >Unknown Flag</add>
	    <add name="Translation" type="Vector3">The translation vector.</add>
	    <add name="Rotation" type="Matrix33">The rotation part of the transformation matrix.</add>
	    <add name="Scale" type="float" default="1.0">Scaling part (only uniform scaling is supported).</add>
	    <add name="Velocity" type="Vector3" ver2="4.2.2.0">Unknown function. Always seems to be (0, 0, 0)</add>
	    <add name="Num Properties" type="uint" vercond="((Version &lt; 20.2.0.7) || (User Version &lt;= 11))">The number of property objects referenced.</add>
	    <add name="Properties" type="Ref" template="NiProperty" arr1="Num Properties" vercond="((Version &lt; 20.2.0.7) || (User Version &lt;= 11))">List of node properties.</add>
	    <add name="Unknown 1" type="uint" arr1="4" ver2="2.3">Always 2,0,2,0.</add>
	    <add name="Unknown 2" type="byte" ver2="2.3">0 or 1.</add>
	    <add name="Has Bounding Box" type="bool" ver1="3.0" ver2="4.2.2.0">Do we have a bounding box?</add>
	    <add name="Bounding Box" type="BoundingBox" cond="Has Bounding Box" ver1="3.0" ver2="4.2.2.0">The bounding box.</add>
	    <add name="Collision Object" type="Ref" template="NiCollisionObject" ver1="10.0.1.0">Refers to NiCollisionObject, which is usually a bounding box or other simple collision shape.  In Oblivion this links the Havok objects.</add>
	</niobject>

	 */

	public NifFlags flags;

	public short unknownShort1;

	public NifVector3 translation;

	public NifMatrix33 rotation;

	public float scale = 1;

	public NifVector3 velocity;

	public int numProperties = 0;

	public NifRef[] properties;

	public boolean hasBoundingBox;

	public NifBoundingBox boundingBox;

	public NifRef collisionObject;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		flags = new NifFlags(stream);
		if ((nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && (nifVer.LOAD_USER_VER >= 11 && nifVer.LOAD_USER_VER2 > 26))
				|| (nifVer.LOAD_VER == NifVer.VER_20_3_0_9 && nifVer.LOAD_USER_VER > 6))
		{
			unknownShort1 = ByteConvert.readShort(stream);
		}
		translation = new NifVector3(stream);
		rotation = new NifMatrix33(stream);
		scale = ByteConvert.readFloat(stream);
		if (nifVer.LOAD_VER <= NifVer.VER_4_2_2_0)
		{
			velocity = new NifVector3(stream);
		}

		if (nifVer.LOAD_VER < NifVer.VER_20_2_0_7 || nifVer.LOAD_USER_VER <= 11 || nifVer.LOAD_VER == NifVer.VER_20_3_0_9)
		{
			numProperties = ByteConvert.readInt(stream);
			properties = new NifRef[numProperties];
			for (int i = 0; i < numProperties; i++)
			{
				properties[i] = new NifRef(NiProperty.class, stream);
			}
		}

		if (nifVer.LOAD_VER <= NifVer.VER_4_2_2_0)
		{
			hasBoundingBox = ByteConvert.readBool(stream, nifVer);
			if (hasBoundingBox)
			{
				boundingBox = new NifBoundingBox(stream);
			}

		}
		if (nifVer.LOAD_VER >= NifVer.VER_10_0_1_0)
		{
			collisionObject = new NifRef(NiCollisionObject.class, stream);
		}

		return success;
	}

	public void addDisplayRows(ArrayList<Object[]> list)
	{
		super.addDisplayRows(list);

		list.add(new Object[]
		{ "NiAVObject", "flags", "" + flags });
		list.add(new Object[]
		{ "NiAVObject", "controller", "" + controller });

		if ((nVer.LOAD_VER >= NifVer.VER_20_2_0_7 && (nVer.LOAD_USER_VER >= 11 && nVer.LOAD_USER_VER2 > 26))
				|| (nVer.LOAD_VER == NifVer.VER_20_3_0_9 && nVer.LOAD_USER_VER > 6))
		{
			list.add(new Object[]
			{ "NiAVObject", "unknownShort1", "" + unknownShort1 });

		}
		list.add(new Object[]
		{ "NiAVObject", "translation", "" + translation });
		list.add(new Object[]
		{ "NiAVObject", "rotation", "" + rotation });
		list.add(new Object[]
		{ "NiAVObject", "scale", "" + scale });

		if (nVer.LOAD_VER < NifVer.VER_20_2_0_7 || nVer.LOAD_USER_VER <= 11 || nVer.LOAD_VER == NifVer.VER_20_3_0_9)
		{
			list.add(new Object[]
			{ "NiAVObject", "numProperties", "" + numProperties });
			list.add(new Object[]
			{ "NiAVObject", "properties", "ArrayOf NifRef " + properties.length });

		}
		list.add(new Object[]
		{ "NiAVObject", "collisionObject", "" + collisionObject });

	}

}