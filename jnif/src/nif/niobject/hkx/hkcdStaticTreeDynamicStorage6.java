package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdStaticTreeDynamicStorage6' version='0' signature='0xb875a652' parent='hkcdStaticTreeDynamicStoragehkcdStaticTreeCodec3Axis6'>
	<members>
	</members>
</struct>*/
public class hkcdStaticTreeDynamicStorage6 extends hkcdStaticTreeDynamicStoragehkcdStaticTreeCodec3Axis6 {
		
	public hkcdStaticTreeDynamicStorage6(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, stream, classOffset);		
	}
}