package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.DataExternal;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
/**
<struct name='hkRefCountedPropertiesEntry' version='0' signature='0x28ef93ed'>
	<members>
		<member name='object' type='struct hkReferencedObject*' ctype='hkReferencedObject' offset='0' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='key' type='hkUint16' offset='8' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='flags' type='hkUint16' offset='10' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hkRefCountedPropertiesEntry  {
	
	public static final int size = 12;
	public long object;
	public int key;
	public int flags;
	
	public hkRefCountedPropertiesEntry(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);
		
		//<member name='object' type='struct hkReferencedObject*' ctype='hkReferencedObject' offset='0' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		DataExternal data = connector.data2.readNext();
		if (data.from == 0 + classOffset) {
			object = data.to;
		} else {
			connector.data2.backtrack();
		}
		//<member name='key' type='hkUint16' offset='8' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		key = Short.toUnsignedInt(stream.getShort(8));
		//<member name='flags' type='hkUint16' offset='10' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		flags = Short.toUnsignedInt(stream.getShort(10));
	}
}