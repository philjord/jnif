package nif.niobject.hkx.animation.skyrim;

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
<class name='hkaSkeleton' version='5' signature='0xfec1cedb' parent='hkReferencedObject'>
	<members>
		<member name='name' type='hkStringPtr' offset='16' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='parentIndices' type='hkArray&lt;hkInt16&gt;' offset='24' vtype='TYPE_ARRAY' vsubtype='TYPE_INT16' arrsize='0' flags='FLAGS_NONE'/>
		<member name='bones' type='hkArray&lt;struct hkaBone&gt;' ctype='hkaBone' offset='40' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='referencePose' type='hkArray&lt;hkQsTransform&gt;' offset='56' vtype='TYPE_ARRAY' vsubtype='TYPE_QSTRANSFORM' arrsize='0' flags='FLAGS_NONE'/>
		<member name='referenceFloats' type='hkArray&lt;hkReal&gt;' offset='72' vtype='TYPE_ARRAY' vsubtype='TYPE_REAL' arrsize='0' flags='FLAGS_NONE'/>
		<member name='floatSlots' type='hkArray&lt;hkStringPtr&gt;' offset='88' vtype='TYPE_ARRAY' vsubtype='TYPE_STRINGPTR' arrsize='0' flags='FLAGS_NONE'/>
		<member name='localFrames' type='hkArray&lt;struct hkaSkeletonLocalFrameOnBone&gt;' ctype='hkaSkeletonLocalFrameOnBone' offset='104' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='partitions' type='hkArray&lt;struct hkaSkeletonPartition&gt;' ctype='hkaSkeletonPartition' offset='120' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hkaSkeleton extends hkReferencedObject {

	public static final int					size	= 120 + 16;
	public String							name;
	public int[]							parentIndices;
	public hkaBone[]						bones;
	public hkQsTransform[]					referencePose;
	public float[]							referenceFloats;
	public String[]							floatSlots;
	public hkaSkeletonLocalFrameOnBone[]	localFrames;
	public hkaSkeletonPartition[]			partitions;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{
		boolean success = super.readFromStream(connector, stream, classOffset);
		name = HKXReader.hkStringPtr(connector, classOffset + 16);

		ByteBuffer file = connector.data.setup(classOffset + 24);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 24;
			ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			parentIndices = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				parentIndices[i] = Short.toUnsignedInt(s2.getShort(i * 2));
			}
		}

		file = connector.data.setup(classOffset + 40);
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 40;
			bones = new hkaBone[arrSize];
			for (int i = 0; i < arrSize; i++) {
				bones[i] = new hkaBone(connector, stream, (int)arrValue.to + (i * hkaBone.size));
			}
		}

		file = connector.data.setup(classOffset + 56);
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 56;
			referencePose = new hkQsTransform[arrSize];
			for (int i = 0; i < arrSize; i++) {
				referencePose[i] = new hkQsTransform(connector, stream, (int)arrValue.to + (i * hkQsTransform.size));
			}
		}

		file = connector.data.setup(classOffset + 72);
		baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 72;
			ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			referenceFloats = new float[arrSize];
			for (int i = 0; i < arrSize; i++) {
				referenceFloats[i] = s2.getFloat(i * 4);
			}
		}

		floatSlots = HKXReader.hkStringArray(connector, classOffset + 88);

		file = connector.data.setup(classOffset + 104);
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 104;
			localFrames = new hkaSkeletonLocalFrameOnBone[arrSize];
			for (int i = 0; i < arrSize; i++) {
				localFrames[i] = new hkaSkeletonLocalFrameOnBone(connector, stream,
						(int)arrValue.to + (i * hkaSkeletonLocalFrameOnBone.size));
			}
		}

		file = connector.data.setup(classOffset + 120);
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 120;
			partitions = new hkaSkeletonPartition[arrSize];
			for (int i = 0; i < arrSize; i++) {
				partitions[i] = new hkaSkeletonPartition(connector, stream,
						(int)arrValue.to + (i * hkaSkeletonPartition.size));
			}
		}
		
		return success;
	}

}