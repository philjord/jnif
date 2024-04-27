package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdStaticTreeCodec3Axis4' version='0' signature='0xd168bc2f' parent='hkcdStaticTreeCodec3Axis'>
	<members>
		<member name='data' type='hkUint8' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hkcdStaticTreeCodec3Axis4 extends hkcdStaticTreeCodec3Axis {
	public static final int size = 4;
	public int data;
	public hkcdStaticTreeCodec3Axis4(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, stream, classOffset);
		
		//<member name='data' type='hkUint8' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		data = Byte.toUnsignedInt(stream.get(classOffset + 3));
	}
}