package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**<struct name='hknpCompressedMeshShapeTreeDataRun' version='0' signature='0xd6dfcdd7' parent='hkcdStaticMeshTreeBasePrimitiveDataRunBasehknpCompressedMeshShapeTreeDataRunData'>
	<members>
	</members>
</struct>
*/
public class hknpCompressedMeshShapeTreeDataRun extends hkcdStaticMeshTreeBasePrimitiveDataRunBasehknpCompressedMeshShapeTreeDataRunData {
	public static final int size = 4;

	public hknpCompressedMeshShapeTreeDataRun(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, stream, classOffset);		
	}

	/**
	 Outline for Havok_TagType hknpCompressedMeshShapeTreeDataRun
	 */
	public hknpCompressedMeshShapeTreeDataRun(Havok_TagObject item) {
		super(item);
		//item.outputOutline();

	
	}
}