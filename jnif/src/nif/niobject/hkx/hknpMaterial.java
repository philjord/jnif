package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;
import nif.tools.FP16;

/**
 * <struct name='hknpMaterial' version='1' signature='0xb7c5f24e'>
	<enums>
		<enum name='TriggerType' flags='00000000'>
			<enumitem name='TRIGGER_TYPE_NONE' value='0'/>
			<enumitem name='TRIGGER_TYPE_BROAD_PHASE' value='1'/>
			<enumitem name='TRIGGER_TYPE_NARROW_PHASE' value='2'/>
			<enumitem name='TRIGGER_TYPE_CONTACT_SOLVER' value='3'/>
		</enum>
		<enum name='CombinePolicy' flags='00000000'>
			<enumitem name='COMBINE_AVG' value='0'/>
			<enumitem name='COMBINE_MIN' value='1'/>
			<enumitem name='COMBINE_MAX' value='2'/>
		</enum>
		<enum name='MassChangerCategory' flags='00000000'>
			<enumitem name='MASS_CHANGER_IGNORE' value='0'/>
			<enumitem name='MASS_CHANGER_DEBRIS' value='1'/>
			<enumitem name='MASS_CHANGER_HEAVY' value='2'/>
		</enum>
	</enums>
	<members>
		<member name='name' type='hkStringPtr' offset='0' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='ALIGN_16'/>
		<member name='isExclusive' type='hkUint32' offset='8' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='flags' type='hkInt32' offset='12' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='triggerType' type='enum TriggerType' etype='TriggerType' offset='16' vtype='TYPE_ENUM' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		<member name='triggerManifoldTolerance' type='struct hkUFloat8' ctype='hkUFloat8' offset='17' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='dynamicFriction' type='hkHalf' offset='18' vtype='TYPE_HALF' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='staticFriction' type='hkHalf' offset='20' vtype='TYPE_HALF' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='restitution' type='hkHalf' offset='22' vtype='TYPE_HALF' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='frictionCombinePolicy' type='enum CombinePolicy' etype='CombinePolicy' offset='24' vtype='TYPE_ENUM' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		<member name='restitutionCombinePolicy' type='enum CombinePolicy' etype='CombinePolicy' offset='25' vtype='TYPE_ENUM' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		<member name='weldingTolerance' type='hkHalf' offset='26' vtype='TYPE_HALF' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='maxContactImpulse' type='hkReal' offset='28' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='fractionOfClippedImpulseToApply' type='hkReal' offset='32' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='massChangerCategory' type='enum MassChangerCategory' etype='MassChangerCategory' offset='36' vtype='TYPE_ENUM' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		<member name='massChangerHeavyObjectFactor' type='hkHalf' offset='38' vtype='TYPE_HALF' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='softContactForceFactor' type='hkHalf' offset='40' vtype='TYPE_HALF' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='softContactDampFactor' type='hkHalf' offset='42' vtype='TYPE_HALF' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='softContactSeperationVelocity' type='struct hkUFloat8' ctype='hkUFloat8' offset='44' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='surfaceVelocity' type='struct hknpSurfaceVelocity*' ctype='hknpSurfaceVelocity' offset='48' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='disablingCollisionsBetweenCvxCvxDynamicObjectsDistance' type='hkHalf' offset='56' vtype='TYPE_HALF' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='userData' type='hkUint64' offset='64' vtype='TYPE_UINT64' vsubtype='TYPE_VOID' arrsize='0' flags='ALIGN_8'/>
		<member name='isShared' type='hkBool' offset='72' vtype='TYPE_BOOL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/

public class hknpMaterial {
	enum TriggerType {
		TRIGGER_TYPE_NONE, TRIGGER_TYPE_BROAD_PHASE, TRIGGER_TYPE_NARROW_PHASE, TRIGGER_TYPE_CONTACT_SOLVER
	};

	enum CombinePolicy {
		COMBINE_AVG, COMBINE_MIN, COMBINE_MAX
	};

	enum MassChangerCategory {
		MASS_CHANGER_IGNORE, MASS_CHANGER_DEBRIS, MASS_CHANGER_HEAVY
	};

	public static final int		size	= 72 + 8;
	public String				name;
	public int					isExclusive;
	public int					flags;
	public TriggerType			triggerType;
	public byte					triggerManifoldTolerance;
	public float				dynamicFriction;
	public float				staticFriction;
	public float				restitution;
	public CombinePolicy		frictionCombinePolicy;
	public CombinePolicy		restitutionCombinePolicy;
	public float				weldingTolerance;
	public float				maxContactImpulse;
	public float				fractionOfClippedImpulseToApply;
	public MassChangerCategory	massChangerCategory;
	public float				massChangerHeavyObjectFactor;
	public float				softContactForceFactor;
	public float				softContactDampFactor;
	public byte					softContactSeperationVelocity;
	public long					surfaceVelocity;
	public float				disablingCollisionsBetweenCvxCvxDynamicObjectsDistance;
	public long					userData;
	public boolean				isShared;

	public hknpMaterial(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		name = HKXReader.hkStringPtr(connector, classOffset + 0);
		isExclusive = stream.getInt(classOffset + 8);
		flags = stream.getInt(classOffset + 12);
		int tt = stream.get(classOffset + 16); //Index 76 out of bounds for length 4
		triggerType = tt >= 0 && tt < TriggerType.values().length ? TriggerType.values()[tt] : TriggerType.values()[0];
		triggerManifoldTolerance = stream.get(classOffset + 17);//FIXME!!!! 8 bit float/ like a half half float? 255 rather than -1 type thign
		dynamicFriction = FP16.toFloat(stream.getShort(classOffset + 18));
		staticFriction = FP16.toFloat(stream.getShort(classOffset + 20));
		restitution = FP16.toFloat(stream.getShort(classOffset + 22));
		int frictionCombinePolicyv = Byte.toUnsignedInt(stream.get(classOffset + 24));// seems to allow 255 as a value? odd
		
		// <member name='restitutionCombinePolicy' type='enum CombinePolicy' etype='CombinePolicy' offset='25' vtype='TYPE_ENUM' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		// I've seen 205, 160?  what are these nomralized ordinals? so 0, 160,205,255which are 00000000,10100000,11100001,11111111,
		// I could use the xml exporter and see what it says about the enum value
		int restitutionCombinePolicyv = Byte.toUnsignedInt(stream.get(classOffset + 25));// seems to allow 255 as a value? odd
		//restitutionCombinePolicy = CombinePolicy.values()[Byte.toUnsignedInt(stream.get(25))];

		weldingTolerance = FP16.toFloat(stream.getShort(classOffset + 26));
		maxContactImpulse = stream.getFloat(classOffset + 28);
		fractionOfClippedImpulseToApply = stream.getFloat(classOffset + 32);
		int massChangerCategoryv = Byte.toUnsignedInt(stream.get(classOffset + 36));// seems to allow 255 as a value? odd
		//massChangerCategory = MassChangerCategory.values()[Byte.toUnsignedInt(stream.get(36))];

		massChangerHeavyObjectFactor = FP16.toFloat(stream.getShort(classOffset + 38));
		softContactForceFactor = FP16.toFloat(stream.getShort(classOffset + 40));
		softContactDampFactor = FP16.toFloat(stream.getShort(classOffset + 42));
		softContactSeperationVelocity = stream.get(classOffset + 44);//FIXME!!!! 8 bit float/ like a half half float? https://en.wikipedia.org/wiki/Minifloat

		surfaceVelocity = HKXReader.getPointer(connector, classOffset + 48);
		disablingCollisionsBetweenCvxCvxDynamicObjectsDistance = FP16.toFloat(stream.getShort(classOffset + 56));
		userData = stream.getLong(classOffset + 64);
		isShared = stream.get(classOffset + 72) != 0;

	}

	
	
	
	/**
	 Outline for Havok_TagObject of type hknpMaterial
	Havok_TagType None
	Havok_TagType hkBaseObject
	Havok_TagType hkReferencedObject
	Havok_TagMember memSizeAndFlags of type Havok_TagType hkUint16
	Havok_TagMember refCount of type Havok_TagType hkUint16		
	
	Outline for Havok_TagType hknpMaterial
	Havok_TagMember name of type hkStringPtr
	Havok_TagMember isExclusive of type hkUint32
	Havok_TagMember flags of type int
	Havok_TagMember triggerType of type hkEnum
	Havok_TagMember triggerManifoldTolerance of type hkUFloat8
	Havok_TagMember dynamicFriction of type hkHalf16
	Havok_TagMember staticFriction of type hkHalf16
	Havok_TagMember restitution of type hkHalf16
	Havok_TagMember frictionCombinePolicy of type hkEnum
	Havok_TagMember restitutionCombinePolicy of type hkEnum
	Havok_TagMember weldingTolerance of type hkHalf16
	Havok_TagMember maxContactImpulse of type hkReal
	Havok_TagMember fractionOfClippedImpulseToApply of type hkReal
	Havok_TagMember massChangerCategory of type hkEnum
	Havok_TagMember massChangerHeavyObjectFactor of type hkHalf16
	Havok_TagMember softContactForceFactor of type hkHalf16
	Havok_TagMember softContactDampFactor of type hkHalf16
	Havok_TagMember softContactSeperationVelocity of type hkUFloat8
	Havok_TagMember surfaceVelocity of type T*
	Havok_TagMember disablingCollisionsBetweenCvxCvxDynamicObjectsDistance of type hkHalf16
	Havok_TagMember userData of type hkUint64
	Havok_TagMember isShared of type hkBool
	 */
	public hknpMaterial(Havok_TagObject item) {		
		//item.outputOutline();		
		int memberIdx = 2;		
		
		name = item.listObjectClass.get(memberIdx++).s_value;
		isExclusive = item.listObjectClass.get(memberIdx++).i_value;
		flags = item.listObjectClass.get(memberIdx++).i_value;
		// <member name='triggerType' type='enum TriggerType' etype='TriggerType' offset='16' vtype='TYPE_ENUM' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		int tt = item.listObjectClass.get(memberIdx++).i_value; //Index 76 out of bounds for length 4
		triggerType = tt >= 0 && tt < TriggerType.values().length ? TriggerType.values()[tt] : TriggerType.values()[0];
		triggerManifoldTolerance = (byte)item.listObjectClass.get(memberIdx++).i_value;//FIXME!!!! 8 bit float/ like a half half float? 255 rather than -1 type thign
		dynamicFriction = item.listObjectClass.get(memberIdx++).f_value;
		staticFriction = item.listObjectClass.get(memberIdx++).f_value;
		restitution = item.listObjectClass.get(memberIdx++).f_value;
		int frictionCombinePolicyv = item.listObjectClass.get(memberIdx++).i_value;// seems to allow 255 as a value? odd
		
		// <member name='restitutionCombinePolicy' type='enum CombinePolicy' etype='CombinePolicy' offset='25' vtype='TYPE_ENUM' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		// I've seen 205, 160?  what are these nomralized ordinals? so 0, 160,205,255which are 00000000,10100000,11100001,11111111,
		// I could use the xml exporter and see what it says about the enum value
		int restitutionCombinePolicyv = item.listObjectClass.get(memberIdx++).i_value;// seems to allow 255 as a value? odd
		//restitutionCombinePolicy = CombinePolicy.values()[Byte.toUnsignedInt(stream.get(25))];

		weldingTolerance = item.listObjectClass.get(memberIdx++).f_value;
		maxContactImpulse = item.listObjectClass.get(memberIdx++).f_value;
		fractionOfClippedImpulseToApply = item.listObjectClass.get(memberIdx++).f_value;
		int massChangerCategoryv = item.listObjectClass.get(memberIdx++).i_value;// seems to allow 255 as a value? odd
		//massChangerCategory = MassChangerCategory.values()[Byte.toUnsignedInt(stream.get(36))];

		massChangerHeavyObjectFactor = item.listObjectClass.get(memberIdx++).f_value;
		softContactForceFactor = item.listObjectClass.get(memberIdx++).f_value;
		softContactDampFactor = item.listObjectClass.get(memberIdx++).f_value;
		softContactSeperationVelocity = (byte)item.listObjectClass.get(memberIdx++).i_value;//FIXME!!!! 8 bit float/ like a half half float? https://en.wikipedia.org/wiki/Minifloat

		surfaceVelocity = TAG0Reader.getRefPtr(item.listObjectClass.get(memberIdx++));
		disablingCollisionsBetweenCvxCvxDynamicObjectsDistance = item.listObjectClass.get(memberIdx++).f_value;
		userData = item.listObjectClass.get(memberIdx++).l_value;
		isShared = item.listObjectClass.get(memberIdx++).i_value != 0;
		 
	}

}