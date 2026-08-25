package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.compound.NifVector4;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;
import nif.niobject.hkx.reader.TAG0Reader;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagItem;
import nif.niobject.hkx.reader.TAG0Reader.Havok_TagObject;
import nif.niobject.hkx.reader.byteutils.ByteUtils;

/**<class name='hknpConvexPolytopeShape' version='1' signature='0x3ce9b3e3' parent='hknpConvexShape'>
	<members>
		<member name='planes' type='hkRelArray&lt;hkVector4&gt;' offset='64' vtype='TYPE_RELARRAY' vsubtype='TYPE_VECTOR4' arrsize='0' flags='FLAGS_NONE'/>
		<member name='faces' type='hkRelArray&lt;struct hknpConvexPolytopeShapeFace&gt;' ctype='hknpConvexPolytopeShapeFace' offset='68' vtype='TYPE_RELARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		<member name='indices' type='hkRelArray&lt;hkUint8&gt;' offset='72' vtype='TYPE_RELARRAY' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>
*/

public class hknpConvexPolytopeShape extends hknpConvexShape {
	public NifVector4[]						planes;
	public hknpConvexPolytopeShape.Face[]	faces;
	public int[]							indices;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

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
			planes[i] = new NifVector4(stream, classOffset + offset + (i * 16));//16 bytes per vec4
		}

		//<member name='faces' type='hkRelArray&lt;struct hknpConvexPolytopeShapeFace&gt;' ctype='hknpConvexPolytopeShapeFace' offset='68' vtype='TYPE_RELARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
		file = connector.data.setup(classOffset + 68);
		file.get(bSize);
		file.get(bOff);
		size = ByteUtils.getUInt(bSize) - 1;
		offset = ByteUtils.getUInt(bOff);
		faces = new hknpConvexPolytopeShape.Face[size];
		for (int i = 0; i < size; i++) {
			faces[i] = new hknpConvexPolytopeShape.Face(connector, stream,
					classOffset + offset + (i * hknpConvexPolytopeShape.Face.size));//16 bytes per vec4
		}

		//<member name='indices' type='hkRelArray&lt;hkUint8&gt;' offset='72' vtype='TYPE_RELARRAY' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		file = connector.data.setup(classOffset + 72);
		file.get(bSize);
		file.get(bOff);
		size = ByteUtils.getUInt(bSize) - 1;
		offset = ByteUtils.getUInt(bOff);
		indices = new int[size];
		for (int i = 0; i < size; i++) {
			indices[i] = Byte.toUnsignedInt(stream.get(classOffset + offset + (i * 1)));
		}

		return success;
	}

	/**
	Outline for Havok_TagType hknpConvexPolytopeShape
	Havok_TagMember planes of type hkRelArray
	Havok_TagMember faces of type hkRelArray
	Havok_TagMember indices of type hkRelArray
	Havok_TagMember connectivity of type hkRefPtr
	 */
	public long connectivity;
	@Override
	public int readFromTAG0(Havok_TagItem item) {
		int memberIdx = super.readFromTAG0(item);
		//item.outputOutline();
		Havok_TagObject value0 = item.value.get(0);

		Havok_TagObject value = value0.listObjectClass.get(memberIdx++);
		int arrSize = value.listObjectArray.size();
		planes = new NifVector4[arrSize];
		for (int i = 0; i < arrSize; i++) {
			planes[i] = new NifVector4(value.listObjectArray.get(i).listObjectTuple);			
		}

		value = value0.listObjectClass.get(memberIdx++);
		arrSize = value.listObjectArray.size();
		faces = new hknpConvexPolytopeShape.Face[arrSize];
		for (int i = 0; i < arrSize; i++) {
			faces[i] = new hknpConvexPolytopeShape.Face(value.listObjectArray.get(i));
		}

		value = value0.listObjectClass.get(memberIdx++);
		arrSize = value.listObjectArray.size();
		indices = new int[arrSize];
		for (int i = 0; i < arrSize; i++) {
			indices[i] = value.listObjectArray.get(i).i_value;
		}

		connectivity = TAG0Reader.getRefPtr(value0.listObjectClass.get(memberIdx++));

		return memberIdx;
	}
	
		

	/**<struct name='hknpConvexPolytopeShapeFace' version='0' signature='0xf3c05540'>
		<members>
			<member name='firstIndex' type='hkUint16' offset='0' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
			<member name='numIndices' type='hkUint8' offset='2' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
			<member name='minHalfAngle' type='hkUint8' offset='3' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		</members>
	</struct>*/
	public static class Face {

		public static final int	size	= 4;
		int						firstIndex;
		int						numIndices;
		int						minHalfAngle;

		public Face(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
				throws IOException, InvalidPositionException {
			firstIndex = Short.toUnsignedInt(stream.getShort(classOffset + 0));
			numIndices = Byte.toUnsignedInt(stream.get(classOffset + 2));
			minHalfAngle = Byte.toUnsignedInt(stream.get(classOffset + 3));
		}

		/**
		 Outline for Havok_TagObject of type hknpConvexPolytopeShape::Face
		Havok_TagType None
		Havok_TagType hknpConvexPolytopeShape::Face
		Havok_TagMember firstIndex of type hkUint16
		Havok_TagMember numIndices of type hkUint8
		Havok_TagMember minHalfAngle of type hkUint8	 
		 */
		public Face(Havok_TagObject item) {
			//item.outputOutline();
			int memberIdx = 0;
			firstIndex = item.listObjectClass.get(memberIdx++).i_value;
			numIndices = item.listObjectClass.get(memberIdx++).i_value;
			minHalfAngle = item.listObjectClass.get(memberIdx++).i_value;
		}
	}
	
	
	
	/**
	 Outline for Havok_TagType hknpConvexPolytopeShape::Connectivity
	Havok_TagMember vertexEdges of type hkArray
	Havok_TagMember faceLinks of type hkArray
	 */
	public static class Connectivity extends hkReferencedObject {
		
		public int[]							vertexEdges;
		public int[]							faceLinks;
		
		@Override
		public int readFromTAG0(Havok_TagItem item) {
			int memberIdx = super.readFromTAG0(item);
			//item.outputOutline();
			Havok_TagObject value0 = item.value.get(0);
			
			Havok_TagObject value = value0.listObjectClass.get(memberIdx++);
			int arrSize = value.listObjectArray.size();
			vertexEdges = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				vertexEdges[i] = value.listObjectArray.get(i).i_value;		
			}

			value = value0.listObjectClass.get(memberIdx++);
			arrSize = value.listObjectArray.size();
			faceLinks = new int[arrSize];
			for (int i = 0; i < arrSize; i++) {
				faceLinks[i] = value.listObjectArray.get(i).i_value;
			}

			
			return memberIdx;
		}
	}
}
