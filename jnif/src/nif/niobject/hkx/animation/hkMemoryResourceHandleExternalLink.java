package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkMemoryResourceHandleExternalLink' version='1' signature='0x3144d17c'>
	<members>
		<member name='memberName' type='hkStringPtr' offset='0' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='externalId' type='hkStringPtr' offset='8' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hkMemoryResourceHandleExternalLink {
	public static final int	size	= 8 + 8;
	public String			memberName;
	public String			externalId;

	public hkMemoryResourceHandleExternalLink(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		memberName = HKXReader.hkStringPtr(connector, classOffset + 0);

		externalId = HKXReader.hkStringPtr(connector, classOffset + 8);
	}

}