package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hknpStaticCompoundShape' version='1' signature='0x4620d11c' parent='hknpCompoundShape'>
	<members>
		<member name='boundingVolumeData' type='struct hknpStaticCompoundShapeData*' ctype='hknpStaticCompoundShapeData' offset='192' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hknpStaticCompoundShape extends hknpCompoundShape {

	public long boundingVolumeData;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		//<member name='boundingVolumeData' type='struct hknpStaticCompoundShapeData*' ctype='hknpStaticCompoundShapeData' offset='192' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		boundingVolumeData = HKXReader.getPointer(connector, classOffset + 192);
		return success;
	}
}