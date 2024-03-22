package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
	public hkCompressedMassProperties(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		
		//<member name='centerOfMass' type='hkInt16[4]' offset='0' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='4' flags='FLAGS_NONE'/>
		centerOfMass[0] = stream.getShort(0);
		centerOfMass[1] = stream.getShort(2);
		centerOfMass[2] = stream.getShort(4);
		centerOfMass[3] = stream.getShort(6);
		//<member name='inertia' type='hkInt16[4]' offset='8' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='4' flags='FLAGS_NONE'/>
		inertia[0] = stream.getShort(8);
		inertia[1] = stream.getShort(10);
		inertia[2] = stream.getShort(12);
		inertia[3] = stream.getShort(14);
		//<member name='majorAxisSpace' type='hkInt16[4]' offset='16' vtype='TYPE_INT16' vsubtype='TYPE_VOID' arrsize='4' flags='FLAGS_NONE'/>
		majorAxisSpace[0] = stream.getShort(16);
		majorAxisSpace[1] = stream.getShort(18);
		majorAxisSpace[2] = stream.getShort(20);
		majorAxisSpace[3] = stream.getShort(22);
		//<member name='mass' type='hkReal' offset='24' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		mass = stream.getFloat(24);
		//<member name='volume' type='hkReal' offset='28' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		volume = stream.getFloat(28);
	}
}