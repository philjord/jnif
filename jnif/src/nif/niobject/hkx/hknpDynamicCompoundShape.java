package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.DataExternal;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<class name='hknpDynamicCompoundShape' version='1' signature='0x4620d11c' parent='hknpCompoundShape'>
	<members>
		<member name='boundingVolumeData' type='struct hknpDynamicCompoundShapeData*' ctype='hknpDynamicCompoundShapeData' offset='192' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>*/

public class hknpDynamicCompoundShape extends hknpCompoundShape {

	public long boundingVolumeData;
	
	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
		
		DataExternal data = connector.data2.readNext();
		//<member name='boundingVolumeData' type='struct hknpDynamicCompoundShapeData*' ctype='hknpDynamicCompoundShapeData' offset='192' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		if (data.from == classOffset + 192) {
			boundingVolumeData = data.to;
		} else {
			connector.data2.backtrack();
		}

		return success;
	}
}