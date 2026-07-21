package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.Data1Interface;
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

		ByteBuffer file = connector.data.setup(classOffset + 128);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 128;
			ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			boneToBodyMap = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				boneToBodyMap[i] = s2.getInt(i * 4);
			}
		}

		return success;
	}

}