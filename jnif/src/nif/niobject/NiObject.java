package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import nif.NifVer;

public abstract class NiObject
{
	/**
	 <niobject name="NiObject" abstract="1">
	 Abstract object type.
	 </niobject>
	 * @param nifVer  
	 */

	public NifVer nVer;

	public int refId = -1;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		this.nVer = nifVer;
		return true;
	}

	public void addDisplayRows(ArrayList<Object[]> list)
	{
		list.add(new Object[] { "NiObject", "nifVer", "" + nVer });
	}

	@Override
	public String toString()
	{
		return "[" + this.getClass().getSimpleName() + "] ";
	}
	
	
	
	
	 
}