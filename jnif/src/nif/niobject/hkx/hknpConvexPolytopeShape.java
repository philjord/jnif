package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.compound.NifVector4;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.byteutils.ByteUtils;

/**<class name='hknpConvexPolytopeShape' version='1' signature='0x3ce9b3e3' parent='hknpConvexShape'>
	<members>
		<member name='planes' type='hkRelArray&lt;hkVector4&gt;' offset='64' vtype='TYPE_RELARRAY' vsubtype='TYPE_VECTOR4' arrsize='0' flags='FLAGS_NONE'/>
		<member name='faces' type='hkRelArray&lt;struct hknpConvexPolytopeShapeFace&gt;' ctype='hknpConvexPolytopeShapeFace' offset='68' vtype='TYPE_RELARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='indices' type='hkRelArray&lt;hkUint8&gt;' offset='72' vtype='TYPE_RELARRAY' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/
 
public class hknpConvexPolytopeShape extends hknpConvexShape  {
	public NifVector4[] planes;
	public hknpConvexPolytopeShapeFace[] faces;
	public int[] indices;
	
	@Override
	public boolean readFromStream(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, classOffset);
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);//use the position as the start
		//<member name='planes' type='hkRelArray&lt;hkVector4&gt;' offset='64' vtype='TYPE_RELARRAY' vsubtype='TYPE_VECTOR4' arrsize='0' flags='FLAGS_NONE'/>
				
		ByteBuffer file = connector.data.setup(classOffset + 64);
		byte[] bSize = new byte[2];
		byte[] bOff = new byte[2];
		file.get(bSize);
		file.get(bOff);
		int size = ByteUtils.getUInt(bSize) - 1;
		int offset = ByteUtils.getUInt(bOff);
		planes = new NifVector4[size];
		for (int i = 0; i < size; i++) {
			planes[i] = new NifVector4(stream, offset + (i * 16));//16 bytes per vec4
		}
		
		//<member name='faces' type='hkRelArray&lt;struct hknpConvexPolytopeShapeFace&gt;' ctype='hknpConvexPolytopeShapeFace' offset='68' vtype='TYPE_RELARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		file = connector.data.setup(classOffset + 68);
		file.get(bSize);
		file.get(bOff);
		size = ByteUtils.getUInt(bSize) - 1;
		offset = ByteUtils.getUInt(bOff);
		faces = new hknpConvexPolytopeShapeFace[size];
		for (int i = 0; i < size; i++) {
			faces[i] = new hknpConvexPolytopeShapeFace(connector, classOffset + offset + (i * hknpConvexPolytopeShapeFace.size));//16 bytes per vec4
		}		
		
		
		//<member name='indices' type='hkRelArray&lt;hkUint8&gt;' offset='72' vtype='TYPE_RELARRAY' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		file = connector.data.setup(classOffset + 72);
		file.get(bSize);
		file.get(bOff);
		size = ByteUtils.getUInt(bSize) - 1;
		offset = ByteUtils.getUInt(bOff);
		indices = new int[size];
		for (int i = 0; i < size; i++) {
			indices[i] = Byte.toUnsignedInt(stream.get(offset + (i * 1)));
		}	
		
		return success;
	}
}
	
 