package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.DataExternal;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;


/**<class name='hknpShape' version='2' signature='0xdb52aabb' parent='hkReferencedObject'>
	<enums>
		<enum name='FlagsEnum' flags='00000000'>
			<enumitem name='IS_CONVEX_SHAPE' value='1'/>
			<enumitem name='IS_CONVEX_POLYTOPE_SHAPE' value='2'/>
			<enumitem name='IS_COMPOSITE_SHAPE' value='4'/>
			<enumitem name='IS_HEIGHT_FIELD_SHAPE' value='8'/>
			<enumitem name='USE_SINGLE_POINT_MANIFOLD' value='16'/>
			<enumitem name='IS_INTERIOR_TRIANGLE' value='32'/>
			<enumitem name='SUPPORTS_COLLISIONS_WITH_INTERIOR_TRIANGLES' value='64'/>
			<enumitem name='USE_NORMAL_TO_FIND_SUPPORT_PLANE' value='128'/>
			<enumitem name='USE_SMALL_FACE_INDICES' value='256'/>
			<enumitem name='NO_GET_SHAPE_KEYS_ON_SPU' value='512'/>
			<enumitem name='SHAPE_NOT_SUPPORTED_ON_SPU' value='1024'/>
			<enumitem name='IS_TRIANGLE_OR_QUAD_SHAPE' value='2048'/>
			<enumitem name='IS_QUAD_SHAPE' value='4096'/>
		</enum>
		<enum name='ScaleMode' flags='00000000'>
			<enumitem name='SCALE_SURFACE' value='0'/>
			<enumitem name='SCALE_VERTICES' value='1'/>
		</enum>
		<enum name='ConvexRadiusDisplayMode' flags='00000000'>
			<enumitem name='CONVEX_RADIUS_DISPLAY_NONE' value='0'/>
			<enumitem name='CONVEX_RADIUS_DISPLAY_PLANAR' value='1'/>
			<enumitem name='CONVEX_RADIUS_DISPLAY_ROUNDED' value='2'/>
		</enum>
	</enums>
	<members>
		<member name='flags' type='flags FlagsEnum' etype='FlagsEnum' offset='16' vtype='TYPE_FLAGS' vsubtype='TYPE_UINT16' arrsize='0' flags='ALIGN_16'/>
		<member name='numShapeKeyBits' type='hkUint8' offset='18' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='dispatchType' type='enum Enum' etype='Enum' offset='19' vtype='TYPE_ENUM' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		<member name='convexRadius' type='hkReal' offset='20' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='userData' type='hkUint64' offset='24' vtype='TYPE_UINT64' vsubtype='mkjhvTYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='properties' type='struct hkRefCountedProperties*' ctype='hkRefCountedProperties' offset='32' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

//bitwise flags!
 

enum ScaleMode {SCALE_SURFACE,SCALE_VERTICES};
enum ConvexRadiusDisplayMode {CONVEX_RADIUS_DISPLAY_NONE,CONVEX_RADIUS_DISPLAY_PLANAR,CONVEX_RADIUS_DISPLAY_ROUNDED};

public class hknpShape extends hkReferencedObject {
	public int flags;
	public int numShapeKeyBits;
	public int dispatchType;
	public float convexRadius;
	public long userData;
	
	public long properties;
	
	@Override
	public boolean readFromStream(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, classOffset);
		
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		
		//<member name='flags' type='flags FlagsEnum' etype='FlagsEnum' offset='16' vtype='TYPE_FLAGS' vsubtype='TYPE_UINT16' arrsize='0' flags='ALIGN_16'/>
		flags =  stream.getShort(16);
		//<member name='numShapeKeyBits' type='hkUint8' offset='18' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		numShapeKeyBits = stream.get(18);
		//<member name='dispatchType' type='enum Enum' etype='Enum' offset='19' vtype='TYPE_ENUM' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		dispatchType = stream.get(19);//enum of what?
		//<member name='convexRadius' type='hkReal' offset='20' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		convexRadius = stream.getFloat(20);//enum of what?
		//<member name='userData' type='hkUint64' offset='24' vtype='TYPE_UINT64' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		userData = stream.getLong(24);
		//<member name='properties' type='struct hkRefCountedProperties*' ctype='hkRefCountedProperties' offset='32' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		DataExternal data = connector.data2.readNext();
		if (data.from == classOffset + 32) {
			properties = data.to;
		} else {
			connector.data2.backtrack();
		}
		return success;
	}
}