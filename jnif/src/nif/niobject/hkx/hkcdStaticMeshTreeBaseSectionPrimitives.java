package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdStaticMeshTreeBaseSectionPrimitives' version='0' signature='0x2b62bb35'>
	<members>
		<member name='data' type='hkUint32' offset='0' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hkcdStaticMeshTreeBaseSectionPrimitives {
	public int data;
	public hkcdStaticMeshTreeBaseSectionPrimitives(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);
		//<member name='data' type='hkUint32' offset='0' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		data = stream.getInt(0);
	}
}