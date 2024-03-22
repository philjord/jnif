package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdDynamicTreeDynamicStorage0hkcdDynamicTreeAnisotropicMetrichkcdDynamicTreeCodec32' version='0' signature='0x0e38e7e3' parent='hkcdDynamicTreeAnisotropicMetric'>
	<members>
		<member name='nodes' type='hkArray&lt;struct hkcdDynamicTreeCodec32&gt;' ctype='hkcdDynamicTreeCodec32' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='firstFree' type='hkUint16' offset='16' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hkcdDynamicTreeDynamicStorage0hkcdDynamicTreeAnisotropicMetrichkcdDynamicTreeCodec32 extends hkcdDynamicTreeAnisotropicMetric {
	hkcdDynamicTreeCodec32[] nodes;
	int firstFree;
	public hkcdDynamicTreeDynamicStorage0hkcdDynamicTreeAnisotropicMetrichkcdDynamicTreeCodec32(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, classOffset);
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		
		//<member name='nodes' type='hkArray&lt;struct hkcdDynamicTreeCodec32&gt;' ctype='hkcdDynamicTreeCodec32' offset='0' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		ByteBuffer file = connector.data.setup(classOffset + 0);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
		int arrSize = HKXReader.getSizeComponent(baseArrayBytes);
		if (arrSize > 0 &&arrSize<10000) {//FIXME: VaultTecVan01HulkStatic-bhkPhysicsSystem_4 has mega sizes
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 0;
			nodes = new hkcdDynamicTreeCodec32[arrSize];
			for (int i = 0; i < arrSize; i++) {
				nodes[i] = new hkcdDynamicTreeCodec32(connector, (int)arrValue.to + (i*hkcdDynamicTreeCodec32.size));
			}
		}
		
		//<member name='firstFree' type='hkUint16' offset='16' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>		 
		firstFree = Short.toUnsignedInt(stream.getShort(16));
	}
}