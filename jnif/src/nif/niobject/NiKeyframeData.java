package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifKeyGroup;
import nif.compound.NifQuatKey;
import nif.compound.NifVector3;
import nif.enums.KeyType;

public class NiKeyframeData extends NiObject
{
	/**
	 
	 <niobject name="NiKeyframeData" abstract="0" inherit="NiObject">

	 Keyframes for mesh animation.
	 
	 <add name="Num Rotation Keys" type="uint">The number of rotation keys.</add>
	 <add name="Rotation Type" type="KeyType" default="0" cond="Num Rotation Keys != 0">
	 The type of interpolation to use for rotation. 
	 Can also be 4 to indicate that separate X, Y, and Z values are used for the rotation instead of Quaternions.
	 </add>
	 <add name="Quaternion Keys" type="QuatKey" arg="Rotation Type" template="Quaternion" arr1="Num Rotation Keys" cond="Rotation Type != 4">
	 The rotation keys if Quaternion rotation is used.</add>
	 <add name="Unknown Float" type="float" cond="Rotation Type == 4" ver2="10.1.0.0">
	 Possibly a vestigial time value?  Doesn't appear to be significant.
	 </add>
	 <add name="XYZ Rotations" type="KeyGroup" template="float" arr1="3" cond="Rotation Type == 4">
	 Individual arrays of keys for rotating X, Y, and Z individually.
	 </add>
	 <add name="Translations" type="KeyGroup" template="Vector3">Translation keys.</add>
	 <add name="Scales" type="KeyGroup" template="float">Scale keys.</add>
	 </niobject>
	 
	 */

	public int numRotationKeys;

	public KeyType rotationType;

	public NifQuatKey[] quaternionKeys;
	
	public float unknownFloat;

	public NifKeyGroup[] xYZRotations;

	public NifKeyGroup translations;

	public NifKeyGroup scales;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numRotationKeys = ByteConvert.readInt(stream);

		if (numRotationKeys > 0)
		{
			rotationType = new KeyType(stream);
			if (rotationType.type != 4)
			{
				quaternionKeys = new NifQuatKey[numRotationKeys];
				for (int i = 0; i < numRotationKeys; i++)
				{
					quaternionKeys[i] = new NifQuatKey(rotationType, stream);
				}
			}
			if (rotationType.type == 4)
			{
				if (nifVer.LOAD_VER <= NifVer.VER_10_1_0_0)
				{
					unknownFloat= ByteConvert.readFloat(stream);
				}
				
				xYZRotations = new NifKeyGroup[3];
				for (int i = 0; i < 3; i++)
				{
					xYZRotations[i] = new NifKeyGroup(Float.class, stream, nifVer);
				}
			}
		}
		translations = new NifKeyGroup(NifVector3.class, stream, nifVer);
		scales = new NifKeyGroup(Float.class, stream, nifVer);

		return success;
	}
}