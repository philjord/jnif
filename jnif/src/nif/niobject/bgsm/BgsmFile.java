package nif.niobject.bgsm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


import nif.ProgressInputStream;

public class BgsmFile
{
	
	public static BSMaterial readMaterialFile(String fileName, InputStream inStr) throws IOException
	{
		if (inStr != null)
		{
			ProgressInputStream in = new ProgressInputStream(inStr);
			byte[] buf = new byte[4];
			in.read(buf);
			String headerString = new String(buf);
			if (headerString.equals("BGEM"))
			{
				BSMaterial m = new EffectMaterial(fileName);
				m.readFile(in);
				return m;
			}
			else if (headerString.equals("BGSM"))
			{
				BSMaterial m = new ShaderMaterial(fileName);
				m.readFile(in);
				return m;
			}
			else
			{
				in.close();
				throw new IOException("BAD Material file header: " + headerString);				 
			}

		}
		else
		{
			System.err.println("File Not Found in Mesh Source: " + fileName);
			return null;
		}
	}

	public static BSMaterial readMaterialFile(File file) throws IOException
	{
		BufferedInputStream nifIn = new BufferedInputStream(new FileInputStream(file));

		BSMaterial m = readMaterialFile(file.getCanonicalPath(), nifIn);
		nifIn.close();
		return m;
	}

}
