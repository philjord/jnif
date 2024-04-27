package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.byteutils.ByteUtils;

 

/**<class name='hkBaseObject' version='0' signature='0xe0708a00'>
	<members>
	</members>
</class>
*/
public class hkBaseObject {
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{
		return true;
	}

	// util for array size getting
	// offset should be classOffset+something
	protected static int getArraySize(HKXReaderConnector connector, int offset) throws InvalidPositionException {
		ByteBuffer stream = connector.data.setup(offset);
		byte[] sizeSpecificBytes = new byte[4];
		for( int i = 0; i < sizeSpecificBytes.length; i++)
			sizeSpecificBytes[i] = stream.get(offset + 8 + i);//8,9,10,11 are an uint of array size		 
		return ByteUtils.getUInt(sizeSpecificBytes);		
		// SEE return HKXReader.getSizeComponent(baseArrayBytes);
	}
}