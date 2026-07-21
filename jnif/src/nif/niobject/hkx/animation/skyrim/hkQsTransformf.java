package nif.niobject.hkx.animation.skyrim;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.compound.NifQuaternionXYZW;
import nif.compound.NifVector4;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkQTransformf' version='0' signature='0x471a21ee'>
	<members>
		<member name='rotation' type='hkQuaternion' offset='0' vtype='TYPE_QUATERNION' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='translation' type='hkVector4' offset='16' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hkQsTransformf {
	public static final int		size	= 16 + 16;
	public NifQuaternionXYZW	rotation;
	public NifVector4			translation;

	public hkQsTransformf(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		rotation = new NifQuaternionXYZW(stream, classOffset + 0);
		translation = new NifVector4(stream, classOffset + 16);

	}

}