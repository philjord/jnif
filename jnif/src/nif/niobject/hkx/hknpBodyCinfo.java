package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.compound.NifQuaternionXYZW;
import nif.compound.NifVector4;
import nif.niobject.hkx.reader.DataExternal;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.byteutils.ByteUtils;

/**
<struct name='hknpBodyCinfo' version='2' signature='0x6896f7c9'>
	<members>
		<member name='shape' type='struct hknpShape*' ctype='hknpShape' offset='0' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='NOT_OWNED'/>
		<member name='reservedBodyId' type='hkUint32' offset='8' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='motionId' type='hkUint32' offset='12' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='qualityId' type='hkUint8' offset='16' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='materialId' type='hkUint16' offset='18' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='collisionFilterInfo' type='hkUint32' offset='20' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='0'/>
		<member name='flags' type='hkInt32' offset='24' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='0'/>
		<member name='collisionLookAheadDistance' type='hkReal' offset='28' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='0.000000'/>
		<member name='name' type='hkStringPtr' offset='32' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='userData' type='hkUint64' offset='40' vtype='TYPE_UINT64' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='position' type='hkVector4' offset='48' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='orientation' type='hkQuaternion' offset='64' vtype='TYPE_QUATERNION' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='spuFlags' type='flags SpuFlagsEnum' etype='SpuFlagsEnum' offset='80' vtype='TYPE_FLAGS' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		<member name='localFrame' type='struct hkLocalFrame*' ctype='hkLocalFrame' offset='88' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/
public class hknpBodyCinfo  {
	public static final int size = 88 + 8;//vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' is 8 long as given by the first shape
	public long shape;
	public int reservedBodyId;
	public int motionId;
	public int qualityId;
	public int materialId;
	public int collisionFilterInfo;
	public int flags;
	public float collisionLookAheadDistance;
	public String name;
	public long userData;
	public NifVector4 position;
	public NifQuaternionXYZW orientation;
	public byte spuFlags;
	public long localFrame;
	
	public hknpBodyCinfo(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);

		//<member name='shape' type='struct hknpShape*' ctype='hknpShape' offset='0' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='NOT_OWNED'/>
		DataExternal data = connector.data2.readNext();
		if (data.from == classOffset + 0) {
			shape = data.to;
		} else {
			connector.data2.backtrack();
		}
		//<member name='reservedBodyId' type='hkUint32' offset='8' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		reservedBodyId = stream.getInt(8);
		//<member name='motionId' type='hkUint32' offset='12' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		motionId = stream.getInt(12);
		//<member name='qualityId' type='hkUint8' offset='16' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		qualityId = stream.get(16);
		//<member name='materialId' type='hkUint16' offset='18' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		materialId = Short.toUnsignedInt(stream.getShort(18));
		//<member name='collisionFilterInfo' type='hkUint32' offset='20' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='0'/>
		collisionFilterInfo = stream.getInt(20);
		//<member name='flags' type='hkInt32' offset='24' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='0'/>
		flags = stream.getInt(24);
		//<member name='collisionLookAheadDistance' type='hkReal' offset='28' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE' default='0.000000'/>
		collisionLookAheadDistance = stream.getFloat(28);
		//<member name='name' type='hkStringPtr' offset='32' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		name = "";
		try {
			DataInternal di = connector.data1.readNext();
			if (di.from == classOffset + 32) {
				ByteBuffer file2 = connector.data.setup(di.to);
				name = ByteUtils.readString(file2);
			} else {
				connector.data1.backtrack();
			}
		} catch (InvalidPositionException e) {
			// NO OP. Met when the last item of the HKX file is a String and is empty.
			name = "";
		}
		//<member name='userData' type='hkUint64' offset='40' vtype='TYPE_UINT64' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		userData = stream.getLong(40);
		//<member name='position' type='hkVector4' offset='48' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		position = new NifVector4(stream, 48);
		//<member name='orientation' type='hkQuaternion' offset='64' vtype='TYPE_QUATERNION' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		orientation = new NifQuaternionXYZW(stream, 64);
		
		//<member name='spuFlags' type='flags SpuFlagsEnum' etype='SpuFlagsEnum' offset='80' vtype='TYPE_FLAGS' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		spuFlags = stream.get(80);

		//<member name='localFrame' type='struct hkLocalFrame*' ctype='hkLocalFrame' offset='88' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		data = connector.data2.readNext();
		if (data.from == classOffset + 88) {
			localFrame = data.to;
		} else {
			connector.data2.backtrack();
		}
	}
	
}