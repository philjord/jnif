package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.compound.NifQuaternionXYZW;
import nif.compound.NifVector4;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

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
public class hknpBodyCinfo {
	public static final int		size	= 88 + 8;			// 'TYPE_POINTER' size 8 for 64 bit (4 for 32)
	public long					shape;
	public int					reservedBodyId;
	public int					motionId;
	public int					qualityId;
	public int					materialId;
	public int					collisionFilterInfo;
	public int					flags;
	public float				collisionLookAheadDistance;
	public String				name;
	public long					userData;
	public NifVector4			position;
	public NifQuaternionXYZW	orientation;
	public byte					spuFlags;
	public long					localFrame;

	public hknpBodyCinfo(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		shape = HKXReader.getPointer(connector, classOffset + 0);
		reservedBodyId = stream.getInt(classOffset + 8);
		motionId = stream.getInt(classOffset + 12);
		qualityId = stream.get(classOffset + 16);
		materialId = Short.toUnsignedInt(stream.getShort(classOffset + 18));
		collisionFilterInfo = stream.getInt(classOffset + 20);
		flags = stream.getInt(classOffset + 24);
		collisionLookAheadDistance = stream.getFloat(classOffset + 28);
		name = HKXReader.hkStringPtr(connector, classOffset + 32);
		userData = stream.getLong(classOffset + 40);
		position = new NifVector4(stream, classOffset + 48);
		orientation = new NifQuaternionXYZW(stream, classOffset + 64);
		spuFlags = stream.get(classOffset + 80);
		localFrame = HKXReader.getPointer(connector, classOffset + 88);

	}

	
	/**			
		Outline for Havok_TagType hknpBodyCinfo
		Havok_TagMember shape of type hkViewPtr
		Havok_TagMember flags of type int
		Havok_TagMember collisionCntrl of type short
		Havok_TagMember collisionFilterInfo of type hkUint32
		Havok_TagMember materialId of type unsigned short
		Havok_TagMember qualityId of type unsigned char
		Havok_TagMember name of type hkStringPtr
		Havok_TagMember userData of type hkUint64
		Havok_TagMember motionType of type unsigned char
		Havok_TagMember position of type hkVector4
		Havok_TagMember orientation of type hkQuaternion
		Havok_TagMember linearVelocity of type hkVector4
		Havok_TagMember angularVelocity of type hkVector4
		Havok_TagMember mass of type hkReal
		Havok_TagMember massDistribution of type hkRefPtr
		Havok_TagMember motionPropertiesId of type unsigned short
		Havok_TagMember reservedBodyId of type hknpBodyId
		Havok_TagMember reservedMotionId of type unsigned int
		Havok_TagMember collisionLookAheadDistance of type hkReal
		Havok_TagMember localFrame of type hkRefPtr
		*/
	public int collisionCntrl;
	public byte motionType;
	public NifVector4			linearVelocity;
	public NifVector4			angularVelocity;
	public float mass;
	public long massDistribution;
	public int reservedMotionId;
	
	public hknpBodyCinfo(Havok_TagObject item) {
		//item.outputOutline();
	
		
		int memberIdx = 0;			 
		shape = TAG0Reader.getRefPtr(item.listObjectClass.get(memberIdx++));
		flags = item.listObjectClass.get(memberIdx++).i_value;
		collisionCntrl = item.listObjectClass.get(memberIdx++).i_value;
		collisionFilterInfo  = item.listObjectClass.get(memberIdx++).i_value;
		materialId  = item.listObjectClass.get(memberIdx++).i_value;
		qualityId  = item.listObjectClass.get(memberIdx++).i_value;
		name  = item.listObjectClass.get(memberIdx++).s_value;
		userData  = item.listObjectClass.get(memberIdx++).i_value;
		motionType = (byte)item.listObjectClass.get(memberIdx++).i_value;
		position = new NifVector4(item.listObjectClass.get(memberIdx++).listObjectTuple);
		orientation = new NifQuaternionXYZW(item.listObjectClass.get(memberIdx++).listObjectTuple);
		linearVelocity = new NifVector4(item.listObjectClass.get(memberIdx++).listObjectTuple);
		angularVelocity = new NifVector4(item.listObjectClass.get(memberIdx++).listObjectTuple);
		mass = item.listObjectClass.get(memberIdx++).f_value;
		massDistribution = TAG0Reader.getRefPtr(item.listObjectClass.get(memberIdx++));
		motionId = item.listObjectClass.get(memberIdx++).i_value;		//motionPropertiesId		
		reservedBodyId = item.listObjectClass.get(memberIdx++).i_value;
		reservedMotionId = item.listObjectClass.get(memberIdx++).i_value;		
		collisionLookAheadDistance  = item.listObjectClass.get(memberIdx++).f_value;			 
		localFrame  = TAG0Reader.getRefPtr(item.listObjectClass.get(memberIdx++));
	}

}