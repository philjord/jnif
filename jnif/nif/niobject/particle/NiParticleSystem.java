package nif.niobject.particle;

import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;

public class NiParticleSystem extends NiParticles
{
	/**
	

	<niobject name="NiParticleSystem" abstract="0" inherit="NiParticles">
	    A particle system.
		<add name="Unknown Short 1" type="ushort" vercond="User Version >= 12">Unknown</add>
		<add name="Unknown Short 2" type="ushort" vercond="User Version >= 12">Unknown</add>
		<add name="Unknown Int 1" type="uint" vercond="User Version >= 12">Unknown</add>
		<add name="World Space" type="bool" ver1="10.1.0.0">If true, Particles are birthed into world space.  If false, Particles are birthed into object space.</add>
	    <add name="Num Modifiers" type="uint" ver1="10.1.0.0">The number of modifier references.</add>
	    <add name="Modifiers" type="Ref" template="NiPSysModifier" arr1="Num Modifiers" ver1="10.1.0.0">The list of particle modifiers.</add>
	</niobject>
	 */

	public short UnknownShort1;

	public short UnknownShort2;

	public int UnknownInt1;

	public boolean worldSpace;

	public int numModifiers;

	public NifRef[] modifiers;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (nifVer.LOAD_USER_VER >= 12 && nifVer.LOAD_VER != NifVer.VER_20_3_0_9)
		{
			UnknownShort1 = ByteConvert.readShort(stream);
			UnknownShort2 = ByteConvert.readShort(stream);
			UnknownInt1 = ByteConvert.readInt(stream);
		}

		if (nifVer.LOAD_VER == NifVer.VER_20_3_0_9)
		{
			if (nifVer.LOAD_USER_VER == 9)
			{//TODO: why?
				ByteConvert.readBytes(6, stream);
			}
			else
			{
				//TODO: why?
				ByteConvert.readBytes(7, stream);
			}

		}

		worldSpace = ByteConvert.readBool(stream, nifVer);

		numModifiers = ByteConvert.readInt(stream);
		modifiers = new NifRef[numModifiers];
		for (int i = 0; i < numModifiers; i++)
		{
			modifiers[i] = new NifRef(NiPSysModifier.class, stream);
		}
		return success;
	}
}