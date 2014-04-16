package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.NiAVObject;
import nif.niobject.NiNode;

public class BSTreeNode extends NiAVObject
{
	/**
	     <niobject name="BSTreeNode" inherit="NiAVObject">
	    Node for handling Trees, Switches branch configurations for variation?
	        <add name="Num Children" type="uint">The number of child objects.</add>
	        <add name="Children" type="Ref" template="NiAVObject" arr1="Num Children">List of child node object indices.</add>
	        <add name="Unknown Int 1" type="uint">Unknown</add>
	        <add name="Num Bones 1" type="uint">Unknown</add>
	        <add name="Bones 1" type="Ref" arr1="Num Bones 1" template="NiNode">Unknown</add>
	        <add name="Num Bones 2" type="uint">Unknown</add>
	        <add name="Bones" type="Ref" arr1="Num Bones 2" template="NiNode">Unknown</add>
	    </niobject>
	 */

	public int numChildren;

	public NifRef[] children;

	public int UnknownInt1;

	public int NumBones1;

	public NifRef[] Bones1;

	public int NumBones2;

	public NifRef[] Bones2;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numChildren = ByteConvert.readInt(stream);

		children = new NifRef[numChildren];
		for (int i = 0; i < numChildren; i++)
		{
			children[i] = new NifRef(NiAVObject.class, stream);
		}

		UnknownInt1 = ByteConvert.readInt(stream);

		NumBones1 = ByteConvert.readInt(stream);

		Bones1 = new NifRef[NumBones1];
		for (int i = 0; i < NumBones1; i++)
		{
			Bones1[i] = new NifRef(NiNode.class, stream);
		}

		NumBones2 = ByteConvert.readInt(stream);

		Bones2 = new NifRef[NumBones2];
		for (int i = 0; i < NumBones2; i++)
		{
			Bones2[i] = new NifRef(NiNode.class, stream);
		}

		return success;
	}
}
