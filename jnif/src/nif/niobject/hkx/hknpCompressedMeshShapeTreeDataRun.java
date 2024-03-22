package nif.niobject.hkx;

import java.io.IOException;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hknpCompressedMeshShapeTreeDataRun' version='0' signature='0xd6dfcdd7' parent='hkcdStaticMeshTreeBasePrimitiveDataRunBasehknpCompressedMeshShapeTreeDataRunData'>
	<members>
	</members>
</struct>
*/
public class hknpCompressedMeshShapeTreeDataRun extends hkcdStaticMeshTreeBasePrimitiveDataRunBasehknpCompressedMeshShapeTreeDataRunData {
	public static final int size = 4;

	public hknpCompressedMeshShapeTreeDataRun(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, classOffset);
		//ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);
	}
}