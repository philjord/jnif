package nif.niobject.hkx;

import java.io.IOException;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<class name='hknpDynamicCompoundShapeData' version='0' signature='0xf33dc3cc' parent='hkReferencedObject'>
	<members>
		<member name='aabbTree' type='struct hknpDynamicCompoundShapeTree' ctype='hknpDynamicCompoundShapeTree' offset='16' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>*/
public class hknpDynamicCompoundShapeData extends hkReferencedObject {

	hknpDynamicCompoundShapeTree aabbTree;
	
	@Override
	public boolean readFromStream(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		boolean success = super.readFromStream(connector, classOffset);
		//<member name='aabbTree' type='struct hknpDynamicCompoundShapeTree' ctype='hknpDynamicCompoundShapeTree' offset='16' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		aabbTree = new hknpDynamicCompoundShapeTree(connector, classOffset + 16);
		return success;
	}
}