package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**
 * <struct name='hkcdStaticMeshTreeBasePrimitiveDataRunBasehknpCompressedMeshShapeTreeDataRunData' version='0' signature='0xad836282'>
	<members>
		<member name='value' type='struct hknpCompressedMeshShapeTreeDataRunData' ctype='hknpCompressedMeshShapeTreeDataRunData' offset='0' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='index' type='hkUint8' offset='2' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='count' type='hkUint8' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hkcdStaticMeshTreeBasePrimitiveDataRunBasehknpCompressedMeshShapeTreeDataRunData {
	public hknpCompressedMeshShapeTreeDataRunData	value;
	public int										index;
	public int										count;

	public hkcdStaticMeshTreeBasePrimitiveDataRunBasehknpCompressedMeshShapeTreeDataRunData(HKXReaderConnector connector,
																							ByteBuffer stream,
																							int classOffset)
			throws IOException, InvalidPositionException {
		value = new hknpCompressedMeshShapeTreeDataRunData(connector, stream, classOffset + 0);
		index = Byte.toUnsignedInt(stream.get(classOffset + 2));
		count = Byte.toUnsignedInt(stream.get(classOffset + 3));
	}

	/**
	 Outline for Havok_TagType hkcdStaticMeshTreeBase::PrimitiveDataRunBase
	Havok_TagMember value of type hknpCompressedMeshShapeTreeDataRunData
	Havok_TagMember index of type hkUint8
	Havok_TagMember count of type hkUint8
	 */
	public hkcdStaticMeshTreeBasePrimitiveDataRunBasehknpCompressedMeshShapeTreeDataRunData(Havok_TagObject item) {
		//item.outputOutline();

		int memberIdx = 0;	
		value = new hknpCompressedMeshShapeTreeDataRunData(item.listObjectClass.get(memberIdx++));
		index = item.listObjectClass.get(memberIdx++).i_value;
		count = item.listObjectClass.get(memberIdx++).i_value;
	}
}