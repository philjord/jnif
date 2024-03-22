package nif.niobject.hkx;

import java.io.IOException;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdDynamicTreeDefaultTree32Storage' version='0' signature='0xfa3c88f4' parent='hkcdDynamicTreeTreehkcdDynamicTreeDynamicStorage16'>
	<members>
	</members>
</struct>*/
public class hkcdDynamicTreeDefaultTree32Storage extends hkcdDynamicTreeTreehkcdDynamicTreeDynamicStorage16 {
		
	public hkcdDynamicTreeDefaultTree32Storage(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, classOffset);
		//ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
	}
}