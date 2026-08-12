package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hknpRagdollData' version='0' signature='0xdc8f20ab' parent='hknpPhysicsSystemData'>
	<members>
		<member name='skeleton' type='struct hkaSkeleton*' ctype='hkaSkeleton' offset='120' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='boneToBodyMap' type='hkArray&lt;hkInt32&gt;' offset='128' vtype='TYPE_ARRAY' vsubtype='TYPE_INT32' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hknpRagdollData extends hknpPhysicsSystemData {

	public long		skeleton;
	public int[]	boneToBodyMap;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
		skeleton = HKXReader.getPointer(connector, classOffset + 120);

		int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 128));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 128;
			boneToBodyMap = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				boneToBodyMap[i] = stream.getInt((int)arrValue.to + (i * 4));
			}
		}

		return success;
	}

}