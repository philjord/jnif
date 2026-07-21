package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.hkReferencedObject;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkResourceBase' version='0' signature='0x2344afe9' parent='hkReferencedObject'>
<enums>
	<enum name='Type' flags='00000000'>
		<enumitem name='TYPE_RESOURCE' value='0'/>
		<enumitem name='TYPE_CONTAINER' value='1'/>
	</enum>
</enums>
<members>
</members>
</class>
*/

public class hkResourceBase extends hkReferencedObject {
	

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);		

		return success;
	}

}