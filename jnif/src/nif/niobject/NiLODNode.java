package nif.niobject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import nif.NifVer;
import nif.basic.NifRef;

public class NiLODNode extends NiSwitchNode
{
	/**
	 
	 <niobject name="NiLODNode" abstract="0" inherit="NiSwitchNode" ver1="3.1">

	 Level of detail selector. Links to different levels of detail of the same model, used to switch a geometry at a specified distance.
	 
	 <add name="LOD Center" type="Vector3" ver1="4.0.0.2" ver2="10.0.1.0">Point to calculate distance from for switching?</add>
	 <add name="Num LOD Levels" type="uint" ver2="10.0.1.0">Number of levels of detail.</add>
	 <add name="LOD Levels" type="LODRange" arr1="Num LOD Levels" ver2="10.0.1.0">
	 The ranges of distance that each level of detail applies in.
	 </add>
	 <add name="LOD Level Data" type="Ref" template="NiLODData" ver1="10.1.0.0">
	 Refers to LOD level information, either distance or screen size based.
	 </add>
	 </niobject>
	 
	 */

	public NifRef lODLevelData;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		lODLevelData = new NifRef(NiLODData.class, stream);

		return success;
	}

	public void addDisplayRows(ArrayList<Object[]> list)
	{
		super.addDisplayRows(list);

		list.add(new Object[]
		{ "NiLODNode", "lODLevelData", "" + lODLevelData });

	}
}