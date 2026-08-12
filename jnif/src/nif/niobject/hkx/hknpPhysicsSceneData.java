package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hknpPhysicsSceneData' version='1' signature='0x701ce72c' parent='hkReferencedObject'>
	<members>
		<member name='systemDatas' type='hkArray&lt;hknpPhysicsSystemData*&gt;' ctype='hknpPhysicsSystemData' offset='16' vtype='TYPE_ARRAY' vsubtype='TYPE_POINTER' arrsize='0' flags='FLAGS_NONE'/>
		<member name='worldCinfo' type='struct hknpRefWorldCinfo*' ctype='hknpRefWorldCinfo' offset='32' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/
public class hknpPhysicsSceneData extends hkReferencedObject {

	
	public long[] systemDatas;
	public long worldCinfo;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 16));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 16;
			systemDatas = new long[arrSize];
			for (int i = 0; i < arrSize; i++) {
				long contentsPosition = arrValue.to + (i * 0x08);//size of pointers
				systemDatas[i] = HKXReader.getPointer(connector, contentsPosition);				
			}
		}
		
		worldCinfo = HKXReader.getPointer(connector, classOffset + 32);
		return success;
	}

}