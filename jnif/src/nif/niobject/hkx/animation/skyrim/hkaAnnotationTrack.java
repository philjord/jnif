package nif.niobject.hkx.animation.skyrim;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkaAnnotationTrack' version='0' signature='0xd4114fdd'>
	<members>
		<member name='trackName' type='hkStringPtr' offset='0' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='annotations' type='hkArray&lt;struct hkaAnnotationTrackAnnotation&gt;' ctype='hkaAnnotationTrackAnnotation' offset='8' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hkaAnnotationTrack {
	public static final int					size	= 8 + 16;
	public String							trackName;
	public hkaAnnotationTrackAnnotation[]	annotations;

	public hkaAnnotationTrack(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		trackName = HKXReader.hkStringPtr(connector, classOffset + 0);

		ByteBuffer file = connector.data.setup(classOffset + 8);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 8;
			annotations = new hkaAnnotationTrackAnnotation[arrSize];
			for (int i = 0; i < arrSize; i++) {
				annotations[i] = new hkaAnnotationTrackAnnotation(connector, stream,
						(int)arrValue.to + (i * hkaAnnotationTrackAnnotation.size));
			}
		}
	}

}