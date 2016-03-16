package nif.niobject.interpolator;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifQuaternion;
import nif.compound.NifVector3;
import nif.niobject.NiTransformData;

public class NiTransformInterpolator extends NiKeyBasedInterpolator
{
	/**
	 <niobject name="NiTransformInterpolator" abstract="0" inherit="NiKeyBasedInterpolator" ver1="10.2.0.0">

	 An interpolator for transform keyframes.
	 
	 <add name="Translation" type="Vector3">Translate.</add>
	 <add name="Rotation" type="Quaternion">Rotation.</add>
	 <add name="Scale" type="float">Scale.</add>
	 <add name="Unknown Bytes" type="byte" arr1="3" ver1="10.1.0.106" ver2="10.1.0.106">Unknown.</add>
	 <add name="Data" type="Ref" template="NiTransformData">Refers to NiTransformData.</add>
	 </niobject>
	 */

	public NifVector3 translation;

	public NifQuaternion rotation;

	public float scale = 1;

	public NifRef data;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		translation = new NifVector3(stream);
		rotation = new NifQuaternion(stream);
		scale = ByteConvert.readFloat(stream);
		if (nifVer.LOAD_VER == NifVer.VER_10_1_0_106)
		{
			ByteConvert.readBytes(3, stream);
		}
		data = new NifRef(NiTransformData.class, stream);

		return success;
	}
}