package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;

public abstract class NiDynamicEffect extends NiAVObject
{
	/**
	 <niobject name="NiDynamicEffect" abstract="true" inherit="NiAVObject" module="NiMain">
        Abstract base class for dynamic effects such as NiLights or projected texture effects.
        <field name="Switch State" type="bool" default="true" since="10.1.0.106" vercond="#NI_BS_LT_FO4#">If true, then the dynamic effect is applied to affected nodes during rendering.</field>
        <field name="Num Affected Nodes" type="uint" until="4.0.0.2" />
        <field name="Affected Nodes" type="Ptr" template="NiNode" length="Num Affected Nodes" until="3.3.0.13">If a node appears in this list, then its entire subtree will be affected by the effect.</field>
        <field name="Affected Node Pointers" type="uint" length="Num Affected Nodes" since="4.0.0.0" until="4.0.0.2">As of 4.0 the pointer hash is no longer stored alongside each NiObject on disk, yet this node list still refers to the pointer hashes. Cannot leave the type as Ptr because the link will be invalid.</field>
        <field name="Num Affected Nodes" type="uint" since="10.1.0.0" vercond="#NI_BS_LT_FO4#" />
        <field name="Affected Nodes" type="Ptr" template="NiNode" length="Num Affected Nodes" since="10.1.0.0" vercond="#NI_BS_LT_FO4#">If a node appears in this list, then its entire subtree will be affected by the effect.</field>
    </niobject>
	 */

	public boolean switchState;

	public int numAffectedNodes = 0;

	public NifRef[] affectedNodes;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_106 && nifVer.NI_BS_LT_FO4())
		{
			switchState = ByteConvert.readBool(stream, nifVer);
		}
		// before 4.0.0.2 these refs are garbage in the nif file
		if (nifVer.LOAD_VER <= NifVer.VER_4_0_0_2)
		{
			numAffectedNodes = ByteConvert.readInt(stream);
			//affectedNodes = new NifRef[numAffectedNodes];
			for (int i = 0; i < numAffectedNodes; i++)
			{
				//affectedNodes[i] = new NifRef(NiAVObject.class, stream);
				ByteConvert.readInt(stream);// chuck it away
			}
		}
		
		if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_0 && nifVer.NI_BS_LT_FO4())
		{
			numAffectedNodes = ByteConvert.readInt(stream);
			affectedNodes = new NifRef[numAffectedNodes];
			for (int i = 0; i < numAffectedNodes; i++)
			{
				affectedNodes[i] = new NifRef(NiAVObject.class, stream);
			}
		}

		return success;
	}
}