package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkaSkeletonMapperDataPartitionMappingRange' version='0' signature='0xfdf54e87'>
	<members>
		<member name='startMappingIndex' type='hkInt32' offset='0' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='numMappings' type='hkInt32' offset='4' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hkaSkeletonMapperDataPartitionMappingRange {
	public static final int	size	= 4 + 4;
	public static final int	size32	= 4 + 4;

	public int				startMappingIndex;
	public int				numMappings;

	public hkaSkeletonMapperDataPartitionMappingRange(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		//64 and 32 the same
		startMappingIndex = stream.getShort(classOffset + 0);
		numMappings = stream.getShort(classOffset + 4);
	}

}