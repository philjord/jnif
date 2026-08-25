package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**
 * <struct name='hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator' version='0' signature='0xda41bd9b'>
 * <members> 
 * <member name='words' type='hkArray&lt;hkUint32&gt;' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT32' arrsize='0' flags='FLAGS_NONE'/> 
 * <member name='numBits' type='hkInt32' offset='16' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/> 
 * </members> 
 * </struct>
 */

public class hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator {
	int[]	words;
	int		numBits;

	public hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator(	HKXReaderConnector connector, ByteBuffer stream,
																		int classOffset)
			throws IOException, InvalidPositionException {
		int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 0));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 0;
			words = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				words[i] = stream.getInt((int)arrValue.to + (i * 4));
			}
		}

		numBits = stream.getInt(classOffset + 16);
	}

	/**
	 Outline for Havok_TagObject of type hkBitFieldStorage
	Havok_TagType None
	Havok_TagType hkBitFieldStorage
	Havok_TagMember words of type hkArray
	Havok_TagMember numBits of type int
	 */
	public hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator(Havok_TagObject item) {
		//item.outputOutline();
		int memberIdx = 0;
		Havok_TagObject value = item.listObjectClass.get(memberIdx++);
		int arrSize = value.listObjectArray.size();
		words = new int[arrSize];
		for (int i = 0; i < arrSize; i++) {
			words[i] = value.listObjectArray.get(i).i_value;
		}

		numBits = item.listObjectClass.get(memberIdx++).i_value;
	}

}
