package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.hkReferencedObject;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkaSkeletonMapper' version='0' signature='0xace9849c' parent='hkReferencedObject'>
	<enums>
		<enum name='ConstraintSource' flags='00000000'>
			<enumitem name='NO_CONSTRAINTS' value='0'/>
			<enumitem name='REFERENCE_POSE' value='1'/>
			<enumitem name='CURRENT_POSE' value='2'/>
		</enum>
	</enums>
	<members>
		<member name='mapping' type='struct hkaSkeletonMapperData' ctype='hkaSkeletonMapperData' offset='16' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hkaSkeletonMapper extends hkReferencedObject {

	public static final int	size	= 16 + hkaSkeletonMapperData.size;
	public static final int	size32	= 8 + hkaSkeletonMapperData.size32;

	enum ConstraintSource {
		NO_CONSTRAINTS, REFERENCE_POSE, CURRENT_POSE
	};

	public hkaSkeletonMapperData mapping;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		if (connector.header.is64bit) {
			mapping = new hkaSkeletonMapperData(connector, stream, classOffset + 16);
		} else {
			mapping = new hkaSkeletonMapperData(connector, stream, classOffset + 8);
		}

		return success;
	}

}