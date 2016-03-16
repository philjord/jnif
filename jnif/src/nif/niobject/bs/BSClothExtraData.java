package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public class BSClothExtraData extends BSbhkNPObject
{
 

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);		

		//F:\game media\Fallout4\Meshes\Clothes\Bathrobe\OutfitF.nif
		// starts with sexy headery goodness

		//__classnames__
		//hclScratchBufferDefinition
		//Collidable_LLeg_Thigh001
		//Collidable_LLeg_Calf
		//DefaultClothPose
		//Skin Simulation
		//StretchLink Constraint
		//BendStiffness Constraint
		//standardLinks
		//stretchLinks
		//Bend Stiffness
		//CLOTH_Dangly_SIM together near the end
		//SimBuf //Root Transforms//Skin Simulation //DefaultClothPose
		//Root stuff then AnimObjectA then millions of strings
		//Root appears 4 times

		// I saw A B C down to Z but the spaces are like 0 not spaces just after ’@

		// root appears here:
		//349,49136,68576,72352,77008
		//77008 is the start of a bunch of 16 length strings
		//System.out.println(""+asS.substring(77008, 77008+500));		
	 
		
		//Data = ByteConvert.readBytes(72000, stream);
	//	String asS = new String(Data);
		//System.out.println("" + new String(Data));

		//for (int i = 0; i < 442; i++)
		//	System.out.println("" + ByteConvert.readShort(stream));

		// abunch of shorts like index values
		
		//1031 0 ints!
	//	for (int i = 0; i < 1031; i++)
		{
	//		if (ByteConvert.readInt(stream) != 0)
	//			System.out.println("non zero there!");
		}
		//6464 bytes of strings = 404 string
	//	Data = ByteConvert.readBytes(6464, stream);
	//	asS = new String(Data);
	//	System.out.println("" + asS);

		return success;
	}
}
