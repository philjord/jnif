package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**
 * <struct name='hkcdStaticTreeDynamicStorage5' version='0' signature='0x6bec5e48' parent='hkcdStaticTreeDynamicStoragehkcdStaticTreeCodec3Axis5'>
	<members>
	</members>
</struct>
*/

public class hkcdStaticTreeDynamicStorage5 extends hkcdStaticTreeDynamicStoragehkcdStaticTreeCodec3Axis5 {
	public hkcdStaticTreeDynamicStorage5(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		super(connector, stream, classOffset);
	}

	/**
	Outline for Havok_TagType ??? nothing
	*/
	public hkcdStaticTreeDynamicStorage5(Havok_TagObject item) {
		super(item);	
	}
}