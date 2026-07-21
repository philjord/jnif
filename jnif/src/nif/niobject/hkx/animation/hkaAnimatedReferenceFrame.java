package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.hkReferencedObject;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkaAnimatedReferenceFrame' version='0' signature='0x985e4297' parent='hkReferencedObject'>
	<enums>
		<enum name='hkaReferenceFrameTypeEnum' flags='00000000'>
			<enumitem name='REFERENCE_FRAME_UNKNOWN' value='0'/>
			<enumitem name='REFERENCE_FRAME_DEFAULT' value='1'/>
			<enumitem name='REFERENCE_FRAME_PARAMETRIC' value='2'/>
		</enum>
	</enums>
	<members>
		<member name='frameType' type='enum unknown' offset='16' vtype='TYPE_ENUM' vsubtype='TYPE_INT8' arrsize='0' flags='SERIALIZE_IGNORED'/>
	</members>
</class>
*/
public class hkaAnimatedReferenceFrame extends hkReferencedObject {

	enum hkaReferenceFrameTypeEnum {
		REFERENCE_FRAME_UNKNOWN, REFERENCE_FRAME_DEFAULT, REFERENCE_FRAME_PARAMETRIC
	};

	public hkaReferenceFrameTypeEnum frameType;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		int frameTypev = stream.get(classOffset + 16);
		return success;
	}

}