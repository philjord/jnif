package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.compound.NifVector4;
import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkaDefaultAnimatedReferenceFrame' version='0' signature='0x60f8e0b8' parent='hkaAnimatedReferenceFrame'>
	<members>
		<member name='up' type='hkVector4' offset='32' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='forward' type='hkVector4' offset='48' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='duration' type='hkReal' offset='64' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='referenceFrameSamples' type='hkArray&lt;hkVector4&gt;' offset='72' vtype='TYPE_ARRAY' vsubtype='TYPE_VECTOR4' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/
public class hkaDefaultAnimatedReferenceFrame extends hkaAnimatedReferenceFrame {

	public NifVector4	up;
	public NifVector4	forward;
	public float		duration;
	public NifVector4[]	referenceFrameSamples;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		up = new NifVector4(stream, classOffset + 32);
		forward = new NifVector4(stream, classOffset + 48);
		duration = stream.getFloat(classOffset + 64);

		ByteBuffer file = connector.data.setup(classOffset + 72);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 72;
			referenceFrameSamples = new NifVector4[arrSize];
			for (int i = 0; i < arrSize; i++) {
				referenceFrameSamples[i] = new NifVector4(stream, (int)arrValue.to + (i * 16));
			}
		}
		return success;
	}

}