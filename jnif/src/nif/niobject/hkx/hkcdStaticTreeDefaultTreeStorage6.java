package nif.niobject.hkx;

import java.io.IOException;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdStaticTreeDefaultTreeStorage6' version='0' signature='0x33735476' parent='hkcdStaticTreeTreehkcdStaticTreeDynamicStorage6'>
	<members>
	</members>
</struct>*/

public class hkcdStaticTreeDefaultTreeStorage6 extends hkcdStaticTreeTreehkcdStaticTreeDynamicStorage6 {
		
	public hkcdStaticTreeDefaultTreeStorage6(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, classOffset);
		//ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
	}
}