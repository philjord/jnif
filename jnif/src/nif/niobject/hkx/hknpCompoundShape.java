package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagItem;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**<class name='hknpCompoundShape' version='2' signature='0x247d5e99' parent='hknpCompositeShape'>
	<members>
		<member name='instances' type='struct hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance' ctype='hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance' offset='96' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='ALIGN_16'/>
		<member name='aabb' type='struct hkAabb' ctype='hkAabb' offset='128' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='isMutable' type='hkBool' offset='160' vtype='TYPE_BOOL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='mutationSignals' type='struct hknpShapeSignals' ctype='hknpShapeSignals' offset='168' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
	</members>
</class>*/
public class hknpCompoundShape extends hknpCompositeShape {

	public hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance	instances;
	public hkAabb																								aabb;
	public boolean																								isMutable;
	public hknpShapeSignals																						mutationSignals;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		instances = new hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance(
				connector, stream, classOffset + 96);
		aabb = new hkAabb(connector, stream, classOffset + 128);
		isMutable = stream.get(classOffset + 160) != 0;
		mutationSignals = new hknpShapeSignals(connector, stream, classOffset + 168);

		return success;

	}

	/**
	 Outline for Havok_TagType hknpCompoundShapeBase
	Havok_TagMember instances of type hkFreeListArray
	Havok_TagMember aabb of type hkAabb
	Havok_TagMember isMutable of type hkBool
	Havok_TagMember estimatedNumShapeKeys of type int
	Havok_TagMember mutationSignals of type hknpShapeSignals
	Havok_TagType hknpCompoundShape
	Havok_TagMember boundingVolumeData of type T*
	 */
	//hknpCompoundShapeBase is not in the hierarchy not loaded here
	public int	estimatedNumShapeKeys;
	public long	boundingVolumeData2;// both sub classes load a boundingVolumeData but of 2 different types

	@Override
	public int readFromTAG0(Havok_TagItem item) {
		int memberIdx = super.readFromTAG0(item);
		//item.outputOutline();
		Havok_TagObject value0 = item.value.get(0);
		instances = new hkFreeListArrayhknpShapeInstancehkHandleshort32767hknpShapeInstanceIdDiscriminant8hknpShapeInstance(
				value0.listObjectClass.get(memberIdx++));
		aabb = new hkAabb(value0.listObjectClass.get(memberIdx++));
		isMutable = value0.listObjectClass.get(memberIdx++).i_value != 0;
		estimatedNumShapeKeys = value0.listObjectClass.get(memberIdx++).i_value;
		mutationSignals = new hknpShapeSignals(value0.listObjectClass.get(memberIdx++));
		boundingVolumeData2 = TAG0Reader.getRefPtr(value0.listObjectClass.get(memberIdx++));
		return memberIdx;
	}

}