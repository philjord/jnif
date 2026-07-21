package nif.niobject.hkx.animation.skyrim;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkaSkeletonPartition' version='1' signature='0x7c8e6a55'>
	<members>
		<member name='name' type='hkStringPtr' offset='0' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='startBoneIndex' type='hkInt16' offset='8' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='numBones' type='hkInt16' offset='10' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hkaSkeletonPartition {
	public static final int	size	= 10 + 2;
	public String			name;
	public int				startBoneIndex;
	public int				numBones;

	public hkaSkeletonPartition(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		
		name = HKXReader.hkStringPtr(connector, classOffset + 0);
		startBoneIndex = stream.getShort(classOffset + 8);
		numBones = stream.getShort(classOffset + 10);
	}

}