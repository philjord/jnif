package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

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
		
		//<member name='meshTree' type='struct hknpCompressedMeshShapeTree' ctype='hknpCompressedMeshShapeTree' offset='16' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		meshTree = new hknpCompressedMeshShapeTree(connector, stream, classOffset + 16);
		
		//<member name='simdTree' type='struct hkcdSimdTree' ctype='hkcdSimdTree' offset='176' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		simdTree = new hkcdSimdTree(connector, stream, classOffset + 176);
		return success;
	}
}