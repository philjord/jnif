package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdStaticMeshTreeBasePrimitiveDataRunBasehknpCompressedMeshShapeTreeDataRunData' version='0' signature='0xad836282'>
	<members>
		<member name='value' type='struct hknpCompressedMeshShapeTreeDataRunData' ctype='hknpCompressedMeshShapeTreeDataRunData' offset='0' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='index' type='hkUint8' offset='2' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='count' type='hkUint8' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/
public class hkcdStaticMeshTreeBasePrimitiveDataRunBasehknpCompressedMeshShapeTreeDataRunData {
	public hknpCompressedMeshShapeTreeDataRunData value;
	public int index;
	public int count;
	
	public hkcdStaticMeshTreeBasePrimitiveDataRunBasehknpCompressedMeshShapeTreeDataRunData(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{					
		//<member name='value' type='struct hknpCompressedMeshShapeTreeDataRunData' ctype='hknpCompressedMeshShapeTreeDataRunData' offset='0' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		value = new hknpCompressedMeshShapeTreeDataRunData(connector, stream, classOffset + 0);
		//<member name='index' type='hkUint8' offset='2' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		index = Byte.toUnsignedInt(stream.get(classOffset + 2));
		//<member name='count' type='hkUint8' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		count = Byte.toUnsignedInt(stream.get(classOffset + 3));
	}
}