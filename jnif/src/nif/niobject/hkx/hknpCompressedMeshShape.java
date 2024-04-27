package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.DataExternal;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

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
		
		//<member name='data' type='struct hknpCompressedMeshShapeData*' ctype='hknpCompressedMeshShapeData' offset='96' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		DataExternal de = connector.data2.readNext();
		if (de.from == classOffset + 96) {
			data = de.to;
		} else {
			connector.data2.backtrack();
		}
				
		//<member name='quadIsFlat' type='struct hkBitField' ctype='hkBitField' offset='104' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		quadIsFlat = new hkBitField(connector, stream, classOffset + 104);
		//<member name='triangleIsInterior' type='struct hkBitField' ctype='hkBitField' offset='128' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		triangleIsInterior = new hkBitField(connector, stream, classOffset + 128);
		//<member name='numTriangles' type='hkInt32' offset='152' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
		numTriangles = stream.getInt(classOffset + 152);
		//<member name='numConvexShapes' type='hkInt32' offset='156' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
		numConvexShapes = stream.getInt(classOffset + 156);
		
		return success;
	}
	
}