package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hknpCompressedMeshShapeTreeDataRunData' version='0' signature='0xc253682b'>
	<members>
		<member name='data' type='hkUint16' offset='0' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hknpCompressedMeshShapeTreeDataRunData  {
	int data;
	public hknpCompressedMeshShapeTreeDataRunData(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{		
		//<member name='data' type='hkUint16' offset='0' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		data = Short.toUnsignedInt(stream.getShort(classOffset + 0));
	}
}