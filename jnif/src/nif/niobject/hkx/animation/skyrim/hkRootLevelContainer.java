package nif.niobject.hkx.animation.skyrim;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.niobject.hkx.hkBaseObject;
import nif.niobject.hkx.hkReferencedObject;
import nif.niobject.hkx.reader.Data1Interface;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
 https://github.com/nitaigao/engine-game-factions/blob/master/etc/vendor/havok/Source/Common/Serialize/Util/hkRootLevelContainer.h
 https://github.com/search?q=repo%3Aret2end%2FHKX2Library%20hkRootLevelContainer&type=code
 https://github.com/SARDONYX-sard/serde-hkx/blob/main/crates/havok_classes/src/generated/hkRootLevelContainer_.rs     
 https://github.com/SARDONYX-sard/serde-hkx/tree/0.1.0/docs/handson_hex_dump/defaultmale     
      
<?xml version="1.0" encoding="UTF-8"?>
<hkpackfile classversion="8" contentsversion="hk_2010.2.0-r1" toplevelobject="#0008">
   <hksection name="__data__">
      <hkobject name="#0010" class="hkbProjectStringData" signature="0x76ad60a">
         <hkparam name="animationFilenames" numelements="0"></hkparam>
         <hkparam name="behaviorFilenames" numelements="0"></hkparam>
         <hkparam name="characterFilenames" numelements="1">
            <hkcstring>characters\Minotaur_character.hkx</hkcstring>
         </hkparam>
         <hkparam name="eventNames" numelements="0"/>
      </hkobject>
      <hkobject name="#0009" class="hkbProjectData" signature="0x13a39ba7">
         <hkparam name="worldUpWS">(0.000000 0.000000 1.000000 0.000000)</hkparam>
         <hkparam name="stringData">#0010</hkparam>
         <hkparam name="defaultEventMode">EVENT_MODE_IGNORE_FROM_GENERATOR</hkparam>
      </hkobject>
      <hkobject name="#0008" class="hkRootLevelContainer" signature="0x2772c11e">
         <hkparam name="namedVariants" numelements="1">
            <hkobject>
               <hkparam name="name">hkbProjectData</hkparam>
               <hkparam name="className">hkbProjectData</hkparam>
               <hkparam name="variant">#0009</hkparam>
            </hkobject>
         </hkparam>
      </hkobject>
   </hksection>
</hkpackfile>
            
      
 */

// NOTE it does NOT extend hkBaseObject but construction is easier if we pretend 
// NOTE VERY VERY WELL!! this class appears to be 32bit system aligned on 4 byte boundaries, UNLIKE any other data
// https://github.com/SARDONYX-sard/serde-hkx/tree/0.1.0/docs/handson_hex_dump/defaultmale  
public class hkRootLevelContainer extends hkBaseObject {
	hkRootLevelContainerNamedVariant[] NamedVariants;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);
		
		// to double check we are in 32bit land we can
		//if(connector.header.is64bit == false)
		
		ByteBuffer file = connector.data.setup(classOffset + 0);
		byte[] baseArrayBytes = new byte[0X10];
		file.get(baseArrayBytes);
 
		final int len = 4;
		int accu = 1;
		int arrSize = 0;
		for (int i = 0; i < len; i++) {
			arrSize += (baseArrayBytes[i + 4] & 0xFF) * accu;// not normal 4 not 8
			accu *= 256;
		}
		if (arrSize > 0) {
			Data1Interface data1 = connector.data1;
			DataInternal arrValue = data1.readNext();
			assert arrValue.from == classOffset + 0;
			NamedVariants = new hkRootLevelContainerNamedVariant[arrSize];
			for (int i = 0; i < arrSize; i++) {
				NamedVariants[i] = new hkRootLevelContainerNamedVariant(connector, stream,
						(int)arrValue.to + (i * hkRootLevelContainerNamedVariant.size));
			}
		}
		
		
	//	Data1Interface data1 = connector.data1;
	//	DataInternal arrValue1 = data1.readNext();
	//	DataInternal arrValue2 = data1.readNext();
	//	DataInternal arrValue3 = data1.readNext();
	//	DataInternal arrValue4 = data1.readNext();
		
		return success;
	}

	public static class hkRootLevelContainerNamedVariant {
		public static final int	size	= 8 + 4;// not normal 4 not 8

		public String			name;				// example  = "hkbProjectData"
		public String			className;			// hkbProjectData in example
		public long				variant;			// pointer to the variant, class hkbProjectData in example				

		public hkRootLevelContainerNamedVariant(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
				throws IOException, InvalidPositionException {
			name = HKXReader.hkStringPtr(connector, classOffset);
			className = HKXReader.hkStringPtr(connector, classOffset + 4);// not normal 4 not 8
			variant = HKXReader.getPointer(connector, classOffset + 8);// not normal 4 not 8
		}
	}
}