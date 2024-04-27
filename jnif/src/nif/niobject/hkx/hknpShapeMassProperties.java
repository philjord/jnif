package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<class name='hknpShapeMassProperties' version='0' signature='0xe9191728' parent='hkReferencedObject'>
	<members>
		<member name='compressedMassProperties' type='struct hkCompressedMassProperties' ctype='hkCompressedMassProperties' offset='16' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='ALIGN_8'/>
	</members>
</class>*/

public class hknpShapeMassProperties extends hkReferencedObject {
	hkCompressedMassProperties compressedMassProperties;
	
	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
				
		//<member name='compressedMassProperties' type='struct hkCompressedMassProperties' ctype='hkCompressedMassProperties' offset='16' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='ALIGN_8'/>
		compressedMassProperties = new hkCompressedMassProperties(connector, stream, classOffset + 16);
		
		return success;
	}
}