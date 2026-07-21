package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.compound.NifVector4;
import nif.niobject.hkx.hkReferencedObject;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
 https://github.com/aerisarn/hkxlib/blob/d3254dc72b2d834d5e222f3a688428aaa0cb1a3f/src/main/java/org/tes/hkx/lib/ext/hkbProjectData.java#L26
 
<class name='hkbProjectData' version='2' signature='0x363c1159' parent='hkReferencedObject'>
	<members>
		<member name='worldUpWS' type='hkVector4' offset='16' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='stringData' type='struct hkbProjectStringData*' ctype='hkbProjectStringData' offset='32' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='defaultEventMode' type='enum EventMode' etype='EventMode' offset='40' vtype='TYPE_ENUM' vsubtype='TYPE_INT8' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/
public class hkbProjectData extends hkReferencedObject {
	enum EventMode {
		EVENT_MODE_IGNORE_FROM_GENERATOR
	};

	public NifVector4	worldUpWS;
	public long			stringData;
	public EventMode	defaultEventMode;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
		worldUpWS = new NifVector4(stream, classOffset + 16);
		stringData = HKXReader.getPointer(connector, classOffset + 32);
		int defaultEventModev = Byte.toUnsignedInt(stream.get(classOffset + 40));// seems to allow 255 as a value? odd
		//defaultEventMode = EventMode.values()[Byte.toUnsignedInt(stream.get(40))];

		return success;
	}
}
