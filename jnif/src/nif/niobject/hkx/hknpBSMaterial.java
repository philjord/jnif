package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<class name='hknpBSMaterial' version='0' signature='0xdc3a0091' parent='hkReferencedObject'>
	<members>
		<member name='uiFilterInfo' type='hkUint32' offset='16' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='uiMaterialCRC' type='hkUint32' offset='20' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>*/

public class hknpBSMaterial extends hkReferencedObject {
	int uiFilterInfo;
	int uiMaterialCRC;
	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
		
		//<member name='uiFilterInfo' type='hkUint32' offset='16' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		uiFilterInfo = stream.getInt(classOffset + 16);
		
		//<member name='uiMaterialCRC' type='hkUint32' offset='20' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		uiMaterialCRC = stream.getInt(classOffset + 20);
		
		return success;
	}
}