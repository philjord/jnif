package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.hkReferencedObject;
import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkaAnimationBinding' version='3' signature='0x0faf9150' parent='hkReferencedObject'>
	<enums>
		<enum name='BlendHint' flags='00000000'>
			<enumitem name='NORMAL' value='0'/>
			<enumitem name='ADDITIVE_DEPRECATED' value='1'/>
			<enumitem name='ADDITIVE' value='2'/>
		</enum>
	</enums>
	<members>
		<member name='originalSkeletonName' type='hkStringPtr' offset='16' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='animation' type='struct hkaAnimation*' ctype='hkaAnimation' offset='24' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='transformTrackToBoneIndices' type='hkArray&lt;hkInt16&gt;' offset='32' vtype='TYPE_ARRAY' vsubtype='TYPE_INT16' arrsize='0' flags='FLAGS_NONE'/>
		<member name='floatTrackToFloatSlotIndices' type='hkArray&lt;hkInt16&gt;' offset='48' vtype='TYPE_ARRAY' vsubtype='TYPE_INT16' arrsize='0' flags='FLAGS_NONE'/>
		<member name='partitionIndices' type='hkArray&lt;hkInt16&gt;' offset='64' vtype='TYPE_ARRAY' vsubtype='TYPE_INT16' arrsize='0' flags='FLAGS_NONE'/>
		<member name='blendHint' type='enum BlendHint' etype='BlendHint' offset='80' vtype='TYPE_ENUM' vsubtype='TYPE_INT8' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hkaAnimationBinding extends hkReferencedObject {
	enum BlendHint {
		NORMAL, ADDITIVE_DEPRECATED, ADDITIVE
	};

	public String		originalSkeletonName;
	public long			animation;
	public int[]		transformTrackToBoneIndices;
	public int[]		floatTrackToFloatSlotIndices;
	public int[]		partitionIndices;
	public BlendHint	blendHint;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		originalSkeletonName = HKXReader.hkStringPtr(connector, classOffset + 16);
		animation = HKXReader.getPointer(connector, classOffset + 24);
		ByteBuffer file = connector.data.setup(classOffset + 32);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 32;
			ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			transformTrackToBoneIndices = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				transformTrackToBoneIndices[i] = Short.toUnsignedInt(s2.getShort(i * 2));
			}
		}
		file = connector.data.setup(classOffset + 48);
		baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			//assert arrValue.from == classOffset + 48;
			ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			floatTrackToFloatSlotIndices = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				floatTrackToFloatSlotIndices[i] = Short.toUnsignedInt(s2.getShort(i * 2));
			}
		}
		file = connector.data.setup(classOffset + 64);
		baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 64;
			ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			partitionIndices = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				partitionIndices[i] = Short.toUnsignedInt(s2.getShort(i * 2));
			}
		}

		int blendHintv = stream.get(classOffset + 80);

		return success;
	}

}