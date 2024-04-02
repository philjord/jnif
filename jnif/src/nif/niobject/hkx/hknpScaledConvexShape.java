package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hknpScaledConvexShape' version='0' signature='0x0f14e5f2' parent='hknpScaledConvexShapeBase'>
	<members>
		<member name='mutationSignals' type='struct hknpShapeSignals' ctype='hknpShapeSignals' offset='96' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
	</members>
</class>
*/


public class hknpScaledConvexShape extends hknpScaledConvexShapeBase {

	public hknpShapeSignals mutationSignals;
	@Override
	public boolean readFromStream(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, classOffset);
		
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		
		//<member name='mutationSignals' type='struct hknpShapeSignals' ctype='hknpShapeSignals' offset='96' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
		mutationSignals = new hknpShapeSignals(connector, classOffset + 96);
		
		return success;
	}
}