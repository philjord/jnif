package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**
<struct name='hkcdStaticTreeTreehkcdStaticTreeDynamicStorage5' version='0' signature='0x1cfe2fb6' parent='hkcdStaticTreeDynamicStorage5'>
	<members>
		<member name='domain' type='struct hkAabb' ctype='hkAabb' offset='16' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/

public class hkcdStaticTreeTreehkcdStaticTreeDynamicStorage5 extends hkcdStaticTreeDynamicStorage5 {
	public hkAabb domain;

	public hkcdStaticTreeTreehkcdStaticTreeDynamicStorage5(	HKXReaderConnector connector, ByteBuffer stream,
															int classOffset)
			throws IOException, InvalidPositionException {
		super(connector, stream, classOffset);

		domain = new hkAabb(connector, stream, classOffset + 16);
	}

	/**
	Outline for Havok_TagType hkcdStaticTree::Tree
	Havok_TagMember domain of type hkAabb
	*/
	public hkcdStaticTreeTreehkcdStaticTreeDynamicStorage5(Havok_TagObject item) {

		super(item);
		//item.outputOutline();

		int memberIdx = 1;
		domain = new hkAabb(item.listObjectClass.get(memberIdx++));
	}
}