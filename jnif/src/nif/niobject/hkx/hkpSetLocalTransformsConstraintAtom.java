package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.compound.NifMatrix44;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkpSetLocalTransformsConstraintAtom' version='0' signature='0x13cd1821' parent='hkpConstraintAtom'>
<members>
	<member name='transformA' type='hkTransform' offset='16' vtype='TYPE_TRANSFORM' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	<member name='transformB' type='hkTransform' offset='80' vtype='TYPE_TRANSFORM' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
</members>
</struct>
*/

public class hkpSetLocalTransformsConstraintAtom extends hkpConstraintAtom {

	public NifMatrix44	transformA;
	public NifMatrix44	transformB;

	public hkpSetLocalTransformsConstraintAtom(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		super(connector, stream, classOffset);
		transformA = new NifMatrix44(stream, classOffset + 16);
		transformB = new NifMatrix44(stream, classOffset + 80);
	}
}