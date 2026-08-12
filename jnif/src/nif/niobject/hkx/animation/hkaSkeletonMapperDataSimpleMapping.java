package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkaSkeletonMapperDataSimpleMapping' version='0' signature='0x3405deca'>
	<members>
		<member name='boneA' type='hkInt16' offset='0' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='boneB' type='hkInt16' offset='2' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='aFromBTransform' type='hkQsTransform' offset='16' vtype='TYPE_QSTRANSFORM' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hkaSkeletonMapperDataSimpleMapping {
	public static final int	size	= 16 + hkQsTransform.size;
	public static final int	size32	= 16 + hkQsTransform.size32;

	public short			boneA;
	public short			boneB;
	public hkQsTransform	aFromBTransform;

	public hkaSkeletonMapperDataSimpleMapping(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		//64 and 32 the same
		boneA = stream.getShort(classOffset + 0);
		boneB = stream.getShort(classOffset + 2);
		aFromBTransform = new hkQsTransform(connector, stream, classOffset + 16);
	}

}