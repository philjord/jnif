package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**
 * <struct name='hkcdStaticMeshTreeBaseSectionPrimitives' version='0' signature='0x2b62bb35'>
	<members>
		<member name='data' type='hkUint32' offset='0' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/

public class hkcdStaticMeshTreeBaseSectionPrimitives {
	public int data;

	public hkcdStaticMeshTreeBaseSectionPrimitives(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		data = stream.getInt(classOffset + 0);
	}

	public hkcdStaticMeshTreeBaseSectionPrimitives(Havok_TagObject item) {
		//item.outputOutline();
		int memberIdx = 0;
		data = item.listObjectClass.get(memberIdx++).i_value;
	}
}