package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagItem;

/**<class name='hkBaseObject' version='0' signature='0xe0708a00'>
	<members>
	</members>
</class>
*/
public class hkBaseObject {

	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {

		// hkReferencedObject starts at offset 8 
		//https://github.com/SARDONYX-sard/serde-hkx/tree/0.1.0/docs/handson_hex_dump/defaultmale
		//states 
		/// The class size is pointer size.
		/// - size: 32bit: 4, 64bit: 8

		return true;
	}

	/**
	 * 
	 * @param item
	 * @return number of member fields decoded
	 */
	public int readFromTAG0(Havok_TagItem item) {
		item.outputOutline();
		return -1;//indicate no implementation
	}

}