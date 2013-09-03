package nif.niobject;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifControllerLink;

public class NiSequence extends NiObject
{
	/**
	 <niobject name="NiSequence" abstract="0" inherit="NiObject" ver1="4.2.2.0">

	 Root node used in some Empire Earth II .kf files (version 4.2.2.0).
	 
	 <add name="Name" type="string">
	 Name of this object. This is also the name of the action associated with this file. For instance, if the original NIF file is called "demon.nif" and this animation file contains an attack sequence, then the file would be called "demon_attack1.kf" and this field would contain the string "attack1".
	 </add>
	 <add name="Text Keys Name" type="string" ver2="10.1.0.0">
	 Name of following referenced NiTextKeyExtraData class.
	 </add>
	 <add name="Text Keys" type="Ref" template="NiTextKeyExtraData" ver2="10.1.0.0">Link to NiTextKeyExtraData.</add>
	 <add name="Num Controlled Blocks" type="uint">Number of controlled objects.</add>
	 <add name="Unknown Int 1" type="uint" ver1="10.1.0.106">Unknown.</add>
	 <add name="Controlled Blocks" type="ControllerLink" arr1="Num Controlled Blocks">Refers to controlled objects.</add>
	 </niobject>
	 
	 */

	public String name;

	public int numControlledBlocks;

	public int unknownInt1;

	public NifControllerLink[] controlledBlocks;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		name = ByteConvert.readIndexString(stream, nifVer);
		numControlledBlocks = ByteConvert.readInt(stream);
		unknownInt1 = ByteConvert.readInt(stream);
		
		controlledBlocks = new NifControllerLink[numControlledBlocks];
		for (int i = 0; i < numControlledBlocks; i++)
		{
			controlledBlocks[i] = new NifControllerLink(stream, nifVer);
		}

		return success;
	}
}