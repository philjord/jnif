package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<class name='hknpCompositeShape' version='0' signature='0x12bb3bef' parent='hknpShape'>
	<members>
		<member name='edgeWeldingMap' type='struct hknpSparseCompactMapunsignedshort' ctype='hknpSparseCompactMapunsignedshort' offset='48' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='shapeTagCodecInfo' type='hkUint32' offset='88' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/
public class hknpCompositeShape extends hknpShape {
	hknpSparseCompactMapunsignedshort edgeWeldingMap;
	int secondaryKeyMask;
	@Override
	public boolean readFromStream(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, classOffset);
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		
		//<member name='edgeWeldingMap' type='struct hknpSparseCompactMapunsignedshort' ctype='hknpSparseCompactMapunsignedshort' offset='48' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		edgeWeldingMap = new hknpSparseCompactMapunsignedshort(connector, classOffset + 48);		
		//<member name='shapeTagCodecInfo' type='hkUint32' offset='88' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		secondaryKeyMask = stream.getInt(88);		
		
		return success;
		
	}
}