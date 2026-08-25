package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**<struct name='hknpCompressedMeshShapeTree' version='0' signature='0xed062659' parent='hkcdStaticMeshTreehkcdStaticMeshTreeCommonConfigunsignedintunsignedlonglong1121hknpCompressedMeshShapeTreeDataRun'>
	<members>
	</members>
</struct>*/

public class hknpCompressedMeshShapeTree extends hkcdStaticMeshTreehkcdStaticMeshTreeCommonConfigunsignedintunsignedlonglong1121hknpCompressedMeshShapeTreeDataRun {
	public hknpCompressedMeshShapeTree(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, stream, classOffset);

	}

	/**
	Outline for Havok_TagType hknpCompressedMeshShapeTree
	*/
	public hknpCompressedMeshShapeTree(Havok_TagObject item) {
		super(item);			
	}
}