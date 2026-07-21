package nif.niobject.hkx.animation.skyrim;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkMemoryResourceHandle' version='3' signature='0xc64e9bc2' parent='hkResourceHandle'>
	<members>
		<member name='variant' type='struct hkReferencedObject*' ctype='hkReferencedObject' offset='16' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='name' type='hkStringPtr' offset='24' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='references' type='hkArray&lt;struct hkMemoryResourceHandleExternalLink&gt;' ctype='hkMemoryResourceHandleExternalLink' offset='32' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hkMemoryResourceHandle extends hkResourceHandle {

	public long									variant;
	public String								name;
	public hkMemoryResourceHandleExternalLink[]	references;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
		variant = HKXReader.getPointer(connector, classOffset + 16);
		name = HKXReader.hkStringPtr(connector, classOffset + 24);

		ByteBuffer file = connector.data.setup(classOffset + 32);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 32;
			references = new hkMemoryResourceHandleExternalLink[arrSize];
			for (int i = 0; i < arrSize; i++) {
				references[i] = new hkMemoryResourceHandleExternalLink(connector, stream,
						(int)arrValue.to + (i * hkMemoryResourceHandleExternalLink.size));
			}
		}

		return success;
	}

}