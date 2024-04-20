package nif.niobject.hkx;

import java.io.IOException;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdStaticTreeTreehkcdStaticTreeDynamicStorage6' version='0' signature='0xcf67d7ac' parent='hkcdStaticTreeDynamicStorage6'>
	<members>
		<member name='domain' type='struct hkAabb' ctype='hkAabb' offset='16' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hkcdStaticTreeTreehkcdStaticTreeDynamicStorage6 extends hkcdStaticTreeDynamicStorage6 {
	public hkAabb domain;
	public hkcdStaticTreeTreehkcdStaticTreeDynamicStorage6(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, classOffset);
		//ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		//<member name='domain' type='struct hkAabb' ctype='hkAabb' offset='16' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		domain = new hkAabb(connector, classOffset + 16);
	}
}