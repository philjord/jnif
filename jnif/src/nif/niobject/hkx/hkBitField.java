package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**
 * <struct name='hkBitField' version='2' signature='0xe5dbbb9c' parent='hkBitFieldBasehkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator'> 
 * <members> 
 * </members> 
 * </struct>
 */

public class hkBitField extends hkBitFieldBasehkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator {
	public hkBitField(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		super(connector, stream, classOffset);
	}

	/**
	 Outline for Havok_TagType hkBitField
	 */
	public hkBitField(Havok_TagObject item) {
		super(item);
	}
}