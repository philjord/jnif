package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.compound.NifVector4;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
 * <struct name='hkcdFourAabb' version='0' signature='0xad9bb6f1'>
	<enums>
		<enum name='BoundIndex' flags='00000000'>
			<enumitem name='X_MIN' value='0'/>
			<enumitem name='X_MAX' value='1'/>
			<enumitem name='Y_MIN' value='2'/>
			<enumitem name='Y_MAX' value='3'/>
			<enumitem name='Z_MIN' value='4'/>
			<enumitem name='Z_MAX' value='5'/>
		</enum>
	</enums>
	<members>
		<member name='lx' type='hkVector4' offset='0' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='hx' type='hkVector4' offset='16' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='ly' type='hkVector4' offset='32' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='hy' type='hkVector4' offset='48' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='lz' type='hkVector4' offset='64' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='hz' type='hkVector4' offset='80' vtype='TYPE_VECTOR4' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>
*/

public class hkcdFourAabb {
	NifVector4	lx;
	NifVector4	hx;
	NifVector4	ly;
	NifVector4	hy;
	NifVector4	lz;
	NifVector4	hz;

	public hkcdFourAabb(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		lx = new NifVector4(stream, classOffset + 0);
		hx = new NifVector4(stream, classOffset + 16);
		ly = new NifVector4(stream, classOffset + 32);
		hy = new NifVector4(stream, classOffset + 48);
		lz = new NifVector4(stream, classOffset + 64);
		hz = new NifVector4(stream, classOffset + 80);
	}
}