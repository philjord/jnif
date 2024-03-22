package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.compound.NifVector4;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkAabb' version='0' signature='0x4a948b16'>
	<members>
		<member name='min' type='hkVector4' offset='0' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='max' type='hkVector4' offset='16' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/
public class hkAabb {
	public NifVector4 min;
	public NifVector4 max;
	public hkAabb(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);
		
		//<member name='min' type='hkVector4' offset='0' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		min = new NifVector4(stream, 0);
		//<member name='max' type='hkVector4' offset='16' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		max = new NifVector4(stream, 16);		 
	}
}