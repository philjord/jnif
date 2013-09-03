package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class FilterMode
{
	/*
	Specifies the availiable texture filter modes. That is, the way pixels within a texture are blended together when textures are displayed on the screen at a size other than their original dimentions.
	0: FILTER_NEAREST
	1: FILTER_BILERP
	2: FILTER_TRILERP
	3: FILTER_NEAREST_MIPNEAREST
	4: FILTER_NEAREST_MIPLERP
	5: FILTER_BILERP_MIPNEAREST
	Implementation
	
	* Niflib: TexFilterMode
	* NifSkope: int
	
	Attribute of...
	
	* TexDesc
	
	*/

	public int mode;

	public FilterMode(InputStream stream) throws IOException
	{
		mode = ByteConvert.readInt(stream);
	}

}
