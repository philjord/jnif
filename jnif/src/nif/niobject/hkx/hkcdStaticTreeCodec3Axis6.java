package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdStaticTreeCodec3Axis6' version='0' signature='0x569b5d82' parent='hkcdStaticTreeCodec3Axis'>
	<members>
		<member name='hiData' type='hkUint8' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='loData' type='hkUint16' offset='4' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hkcdStaticTreeCodec3Axis6 extends hkcdStaticTreeCodec3Axis {
	public static int size = 6;//4 plus 2
	
	int hiData;
	int loData;
	public hkcdStaticTreeCodec3Axis6(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, classOffset);
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);
		
		//<member name='hiData' type='hkUint8' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		hiData = Byte.toUnsignedInt(stream.get(3));
		//<member name='loData' type='hkUint16' offset='4' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		loData = Short.toUnsignedInt(stream.getShort(4));		
	}
}