package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**
 * <struct name='hkBitFieldBasehkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator' version='0' signature='0x7538539b'>
 * <members>
		<member name='storage' type='struct hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator' ctype='hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator' offset='0' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hkBitFieldBasehkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator {
	hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator storage;

	public hkBitFieldBasehkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator(	HKXReaderConnector connector,
																						ByteBuffer stream,
																						int classOffset)
			throws IOException, InvalidPositionException {
		storage = new hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator(connector, stream, classOffset + 0);
	}

	/**
	 Outline for Havok_TagObject of type hkBitField
	Havok_TagType None
	Havok_TagType hkBitFieldBase
	Havok_TagMember storage of type hkBitFieldStorage
	Havok_TagType hkBitField
	 */
	public hkBitFieldBasehkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator(Havok_TagObject item) {
		//item.outputOutline();
		int memberIdx = 0;		
		storage = new hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator(item.listObjectClass.get(memberIdx++));
	}
}