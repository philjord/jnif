package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdSimdTreeNode' version='0' signature='0xc4e406c7' parent='hkcdFourAabb'>
	<enums>
		<enum name='Flags' flags='00000000'>
			<enumitem name='HAS_INTERNALS' value='1'/>
			<enumitem name='HAS_LEAVES' value='2'/>
			<enumitem name='HAS_NULLS' value='4'/>
		</enum>
	</enums>
	<members>
		<member name='data' type='hkUint32[4]' offset='96' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='4' flags='FLAGS_NONE'/>
	</members>
</struct>*/
public class hkcdSimdTreeNode extends hkcdFourAabb {
	public static final int size = 112;
	int[] data = new int[4];
	public hkcdSimdTreeNode(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, stream, classOffset);		
		
		//<member name='data' type='hkUint32[4]' offset='96' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='4' flags='FLAGS_NONE'/>		
		data[0] = stream.getInt(classOffset + 96);
		data[1] = stream.getInt(classOffset + 100);
		data[2] = stream.getInt(classOffset + 104);
		data[3] = stream.getInt(classOffset + 108);
	}
}