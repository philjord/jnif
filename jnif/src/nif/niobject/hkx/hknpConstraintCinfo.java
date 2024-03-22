package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.DataExternal;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hknpConstraintCinfo' version='2' signature='0x67ea986d'>
	<members>
		<member name='constraintData' type='struct hkpConstraintData*' ctype='hkpConstraintData' offset='0' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='bodyA' type='hkUint32' offset='8' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='bodyB' type='hkUint32' offset='12' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='flags' type='flags FlagsEnum' etype='FlagsEnum' offset='16' vtype='TYPE_FLAGS' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hknpConstraintCinfo  {
	
	public static final int size = 17;
	long constraintData;
	int bodyA;
	int bodyB;
	int flags;	 

	
	public hknpConstraintCinfo(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		
		//<member name='constraintData' type='struct hkpConstraintData*' ctype='hkpConstraintData' offset='0' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		DataExternal data = connector.data2.readNext();
		if (data.from == classOffset + 0) {
			constraintData = data.to;
		} else {
			connector.data2.backtrack();
		}
		//<member name='bodyA' type='hkUint32' offset='8' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		bodyA = stream.getInt(8);
		//<member name='bodyB' type='hkUint32' offset='12' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		bodyB = stream.getInt(12);
		//<member name='flags' type='flags FlagsEnum' etype='FlagsEnum' offset='16' vtype='TYPE_FLAGS' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		flags = Byte.toUnsignedInt(stream.get(16));	
	}
}	