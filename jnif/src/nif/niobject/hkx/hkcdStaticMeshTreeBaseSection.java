package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkcdStaticMeshTreeBaseSection' version='3' signature='0xfd01ccd8' parent='hkcdStaticTreeTreehkcdStaticTreeDynamicStorage4'>
	<enums>
		<enum name='Flags' flags='00000000'>
			<enumitem name='SF_REQUIRE_TREE' value='1'/>
		</enum>
	</enums>
	<members>
		<member name='codecParms' type='hkReal[6]' offset='48' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='6' flags='FLAGS_NONE'/>
		<member name='firstPackedVertex' type='hkUint32' offset='72' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='sharedVertices' type='struct hkcdStaticMeshTreeBaseSectionSharedVertices' ctype='hkcdStaticMeshTreeBaseSectionSharedVertices' offset='76' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='primitives' type='struct hkcdStaticMeshTreeBaseSectionPrimitives' ctype='hkcdStaticMeshTreeBaseSectionPrimitives' offset='80' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='dataRuns' type='struct hkcdStaticMeshTreeBaseSectionDataRuns' ctype='hkcdStaticMeshTreeBaseSectionDataRuns' offset='84' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='numPackedVertices' type='hkUint8' offset='88' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='numSharedIndices' type='hkUint8' offset='89' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='leafIndex' type='hkUint16' offset='90' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='page' type='hkUint8' offset='92' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='flags' type='hkUint8' offset='93' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='layerData' type='hkUint8' offset='94' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='unusedData' type='hkUint8' offset='95' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hkcdStaticMeshTreeBaseSection extends hkcdStaticTreeTreehkcdStaticTreeDynamicStorage4 {
	
	public static final int size = 96;
	public float[] codecParms = new float[6]; 
	public int firstPackedVertex;
	public hkcdStaticMeshTreeBaseSectionSharedVertices  sharedVertices;
	public hkcdStaticMeshTreeBaseSectionPrimitives  primitives;
	public hkcdStaticMeshTreeBaseSectionDataRuns  dataRuns;
	public int numPackedVertices;
	public int numSharedIndices;
	public int leafIndex;
	public int page;
	public int flags;
	public int layerData;
	public int unusedData;
	
	public hkcdStaticMeshTreeBaseSection(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		super(connector, classOffset);
		ByteBuffer stream = connector.data.setup(classOffset).slice().order(ByteOrder.LITTLE_ENDIAN);
		
		//<member name='codecParms' type='hkReal[6]' offset='48' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='6' flags='FLAGS_NONE'/>		
		codecParms[0] =  stream.getFloat(48);
		codecParms[1] =  stream.getFloat(52);
		codecParms[2] =  stream.getFloat(56);
		codecParms[3] =  stream.getFloat(60);
		codecParms[4] =  stream.getFloat(64);
		codecParms[5] =  stream.getFloat(68);
		
		//<member name='firstPackedVertex' type='hkUint32' offset='72' vtype='TYPE_UINT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		firstPackedVertex = stream.getInt(72);
		
		//<member name='sharedVertices' type='struct hkcdStaticMeshTreeBaseSectionSharedVertices' ctype='hkcdStaticMeshTreeBaseSectionSharedVertices' offset='76' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		sharedVertices = new hkcdStaticMeshTreeBaseSectionSharedVertices(connector, classOffset + 76);
		//<member name='primitives' type='struct hkcdStaticMeshTreeBaseSectionPrimitives' ctype='hkcdStaticMeshTreeBaseSectionPrimitives' offset='80' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		primitives = new hkcdStaticMeshTreeBaseSectionPrimitives(connector, classOffset + 80);
		//<member name='dataRuns' type='struct hkcdStaticMeshTreeBaseSectionDataRuns' ctype='hkcdStaticMeshTreeBaseSectionDataRuns' offset='84' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		dataRuns = new hkcdStaticMeshTreeBaseSectionDataRuns(connector, classOffset + 84);
		//<member name='numPackedVertices' type='hkUint8' offset='88' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		numPackedVertices = Byte.toUnsignedInt(stream.get(88));
		//<member name='numSharedIndices' type='hkUint8' offset='89' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		numSharedIndices = Byte.toUnsignedInt(stream.get(89));
		//<member name='leafIndex' type='hkUint16' offset='90' vtype='TYPE_UINT16' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		leafIndex = Short.toUnsignedInt(stream.getShort(90));
		//<member name='page' type='hkUint8' offset='92' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		page = Byte.toUnsignedInt(stream.get(92));
		//<member name='flags' type='hkUint8' offset='93' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		flags = Byte.toUnsignedInt(stream.get(93));
		//<member name='layerData' type='hkUint8' offset='94' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		layerData = Byte.toUnsignedInt(stream.get(94));
		//<member name='unusedData' type='hkUint8' offset='95' vtype='TYPE_UINT8' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		unusedData = Byte.toUnsignedInt(stream.get(95));
	}
}
