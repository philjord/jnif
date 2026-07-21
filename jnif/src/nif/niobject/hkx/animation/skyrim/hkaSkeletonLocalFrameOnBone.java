package nif.niobject.hkx.animation.skyrim;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkaSkeletonLocalFrameOnBone' version='0' signature='0xe9151edc'>
	<members>
		<member name='localFrame' type='struct hkLocalFrame*' ctype='hkLocalFrame' offset='0' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='boneIndex' type='hkInt16' offset='8' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hkaSkeletonLocalFrameOnBone {
	public static final int	size	= 8 + 2;
	public long				localFrame;
	public int				boneIndex;

	public hkaSkeletonLocalFrameOnBone(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {

		localFrame = HKXReader.getPointer(connector, classOffset + 0);
		boneIndex = stream.getShort(classOffset + 8);
	}

}