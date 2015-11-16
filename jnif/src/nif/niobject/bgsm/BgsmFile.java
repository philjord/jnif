package nif.niobject.bgsm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.ProgressInputStream;

public class BgsmFile
{
	public String headerString = "";
	public String[] textures;

	public BgsmFile(ProgressInputStream stream) throws IOException
	{
		byte[] buf = new byte[4];
		stream.read(buf);
		headerString = new String(buf);
		if (!headerString.equals("BGSM"))
		{
			throw new IOException("BAD BGSM header");
		}

		textures = new String[6];

		ByteConvert.readInt(stream);
		ByteConvert.readInt(stream);
		ByteConvert.readInt(stream);
		ByteConvert.readInt(stream);
		buf = new byte[43];
		stream.read(buf);

		textures[0] = ByteConvert.readSizedString(stream);
		System.out.println("textures[0] " + textures[0]);
		textures[1] = ByteConvert.readSizedString(stream);
		System.out.println("textures[1] " + textures[1]);
		textures[2] = ByteConvert.readSizedString(stream);
		System.out.println("textures[2] " + textures[2]);

		
	
		/*buf = new byte[90]; //nope
		 
		stream.read(buf);
		
		// possibly a temple file (bgsm) now
		textures[4] = ByteConvert.readSizedString(stream);
		System.out.println("textures[4] " + textures[4]);
		
		//23 more?
		buf = new byte[23];
		stream.read(buf);*/

	}

	public static BgsmFile readBgsmFile(String fileName, InputStream inStr) throws IOException
	{
		ProgressInputStream in = new ProgressInputStream(inStr);
		return new BgsmFile(in);
	}

	public static BgsmFile readBgsmFile(File file) throws IOException
	{
		BufferedInputStream nifIn = new BufferedInputStream(new FileInputStream(file));

		BgsmFile bgsmFile = readBgsmFile(file.getCanonicalPath(), nifIn);
		nifIn.close();
		return bgsmFile;
	}

}
