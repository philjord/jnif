package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkaSkeletonMapperData' version='2' signature='0x3e0a67fd'>
	<enums>
		<enum name='MappingType' flags='00000000'>
			<enumitem name='HK_RAGDOLL_MAPPING' value='0'/>
			<enumitem name='HK_RETARGETING_MAPPING' value='1'/>
		</enum>
	</enums>
	<members>
		<member name='skeletonA' type='struct hkaSkeleton*' ctype='hkaSkeleton' offset='0' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='skeletonB' type='struct hkaSkeleton*' ctype='hkaSkeleton' offset='8' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='partitionMap' type='hkArray&lt;hkInt16&gt;' offset='16' vtype='TYPE_ARRAY' vsubtype='TYPE_INT16' arrsize='0' flags='FLAGS_NONE'/>
		<member name='simpleMappingPartitionRanges' type='hkArray&lt;struct hkaSkeletonMapperDataPartitionMappingRange&gt;' ctype='hkaSkeletonMapperDataPartitionMappingRange' offset='32' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='chainMappingPartitionRanges' type='hkArray&lt;struct hkaSkeletonMapperDataPartitionMappingRange&gt;' ctype='hkaSkeletonMapperDataPartitionMappingRange' offset='48' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='simpleMappings' type='hkArray&lt;struct hkaSkeletonMapperDataSimpleMapping&gt;' ctype='hkaSkeletonMapperDataSimpleMapping' offset='64' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='chainMappings' type='hkArray&lt;struct hkaSkeletonMapperDataChainMapping&gt;' ctype='hkaSkeletonMapperDataChainMapping' offset='80' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='unmappedBones' type='hkArray&lt;hkInt16&gt;' offset='96' vtype='TYPE_ARRAY' vsubtype='TYPE_INT16' arrsize='0' flags='FLAGS_NONE'/>
		<member name='extractedMotionMapping' type='hkQsTransform' offset='112' vtype='TYPE_QSTRANSFORM' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='keepUnmappedLocal' type='hkBool' offset='160' vtype='TYPE_BOOL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='mappingType' type='enum MappingType' etype='MappingType' offset='164' vtype='TYPE_ENUM' vsubtype='TYPE_INT32' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hkaSkeletonMapperData {

	enum MappingType {
		HK_RAGDOLL_MAPPING, HK_RETARGETING_MAPPING
	};

	public static final int								size	= 164 + 4;
	public static final int								size32	= 132 + 4;

	public long											skeletonA;
	public long											skeletonB;
	public short[]										partitionMap;
	public hkaSkeletonMapperDataPartitionMappingRange[]	simpleMappingPartitionRanges;
	public hkaSkeletonMapperDataPartitionMappingRange[]	chainMappingPartitionRanges;
	public hkaSkeletonMapperDataSimpleMapping[]			simpleMappings;
	public hkaSkeletonMapperDataChainMapping[]			chainMappings;
	public short[]										unmappedBones;
	public hkQsTransform								extractedMotionMapping;
	public boolean										keepUnmappedLocal;
	public int											mappingType;

	public hkaSkeletonMapperData(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {

		if (connector.header.is64bit) {
			skeletonA = HKXReader.getPointer(connector, classOffset + 0);
			skeletonB = HKXReader.getPointer(connector, classOffset + 8);

			int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 16));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 16;
				partitionMap = new short[arrSize];
				for (int i = 0; i < arrSize; i++) {
					partitionMap[i] = stream.getShort((int)arrValue.to + (i * 2));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 32));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 32;
				simpleMappingPartitionRanges = new hkaSkeletonMapperDataPartitionMappingRange[arrSize];
				for (int i = 0; i < arrSize; i++) {
					simpleMappingPartitionRanges[i] = new hkaSkeletonMapperDataPartitionMappingRange(connector, stream,
							(int)arrValue.to + (i * hkaSkeletonMapperDataPartitionMappingRange.size));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 48));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 48;
				chainMappingPartitionRanges = new hkaSkeletonMapperDataPartitionMappingRange[arrSize];
				for (int i = 0; i < arrSize; i++) {
					chainMappingPartitionRanges[i] = new hkaSkeletonMapperDataPartitionMappingRange(connector, stream,
							(int)arrValue.to + (i * hkaSkeletonMapperDataPartitionMappingRange.size));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 64));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 64;
				simpleMappings = new hkaSkeletonMapperDataSimpleMapping[arrSize];
				for (int i = 0; i < arrSize; i++) {
					simpleMappings[i] = new hkaSkeletonMapperDataSimpleMapping(connector, stream,
							(int)arrValue.to + (i * hkaSkeletonMapperDataSimpleMapping.size));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 80));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 80;
				chainMappings = new hkaSkeletonMapperDataChainMapping[arrSize];
				for (int i = 0; i < arrSize; i++) {
					chainMappings[i] = new hkaSkeletonMapperDataChainMapping(connector, stream,
							(int)arrValue.to + (i * hkaSkeletonMapperDataChainMapping.size));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 96));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 96;
				unmappedBones = new short[arrSize];
				for (int i = 0; i < arrSize; i++) {
					unmappedBones[i] = stream.getShort((int)arrValue.to + (i * 2));
				}
			}

			extractedMotionMapping = new hkQsTransform(connector, stream, classOffset + 112);
			keepUnmappedLocal = stream.getInt(classOffset + 160) != 0;
			mappingType = stream.getInt(classOffset + 164);

		} else {
			skeletonA = HKXReader.getPointer(connector, classOffset + 0);
			skeletonB = HKXReader.getPointer(connector, classOffset + 4);

			int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 8));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 8;
				partitionMap = new short[arrSize];
				for (int i = 0; i < arrSize; i++) {
					partitionMap[i] = stream.getShort((int)arrValue.to + (i * 2));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 20));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 20;
				simpleMappingPartitionRanges = new hkaSkeletonMapperDataPartitionMappingRange[arrSize];
				for (int i = 0; i < arrSize; i++) {
					simpleMappingPartitionRanges[i] = new hkaSkeletonMapperDataPartitionMappingRange(connector, stream,
							(int)arrValue.to + (i * hkaSkeletonMapperDataPartitionMappingRange.size));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 32));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 32;
				chainMappingPartitionRanges = new hkaSkeletonMapperDataPartitionMappingRange[arrSize];
				for (int i = 0; i < arrSize; i++) {
					chainMappingPartitionRanges[i] = new hkaSkeletonMapperDataPartitionMappingRange(connector, stream,
							(int)arrValue.to + (i * hkaSkeletonMapperDataPartitionMappingRange.size));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 44));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 44;
				simpleMappings = new hkaSkeletonMapperDataSimpleMapping[arrSize];
				for (int i = 0; i < arrSize; i++) {
					simpleMappings[i] = new hkaSkeletonMapperDataSimpleMapping(connector, stream,
							(int)arrValue.to + (i * hkaSkeletonMapperDataSimpleMapping.size));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 56));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 56;
				chainMappings = new hkaSkeletonMapperDataChainMapping[arrSize];
				for (int i = 0; i < arrSize; i++) {
					chainMappings[i] = new hkaSkeletonMapperDataChainMapping(connector, stream,
							(int)arrValue.to + (i * hkaSkeletonMapperDataChainMapping.size));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 68));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 68;
				unmappedBones = new short[arrSize];
				for (int i = 0; i < arrSize; i++) {
					unmappedBones[i] = stream.getShort((int)arrValue.to + (i * 2));
				}
			}

			extractedMotionMapping = new hkQsTransform(connector, stream, classOffset + 80);
			keepUnmappedLocal = stream.getInt(classOffset + 128) != 0;
			mappingType = stream.getInt(classOffset + 132);
		}
	}

}