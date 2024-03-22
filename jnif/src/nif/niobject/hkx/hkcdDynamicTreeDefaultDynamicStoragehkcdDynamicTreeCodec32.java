package nif.niobject.hkx;

import java.io.IOException;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdDynamicTreeDefaultDynamicStoragehkcdDynamicTreeCodec32' version='0' signature='0x00514452' parent='hkcdDynamicTreeDynamicStorage0hkcdDynamicTreeAnisotropicMetrichkcdDynamicTreeCodec32'>
	<members>
	</members>
</struct>*/
public class hkcdDynamicTreeDefaultDynamicStoragehkcdDynamicTreeCodec32 extends hkcdDynamicTreeDynamicStorage0hkcdDynamicTreeAnisotropicMetrichkcdDynamicTreeCodec32 {
		
	public hkcdDynamicTreeDefaultDynamicStoragehkcdDynamicTreeCodec32(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, classOffset);
		//ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
	}
}