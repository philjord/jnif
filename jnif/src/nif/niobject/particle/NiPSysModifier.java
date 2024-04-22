package nif.niobject.particle;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;
import nif.niobject.NiObject;

public abstract class NiPSysModifier extends NiObject
{
	/**
 
	 <niobject name="NiPSysModifier" abstract="true" inherit="NiObject" module="NiParticle">
        Abstract base class for all particle system modifiers.
        <field name="Name" type="string">Used to locate the modifier.</field>
        <field name="Order" type="NiPSysModifierOrder" default="ORDER_GENERAL">
            Modifier's priority in the particle modifier chain.
            <default onlyT="NiPSysAgeDeathModifier" value="ORDER_KILLOLDPARTICLES" />
            <default onlyT="NiPSysMeshEmitter" value="ORDER_EMITTER" />
            <default onlyT="NiPSysVolumeEmitter" value="ORDER_EMITTER" />
            <default onlyT="NiPSysSpawnModifier" value="ORDER_EMITTER" />
            <default onlyT="NiPSysColorModifier" value="ORDER_GENERAL" />
            <default onlyT="NiPSysGrowFadeModifier" value="ORDER_GENERAL" />
            <default onlyT="NiPSysRotationModifier" value="ORDER_GENERAL" />
            <default onlyT="NiPSysBombModifier" value="ORDER_FORCE" />
            <default onlyT="NiPSysDragModifier" value="ORDER_FORCE" />
            <default onlyT="NiPSysFieldModifier" value="ORDER_FORCE" />
            <default onlyT="NiPSysGravityModifier" value="ORDER_FORCE" />
            <default onlyT="NiPSysColliderManager" value="ORDER_COLLIDER" />
            <default onlyT="NiPSysPositionModifier" value="ORDER_POS_UPDATE" />
            <default onlyT="NiPSysMeshUpdateModifier" value="ORDER_POSTPOS_UPDATE" />
            <default onlyT="NiPSysBoundUpdateModifier" value="ORDER_BOUND_UPDATE" />
            <default onlyT="BSPSysInheritVelocityModifier" value="ORDER_EMITTER" />
            <default onlyT="BSPSysScaleModifier" value="ORDER_GENERAL" />
            <default onlyT="BSPSysSimpleColorModifier" value="ORDER_GENERAL" />
            <default onlyT="BSPSysSubTexModifier" value="ORDER_GENERAL" />
            <default onlyT="BSParentVelocityModifier" value="ORDER_GENERAL" />
            <default onlyT="BSWindModifier" value="ORDER_FORCE" />
            <default onlyT="BSPSysRecycleBoundModifier" value="ORDER_POSTPOS_UPDATE" />
            <default onlyT="BSPSysLODModifier" value="ORDER_BSLOD" />
            <default onlyT="BSPSysStripUpdateModifier" value="ORDER_FO3_BSSTRIPUPDATE" versions="V20_2_0_7_FO3" />
            <default onlyT="BSPSysStripUpdateModifier" value="ORDER_SK_BSSTRIPUPDATE" versions="V20_2_0_7_SKY V20_2_0_7_SSE" />
            <default onlyT="NiPSysPartSpawnModifier" value="ORDER_WORLDSHIFT_PARTSPAWN" />
        </field>
        <field name="Target" type="Ptr" template="NiParticleSystem">NiParticleSystem parent of this modifier.</field>
        <field name="Active" type="bool" default="true">Whether or not the modifier is active.</field>
    </niobject>
	 */
	public String name;

	public int order;

	public NifPtr target;

	public boolean active;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		name = ByteConvert.readIndexString(stream, nifVer);
		order = ByteConvert.readInt(stream);
		target = new NifPtr(NiParticleSystem.class, stream);
		active = ByteConvert.readBool(stream, nifVer);

		return success;
	}
}
