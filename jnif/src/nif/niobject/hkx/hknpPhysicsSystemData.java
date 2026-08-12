package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
 * <class name='hknpPhysicsSystemData' version='0' signature='0xb857718b' parent='hkReferencedObject'> 
 * <members>
 * <member name='materials' type='hkArray&lt;struct hknpMaterial&gt;' ctype='hknpMaterial' offset='16' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
 * <member name='motionProperties' type='hkArray&lt;struct hknpMotionProperties&gt;' ctype='hknpMotionProperties' offset='32' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
 * <member name='motionCinfos' type='hkArray&lt;struct hknpMotionCinfo&gt;' ctype='hknpMotionCinfo' offset='48' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
 * <member name='bodyCinfos' type='hkArray&lt;struct hknpBodyCinfo&gt;' ctype='hknpBodyCinfo' offset='64' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
 * <member name='constraintCinfos' type='hkArray&lt;struct hknpConstraintCinfo&gt;' ctype='hknpConstraintCinfo' offset='80' vtype='TYPE_ARRAY' vsubtype='TYPE_STRUCT' arrsize='0' flags='FLAGS_NONE'/>
 * <member name='referencedObjects' type='hkArray&lt;hkReferencedObject*&gt;' ctype='hkReferencedObject' offset='96' vtype='TYPE_ARRAY' vsubtype='TYPE_POINTER' arrsize='0' flags='FLAGS_NONE'/>
 * <member name='name' type='hkStringPtr' offset='112' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/> 
 * </members> 
 * </class>
 */

public class hknpPhysicsSystemData extends hkReferencedObject {

	public hknpMaterial[]			materials;
	public hknpMotionProperties[]	motionProperties;
	public hknpMotionCinfo[]		motionCinfos;
	public hknpBodyCinfo[]			bodyCinfos;
	public hknpConstraintCinfo[]	constraintCinfos;
	public long[]					referencedObjects;
	public String					name;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 16));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 16;
			materials = new hknpMaterial[arrSize];
			for (int i = 0; i < arrSize; i++) {
				materials[i] = new hknpMaterial(connector, stream, (int)arrValue.to + (i * hknpMaterial.size));
			}
		}

		arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 32));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 32;
			motionProperties = new hknpMotionProperties[arrSize];
			for (int i = 0; i < arrSize; i++) {
				motionProperties[i] = new hknpMotionProperties(connector, stream,
						(int)arrValue.to + (i * hknpMotionProperties.size));
			}
		}

		arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 48));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 48;
			motionCinfos = new hknpMotionCinfo[arrSize];
			for (int i = 0; i < arrSize; i++) {
				motionCinfos[i] = new hknpMotionCinfo(connector, stream, (int)arrValue.to + (i * hknpMotionCinfo.size));
			}
		}

		arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 64));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 64;
			bodyCinfos = new hknpBodyCinfo[arrSize];
			for (int i = 0; i < arrSize; i++) {
				bodyCinfos[i] = new hknpBodyCinfo(connector, stream, (int)arrValue.to + (i * hknpBodyCinfo.size));
			}
		}

		arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 80));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 80;
			constraintCinfos = new hknpConstraintCinfo[arrSize];
			for (int i = 0; i < arrSize; i++) {
				constraintCinfos[i] = new hknpConstraintCinfo(connector, stream,
						(int)arrValue.to + (i * hknpConstraintCinfo.size));
			}
		}

		arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 96));
		if (arrSize > 0) {
			DataInternal arrValue = connector.data1.readNext();
			assert arrValue.from == classOffset + 96;
			referencedObjects = new long[arrSize];
			for (int i = 0; i < arrSize; i++) {
				long contentsPosition = arrValue.to + (i * 0x08);//size of pointers
				referencedObjects[i] = HKXReader.getPointer(connector, contentsPosition);
			}
		}

		name = HKXReader.hkStringPtr(connector, classOffset + 112);

		return success;
	}

}