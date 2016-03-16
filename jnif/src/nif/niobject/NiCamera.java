package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;

public class NiCamera extends NiAVObject
{
	/**	 
	 <niobject name="NiCamera" abstract="0" inherit="NiAVObject">

	 Camera object.
	 
	 <add name="Unknown Short" type="ushort" ver1="10.1.0.0">Unknown.</add>
	 <add name="Frustum Left" type="float">Frustrum left.</add>
	 <add name="Frustum Right" type="float">Frustrum right.</add>
	 <add name="Frustum Top" type="float">Frustrum top.</add>
	 <add name="Frustum Bottom" type="float">Frustrum bottom.</add>
	 <add name="Frustum Near" type="float">Frustrum near.</add>
	 <add name="Frustum Far" type="float">Frustrum far.</add>
	 <add name="Use Orthographic Projection" type="bool" ver1="10.1.0.0">
	 Determines whether perspective is used.  Orthographic means no perspective.
	 </add>
	 <add name="Viewport Left" type="float">Viewport left.</add>
	 <add name="Viewport Right" type="float">Viewport right.</add>
	 <add name="Viewport Top" type="float">Viewport top.</add>
	 <add name="Viewport Bottom" type="float">Viewport bottom.</add>
	 <add name="LOD Adjust" type="float">Level of detail adjust.</add>
	 <add name="Unknown Link" type="Ref" template="NiObject">Unknown.</add>
	 <add name="Unknown Int" type="uint">Unknown.  Changing value crashes viewer.</add>
	 <add name="Unknown Int 2" type="uint" ver1="4.2.1.0">Unknown.  Changing value crashes viewer.</add>
	 <add name="Unknown Int 3" type="uint" ver2="3.1">Unknown.</add>
	 </niobject>
	 */

	public short unknownShort;

	public float frustumLeft;

	public float frustumRight;

	public float frustumTop;

	public float frustumBottom;

	public float frustumNear;

	public float frustumFar;

	public boolean useOrthographicProjection;

	public float viewportLeft;

	public float viewportRight;

	public float viewportTop;

	public float viewportBottom;

	public float lODAdjust;

	public NifRef unknownLink;

	public int unknownInt;

	public int unknownInt2;

	public int unknownInt3;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0)
		{
			unknownShort = ByteConvert.readShort(stream);
		}
		frustumLeft = ByteConvert.readFloat(stream);
		frustumRight = ByteConvert.readFloat(stream);
		frustumTop = ByteConvert.readFloat(stream);
		frustumBottom = ByteConvert.readFloat(stream);
		frustumNear = ByteConvert.readFloat(stream);
		frustumFar = ByteConvert.readFloat(stream);
		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0)
		{
			useOrthographicProjection = ByteConvert.readBool(stream, nifVer);
		}
		viewportLeft = ByteConvert.readFloat(stream);
		viewportRight = ByteConvert.readFloat(stream);
		viewportTop = ByteConvert.readFloat(stream);
		viewportBottom = ByteConvert.readFloat(stream);
		lODAdjust = ByteConvert.readFloat(stream);
		unknownLink = new NifRef(NiObject.class, stream);
		unknownInt = ByteConvert.readInt(stream);
		if (nifVer.LOAD_VER >= NifVer.VER_4_2_1_0)
		{
			unknownInt2 = ByteConvert.readInt(stream);
		}
		if (nifVer.LOAD_VER <= NifVer.VER_3_1)
		{
			unknownInt3 = ByteConvert.readInt(stream);
		}

		return success;
	}
}