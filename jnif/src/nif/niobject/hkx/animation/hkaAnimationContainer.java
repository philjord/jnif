package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.hkReferencedObject;
import nif.niobject.hkx.reader.Data1Interface;
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

		ByteBuffer file = connector.data.setup(classOffset + 16);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 16;
			skeletons = new long[arrSize];
			for (int i = 0; i < arrSize; i++) {
				long contentsPosition = arrValue.to + (i * 0x08);//size of pointers
				skeletons[i] = HKXReader.getPointer(connector, contentsPosition);
			}
		}

		file = connector.data.setup(classOffset + 32);
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 32;
			animations = new long[arrSize];
			for (int i = 0; i < arrSize; i++) {
				long contentsPosition = arrValue.to + (i * 0x08);//size of pointers
				animations[i] = HKXReader.getPointer(connector, contentsPosition);
			}
		}

		file = connector.data.setup(classOffset + 48);
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 48;
			bindings = new long[arrSize];
			for (int i = 0; i < arrSize; i++) {
				long contentsPosition = arrValue.to + (i * 0x08);//size of pointers
				bindings[i] = HKXReader.getPointer(connector, contentsPosition);
			}
		}

		file = connector.data.setup(classOffset + 64);
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 64;
			attachments = new long[arrSize];
			for (int i = 0; i < arrSize; i++) {
				long contentsPosition = arrValue.to + (i * 0x08);//size of pointers
				attachments[i] = HKXReader.getPointer(connector, contentsPosition);
			}
		}

		file = connector.data.setup(classOffset + 80);
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 80;
			skins = new long[arrSize];
			for (int i = 0; i < arrSize; i++) {
				long contentsPosition = arrValue.to + (i * 0x08);//size of pointers
				skins[i] = HKXReader.getPointer(connector, contentsPosition);
			}
		}

		return success;
	}
}
