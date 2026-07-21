package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<struct name='hkpAngFrictionConstraintAtom' version='0' signature='0x89f70523' parent='hkpConstraintAtom'>
	<members>
		<member name='isEnabled' type='hkUint8' offset='2' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='firstFrictionAxis' type='hkUint8' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='numFrictionAxes' type='hkUint8' offset='4' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='maxFrictionTorque' type='hkReal' offset='8' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='padding' type='hkUint8[4]' offset='12' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='4' flags='SERIALIZE_IGNORED'/>
	</members>
	</struct>
*/

public class hkpAngFrictionConstraintAtom extends hkpConstraintAtom {

	public byte		isEnabled;
	public byte		firstFrictionAxis;
	public byte		numFrictionAxes;
	public float	maxFrictionTorque;
	public int		padding;

	public hkpAngFrictionConstraintAtom(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		super(connector, stream, classOffset);
		isEnabled = stream.get(classOffset + 2);
		firstFrictionAxis = stream.get(classOffset + 3);
		numFrictionAxes = stream.get(classOffset + 4);
		maxFrictionTorque = stream.getFloat(classOffset + 8);
		padding = stream.getInt(classOffset + 12);
	}
}