package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.compound.NifVector4;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
 <class name='hknpScaledConvexShapeBase' version='2' signature='0x1d14c258' parent='hknpDecoratorShape'>
 
	<members>
		<member name='scale' type='hkVector4' offset='64' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='translation' type='hkVector4' offset='80' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/


public class hknpScaledConvexShapeBase extends hknpDecoratorShape {

	public NifVector4 scale;
	public NifVector4 position;
	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
			
		//<member name='scale' type='hkVector4' offset='64' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		scale = new NifVector4(stream, classOffset + 64);
		
		//<member name='translation' type='hkVector4' offset='80' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		position = new NifVector4(stream, classOffset + 80);
		
		return success;
	}
}