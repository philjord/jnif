package nif.niobject.hkx.animation.skyrim;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.hkReferencedObject;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
 <class name='hkbProjectStringData' version='2' signature='0xca08c2ba' parent='hkReferencedObject'>
	<members>
		<member name='animationFilenames' type='hkArray&lt;hkStringPtr&gt;' offset='16' vtype='TYPE_ARRAY' vsubtype='TYPE_STRINGPTR' arrsize='0' flags='FLAGS_NONE'/>
		<member name='behaviorFilenames' type='hkArray&lt;hkStringPtr&gt;' offset='32' vtype='TYPE_ARRAY' vsubtype='TYPE_STRINGPTR' arrsize='0' flags='FLAGS_NONE'/>
		<member name='characterFilenames' type='hkArray&lt;hkStringPtr&gt;' offset='48' vtype='TYPE_ARRAY' vsubtype='TYPE_STRINGPTR' arrsize='0' flags='FLAGS_NONE'/>
		<member name='eventNames' type='hkArray&lt;hkStringPtr&gt;' offset='64' vtype='TYPE_ARRAY' vsubtype='TYPE_STRINGPTR' arrsize='0' flags='FLAGS_NONE'/>
		<member name='animationPath' type='hkStringPtr' offset='80' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='behaviorPath' type='hkStringPtr' offset='88' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='characterPath' type='hkStringPtr' offset='96' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='scriptsPath' type='hkStringPtr' offset='104' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='fullPathToSource' type='hkStringPtr' offset='112' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='rootPath' type='hkStringPtr' offset='120' vtype='TYPE_STRINGPTR' vsubtype='TYPE_VOID' arrsize='0' flags='SERIALIZE_IGNORED'/>
	</members>
</class>
 */
public class hkbProjectStringData extends hkReferencedObject {
	String[]	animationFilenames;
	String[]	behaviorFilenames;
	String[]	characterFilenames;
	String[]	eventNames;
	String		animationPath;
	String		behaviorPath;
	String		characterPath;
	String		scriptsPath;
	String		fullPathToSource;
	String		rootPath;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
		animationFilenames = HKXReader.hkStringArray(connector, classOffset + 16);
		behaviorFilenames = HKXReader.hkStringArray(connector, classOffset + 32);
		characterFilenames = HKXReader.hkStringArray(connector, classOffset + 48);
		eventNames = HKXReader.hkStringArray(connector, classOffset + 64);
		animationPath = HKXReader.hkStringPtr(connector, classOffset + 80);
		behaviorPath = HKXReader.hkStringPtr(connector, classOffset + 88);
		characterPath = HKXReader.hkStringPtr(connector, classOffset + 96);
		scriptsPath = HKXReader.hkStringPtr(connector, classOffset + 104);
		fullPathToSource = HKXReader.hkStringPtr(connector, classOffset + 112);
		rootPath = HKXReader.hkStringPtr(connector, classOffset + 120);
		return success;
	}

}
