package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hknpDecoratorShape' version='0' signature='0x19ac37e2' parent='hknpShape'>
	<members>
		<member name='coreShape' type='struct hknpShape*' ctype='hknpShape' offset='48' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='coreShapeSize' type='hkInt32' offset='56' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hknpDecoratorShape extends hknpShape {

	public long	coreShape;
	public int	coreShapeSize;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		//<member name='coreShape' type='struct hknpShape*' ctype='hknpShape' offset='48' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		coreShape = HKXReader.getPointer(connector, classOffset + 48);
		//<member name='coreShapeSize' type='hkInt32' offset='56' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		coreShapeSize = stream.getInt(classOffset + 56);

		return success;
	}
}