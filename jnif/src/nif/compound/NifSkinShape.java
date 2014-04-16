package nif.compound;

import java.io.IOException;
import java.io.InputStream;

import nif.basic.NifPtr;
import nif.basic.NifRef;
import nif.niobject.NiSkinInstance;
import nif.niobject.NiTriBasedGeom;

public class NifSkinShape
{
	/**
	 <compound name="SkinShape">

	 Reference to shape and skin instance.
	 
	 <add name="Shape" type="Ptr" template="NiTriBasedGeom">The shape.</add>
	 <add name="Skin Instance" type="Ref" template="NiSkinInstance">Skinning instance for the shape?</add>
	 </compound>
	 */

	public NifPtr shape;

	public NifRef skinInstance;

	public NifSkinShape(InputStream stream) throws IOException
	{
		shape = new NifPtr(NiTriBasedGeom.class, stream);
		skinInstance = new NifRef(NiSkinInstance.class, stream);
	}
}
