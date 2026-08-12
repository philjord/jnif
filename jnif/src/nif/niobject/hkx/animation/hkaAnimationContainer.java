package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.hkReferencedObject;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
 https://github.com/nitaigao/engine-showcase/blob/master/etc/vendor/havok/Source/Animation/Animation/hkaAnimationContainer.h
 https://ffxiv.wildwolf.dev/api/FFXIVClientStructs.Havok.Animation.hkaAnimationContainer.html
<class name='hkaAnimationContainer' version='1' signature='0x26859f4c' parent='hkReferencedObject'>
	<members>
		<member name='skeletons' type='hkArray&lt;hkaSkeleton*&gt;' ctype='hkaSkeleton' offset='16' vtype='TYPE_ARRAY' vsubtype='TYPE_POINTER' arrsize='0' flags='FLAGS_NONE'/>
		<member name='animations' type='hkArray&lt;hkaAnimation*&gt;' ctype='hkaAnimation' offset='32' vtype='TYPE_ARRAY' vsubtype='TYPE_POINTER' arrsize='0' flags='FLAGS_NONE'/>
		<member name='bindings' type='hkArray&lt;hkaAnimationBinding*&gt;' ctype='hkaAnimationBinding' offset='48' vtype='TYPE_ARRAY' vsubtype='TYPE_POINTER' arrsize='0' flags='FLAGS_NONE'/>
		<member name='attachments' type='hkArray&lt;hkaBoneAttachment*&gt;' ctype='hkaBoneAttachment' offset='64' vtype='TYPE_ARRAY' vsubtype='TYPE_POINTER' arrsize='0' flags='FLAGS_NONE'/>
		<member name='skins' type='hkArray&lt;hkaMeshBinding*&gt;' ctype='hkaMeshBinding' offset='80' vtype='TYPE_ARRAY' vsubtype='TYPE_POINTER' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/
public class hkaAnimationContainer extends hkReferencedObject {

	public long[]	skeletons;
	public long[]	animations;
	public long[]	bindings;
	public long[]	attachments;
	public long[]	skins;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		if (connector.header.is64bit) {
			int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 16));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 16;
				skeletons = new long[arrSize];
				for (int i = 0; i < arrSize; i++) {
					long contentsPosition = arrValue.to + (i * 0x08);//size of pointers
					skeletons[i] = HKXReader.getPointer(connector, contentsPosition);
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 32));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 32;
				animations = new long[arrSize];
				for (int i = 0; i < arrSize; i++) {
					long contentsPosition = arrValue.to + (i * 0x08);//size of pointers
					animations[i] = HKXReader.getPointer(connector, contentsPosition);
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 48));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 48;
				bindings = new long[arrSize];
				for (int i = 0; i < arrSize; i++) {
					long contentsPosition = arrValue.to + (i * 0x08);//size of pointers
					bindings[i] = HKXReader.getPointer(connector, contentsPosition);
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 64));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 64;
				attachments = new long[arrSize];
				for (int i = 0; i < arrSize; i++) {
					long contentsPosition = arrValue.to + (i * 0x08);//size of pointers
					attachments[i] = HKXReader.getPointer(connector, contentsPosition);
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 80));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 80;
				skins = new long[arrSize];
				for (int i = 0; i < arrSize; i++) {
					long contentsPosition = arrValue.to + (i * 0x08);//size of pointers
					skins[i] = HKXReader.getPointer(connector, contentsPosition);
				}
			}
		} else {
			int arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 8));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 8;
				skeletons = new long[arrSize];
				for (int i = 0; i < arrSize; i++) {
					long contentsPosition = arrValue.to + (i * 0x04);//size of pointers
					skeletons[i] = HKXReader.getPointer(connector, contentsPosition);
				}
			}

			arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 20));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 20;
				animations = new long[arrSize];
				for (int i = 0; i < arrSize; i++) {
					long contentsPosition = arrValue.to + (i * 0x04);//size of pointers
					animations[i] = HKXReader.getPointer(connector, contentsPosition);
				}
			}

			arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 32));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 32;
				bindings = new long[arrSize];
				for (int i = 0; i < arrSize; i++) {
					long contentsPosition = arrValue.to + (i * 0x04);//size of pointers
					bindings[i] = HKXReader.getPointer(connector, contentsPosition);
				}
			}

			arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 44));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 44;
				attachments = new long[arrSize];
				for (int i = 0; i < arrSize; i++) {
					long contentsPosition = arrValue.to + (i * 0x04);//size of pointers
					attachments[i] = HKXReader.getPointer(connector, contentsPosition);
				}
			}

			arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 56));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 56;
				skins = new long[arrSize];
				for (int i = 0; i < arrSize; i++) {
					long contentsPosition = arrValue.to + (i * 0x04);//size of pointers
					skins[i] = HKXReader.getPointer(connector, contentsPosition);
				}
			}
		}

		return success;
	}
}
