package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.compound.NifMatrix44;
import nif.niobject.hkx.hkReferencedObject;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkaMeshBinding' version='3' signature='0x32b0ecb6' parent='hkReferencedObject'>
	<members>
		<member name='mesh' type='struct hkxMesh*' ctype='hkxMesh' offset='16' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='originalSkeletonName' type='hkStringPtr' offset='24' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='name' type='hkStringPtr' offset='32' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='skeleton' type='struct hkaSkeleton*' ctype='hkaSkeleton' offset='40' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='mappings' type='hkArray&lt;struct hkaMeshBindingMapping&gt;' ctype='hkaMeshBindingMapping' offset='48' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='boneFromSkinMeshTransforms' type='hkArray&lt;hkTransform&gt;' offset='64' vtype='TYPE_ARRAY' vsubtype='TYPE_TRANSFORM' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hkaMeshBinding extends hkReferencedObject {

	public long						mesh;
	public String					originalSkeletonName;
	public String					name;
	public long						skeleton;
	public hkaMeshBindingMapping[]	mappings;
	public NifMatrix44[]			boneFromSkinMeshTransforms;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
		if (connector.header.is64bit) {
			mesh = HKXReader.getPointer(connector, classOffset + 16);
			originalSkeletonName = HKXReader.hkStringPtr(connector, classOffset + 24);
			name = HKXReader.hkStringPtr(connector, classOffset + 32);
			skeleton = HKXReader.getPointer(connector, classOffset + 40);

			int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 48));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 48;
				mappings = new hkaMeshBindingMapping[arrSize];
				for (int i = 0; i < arrSize; i++) {
					mappings[i] = new hkaMeshBindingMapping(connector, stream,
							(int)arrValue.to + (i * hkaMeshBindingMapping.size));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 64));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 64;
				boneFromSkinMeshTransforms = new NifMatrix44[arrSize];
				for (int i = 0; i < arrSize; i++) {
					boneFromSkinMeshTransforms[i] = new NifMatrix44(stream, (int)arrValue.to + (i * 64));
				}
			}
		} else {
			mesh = HKXReader.getPointer(connector, classOffset + 8);
			originalSkeletonName = HKXReader.hkStringPtr(connector, classOffset + 12);
			name = HKXReader.hkStringPtr(connector, classOffset + 16);
			skeleton = HKXReader.getPointer(connector, classOffset + 20);

			int arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 24));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 24;
				mappings = new hkaMeshBindingMapping[arrSize];
				for (int i = 0; i < arrSize; i++) {
					mappings[i] = new hkaMeshBindingMapping(connector, stream,
							(int)arrValue.to + (i * hkaMeshBindingMapping.size32));
				}
			}

			arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 36));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 36;
				boneFromSkinMeshTransforms = new NifMatrix44[arrSize];
				for (int i = 0; i < arrSize; i++) {
					boneFromSkinMeshTransforms[i] = new NifMatrix44(stream, (int)arrValue.to + (i * 64));
				}
			}
		}

		return success;
	}

}