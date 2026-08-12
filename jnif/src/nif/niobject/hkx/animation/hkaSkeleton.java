package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.hkReferencedObject;
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
	public static final int					size32	= 84 + 12;
	public String							name;
	public short[]							parentIndices;
	public hkaBone[]						bones;
	public hkQsTransform[]					referencePose;
	public float[]							referenceFloats;
	public String[]							floatSlots;
	public hkaSkeletonLocalFrameOnBone[]	localFrames;
	public hkaSkeletonPartition[]			partitions;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		if (connector.header.is64bit) {
			name = HKXReader.hkStringPtr(connector, classOffset + 16);

			int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 24));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 24;
				parentIndices = new short[arrSize];
				for (int i = 0; i < arrSize; i++) {
					parentIndices[i] = stream.getShort((int)arrValue.to + (i * 2));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 40));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 40;
				bones = new hkaBone[arrSize];
				for (int i = 0; i < arrSize; i++) {
					bones[i] = new hkaBone(connector, stream, (int)arrValue.to + (i * hkaBone.size));
				}
			}
						
			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 56));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 56;
				referencePose = new hkQsTransform[arrSize];
				for (int i = 0; i < arrSize; i++) {
					referencePose[i] = new hkQsTransform(connector, stream,
							(int)arrValue.to + (i * hkQsTransform.size));
				}
			}
			

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 72));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 72;
				referenceFloats = new float[arrSize];
				for (int i = 0; i < arrSize; i++) {
					referenceFloats[i] = stream.getFloat((int)arrValue.to + (i * 4));
				}
			}

			floatSlots = HKXReader.hkStringArray(connector, classOffset + 88);

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 104));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 104;
				localFrames = new hkaSkeletonLocalFrameOnBone[arrSize];
				for (int i = 0; i < arrSize; i++) {
					localFrames[i] = new hkaSkeletonLocalFrameOnBone(connector, stream,
							(int)arrValue.to + (i * hkaSkeletonLocalFrameOnBone.size));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 120));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 120;
				partitions = new hkaSkeletonPartition[arrSize];
				for (int i = 0; i < arrSize; i++) {
					partitions[i] = new hkaSkeletonPartition(connector, stream,
							(int)arrValue.to + (i * hkaSkeletonPartition.size));
				}
			}

		} else {
			//https://github.com/nitaigao/engine-showcase/blob/master/etc/vendor/havok/Source/Animation/Animation/Rig/hkaSkeleton.h
			name = HKXReader.hkStringPtr(connector, classOffset + 8);

			int arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 12));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 12;
				parentIndices = new short[arrSize];
				for (int i = 0; i < arrSize; i++) {
					parentIndices[i] = stream.getShort((int)arrValue.to + (i * 2));
				}
			}

			arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 24));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 24;
				bones = new hkaBone[arrSize];
				for (int i = 0; i < arrSize; i++) {
					bones[i] = new hkaBone(connector, stream, (int)arrValue.to + (i * hkaBone.size32));
				}
			}

			arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 36));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 36;
				referencePose = new hkQsTransform[arrSize];
				for (int i = 0; i < arrSize; i++) {
					referencePose[i] = new hkQsTransform(connector, stream,
							(int)arrValue.to + (i * hkQsTransform.size32));
				}
			}

			arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 48));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 48;
				referenceFloats = new float[arrSize];
				for (int i = 0; i < arrSize; i++) {
					referenceFloats[i] = stream.getFloat((int)arrValue.to + (i * 4));
				}
			}

			floatSlots = HKXReader.hkStringArray32(connector, classOffset + 60);

			arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 72));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 72;
				localFrames = new hkaSkeletonLocalFrameOnBone[arrSize];
				for (int i = 0; i < arrSize; i++) {
					localFrames[i] = new hkaSkeletonLocalFrameOnBone(connector, stream,
							(int)arrValue.to + (i * hkaSkeletonLocalFrameOnBone.size32));
				}
			}

			//looks like partitions is not part of 32bit format
			/*
						arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 84));
						if (arrSize > 0) {
							DataInternal arrValue = connector.data1.readNext();
							assert arrValue.from == classOffset + 84;
							partitions = new hkaSkeletonPartition[arrSize];
							for (int i = 0; i < arrSize; i++) {
								partitions[i] = new hkaSkeletonPartition(connector, stream,
										(int)arrValue.to + (i * hkaSkeletonPartition.size32));
							}
						}*/
		}

		return success;
	}

}