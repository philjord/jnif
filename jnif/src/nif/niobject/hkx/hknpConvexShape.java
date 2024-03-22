package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.compound.NifVector4;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.byteutils.ByteUtils;

/**<class name='hknpConvexShape' version='0' signature='0xc8f7c10d' parent='hknpShape'>
	<members>
		<member name='vertices' type='hkRelArray&lt;hkVector4&gt;' offset='48' vtype='TYPE_RELARRAY' vsubtype='TYPE_VECTOR4' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/
public class hknpConvexShape extends hknpShape {
	
	NifVector4[] vertices;
	
	@Override
	public boolean readFromStream(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, classOffset);
		
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		
		//<member name='vertices' type='hkRelArray&lt;hkVector4&gt;' offset='48' vtype='TYPE_RELARRAY' vsubtype='TYPE_VECTOR4' arrsize='0' flags='FLAGS_NONE'/>
		ByteBuffer file = connector.data.setup(classOffset + 48);
		byte[] bSize = new byte[2];
		byte[] bOff = new byte[2];
		file.get(bSize);
		file.get(bOff);
		int size = ByteUtils.getUInt(bSize) - 1;
		int offset = ByteUtils.getUInt(bOff);
		vertices = new NifVector4[size];
		for (int i = 0; i < size; i++) {
			vertices[i] = new NifVector4(stream, offset + (i * 16));//16 bytes per vec4
		}
		
		return success;
	}
}
