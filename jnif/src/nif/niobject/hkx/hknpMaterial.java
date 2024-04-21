package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.DataExternal;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.byteutils.ByteUtils;
import nif.tools.MiniFloat;

	
/**<struct name='hknpMaterial' version='1' signature='0xb7c5f24e'>
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
</struct>*/

enum TriggerType {TRIGGER_TYPE_NONE, TRIGGER_TYPE_BROAD_PHASE,TRIGGER_TYPE_NARROW_PHASE, TRIGGER_TYPE_CONTACT_SOLVER};
enum CombinePolicy {COMBINE_AVG, COMBINE_MIN,COMBINE_MAX};
enum MassChangerCategory {MASS_CHANGER_IGNORE, MASS_CHANGER_DEBRIS,MASS_CHANGER_HEAVY};

public class hknpMaterial  {
	public static final int size = 73; // or does align 16 want this to be 80?
	public String name;
	public int isExclusive;
	public int flags;
	public TriggerType triggerType;
	public byte triggerManifoldTolerance;
	public float dynamicFriction;
	public float staticFriction;
	public float restitution;
	public CombinePolicy frictionCombinePolicy;
	public CombinePolicy restitutionCombinePolicy;
	public float weldingTolerance;
	public float maxContactImpulse;
	public float fractionOfClippedImpulseToApply;
	public MassChangerCategory massChangerCategory;
	public float massChangerHeavyObjectFactor;
	public float softContactForceFactor;
	public float softContactDampFactor;
	public byte softContactSeperationVelocity;
	public long surfaceVelocity;
	public float disablingCollisionsBetweenCvxCvxDynamicObjectsDistance;
	public long userData;
	public boolean isShared;
	
	public hknpMaterial(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		
		// <member name='name' type='hkStringPtr' offset='0' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='ALIGN_16'/>
		name = "";
		try {
			DataInternal data = connector.data1.readNext();
			if (data.from == classOffset + 0) {
				ByteBuffer file2 = connector.data.setup(data.to);
				name = ByteUtils.readString(file2);
			} else {
				connector.data1.backtrack();
			}
		} catch (InvalidPositionException e) {
			// NO OP. Met when the last item of the HKX file is a String and is empty.
			name = "";
		}
		// <member name='isExclusive' type='hkUint32' offset='8' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		isExclusive = stream.getInt(8);
		// <member name='flags' type='hkInt32' offset='12' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		flags = stream.getInt(12);
		// <member name='triggerType' type='enum TriggerType' etype='TriggerType' offset='16' vtype='TYPE_ENUM' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		int tt = stream.get(16); //Index 76 out of bounds for length 4
		triggerType = tt < TriggerType.values().length ? TriggerType.values()[tt] : TriggerType.values()[0];
		// <member name='triggerManifoldTolerance' type='struct hkUFloat8' ctype='hkUFloat8' offset='17' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		triggerManifoldTolerance = stream.get(17);//FIXME!!!! 8 bit float/ like a half half float? 255 rather than -1 type thign
		// <member name='dynamicFriction' type='hkHalf' offset='18' vtype='TYPE_HALF' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		dynamicFriction = MiniFloat.toFloat(stream.getShort(18));
		// <member name='staticFriction' type='hkHalf' offset='20' vtype='TYPE_HALF' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		staticFriction = MiniFloat.toFloat(stream.getShort(20));
		// <member name='restitution' type='hkHalf' offset='22' vtype='TYPE_HALF' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		restitution = MiniFloat.toFloat(stream.getShort(22));
		// <member name='frictionCombinePolicy' type='enum CombinePolicy' etype='CombinePolicy' offset='24' vtype='TYPE_ENUM' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		int frictionCombinePolicyv = Byte.toUnsignedInt(stream.get(24));// seems to allow 255 as a value? odd
		//frictionCombinePolicy = CombinePolicy.values()[];
		
		// <member name='restitutionCombinePolicy' type='enum CombinePolicy' etype='CombinePolicy' offset='25' vtype='TYPE_ENUM' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		// I've seen 205, 160?  what are these nomralized ordinals? so 0, 160,205,255which are 00000000,10100000,11100001,11111111,
		// I could use the xml exporter and see what it says about the enum value
		int restitutionCombinePolicyv = Byte.toUnsignedInt(stream.get(25));// seems to allow 255 as a value? odd
		//restitutionCombinePolicy = CombinePolicy.values()[Byte.toUnsignedInt(stream.get(25))];
		
		// <member name='weldingTolerance' type='hkHalf' offset='26' vtype='TYPE_HALF' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		weldingTolerance = MiniFloat.toFloat(stream.getShort(26));
		// <member name='maxContactImpulse' type='hkReal' offset='28' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		maxContactImpulse = stream.getFloat(28);
		// <member name='fractionOfClippedImpulseToApply' type='hkReal' offset='32' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		fractionOfClippedImpulseToApply = stream.getFloat(32);
		// <member name='massChangerCategory' type='enum MassChangerCategory' etype='MassChangerCategory' offset='36' vtype='TYPE_ENUM' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		int massChangerCategoryv = Byte.toUnsignedInt(stream.get(36));// seems to allow 255 as a value? odd
		//massChangerCategory = MassChangerCategory.values()[Byte.toUnsignedInt(stream.get(36))];
		
		// <member name='massChangerHeavyObjectFactor' type='hkHalf' offset='38' vtype='TYPE_HALF' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		massChangerHeavyObjectFactor = MiniFloat.toFloat(stream.getShort(38));
		// <member name='softContactForceFactor' type='hkHalf' offset='40' vtype='TYPE_HALF' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		softContactForceFactor = MiniFloat.toFloat(stream.getShort(40));
		// <member name='softContactDampFactor' type='hkHalf' offset='42' vtype='TYPE_HALF' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		softContactDampFactor = MiniFloat.toFloat(stream.getShort(42));
		// <member name='softContactSeperationVelocity' type='struct hkUFloat8' ctype='hkUFloat8' offset='44' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		softContactSeperationVelocity = stream.get(44);//FIXME!!!! 8 bit float/ like a half half float? https://en.wikipedia.org/wiki/Minifloat
		 
		// <member name='surfaceVelocity' type='struct hknpSurfaceVelocity*' ctype='hknpSurfaceVelocity' offset='48' vtype='TYPE_POINTER' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		DataExternal data = connector.data2.readNext();
		if (data.from == classOffset + 48) {
			surfaceVelocity = data.to;
		} else {
			connector.data2.backtrack();
		}
		// <member name='disablingCollisionsBetweenCvxCvxDynamicObjectsDistance' type='hkHalf' offset='56' vtype='TYPE_HALF' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		disablingCollisionsBetweenCvxCvxDynamicObjectsDistance = MiniFloat.toFloat(stream.getShort(56));
		// <member name='userData' type='hkUint64' offset='64' vtype='TYPE_UINT64' vsubtype='TYPE_VOID' arrsize='0' flags='ALIGN_8'/>
		userData = stream.getLong(64);
		// <member name='isShared' type='hkBool' offset='72' vtype='TYPE_BOOL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		isShared = stream.get(72) != 0;
		
	}
	
}