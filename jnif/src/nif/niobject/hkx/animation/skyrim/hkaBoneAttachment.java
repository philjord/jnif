package nif.niobject.hkx.animation.skyrim;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.compound.NifMatrix44;
import nif.niobject.hkx.hkReferencedObject;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkaBoneAttachment' version='2' signature='0xb566faa5' parent='hkReferencedObject'>
	<members>
		<member name='originalSkeletonName' type='hkStringPtr' offset='16' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='boneFromAttachment' type='hkMatrix4' offset='32' vtype='TYPE_MATRIX4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='attachment' type='struct hkReferencedObject*' ctype='hkReferencedObject' offset='96' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='name' type='hkStringPtr' offset='104' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='boneIndex' type='hkInt16' offset='112' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hkaBoneAttachment extends hkReferencedObject {

	public String		originalSkeletonName;
	public NifMatrix44	boneFromAttachment;
	public long			attachment;
	public String		name;
	public int			boneIndex;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		originalSkeletonName = HKXReader.hkStringPtr(connector, classOffset + 16);
		boneFromAttachment = new NifMatrix44(stream, classOffset + 32);
		attachment = HKXReader.getPointer(connector, classOffset + 96);
		name = HKXReader.hkStringPtr(connector, classOffset + 104);
		boneIndex = stream.getShort(classOffset + 112);

		return success;
	}

}