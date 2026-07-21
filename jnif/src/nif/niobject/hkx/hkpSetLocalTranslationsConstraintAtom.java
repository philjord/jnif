package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.compound.NifVector4;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkpSetLocalTranslationsConstraintAtom' version='0' signature='0x3d4c316a' parent='hkpConstraintAtom'>
	<members>
		<member name='translationA' type='hkVector4' offset='16' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='translationB' type='hkVector4' offset='32' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/

public class hkpSetLocalTranslationsConstraintAtom extends hkpConstraintAtom {

	public NifVector4	translationA;
	public NifVector4	translationB;

	public hkpSetLocalTranslationsConstraintAtom(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		super(connector, stream, classOffset);

		translationA = new NifVector4(stream, classOffset + 16);
		translationB = new NifVector4(stream, classOffset + 32);
	}
}