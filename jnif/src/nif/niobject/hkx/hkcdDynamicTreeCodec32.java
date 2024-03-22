package nif.niobject.hkx;

import java.io.IOException;

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

	public hkcdDynamicTreeCodec32(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException {
		//ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		
		//<member name='aabb' type='struct hkAabb' ctype='hkAabb' offset='0' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		aabb = new hkAabb(connector, classOffset + 0);

		
	}

}