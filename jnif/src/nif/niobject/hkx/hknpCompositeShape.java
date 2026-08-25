package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagItem;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**<class name='hknpCompositeShape' version='0' signature='0x12bb3bef' parent='hknpShape'>
	<members>
		<member name='edgeWeldingMap' type='struct hknpSparseCompactMapunsignedshort' ctype='hknpSparseCompactMapunsignedshort' offset='48' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='shapeTagCodecInfo' type='hkUint32' offset='88' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/
public class hknpCompositeShape extends hknpShape {
	hknpSparseCompactMapunsignedshort edgeWeldingMap;
	int secondaryKeyMask;
	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
		
		edgeWeldingMap = new hknpSparseCompactMapunsignedshort(connector, stream, classOffset + 48);		
		secondaryKeyMask = stream.getInt(classOffset + 88);		
		
		return success;
		
	}
	
	
	
	
	/** 	
	Outline for Havok_TagType hknpCompositeShape
	Havok_TagMember edgeWeldingMap of type hknpSparseCompactMap
	Havok_TagMember shapeTagCodecInfo of type hkUint32
	Havok_TagMember materialTable of type hkRefPtr	
	 */
	int shapeTagCodecInfo;
	long materialTable;

	@Override
	public int readFromTAG0(Havok_TagItem item) {
		int memberIdx = super.readFromTAG0(item);
		//item.outputOutline();
		Havok_TagObject value0 = item.value.get(0);
		edgeWeldingMap = new hknpSparseCompactMapunsignedshort(value0.listObjectClass.get(memberIdx++));		 
		shapeTagCodecInfo = value0.listObjectClass.get(memberIdx++).i_value;
		materialTable = value0.listObjectClass.get(memberIdx++).i_value;		

		return memberIdx;
	}
}