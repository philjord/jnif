package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifParticleDesc;
import nif.compound.NifVector3;
 

public class NiPSysData extends NiParticlesData
{
	/**

	<niobject name="NiPSysData" inherit="NiParticlesData" module="NiParticle">
        Particle system data.
        <field name="Particle Info" type="NiParticleInfo" length="Num Vertices" vercond="!#BS202#" />
        <field name="Unknown Vector" type="Vector3" vercond="#BS_GTE_F76#" />
        <field name="Unknown QQSpeed Byte 1" type="byte" since="20.2.4.7" until="20.2.4.7" />
        <field name="Has Rotation Speeds" type="bool" since="20.0.0.2" />
        <field name="Rotation Speeds" type="float" length="Num Vertices" cond="Has Rotation Speeds" since="20.0.0.2" vercond="!#BS202#" />
        <field name="Num Added Particles" type="ushort" vercond="!#BS202#" />
        <field name="Added Particles Base" type="ushort" vercond="!#BS202#" />
        <field name="Unknown QQSpeed Byte 2" type="byte" since="20.2.4.7" until="20.2.4.7">Exact position unknown, could be before Num Added Particles instead.</field>
    </niobject>
	 */

	public NifParticleDesc[] particleDescriptions;
	
	public NifVector3 UnknownVector;
	
	public byte UnknownQQSpeedByte1;

	public boolean HasRotationSpeeds;

	public float[] RotationSpeeds;

	public short NumAddedParticles;

	public short AddedParticlesBase;
	
	public byte UnknownQQSpeedByte2;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
       	
		//<field name="Particle Info" type="NiParticleInfo" length="Num Vertices" vercond="!#BS202#" />
		if (!nifVer.BS202()) {
			particleDescriptions = new NifParticleDesc[numVertices];
			for (int i = 0; i < numVertices; i++) {
				particleDescriptions[i] = new NifParticleDesc(stream);
			}
		}
		
		//<field name="Unknown Vector" type="Vector3" vercond="#BS_GTE_F76#" />
		if (nifVer.BS_GTE_F76())  
			UnknownVector = new NifVector3(stream);
 
        //<field name="Unknown QQSpeed Byte 1" type="byte" since="20.2.4.7" until="20.2.4.7" />
		if (nifVer.LOAD_VER == NifVer.VER_20_2_4_7)
			UnknownQQSpeedByte1 = ByteConvert.readByte(stream);
		
        //<field name="Has Rotation Speeds" type="bool" since="20.0.0.2" />
		if (nifVer.LOAD_VER >= NifVer.VER_20_0_0_2)
			HasRotationSpeeds = ByteConvert.readBool(stream, nifVer);
		
		//<field name="Rotation Speeds" type="float" length="Num Vertices" cond="Has Rotation Speeds" since="20.0.0.2" vercond="!#BS202#" />
		if (nifVer.LOAD_VER >= NifVer.VER_20_0_0_2 && !nifVer.BS202()) {
			if (HasRotationSpeeds) {
				RotationSpeeds = new float[numVertices];
				for (int i = 0; i < numVertices; i++) {
					RotationSpeeds[i] = ByteConvert.readFloat(stream);
				}
			}
		}
        
		if (!nifVer.BS202()) {
			//<field name="Num Added Particles" type="ushort" vercond="!#BS202#" />
			NumAddedParticles = ByteConvert.readShort(stream);
			//<field name="Added Particles Base" type="ushort" vercond="!#BS202#" />        
			AddedParticlesBase = ByteConvert.readShort(stream);
		}
		
		//<field name="Unknown QQSpeed Byte 2" type="byte" since="20.2.4.7" until="20.2.4.7">Exact position unknown, could be before Num Added Particles instead.</field>
		if (nifVer.LOAD_VER == NifVer.VER_20_2_4_7)
			UnknownQQSpeedByte2 = ByteConvert.readByte(stream);

		return success;
	}
}