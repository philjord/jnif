package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;

public class NiNode extends NiAVObject
{
	/**
	 
	 <niobject name="NiNode" inherit="NiAVObject" module="NiMain">
        Generic node object for grouping.
        <field name="Num Children" type="uint">The number of child objects.</field>
        <field name="Children" type="Ref" template="NiAVObject" length="Num Children">List of child node object indices.</field>
        <field name="Num Effects" type="uint" vercond="#NI_BS_LT_FO4#">The number of references to effect objects that follow.</field>
        <field name="Effects" type="Ref" template="NiDynamicEffect" length="Num Effects" vercond="#NI_BS_LT_FO4#">List of node effects. ADynamicEffect?</field>
    </niobject>
	 
	 */

	public int numChildren;

	public NifRef[] children;

	public int numEffects;

	public NifRef[] effects;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		numChildren = ByteConvert.readInt(stream);
		children = new NifRef[numChildren];
		for (int i = 0; i < numChildren; i++)
		{
			children[i] = new NifRef(NiAVObject.class, stream);
		}
		
		if (nifVer.NI_BS_LT_FO4())
		{			
			numEffects = ByteConvert.readInt(stream);
			effects = new NifRef[numEffects];
			for (int i = 0; i < numEffects; i++)
			{
				effects[i] = new NifRef(NiDynamicEffect.class, stream);
			}
		}

		return success;
	}

	@Override
	public void addDisplayRows(ArrayList<Object[]> list)
	{
		super.addDisplayRows(list);

		list.add(new Object[]
		{ "NiNode", "numChildren", "" + numChildren });
		list.add(new Object[]
		{ "NiNode", "children", "ArrayOf NifRef" + children.length });

		list.add(new Object[]
		{ "NiNode", "numEffects", "" + numEffects });
		list.add(new Object[]
		{ "NiNode", "effects", "ArrayOf NifRef" + effects.length });

	}

	/**
	 * This is not part of spec, but an assistive reverse pointer setup 
	 * @param blocks
	 */
	public void setChildToParentPointers(NiObject[] allNiObjects)
	{
		for (int i = 0; i < numChildren; i++)
		{
			NifRef child = children[i];
			if (child.ref >= 0)
			{
				NiObject actualNiObject = allNiObjects[child.ref];
				if (actualNiObject instanceof NiAVObject)
				{
					((NiAVObject) actualNiObject).parent = this;
				}
				else
				{
					System.out.println("child " + i + " is  " + actualNiObject + " but NiNode children should be" + NiAVObject.class);
				}
			}
			else
			{
				//-1 is ignored
			}
		}
	}
}