package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hknpSparseCompactMapunsignedshort' version='0' signature='0x4558127c'>
	<members>
		<member name='secondaryKeyMask' type='hkUint32' offset='0' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='sencondaryKeyBits' type='hkUint32' offset='4' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='primaryKeyToIndex' type='hkArray&lt;hkUint16&gt;' offset='8' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT16' arrsize='0' flags='FLAGS_NONE'/>
		<member name='valueAndSecondaryKeys' type='hkArray&lt;hkUint16&gt;' offset='24' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT16' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/

public class hknpSparseCompactMapunsignedshort {
	int		secondaryKeyMask;
	int		sencondaryKeyBits;
	int[]	primaryKeyToIndex;
	int[]	valueAndSecondaryKeys;

	public hknpSparseCompactMapunsignedshort(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		secondaryKeyMask = stream.getInt(classOffset + 0);
		sencondaryKeyBits = stream.getInt(classOffset + 4);
		int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 8));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 8;
			primaryKeyToIndex = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				primaryKeyToIndex[i] = Short.toUnsignedInt(stream.getShort((int)arrValue.to + (i * 2)));
			}
		}

		arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 24));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 24;
			valueAndSecondaryKeys = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				valueAndSecondaryKeys[i] = Short.toUnsignedInt(stream.getShort((int)arrValue.to + (i * 2)));
			}
		}

	}

}