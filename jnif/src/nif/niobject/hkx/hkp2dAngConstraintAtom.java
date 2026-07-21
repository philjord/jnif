package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
	<struct name='hkp2dAngConstraintAtom' version='0' signature='0xd277c114' parent='hkpConstraintAtom'>
		<members>
			<member name='freeRotationAxis' type='hkUint8' offset='2' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
			<member name='padding' type='hkUint8[12]' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='12' flags='SERIALIZE_IGNORED'/>
		</members>
	</struct>
*/

public class hkp2dAngConstraintAtom extends hkpConstraintAtom {

	public byte		freeRotationAxis;
	//public byte[]	padding	= new byte[12];

	public hkp2dAngConstraintAtom(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		super(connector, stream, classOffset);
		freeRotationAxis = stream.get(classOffset + 2);
		// ignore padding		 
	}
}