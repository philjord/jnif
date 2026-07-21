package nif.niobject.hkx.animation.skyrim;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkQTransform' version='2' signature='0x6406e73b' parent='hkQTransformf'>
	<members>
	</members>
</struct>
*/
public class hkQsTransform extends hkQsTransformf {

	public hkQsTransform(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		super(connector, stream, classOffset);
	}

}