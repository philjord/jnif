package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagItem;

/**<class name='hkReferencedObject' version='0' signature='0xb70c7949' parent='hkBaseObject'>
	<members>
		<member name='memSizeAndRefCount' type='hkUint32' offset='8' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
	</members>
</class>


    /// - offset: 32bit: 4, 64bit:  8
    /// -  flags: `FLAGS_NONE|SERIALIZE_IGNORED`
    hkUint16 memSizeAndFlags;
    /// - offset: 32bit: 6, 64bit: 10
    /// -  flags: `FLAGS_NONE|SERIALIZE_IGNORED`
    hkUint16 referenceCount;
    
    // 64bit: 8 + 2 + 2 = 12 -> need 4bytes for 8bytes align hence the 16 bit start after this
    // 32bit: 4 + 2 + 2 = 8 -> align to 4 so no change needed
    
*/
public class hkReferencedObject extends hkBaseObject {
	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{
		boolean success = super.readFromStream(connector, stream, classOffset);
		return success;
	}
	
	
	/**
	Outline for Havok_TagType hkReferencedObject
	Havok_TagMember memSizeAndFlags of type hkUint16
	Havok_TagMember refCount of type hkUint16
	*/
	@Override
	public int readFromTAG0(Havok_TagItem item) {
		//don't call super cos that's catches unimplemented classes super.readFromTAG0(item);
		// we don't actually bother as they are both 0 at this point ready for the engine to fill up
		return 2;
	}
	
}