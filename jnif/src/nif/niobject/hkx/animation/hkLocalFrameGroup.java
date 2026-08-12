package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.hkReferencedObject;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkLocalFrameGroup' version='0' signature='0x41be9bba' parent='hkReferencedObject'>
	<members>
		<member name='name' type='hkStringPtr' offset='16' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hkLocalFrameGroup extends hkReferencedObject {

	public String name;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
		if (connector.header.is64bit) {
			name = HKXReader.hkStringPtr(connector, classOffset + 16);
		} else {
			name = HKXReader.hkStringPtr(connector, classOffset + 8);
		}

		return success;
	}

}