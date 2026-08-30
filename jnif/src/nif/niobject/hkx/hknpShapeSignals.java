package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**
<struct name='hknpShapeSignals' version='0' signature='0xc18bf544'>
	<enums>
		<enum name='MutationFlagsEnum' flags='00000000'>
			<enumitem name='MUTATION_AABB_CHANGED' value='1'/>
			<enumitem name='MUTATION_UPDATE_COLLISION_CACHES' value='2'/>
			<enumitem name='MUTATION_REBUILD_COLLISION_CACHES' value='4'/>
		</enum>
	</enums>
	<members>
		<member name='shapeMutated' type='void*' offset='0' vtype='TYPE_POINTER' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
		<member name='shapeDestroyed' type='void*' offset='8' vtype='TYPE_POINTER' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
	</members>
</struct>
*/
public class hknpShapeSignals {
	public long	shapeMutated;
	public long	shapeDestroyed;

	public hknpShapeSignals(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		shapeMutated = HKXReader.getPointer(connector, classOffset + 0);
		shapeDestroyed = HKXReader.getPointer(connector, classOffset + 8);
	}

	/**
	 Outline for Havok_TagType hknpShapeSignals
	Havok_TagMember shapeMutated of type T*
	Havok_TagMember shapeDestroyed of type T*
	 */
	public hknpShapeSignals(Havok_TagObject item) {
		//item.outputOutline();
		shapeMutated = TAG0Reader.getRefPtr(item.listObjectClass.get(0));
		shapeDestroyed = TAG0Reader.getRefPtr(item.listObjectClass.get(1));
	}
}