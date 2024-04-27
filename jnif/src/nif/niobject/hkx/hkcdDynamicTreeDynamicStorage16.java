package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdDynamicTreeDynamicStorage16' version='0' signature='0x62b579b8' parent='hkcdDynamicTreeDefaultDynamicStoragehkcdDynamicTreeCodec32'>
	<members>
	</members>
</struct>*/
public class hkcdDynamicTreeDynamicStorage16 extends hkcdDynamicTreeDefaultDynamicStoragehkcdDynamicTreeCodec32 {
		
	public hkcdDynamicTreeDynamicStorage16(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, stream, classOffset);	
	}
}