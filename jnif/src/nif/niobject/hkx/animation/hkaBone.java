package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkaBone' version='0' signature='0x35912f8a'>
	<members>
		<member name='name' type='hkStringPtr' offset='0' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='lockTranslation' type='hkBool' offset='8' vtype='TYPE_BOOL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hkaBone {
	public static final int	size	= 8 + 8;
	public static final int	size32	= 4 + 4;
	public String			name;

	public boolean			lockTranslation;

	public hkaBone(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		if (connector.header.is64bit) {
			name = HKXReader.hkStringPtr(connector, classOffset + 0);
			lockTranslation = stream.get(classOffset + 8) != 0;
		} else {
			name = HKXReader.hkStringPtr(connector, classOffset + 0);
			lockTranslation = stream.get(classOffset + 4) != 0;
		}
	}

}