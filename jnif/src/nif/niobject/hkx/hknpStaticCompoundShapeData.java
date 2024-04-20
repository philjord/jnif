package nif.niobject.hkx;

import java.io.IOException;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<class name='hknpStaticCompoundShapeData' version='0' signature='0x4fd87fd9' parent='hkReferencedObject'>
	<members>
		<member name='aabbTree' type='struct hknpStaticCompoundShapeTree' ctype='hknpStaticCompoundShapeTree' offset='16' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>*/

public class hknpStaticCompoundShapeData extends hkReferencedObject {

	public hknpStaticCompoundShapeTree aabbTree;
	
	@Override
	public boolean readFromStream(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		boolean success = super.readFromStream(connector, classOffset);
		//<member name='aabbTree' type='struct hknpStaticCompoundShapeTree' ctype='hknpStaticCompoundShapeTree' offset='16' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		aabbTree = new hknpStaticCompoundShapeTree(connector, classOffset + 16);
		return success;
	}
}