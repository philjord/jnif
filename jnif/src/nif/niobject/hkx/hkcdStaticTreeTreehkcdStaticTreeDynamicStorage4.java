package nif.niobject.hkx;

import java.io.IOException;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdStaticTreeTreehkcdStaticTreeDynamicStorage4' version='0' signature='0xe603f6aa' parent='hkcdStaticTreeDynamicStorage4'>
	<members>
		<member name='domain' type='struct hkAabb' ctype='hkAabb' offset='16' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hkcdStaticTreeTreehkcdStaticTreeDynamicStorage4 extends hkcdStaticTreeDynamicStorage4 {
	public hkAabb domain;
	public hkcdStaticTreeTreehkcdStaticTreeDynamicStorage4(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, classOffset);
		
		//<member name='domain' type='struct hkAabb' ctype='hkAabb' offset='16' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		domain = new hkAabb(connector, classOffset + 16);
	}
}
		