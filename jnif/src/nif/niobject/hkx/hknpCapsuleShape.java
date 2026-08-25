package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.compound.NifVector4;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagItem;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**<class name='hknpCapsuleShape' version='0' signature='0x60a75f4c' parent='hknpConvexPolytopeShape'>
	<members>
		<member name='a' type='hkVector4' offset='80' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='b' type='hkVector4' offset='96' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>*/

public class hknpCapsuleShape extends hknpConvexPolytopeShape  {
	public NifVector4 a;
	public NifVector4 b;
	
	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
		
		a = new NifVector4(stream, classOffset + 80);
		b = new NifVector4(stream, classOffset + 96);
		
		return success;
	}
	
	
	/**
	 Outline for Havok_TagType hknpCapsuleShape
	Havok_TagMember a of type hkVector4
	Havok_TagMember b of type hkVector4
	 */
	
	@Override
	public int readFromTAG0(Havok_TagItem item) {
		int memberIdx = super.readFromTAG0(item);
		//item.outputOutline();
		Havok_TagObject value0 = item.value.get(0);
		
		Havok_TagObject value = value0.listObjectClass.get(memberIdx++);
		a = new NifVector4(value.listObjectTuple);
		value = value0.listObjectClass.get(memberIdx++);
		b  = new NifVector4(value.listObjectTuple);		
		
		return memberIdx;
	}
}
		