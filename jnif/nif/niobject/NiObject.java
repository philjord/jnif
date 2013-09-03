package nif.niobject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import nif.NifVer;

public abstract class NiObject
{
	/**
	 <niobject name="NiObject" abstract="1">
	 Abstract object type.
	 </niobject>
	 * @param nifVer TODO
	 */

	public NifVer nVer;

	// Note needs the suppress as the interface must be thus for inheritors
	@SuppressWarnings("unused")
	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		this.nVer = nifVer;
		return true;
	}

	public void addDisplayRows(ArrayList<Object[]> list)
	{
		list.add(new Object[]
		{ "NiObject", "nifVer", "" + nVer });
	}

	public String toString()
	{
		return "[" + this.getClass().getSimpleName() + "] ";
	}
}