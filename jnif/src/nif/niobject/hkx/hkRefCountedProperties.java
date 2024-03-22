package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkRefCountedProperties' version='1' signature='0x7c574867'>
	<enums>
		<enum name='ReferenceCountHandling' flags='00000000'>
			<enumitem name='REFERENCE_COUNT_INCREMENT' value='0'/>
			<enumitem name='REFERENCE_COUNT_IGNORE' value='1'/>
		</enum>
	</enums>
	<members>
		<member name='entries' type='hkArray&lt;struct hkRefCountedPropertiesEntry&gt;' ctype='hkRefCountedPropertiesEntry' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
 

public class hkRefCountedProperties  extends hkBaseObject {
	@Override
	public boolean readFromStream(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException {
		
		//<member name='entries' type='hkArray&lt;struct hkRefCountedPropertiesEntry&gt;' ctype='hkRefCountedPropertiesEntry' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		ByteBuffer file = connector.data.setup(classOffset + 0);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 0;
			hkRefCountedPropertiesEntry[] entries = new hkRefCountedPropertiesEntry[arrSize];
			for (int i = 0; i < arrSize; i++) {
				entries[i] = new hkRefCountedPropertiesEntry(connector, (int)arrValue.to + (i*hkRefCountedPropertiesEntry.size));
			}
		}
		
		return true;
	}
}