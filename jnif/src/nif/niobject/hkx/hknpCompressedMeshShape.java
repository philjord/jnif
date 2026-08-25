package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagItem;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**<class name='hknpCompressedMeshShape' version='5' signature='0x5f60d536' parent='hknpCompositeShape'>
	<members>
		<member name='data' type='struct hknpCompressedMeshShapeData*' ctype='hknpCompressedMeshShapeData' offset='96' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='quadIsFlat' type='struct hkBitField' ctype='hkBitField' offset='104' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='triangleIsInterior' type='struct hkBitField' ctype='hkBitField' offset='128' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='numTriangles' type='hkInt32' offset='152' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
		<member name='numConvexShapes' type='hkInt32' offset='156' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
	</members>
</class>
*/
public class hknpCompressedMeshShape extends hknpCompositeShape {
	public long data;
	hkBitField quadIsFlat;
	hkBitField triangleIsInterior;
	int numTriangles;
	int numConvexShapes;
	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
		
		data = HKXReader.getPointer(connector, classOffset + 96);	
		quadIsFlat = new hkBitField(connector, stream, classOffset + 104);
		triangleIsInterior = new hkBitField(connector, stream, classOffset + 128);
		numTriangles = stream.getInt(classOffset + 152);
		numConvexShapes = stream.getInt(classOffset + 156);
		
		return success;
	}
	
	
	

	/** 
	
	Outline for Havok_TagType hknpCompressedMeshShape
	Havok_TagMember data of type hkRefPtr
	Havok_TagMember triangleIsInterior of type hkBitField
	Havok_TagMember numTriangles of type int
	Havok_TagMember numConvexShapes of type int
	 */
	
	@Override
	public int readFromTAG0(Havok_TagItem item) {
		int memberIdx = super.readFromTAG0(item);
		//item.outputOutline();
		Havok_TagObject value0 = item.value.get(0);

		data = TAG0Reader.getRefPtr(value0.listObjectClass.get(memberIdx++));
		triangleIsInterior  = new hkBitField(value0.listObjectClass.get(memberIdx++));
		numTriangles = value0.listObjectClass.get(memberIdx++).i_value;
		numConvexShapes  = value0.listObjectClass.get(memberIdx++).i_value;
		
		return memberIdx;
	}
}