package nif.niobject.interpolator;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifQuaternion;
import nif.compound.NifVector3;
import nif.niobject.NiNode;

public class NiLookAtInterpolator extends NiInterpolator
{
	/**
	 
	 <niobject name="NiLookAtInterpolator" abstract="0" inherit="NiInterpolator" ver1="10.2.0.0">

	 Unknown.
	 
	 <add name="Unknown Short" type="ushort">Unknown.</add>
	 <add name="Look At" type="Ref" template="NiNode">Refers to a Node to focus on.</add>
	 <add name="Target" type="string">Target node name.</add>
	 <add name="Translation" type="Vector3">Translate.</add>
	 <add name="Rotation" type="Quaternion">Rotation.</add>
	 <add name="Scale" type="float">Scale.</add>
	 <add name="Unknown Link 1" type="Ref" template="NiPoint3Interpolator">Refers to NiPoint3Interpolator.</add>
	 <add name="Unknown Link 2" type="Ref" template="NiFloatInterpolator">Refers to a NiFloatInterpolator.</add>
	 <add name="Unknown Link 3" type="Ref" template="NiFloatInterpolator">Refers to a NiFloatInterpolator.</add>
	 </niobject>		
	 
	 */

	public short unknownShort;

	public NifRef lookAt;

	public String target;

	public NifVector3 translation;

	public NifQuaternion rotation;

	public float scale;

	public NifRef unknownLink1;

	public NifRef unknownLink2;

	public NifRef unknownLink3;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknownShort = ByteConvert.readShort(stream);
		lookAt = new NifRef(NiNode.class, stream);
		target = ByteConvert.readIndexString(stream, nifVer);
		translation = new NifVector3(stream);
		rotation = new NifQuaternion(stream);
		scale = ByteConvert.readFloat(stream);
		unknownLink1 = new NifRef(NiPoint3Interpolator.class, stream);
		unknownLink2 = new NifRef(NiFloatInterpolator.class, stream);
		unknownLink3 = new NifRef(NiFloatInterpolator.class, stream);

		return success;

	}
}