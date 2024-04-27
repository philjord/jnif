package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hknpBSMaterialProperties' version='0' signature='0xa3e47a9a' parent='hkReferencedObject'>
	<members>
		<member name='MaterialA' type='hkArray&lt;struct hknpBSMaterial&gt;' ctype='hknpBSMaterial' offset='16' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/
public class hknpBSMaterialProperties extends hkReferencedObject {
	hknpBSMaterial[] MaterialA;
	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
		
		//<member name='MaterialA' type='hkArray&lt;struct hknpBSMaterial&gt;' ctype='hknpBSMaterial' offset='16' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		ByteBuffer file = connector.data.setup(classOffset + 16);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 16;
			MaterialA = new hknpBSMaterial[arrSize];
			for (int i = 0; i < arrSize; i++) {
				MaterialA[i] = new hknpBSMaterial();
				MaterialA[i].readFromStream(connector, stream, (int)arrValue.to);
			}
		}		
		
		return success;
	}
}