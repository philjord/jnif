package nif;

import java.io.IOException;
import java.io.InputStream;

public class ProgressInputStream extends InputStream
{
	// The InputStream to read bytes from
	private InputStream in = null;

	// The number of bytes that have been read from the InputStream
	private long bytesRead = 0;

	// in case a reset is called
	private long markedBytesRead = 0;

	public ProgressInputStream(InputStream in)
	{
		this.in = in;
	}

	@Override
	public int available() throws IOException
	{
		return in.available();
	}

	public long getBytesRead()
	{
		return bytesRead;
	}

	@Override
	public int read() throws IOException
	{
		int b = in.read();
		if (b != -1)
		{
			bytesRead++;
		}
		return b;
	}

	@Override
	public int read(byte[] b) throws IOException
	{
		int read = in.read(b);
		bytesRead += read;
		return read;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException
	{
		int read = in.read(b, off, len);
		bytesRead += read;
		return read;
	}

	@Override
	public void mark(int readlimit)
	{
		in.mark(readlimit);
		markedBytesRead = bytesRead;
	}

	@Override
	public void reset() throws IOException
	{
		in.reset();
		bytesRead = markedBytesRead;
	}

	@Override
	public long skip(long n) throws IOException
	{
		long skipped = in.skip(n);
		bytesRead += skipped;
		return skipped;
	}

	@Override
	public boolean markSupported()
	{
		return in.markSupported();
	}

	@Override
	public void close() throws IOException
	{
		in.close();
	}
}
