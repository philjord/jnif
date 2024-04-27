package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hknpConvexPolytopeShapeFace' version='0' signature='0xf3c05540'>
	<members>
		<member name='firstIndex' type='hkUint16' offset='0' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='numIndices' type='hkUint8' offset='2' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='minHalfAngle' type='hkUint8' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/
public class hknpConvexPolytopeShapeFace  {
	
	public static final int size = 4;  
	int firstIndex;
	int numIndices;
	int minHalfAngle;
	
	public hknpConvexPolytopeShapeFace(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{
		//<member name='firstIndex' type='hkUint16' offset='0' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		firstIndex = Short.toUnsignedInt(stream.getShort(classOffset + 0));
		//<member name='numIndices' type='hkUint8' offset='2' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		numIndices = Byte.toUnsignedInt(stream.get(classOffset + 2));
		//<member name='minHalfAngle' type='hkUint8' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		minHalfAngle = Byte.toUnsignedInt(stream.get(classOffset + 3));
	}
}