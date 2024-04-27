package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<class name='hkReferencedObject' version='0' signature='0xb70c7949' parent='hkBaseObject'>
	<members>
		<member name='memSizeAndRefCount' type='hkUint32' offset='8' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
	</members>
</class>
*/
public class hkReferencedObject extends hkBaseObject {
	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{
		boolean success = super.readFromStream(connector, stream, classOffset);

		return success;
	}
	
}