package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagItem;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**<class name='hknpCompressedMeshShapeData' version='0' signature='0xa2bdfc59' parent='hkReferencedObject'>
	<members>
		<member name='meshTree' type='struct hknpCompressedMeshShapeTree' ctype='hknpCompressedMeshShapeTree' offset='16' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='simdTree' type='struct hkcdSimdTree' ctype='hkcdSimdTree' offset='176' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/
public class hknpCompressedMeshShapeData extends hkReferencedObject {
	public hknpCompressedMeshShapeTree meshTree;
	public hkcdSimdTree simdTree;
	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
		
		meshTree = new hknpCompressedMeshShapeTree(connector, stream, classOffset + 16);
		simdTree = new hkcdSimdTree(connector, stream, classOffset + 176);
		return success;
	}
	
 
	/** 
	
	Outline for Havok_TagItem of type hknpCompressedMeshShapeData
	Havok_TagType None
	Havok_TagType hkBaseObject
	Havok_TagType hkReferencedObject
	Havok_TagMember memSizeAndFlags of type hkUint16
	Havok_TagMember refCount of type hkUint16
	Havok_TagType hknpCompressedMeshShapeData
	Havok_TagMember meshTree of type hknpCompressedMeshShapeTree
	Havok_TagMember simdTree of type hkcdSimdTree
	Havok_TagMember connectivity of type hkcdStaticMeshTreeBase::Connectivity
	 */
	
	@Override
	public int readFromTAG0(Havok_TagItem item) {
		int memberIdx = super.readFromTAG0(item);
		//item.outputOutline();
		Havok_TagObject value0 = item.value.get(0);

		meshTree = new hknpCompressedMeshShapeTree(value0.listObjectClass.get(memberIdx++));
//FIXME:		simdTree = new hkcdSimdTree(value0.listObjectClass.get(memberIdx++));
		return memberIdx;
	}
}