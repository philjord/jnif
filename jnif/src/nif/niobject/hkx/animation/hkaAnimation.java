package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.hkReferencedObject;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkaAnimation' version='3' signature='0xb545fe18' parent='hkReferencedObject'>
	<enums>
		<enum name='AnimationType' flags='00000000'>
			<enumitem name='HK_UNKNOWN_ANIMATION' value='0'/>
			<enumitem name='HK_INTERLEAVED_ANIMATION' value='1'/>
			<enumitem name='HK_MIRRORED_ANIMATION' value='2'/>
			<enumitem name='HK_SPLINE_COMPRESSED_ANIMATION' value='3'/>
			<enumitem name='HK_QUANTIZED_COMPRESSED_ANIMATION' value='4'/>
			<enumitem name='HK_PREDICTIVE_COMPRESSED_ANIMATION' value='5'/>
			<enumitem name='HK_REFERENCE_POSE_ANIMATION' value='6'/>
		</enum>
	</enums>
	<members>
		<member name='type' type='enum AnimationType' etype='AnimationType' offset='16' vtype='TYPE_ENUM' vsubtype='TYPE_INT32' arrsize='0' flags='FLAGS_NONE'/>
		<member name='duration' type='hkReal' offset='20' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='numberOfTransformTracks' type='hkInt32' offset='24' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='numberOfFloatTracks' type='hkInt32' offset='28' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='extractedMotion' type='struct hkaAnimatedReferenceFrame*' ctype='hkaAnimatedReferenceFrame' offset='32' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='annotationTracks' type='hkArray&lt;struct hkaAnnotationTrack&gt;' ctype='hkaAnnotationTrack' offset='40' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hkaAnimation extends hkReferencedObject {
	enum AnimationType {
		HK_UNKNOWN_ANIMATION, HK_INTERLEAVED_ANIMATION, HK_MIRRORED_ANIMATION, HK_SPLINE_COMPRESSED_ANIMATION, HK_QUANTIZED_COMPRESSED_ANIMATION, HK_PREDICTIVE_COMPRESSED_ANIMATION, HK_REFERENCE_POSE_ANIMATION
	};

	public AnimationType		type;
	public float				duration;
	public int					numberOfTransformTracks;
	public int					numberOfFloatTracks;
	public long					extractedMotion;
	public hkaAnnotationTrack[]	annotationTracks;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		if (connector.header.is64bit) {
			int typev = stream.getInt(classOffset + 16);

			duration = stream.getFloat(classOffset + 20);
			numberOfTransformTracks = stream.getInt(classOffset + 24);
			numberOfFloatTracks = stream.getInt(classOffset + 28);
			extractedMotion = HKXReader.getPointer(connector, classOffset + 32);

			int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 40));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 40;
				annotationTracks = new hkaAnnotationTrack[arrSize];
				for (int i = 0; i < arrSize; i++) {
					annotationTracks[i] = new hkaAnnotationTrack(connector, stream,
							(int)arrValue.to + (i * hkaAnnotationTrack.size));
				}
			}

		} else {
			int typev = stream.getInt(classOffset + 8);

			duration = stream.getFloat(classOffset + 12);
			numberOfTransformTracks = stream.getInt(classOffset + 16);
			numberOfFloatTracks = stream.getInt(classOffset + 20);
			extractedMotion = HKXReader.getPointer(connector, classOffset + 24);

			int arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 28));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 28;
				annotationTracks = new hkaAnnotationTrack[arrSize];
				for (int i = 0; i < arrSize; i++) {
					annotationTracks[i] = new hkaAnnotationTrack(connector, stream,
							(int)arrValue.to + (i * hkaAnnotationTrack.size32));
				}
			}
		}

		return success;
	}

}