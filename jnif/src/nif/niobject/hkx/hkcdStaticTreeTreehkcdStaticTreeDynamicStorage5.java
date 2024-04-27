package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkcdStaticTreeTreehkcdStaticTreeDynamicStorage5' version='0' signature='0x1cfe2fb6' parent='hkcdStaticTreeDynamicStorage5'>
	<members>
		<member name='domain' type='struct hkAabb' ctype='hkAabb' offset='16' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/

public class hkcdStaticTreeTreehkcdStaticTreeDynamicStorage5 extends hkcdStaticTreeDynamicStorage5 {
	public hkAabb domain;
	public hkcdStaticTreeTreehkcdStaticTreeDynamicStorage5(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, stream, classOffset);
		
		//<member name='domain' type='struct hkAabb' ctype='hkAabb' offset='16' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		domain = new hkAabb(connector, stream, classOffset + 16);
	}
}