package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<class name='hknpCompoundShape' version='2' signature='0x247d5e99' parent='hknpCompositeShape'>
	<members>
		<member name='instances' type='struct hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance' ctype='hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance' offset='96' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='ALIGN_16'/>
		<member name='aabb' type='struct hkAabb' ctype='hkAabb' offset='128' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='isMutable' type='hkBool' offset='160' vtype='TYPE_BOOL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='mutationSignals' type='struct hknpShapeSignals' ctype='hknpShapeSignals' offset='168' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
	</members>
</class>*/
public class hknpCompoundShape extends hknpCompositeShape {	
	
	public hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance instances;
	public hkAabb aabb;
	public boolean isMutable;
	public hknpShapeSignals mutationSignals;
	
	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
				
		//<member name='instances' type='struct hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance' ctype='hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance' offset='96' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='ALIGN_16'/>
		instances = new hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance(connector, stream, classOffset + 96);	
		//<member name='aabb' type='struct hkAabb' ctype='hkAabb' offset='128' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		aabb = new hkAabb(connector, stream, classOffset + 128);
		//<member name='isMutable' type='hkBool' offset='160' vtype='TYPE_BOOL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		isMutable = stream.get(classOffset + 160) != 0;	
		//<member name='mutationSignals' type='struct hknpShapeSignals' ctype='hknpShapeSignals' offset='168' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
		mutationSignals = new hknpShapeSignals(connector, stream, classOffset + 168);		
		
		return success;
		
	}

}