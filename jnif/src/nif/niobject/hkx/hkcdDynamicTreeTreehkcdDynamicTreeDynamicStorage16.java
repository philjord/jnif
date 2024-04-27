package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdDynamicTreeTreehkcdDynamicTreeDynamicStorage16' version='0' signature='0xa4c4875a' parent='hkcdDynamicTreeDynamicStorage16'>
	<members>
		<member name='numLeaves' type='hkUint32' offset='24' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='path' type='hkUint32' offset='28' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='root' type='hkUint16' offset='32' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/
public class hkcdDynamicTreeTreehkcdDynamicTreeDynamicStorage16 extends hkcdDynamicTreeDynamicStorage16 {
	int numLeaves;
	int path;
	int root;
	public hkcdDynamicTreeTreehkcdDynamicTreeDynamicStorage16(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, stream, classOffset);
		
		//<member name='numLeaves' type='hkUint32' offset='24' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		numLeaves = stream.getInt(classOffset + 24);
		//<member name='path' type='hkUint32' offset='28' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		path = stream.getInt(classOffset + 28);
		//<member name='root' type='hkUint16' offset='32' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		root = Short.toUnsignedInt(stream.getShort(classOffset + 32));
	}
}