package nif.niobject.hkx;

import java.io.IOException;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

 

/**<class name='hkBaseObject' version='0' signature='0xe0708a00'>
	<members>
	</members>
</class>
*/
public class hkBaseObject {
	public boolean readFromStream(HKXReaderConnector connector, int classOffset) throws IOException, InvalidPositionException
	{
		return true;
	}

}