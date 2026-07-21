package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkaSplineCompressedAnimation' version='0' signature='0x8c3b5f7e' parent='hkaAnimation'>
	<members>
		<member name='numFrames' type='hkInt32' offset='56' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='numBlocks' type='hkInt32' offset='60' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='maxFramesPerBlock' type='hkInt32' offset='64' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='maskAndQuantizationSize' type='hkInt32' offset='68' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='blockDuration' type='hkReal' offset='72' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='blockInverseDuration' type='hkReal' offset='76' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='frameDuration' type='hkReal' offset='80' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='blockOffsets' type='hkArray&lt;hkUint32&gt;' offset='88' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT32' arrsize='0' flags='FLAGS_NONE'/>
		<member name='floatBlockOffsets' type='hkArray&lt;hkUint32&gt;' offset='104' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT32' arrsize='0' flags='FLAGS_NONE'/>
		<member name='transformOffsets' type='hkArray&lt;hkUint32&gt;' offset='120' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT32' arrsize='0' flags='FLAGS_NONE'/>
		<member name='floatOffsets' type='hkArray&lt;hkUint32&gt;' offset='136' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT32' arrsize='0' flags='FLAGS_NONE'/>
		<member name='data' type='hkArray&lt;hkUint8&gt;' offset='152' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		<member name='endian' type='hkInt32' offset='168' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/
public class hkaSplineCompressedAnimation extends hkaAnimation {

	public int		numFrames;
	public int		numBlocks;
	public int		maxFramesPerBlock;
	public int		maskAndQuantizationSize;
	public float	blockDuration;
	public float	blockInverseDuration;
	public float	frameDuration;
	public int[]	blockOffsets;
	public int[]	floatBlockOffsets;
	public int[]	transformOffsets;
	public int[]	floatOffsets;
	public byte[]	data;
	public int		endian;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		numFrames = stream.getInt(classOffset + 56);
		numBlocks = stream.getInt(classOffset + 60);
		maxFramesPerBlock = stream.getInt(classOffset + 64);
		maskAndQuantizationSize = stream.getInt(classOffset + 68);
		blockDuration = stream.getFloat(classOffset + 72);
		blockInverseDuration = stream.getFloat(classOffset + 76);
		frameDuration = stream.getFloat(classOffset + 80);

		ByteBuffer file = connector.data.setup(classOffset + 88);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 88;
			ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			blockOffsets = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				blockOffsets[i] = s2.getInt(i * 4);
			}
		}

		file = connector.data.setup(classOffset + 104);
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 104;
			ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			floatBlockOffsets = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				floatBlockOffsets[i] = s2.getInt(i * 4);
			}
		}
		file = connector.data.setup(classOffset + 120);
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 120;
			ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			transformOffsets = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				transformOffsets[i] = s2.getInt(i * 4);
			}
		}
		file = connector.data.setup(classOffset + 136);
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 136;
			ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			floatOffsets = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				floatOffsets[i] = s2.getInt(i * 4);
			}
		}

		file = connector.data.setup(classOffset + 152);
		file.get(baseArrayBytes);
		arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 152;
			ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
			data = new byte[arrSize];
			for (int i = 0; i < arrSize; i++) {
				data[i] = s2.get(i * 1);
			}
		}
		endian = stream.getInt(classOffset + 168);
		return success;
	}

}