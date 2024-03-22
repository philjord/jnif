package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdStaticMeshTreeBasePrimitive' version='0' signature='0x56da2f7c'>
	<enums>
		<enum name='Type' flags='00000000'>
			<enumitem name='INVALID' value='0'/>
			<enumitem name='TRIANGLE' value='1'/>
			<enumitem name='QUAD' value='2'/>
			<enumitem name='CUSTOM' value='3'/>
			<enumitem name='NUM_TYPES' value='4'/>
		</enum>
	</enums>
	<members>
		<member name='indices' type='hkUint8[4]' offset='0' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='4' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hkcdStaticMeshTreeBasePrimitive {
	public static final int size = 4;
	public int[] indices = new int[4];
	public hkcdStaticMeshTreeBasePrimitive(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);
		//<member name='indices' type='hkUint8[4]' offset='0' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='4' flags='FLAGS_NONE'/>		
		indices[0] = Byte.toUnsignedInt(stream.get(0));
		indices[1] = Byte.toUnsignedInt(stream.get(1));
		indices[2] = Byte.toUnsignedInt(stream.get(2));
		indices[3] = Byte.toUnsignedInt(stream.get(3));		
		
	}
}