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
	
	public hkcdStaticMeshTreeBasePrimitiveDataRunBasehknpCompressedMeshShapeTreeDataRunData(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{		 
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);
		
		//<member name='value' type='struct hknpCompressedMeshShapeTreeDataRunData' ctype='hknpCompressedMeshShapeTreeDataRunData' offset='0' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		value = new hknpCompressedMeshShapeTreeDataRunData(connector, classOffset + 0);
		//<member name='index' type='hkUint8' offset='2' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		index = Byte.toUnsignedInt(stream.get(2));
		//<member name='count' type='hkUint8' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		count = Byte.toUnsignedInt(stream.get(3));
	}
}