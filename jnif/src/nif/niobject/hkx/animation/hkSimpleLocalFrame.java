package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.compound.NifMatrix44;
import nif.niobject.hkx.hkReferencedObject;
import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkSimpleLocalFrame' version='1' signature='0x798adf92' parent='hkLocalFrame'>
	<members>
		<member name='transform' type='hkTransform' offset='16' vtype='TYPE_TRANSFORM' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='children' type='hkArray&lt;hkLocalFrame*&gt;' ctype='hkLocalFrame' offset='80' vtype='TYPE_ARRAY' vsubtype='TYPE_POINTER' arrsize='0' flags='FLAGS_NONE'/>
		<member name='parentFrame' type='struct hkLocalFrame*' ctype='hkLocalFrame' offset='96' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='NOT_OWNED'/>
		<member name='group' type='struct hkLocalFrameGroup*' ctype='hkLocalFrameGroup' offset='104' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='name' type='hkStringPtr' offset='112' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hkSimpleLocalFrame extends hkReferencedObject {
	public NifMatrix44	transform;
	public long[]		children;
	public long			parentFrame;
	public long			group;
	public String		name;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		transform = new NifMatrix44(stream, classOffset + 16);

		ByteBuffer file = connector.data.setup(classOffset + 80);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 16;
			children = new long[arrSize];
			for (int i = 0; i < arrSize; i++) {
				long contentsPosition = arrValue.to + (i * 0x08);//size of pointers
				children[i] = HKXReader.getPointer(connector, contentsPosition);
			}
		}

		parentFrame = HKXReader.getPointer(connector, classOffset + 96);
		group = HKXReader.getPointer(connector, classOffset + 104);
		name = HKXReader.hkStringPtr(connector, classOffset + 112);

		return success;
	}

}