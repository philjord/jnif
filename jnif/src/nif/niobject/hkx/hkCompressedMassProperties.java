package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkCompressedMassProperties' version='0' signature='0x9ac5cee1'>
	<members>
		<member name='centerOfMass' type='hkInt16[4]' offset='0' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='4' flags='FLAGS_NONE'/>
		<member name='inertia' type='hkInt16[4]' offset='8' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='4' flags='FLAGS_NONE'/>
		<member name='majorAxisSpace' type='hkInt16[4]' offset='16' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='4' flags='FLAGS_NONE'/>
		<member name='mass' type='hkReal' offset='24' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='volume' type='hkReal' offset='28' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/
public class hkCompressedMassProperties  {
	int[] centerOfMass = new int[4];
	int[] inertia = new int[4];
	int[] majorAxisSpace = new int[4];
	float mass;
	float volume;
	public hkCompressedMassProperties(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{		
		//<member name='centerOfMass' type='hkInt16[4]' offset='0' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='4' flags='FLAGS_NONE'/>
		centerOfMass[0] = stream.getShort(classOffset + 0);
		centerOfMass[1] = stream.getShort(classOffset + 2);
		centerOfMass[2] = stream.getShort(classOffset + 4);
		centerOfMass[3] = stream.getShort(classOffset + 6);
		//<member name='inertia' type='hkInt16[4]' offset='8' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='4' flags='FLAGS_NONE'/>
		inertia[0] = stream.getShort(classOffset + 8);
		inertia[1] = stream.getShort(classOffset + 10);
		inertia[2] = stream.getShort(classOffset + 12);
		inertia[3] = stream.getShort(classOffset + 14);
		//<member name='majorAxisSpace' type='hkInt16[4]' offset='16' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='4' flags='FLAGS_NONE'/>
		majorAxisSpace[0] = stream.getShort(classOffset + 16);
		majorAxisSpace[1] = stream.getShort(classOffset + 18);
		majorAxisSpace[2] = stream.getShort(classOffset + 20);
		majorAxisSpace[3] = stream.getShort(classOffset + 22);
		//<member name='mass' type='hkReal' offset='24' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		mass = stream.getFloat(classOffset + 24);
		//<member name='volume' type='hkReal' offset='28' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		volume = stream.getFloat(classOffset + 28);
	}
}