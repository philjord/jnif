package nif;

import java.util.Iterator;

import nif.basic.NifPtr;
import nif.basic.NifRef;
import nif.niobject.NiObject;

public class NiObjectList implements Iterable<NiObject>
{
	public NifVer nifVer;

	private NiObject[] niObjects;

	public NiObjectList(NifVer nifVer, NiObject[] niObjects)
	{
		this.nifVer = nifVer;
		this.niObjects = niObjects;
	}

	//TODO: the NifFile itself has a NifFooter that actually literraly 
	//points to valid roots in teh nif file, this is a poor subsitute
	public NiObject root()
	{
		return niObjects[0];
	}

	public NiObject get(NifRef nr)
	{
		if (nr != null && nr.ref != -1)
		{
			return niObjects[nr.ref];
		}
		else
		{
			return null;
		}
	}

	public NiObject get(NifPtr np)
	{
		if (np != null && np.ptr != -1)
		{
			return niObjects[np.ptr];
		}
		else
		{
			return null;
		}
	}

	public int length()
	{
		return niObjects.length;
	}

	public NiObject[] getNiObjects()
	{
		return niObjects;
	}

	@Override
	public Iterator<NiObject> iterator()
	{
		return new Iterator<NiObject>()
		{
			private int idx = 0;

			@Override
			public boolean hasNext()
			{
				return idx < niObjects.length;
			}

			@Override
			public NiObject next()
			{
				return niObjects[idx++];
			}

			@Override
			public void remove()
			{
				new Throwable("Remove!! not implemented ").printStackTrace();
			}
		};
	}
}
