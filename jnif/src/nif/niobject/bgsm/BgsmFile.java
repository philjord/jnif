package nif.niobject.bgsm;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel.MapMode;

public class BgsmFile
{

	public static BSMaterial readMaterialFile(String fileName, ByteBuffer in) throws IOException
	{
		if (in != null)
		{
			in.order(ByteOrder.LITTLE_ENDIAN);
			byte[] buf = new byte[4];
			in.get(buf);
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
		RandomAccessFile nifIn = new RandomAccessFile(file, "r");

		BSMaterial m = readMaterialFile(file.getCanonicalPath(), nifIn.getChannel().map(MapMode.READ_ONLY, 0, file.length()));
		nifIn.close();
		return m;
	}

}
