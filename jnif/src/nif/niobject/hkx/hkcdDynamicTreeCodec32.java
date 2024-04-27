package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdDynamicTreeCodec32' version='0' signature='0x2d65e9f4'>
	<members>
		<member name='aabb' type='struct hkAabb' ctype='hkAabb' offset='0' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/
public class hkcdDynamicTreeCodec32 {	

	public static final int size = 32;
	hkAabb aabb;

	public hkcdDynamicTreeCodec32(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException {		
		//<member name='aabb' type='struct hkAabb' ctype='hkAabb' offset='0' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		aabb = new hkAabb(connector, stream, classOffset + 0);

		
	}

}