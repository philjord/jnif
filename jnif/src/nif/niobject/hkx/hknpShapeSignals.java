package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

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
		//<member name='shapeMutated' type='void*' offset='0' vtype='TYPE_POINTER' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
		shapeMutated = HKXReader.getPointer(connector, classOffset + 0);
		//<member name='shapeDestroyed' type='void*' offset='8' vtype='TYPE_POINTER' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
		shapeDestroyed = HKXReader.getPointer(connector, classOffset + 8);
	}
}