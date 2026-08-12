package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkaSkeletonMapperDataChainMapping' version='0' signature='0xa528f7cf'>
	<members>
		<member name='startBoneA' type='hkInt16' offset='0' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='endBoneA' type='hkInt16' offset='2' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='startBoneB' type='hkInt16' offset='4' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='endBoneB' type='hkInt16' offset='6' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='startAFromBTransform' type='hkQsTransform' offset='16' vtype='TYPE_QSTRANSFORM' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='endAFromBTransform' type='hkQsTransform' offset='64' vtype='TYPE_QSTRANSFORM' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hkaSkeletonMapperDataChainMapping {
	public static final int	size	= 64 + hkQsTransform.size;
	public static final int	size32	= 64 + hkQsTransform.size32;

	public short			startBoneA;
	public short			endBoneA;
	public short			startBoneB;
	public short			endBoneB;
	public hkQsTransform	startAFromBTransform;
	public hkQsTransform	endAFromBTransform;

	public hkaSkeletonMapperDataChainMapping(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		//64 and 32 the same
		startBoneA = stream.getShort(classOffset + 0);
		endBoneA = stream.getShort(classOffset + 2);
		startBoneB = stream.getShort(classOffset + 4);
		endBoneB = stream.getShort(classOffset + 6);
		startAFromBTransform = new hkQsTransform(connector, stream, classOffset + 16);
		endAFromBTransform = new hkQsTransform(connector, stream, classOffset + 64);
	}

}