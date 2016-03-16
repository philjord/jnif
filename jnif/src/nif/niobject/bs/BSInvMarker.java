package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiExtraData;

public class BSInvMarker extends NiExtraData
{
	/**
	    <niobject name="BSInvMarker" abstract="0" inherit="NiExtraData">
	    Orientation marker for Skyrim's inventory view.
	        How to show the nif in the player's inventory.
	        Typically attached to the root node of the nif tree.
	        If not present, then Skyrim will still show the nif in inventory,
	        using the default values.
	        Name should be 'INV' (without the quotes).
	        For rotations, a short of "4712" appears as "4.712" but "959" appears as "0.959"  meshes\weapons\daedric\daedricbowskinned.nif
	        <add name="Rotation X" type="ushort" default="4712"></add>
	        <add name="Rotation Y" type="ushort" default="6283"></add>
	        <add name="Rotation Z" type="ushort" default="0"></add>
	        <add name="Zoom" type="float" default="1.0">Zoom factor.</add>
	    </niobject>
	    */

	public short RotationX;

	public short RotationY;

	public short RotationZ;

	public float Zoom;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		RotationX = ByteConvert.readShort(stream);
		RotationY = ByteConvert.readShort(stream);
		RotationZ = ByteConvert.readShort(stream);
		Zoom = ByteConvert.readFloat(stream);

		return success;
	}
}
