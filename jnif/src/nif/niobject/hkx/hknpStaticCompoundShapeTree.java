package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hknpStaticCompoundShapeTree' version='0' signature='0x357ccb79' parent='hkcdStaticTreeDefaultTreeStorage6'>
	<members>
	</members>
</struct>*/
public class hknpStaticCompoundShapeTree extends hkcdStaticTreeDefaultTreeStorage6 {
		
	public hknpStaticCompoundShapeTree(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, stream, classOffset);		
	}
}