package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<class name='hknpSphereShape' version='0' signature='0x741e9012' parent='hknpConvexShape'>
	<members>
	</members>
</class>*/
public class hknpSphereShape extends hknpConvexShape {
	
	
	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
				
		return success;
	}
}