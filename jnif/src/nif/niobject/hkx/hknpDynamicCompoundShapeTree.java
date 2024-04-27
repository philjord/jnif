package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hknpDynamicCompoundShapeTree' version='0' signature='0x41084dbd' parent='hkcdDynamicTreeDefaultTree32Storage'>
	<members>
	</members>
</struct>*/

public class hknpDynamicCompoundShapeTree extends hkcdDynamicTreeDefaultTree32Storage {
		
	public hknpDynamicCompoundShapeTree(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, stream, classOffset);		
	}
}