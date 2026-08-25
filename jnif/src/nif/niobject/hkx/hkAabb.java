package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.compound.NifVector4;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;

/**<struct name='hkAabb' version='0' signature='0x4a948b16'>
	<members>
		<member name='min' type='hkVector4' offset='0' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='max' type='hkVector4' offset='16' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/
public class hkAabb {
	public NifVector4	min;
	public NifVector4	max;

	public hkAabb(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		min = new NifVector4(stream, classOffset + 0);
		max = new NifVector4(stream, classOffset + 16);
	}

	/**
	 Outline for Havok_TagType hkAabb
	Havok_TagMember min of type hkVector4
	Havok_TagMember max of type hkVector4
	 */
	public hkAabb(Havok_TagObject item) {
		//item.outputOutline();
		min = new NifVector4(item.listObjectClass.get(0).listObjectTuple);
		max = new NifVector4(item.listObjectClass.get(1).listObjectTuple);
	}
}