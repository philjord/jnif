package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class NifbhkCMSDMaterial
{
/**
 * 	<compound name="bhkCMSDMaterial">
    per-chunk material, used in bhkCompressedMeshShapeData
		<add name="Material ID" type="uint">Unknown</add>
		<add name="Unknown Integer" type="uint">Always 1?</add>
	</compound>
 */
	
	public int MaterialID;

	public int UnknownInteger;

	 

	public NifbhkCMSDMaterial(InputStream stream) throws IOException
	{		
		MaterialID = ByteConvert.readInt(stream);
		UnknownInteger = ByteConvert.readInt(stream);
	}
}
