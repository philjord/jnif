package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**<struct name='hknpCompressedMeshShapeTreeDataRunData' version='0' signature='0xc253682b'>
	<members>
		<member name='data' type='hkUint16' offset='0' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hknpCompressedMeshShapeTreeDataRunData  {
	int data;
	public hknpCompressedMeshShapeTreeDataRunData(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{		
		data = Short.toUnsignedInt(stream.getShort(classOffset + 0));
	}
	
	/**
	 Outline for HHavok_TagType hknpCompressedMeshShapeTreeDataRunData
	Havok_TagMember data of type hkUint16
	 */
	public hknpCompressedMeshShapeTreeDataRunData(Havok_TagObject item) {
		//item.outputOutline();
		int memberIdx = 0;			
		data = item.listObjectClass.get(memberIdx++).i_value;
	}
}