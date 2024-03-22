package nif.niobject.hkx;

import java.io.IOException;

import nif.niobject.hkx.reader.DataExternal;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hknpShapeSignals' version='0' signature='0xc18bf544'>
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
</struct>*/
public class hknpShapeSignals {
	long shapeMutated;
	long shapeDestroyed;
	public hknpShapeSignals(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{

		//ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		
		//<member name='shapeMutated' type='void*' offset='0' vtype='TYPE_POINTER' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
		DataExternal de = connector.data2.readNext();
		if (de.from == classOffset + 0) {
			shapeMutated = de.to;
		} else {
			connector.data2.backtrack();
		}
		//<member name='shapeDestroyed' type='void*' offset='8' vtype='TYPE_POINTER' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
		de = connector.data2.readNext();
		if (de.from == classOffset + 8) {
			shapeDestroyed = de.to;
		} else {
			connector.data2.backtrack();
		}
	}
}