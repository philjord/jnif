package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.compound.NifMatrix44;
import nif.compound.NifVector4;
import nif.niobject.hkx.reader.DataExternal;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hknpShapeInstance' version='1' signature='0x5e3dae05'>
	<enums>
		<enum name='Flags' flags='00000000'>
			<enumitem name='HAS_TRANSLATION' value='2'/>
			<enumitem name='HAS_ROTATION' value='4'/>
			<enumitem name='HAS_SCALE' value='8'/>
			<enumitem name='FLIP_ORIENTATION' value='16'/>
			<enumitem name='SCALE_SURFACE' value='32'/>
			<enumitem name='IS_ENABLED' value='64'/>
			<enumitem name='DEFAULT_FLAGS' value='64'/>
		</enum>
	</enums>
	<members>
		<member name='transform' type='hkTransform' offset='0' vtype='TYPE_TRANSFORM' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='scale' type='hkVector4' offset='64' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='shape' type='struct hknpShape*' ctype='hknpShape' offset='80' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='shapeTag' type='hkUint16' offset='88' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='destructionTag' type='hkUint16' offset='90' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='padding' type='hkUint8[30]' offset='92' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='30' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hknpShapeInstance  {
	public static final int size = 122;
	NifMatrix44 transform;		//FIXME! presumably 4x4 = 16 floats = 16 4 byte floats?
	NifVector4 scale;
	long shape;
	int shapeTag;
	int destructionTag;
	int[] padding = new int[30];
	
	public hknpShapeInstance(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		
		//<member name='transform' type='hkTransform' offset='0' vtype='TYPE_TRANSFORM' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		transform = new NifMatrix44(stream, 0);
		//<member name='scale' type='hkVector4' offset='64' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		scale = new NifVector4(stream, 64);
		//<member name='shape' type='struct hknpShape*' ctype='hknpShape' offset='80' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		DataExternal de = connector.data2.readNext();
		if (de.from == classOffset + 80) {
			shape = de.to;
		} else {
			connector.data2.backtrack();
		}
		//<member name='shapeTag' type='hkUint16' offset='88' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		shapeTag = Short.toUnsignedInt(stream.getShort(88));
		//<member name='destructionTag' type='hkUint16' offset='90' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		destructionTag = Short.toUnsignedInt(stream.getShort(90));
		//<member name='padding' type='hkUint8[30]' offset='92' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='30' flags='FLAGS_NONE'/>
		for(int i = 0; i < 30; i++) {
			padding[i] = Byte.toUnsignedInt(stream.get(92+i));
		}
		
		
	}
}