package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**
 * <struct name='hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance' version='0' signature='0x99a1f34c'>
	<members>
		<member name='elements' type='hkArray&lt;struct hknpShapeInstance&gt;' ctype='hknpShapeInstance' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='firstFree' type='hkInt32' offset='16' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/

public class hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance {
	public hknpShapeInstance[]	elements;
	public int					firstFree;

	public hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance(	HKXReaderConnector connector,
																												ByteBuffer stream,
																												int classOffset)
			throws IOException, InvalidPositionException {
		int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 0));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 0;
			elements = new hknpShapeInstance[arrSize];
			for (int i = 0; i < arrSize; i++) {
				elements[i] = new hknpShapeInstance(connector, stream, (int)arrValue.to + (i * hknpShapeInstance.size));
			}
		}
		firstFree = stream.getInt(classOffset + 16);

	}

	/**
	 Outline for Havok_TagType hkFreeListArray
	Havok_TagMember elements of type hkArray
	Havok_TagMember firstFree of type hkInt32
	 */
	public hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance(Havok_TagObject item) {
		//item.outputOutline();
		Havok_TagObject value = item.listObjectClass.get(0);
		int arrSize = value.listObjectArray.size();
		elements = new hknpShapeInstance[arrSize];
		for (int i = 0; i < arrSize; i++) {
			elements[i] = new hknpShapeInstance(value.listObjectArray.get(i));
		}
		firstFree = item.listObjectClass.get(1).i_value;
	}
}
