package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkpConstraintMotor' version='0' signature='0xc464ae4d' parent='hkReferencedObject'>
	<enums>
		<enum name='MotorType' flags='00000000'>
			<enumitem name='TYPE_INVALID' value='0'/>
			<enumitem name='TYPE_POSITION' value='1'/>
			<enumitem name='TYPE_VELOCITY' value='2'/>
			<enumitem name='TYPE_SPRING_DAMPER' value='3'/>
			<enumitem name='TYPE_CALLBACK' value='4'/>
			<enumitem name='TYPE_MAX' value='5'/>
		</enum>
	</enums>
	<members>
		<member name='type' type='enum MotorType' etype='MotorType' offset='16' vtype='TYPE_ENUM' vsubtype='TYPE_INT8' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hkpConstraintMotor extends hkReferencedObject {

	enum MotorType {
		TYPE_INVALID, TYPE_POSITION, TYPE_VELOCITY, TYPE_SPRING_DAMPER, TYPE_CALLBACK, TYPE_MAX
	}

	public MotorType type;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		int typev = stream.get(classOffset + 16);

		return success;
	}

}