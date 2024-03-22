package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.compound.NifVector4;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<class name='hknpCapsuleShape' version='0' signature='0x60a75f4c' parent='hknpConvexPolytopeShape'>
	<members>
		<member name='a' type='hkVector4' offset='80' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='b' type='hkVector4' offset='96' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>*/

public class hknpCapsuleShape extends hknpConvexPolytopeShape  {
	NifVector4 a;
	NifVector4 b;
	
	@Override
	public boolean readFromStream(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, classOffset);
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		
		
		//<member name='a' type='hkVector4' offset='80' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		a = new NifVector4(stream, 80);
		//<member name='b' type='hkVector4' offset='96' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		b = new NifVector4(stream, 96);
		
		return success;
	}
}
		