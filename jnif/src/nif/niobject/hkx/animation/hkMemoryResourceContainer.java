package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkMemoryResourceContainer' version='1' signature='0x1de13a73' parent='hkResourceContainer'>
	<members>
		<member name='name' type='hkStringPtr' offset='16' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='parent' type='struct hkMemoryResourceContainer*' ctype='hkMemoryResourceContainer' offset='24' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='SERIALIZE_IGNORED'/>
		<member name='resourceHandles' type='hkArray&lt;hkMemoryResourceHandle*&gt;' ctype='hkMemoryResourceHandle' offset='32' vtype='TYPE_ARRAY' vsubtype='TYPE_POINTER' arrsize='0' flags='FLAGS_NONE'/>
		<member name='children' type='hkArray&lt;hkMemoryResourceContainer*&gt;' ctype='hkMemoryResourceContainer' offset='48' vtype='TYPE_ARRAY' vsubtype='TYPE_POINTER' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hkMemoryResourceContainer extends hkResourceContainer {
	public String	name;
	public long		parent;
	public long[]	resourceHandles;
	public long[]	children;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
		name = HKXReader.hkStringPtr(connector, classOffset + 16);
		parent = HKXReader.getPointer(connector, classOffset + 24);

		ByteBuffer file = connector.data.setup(classOffset + 32);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 32;
			resourceHandles = new long[arrSize];
			for (int i = 0; i < arrSize; i++) {
				long contentsPosition = arrValue.to + (i * 0x08);//size of pointers
				resourceHandles[i] = HKXReader.getPointer(connector, contentsPosition);
			}
		}

		file = connector.data.setup(classOffset + 48);
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 48;
			children = new long[arrSize];
			for (int i = 0; i < arrSize; i++) {
				long contentsPosition = arrValue.to + (i * 0x08);//size of pointers
				children[i] = HKXReader.getPointer(connector, contentsPosition);
			}
		}

		return success;
	}

}