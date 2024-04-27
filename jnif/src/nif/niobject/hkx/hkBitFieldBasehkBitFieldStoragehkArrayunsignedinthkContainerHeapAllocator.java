package nif.niobject.hkx;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**<struct name='hkBitFieldBasehkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator' version='0' signature='0x7538539b'>
	<members>
		<member name='storage' type='struct hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator' ctype='hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator' offset='0' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</struct>*/

public class hkBitFieldBasehkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator    {
	hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator storage;
	
	public hkBitFieldBasehkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator(HKXReaderConnector connector, ByteBuffer stream, int classOffset) throws IOException, InvalidPositionException
	{		
		 //<member name='storage' type='struct hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator' ctype='hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator' offset='0' vtype='TYPE_STRUCT' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		storage = new hkBitFieldStoragehkArrayunsignedinthkContainerHeapAllocator(connector, stream, classOffset + 0);		
	}
}