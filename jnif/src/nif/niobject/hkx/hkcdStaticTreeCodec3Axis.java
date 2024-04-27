package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdStaticTreeCodec3Axis' version='0' signature='0x4ad23f31'>
	<members>
		<member name='xyz' type='hkUint8[3]' offset='0' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='3' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hkcdStaticTreeCodec3Axis {
	public int[] xyz = new int[3];
	public hkcdStaticTreeCodec3Axis(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{		
		//<member name='xyz' type='hkUint8[3]' offset='0' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='3' flags='FLAGS_NONE'/>		
		xyz[0] = Byte.toUnsignedInt(stream.get(classOffset + 0));
		xyz[1] = Byte.toUnsignedInt(stream.get(classOffset + 1));
		xyz[2] = Byte.toUnsignedInt(stream.get(classOffset + 2));
		
	}
}
