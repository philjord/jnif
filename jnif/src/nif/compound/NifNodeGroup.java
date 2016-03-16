package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.basic.NifRef;
import nif.niobject.NiNode;

public class NifNodeGroup
{
	/**
	 <compound name="NodeGroup">

	 A group of NiNodes references.
	 
	 <add name="Num Nodes" type="uint">Number of node references that follow.</add>
	 <add name="Nodes" type="Ptr" template="NiNode" arr1="Num Nodes">The list of NiNode references.</add>
	 </compound>
	 */

	public int numNodes;

	public NifRef[] nodes;

	public NifNodeGroup(ByteBuffer stream) throws IOException
	{
		numNodes = ByteConvert.readInt(stream);
		nodes = new NifRef[numNodes];
		for (int i = 0; i < numNodes; i++)
		{
			nodes[i] = new NifRef(NiNode.class, stream);
		}

	}
}
