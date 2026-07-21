package nif.niobject.hkx.animation.skyrim;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkaAnnotationTrackAnnotation' version='0' signature='0x623bf34f'>
	<members>
		<member name='time' type='hkReal' offset='0' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='text' type='hkStringPtr' offset='8' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hkaAnnotationTrackAnnotation {
	public static final int	size	= 8 + 8;
	public float			time;
	public String			text;

	public hkaAnnotationTrackAnnotation(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		time = stream.getFloat(classOffset + 0); // 8 bytes diff suggest a double?
		text = HKXReader.hkStringPtr(connector, classOffset + 8);
	}

}