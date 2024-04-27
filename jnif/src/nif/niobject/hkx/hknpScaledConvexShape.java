package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

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
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
				
		//<member name='mutationSignals' type='struct hknpShapeSignals' ctype='hknpShapeSignals' offset='96' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
		mutationSignals = new hknpShapeSignals(connector, stream, classOffset + 96);
		
		return success;
	}
}