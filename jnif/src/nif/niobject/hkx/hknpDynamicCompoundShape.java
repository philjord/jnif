package nif.niobject.hkx;

import java.io.IOException;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<class name='hknpDynamicCompoundShape' version='1' signature='0x4620d11c' parent='hknpCompoundShape'>
	<members>
		<member name='boundingVolumeData' type='struct hknpDynamicCompoundShapeData*' ctype='hknpDynamicCompoundShapeData' offset='192' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>*/

public class hknpDynamicCompoundShape extends hknpCompoundShape {

	hknpDynamicCompoundShapeData boundingVolumeData;
	
	@Override
	public boolean readFromStream(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, classOffset);
		
		boundingVolumeData = new hknpDynamicCompoundShapeData();
		boundingVolumeData.readFromStream(connector, classOffset + 192);

		return success;
	}
}