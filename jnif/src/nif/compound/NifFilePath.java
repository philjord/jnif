package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class NifFilePath
{
	//NOTE just an index string

	/**
	 * <compound name="FilePath" niflibtype="IndexString" nifskopetype="filepath">

	 A string that contains the path to a file.
	 
	 <add name="String" type="SizedString" ver2="20.0.0.5">The normal string.</add>
	 <add name="Index" type="StringIndex" ver1="20.1.0.3">The string index.</add>
	 </compound>
	 */

	public String string;

	public NifFilePath(InputStream stream, NifVer nifVer) throws IOException
	{
		string = ByteConvert.readIndexString(stream, nifVer);
	}
}
