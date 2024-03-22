package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hknpDynamicCompoundShapeTree' version='0' signature='0x41084dbd' parent='hkcdDynamicTreeDefaultTree32Storage'>
	<members>
	</members>
</struct>*/

public class hknpDynamicCompoundShapeTree extends hkcdDynamicTreeDefaultTree32Storage {
		
	public hknpDynamicCompoundShapeTree(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, classOffset);
		//ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
	}
}