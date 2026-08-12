package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.hkReferencedObject;
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
	public int[]		transformTrackToBoneIndices;	// can be null if 1 to 1 mapping
	public int[]		floatTrackToFloatSlotIndices;
	public int[]		partitionIndices;
	public BlendHint	blendHint;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		if (connector.header.is64bit) {
			originalSkeletonName = HKXReader.hkStringPtr(connector, classOffset + 16);
			animation = HKXReader.getPointer(connector, classOffset + 24);

			int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 32));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 32;
				transformTrackToBoneIndices = new int[arrSize];
				for (int i = 0; i < arrSize; i++) {
					transformTrackToBoneIndices[i] = Short.toUnsignedInt(stream.getShort((int)arrValue.to + (i * 2)));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 48));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 48;
				floatTrackToFloatSlotIndices = new int[arrSize];
				for (int i = 0; i < arrSize; i++) {
					floatTrackToFloatSlotIndices[i] = Short.toUnsignedInt(stream.getShort((int)arrValue.to + (i * 2)));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 64));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 64;
				partitionIndices = new int[arrSize];
				for (int i = 0; i < arrSize; i++) {
					partitionIndices[i] = Short.toUnsignedInt(stream.getShort((int)arrValue.to + (i * 2)));
				}
			}

			int blendHintv = stream.get(classOffset + 80);

		} else {
			originalSkeletonName = HKXReader.hkStringPtr(connector, classOffset + 8);
			animation = HKXReader.getPointer(connector, classOffset + 12);

			/*
			 D:\downloads&installs\develop\havok\Havok_Physics_Animation_600_PC_XS_win32_VS2005_keycode_perpetual_20080925.zip\hk600r1\Docs\ReleaseNotes
			 Havok Animation 6.0.0.pdf page 7
			HKA-1039
			Implemented
			Ensure Animation fully supports NULL/Identity bindings.
			6.0.0 RC1
			As a result of EXP-477 the user may now optionally 'prune' the index arrays in the animation bindings
			(hkaAnimationBinding objects) on export	from the Content Tools if the binding is equal to the identity
			(track indices map exactly to bone indices). This will set m_transformTrackToBoneIndices or 
			m_floatTrackToFloatSlotIndices to HK_NULL and m_numTransformTrackToBoneIndices or 
			m_numFloatTrackToFloatSlotIndices to 0 for transform or float components of the binding respectively.
			This is supported on the runtime side by identifying this case and using an implicit identity mapping.
			All user code which previously assumed these array were non-empty will have to handle this case if the 
			user intends to prune bindings on export or at runtime.
			*/

			int arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 16));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 16;
				transformTrackToBoneIndices = new int[arrSize];
				for (int i = 0; i < arrSize; i++) {
					transformTrackToBoneIndices[i] = Short.toUnsignedInt(stream.getShort((int)arrValue.to + (i * 2)));
				}
			}

			// float tracks are for non boned thing, pure lists of floats for animation of other things
			arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 28));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 28;
				floatTrackToFloatSlotIndices = new int[arrSize];
				for (int i = 0; i < arrSize; i++) {
					floatTrackToFloatSlotIndices[i] = Short.toUnsignedInt(stream.getShort((int)arrValue.to + (i * 2)));
				}
			}

			//TODO: version 1 does not have partitions, version 1 is the skyrim version
			/*arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 40));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 40;
				partitionIndices = new int[arrSize];
				for (int i = 0; i < arrSize; i++) {
					partitionIndices[i] = Short.toUnsignedInt(stream.getShort((int)arrValue.to + (i * 2)));
				}
			}*/

			int blendHintv = stream.get(classOffset + 40);
		}

		return success;
	}

}