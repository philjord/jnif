package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;

public class NiParticleSystem extends NiParticles
{
	/**
	 * Notice all the way back to NiGeometry due to the odd inheritance thing! Note I have no BSGeometry calss and I ignore that inheritance thing entirely
	 * I'm not sure this affects anything much?, the gear below smash NiGeometry and BSGeometry together
	 * BSGeomtry is an new inheritance path, for new block types, but the exisiting particles got moved across so particles has this crazy gear to keep it in 
	 * NiGeometry but still parse properly on the later versions
	 * 
	<niobject name="NiGeometry" abstract="true" inherit="NiAVObject" module="NiMain">
        Describes a visible scene element with vertices like a mesh, a particle system, lines, etc.
            Bethesda 20.2.0.7 NIFs: NiGeometry was changed to BSGeometry. 
            Most new blocks (e.g. BSTriShape) do not refer to NiGeometry except NiParticleSystem was changed to use BSGeometry.
            This causes massive inheritance problems so the rows below are doubled up to exclude NiParticleSystem for Bethesda Stream 100+
            and to add data exclusive to BSGeometry.
        <field name="Bounding Sphere" type="NiBound" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GTE_SSE#" onlyT="NiParticleSystem" />
        <field name="Bound Min Max" type="float" length="6" since="20.2.0.7" until="20.2.0.7" vercond="#BS_F76#" onlyT="NiParticleSystem" />
        <field name="Skin" type="Ref" template="NiObject" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GTE_SSE#" onlyT="NiParticleSystem" />
        <field name="Data" type="Ref" template="NiGeometryData" vercond="#NI_BS_LT_SSE#">Data index (NiTriShapeData/NiTriStripData).</field>
        <field name="Data" type="Ref" template="NiGeometryData" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GTE_SSE#" excludeT="NiParticleSystem">Data index (NiTriShapeData/NiTriStripData).</field>
        <field name="Skin Instance" type="Ref" template="NiSkinInstance" since="3.3.0.13" vercond="#NI_BS_LT_SSE#" />
        <field name="Skin Instance" type="Ref" template="NiSkinInstance" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GTE_SSE#" excludeT="NiParticleSystem" />
        <field name="Material Data" type="MaterialData" since="10.0.1.0" vercond="#NI_BS_LT_SSE#" />
        <field name="Material Data" type="MaterialData" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GTE_SSE#" excludeT="NiParticleSystem" />
        <field name="Shader Property" type="Ref" template="BSShaderProperty" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GT_FO3#" />
        <field name="Alpha Property" type="Ref" template="NiAlphaProperty" since="20.2.0.7" until="20.2.0.7" vercond="#BS_GT_FO3#" />
    </niobject>
     <niobject name="NiParticles" inherit="NiGeometry" module="NiMain">
        Generic particle system node.
    </niobject>
 <niobject name="NiParticleSystem" inherit="NiParticles" module="NiParticle">
        A particle system.
        Contains members to mimic inheritance shifts for Bethesda 20.2, where NiParticles switched to inheriting BSGeometry.
        Until inheritance shifts are supported, the members are on NiParticleSystem instead of NiParticles for module reasons.
        <field name="Vertex Desc" type="BSVertexDesc" vercond="#BS_GTE_SSE#" />
        <field name="Far Begin" type="ushort" vercond="#BS_GTE_SKY#" />
        <field name="Far End" type="ushort" vercond="#BS_GTE_SKY#" />
        <field name="Near Begin" type="ushort" vercond="#BS_GTE_SKY#" />
        <field name="Near End" type="ushort" vercond="#BS_GTE_SKY#" />
        <field name="Data" type="Ref" template="NiPSysData" vercond="#BS_GTE_SSE#" />

        <field name="World Space" type="bool" default="true" since="10.1.0.0">If true, Particles are birthed into world space.  If false, Particles are birthed into object space.</field>
        <field name="Num Modifiers" type="uint" since="10.1.0.0">The number of modifier references.</field>
        <field name="Modifiers" type="Ref" template="NiPSysModifier" length="Num Modifiers" since="10.1.0.0">The list of particle modifiers.</field>
    </niobject>
    
    
    
    FOR REF here is BSGeometry
     <niobject name="BSGeometry" inherit="NiAVObject" module="BSMain" versions="#STF#">
        <field name="Bounding Sphere" type="NiBound" />
        <field name="Bound Min Max" type="float" length="6" />
        <field name="Skin" type="Ref" template="NiObject" />
        <field name="Shader Property" type="Ref" template="BSShaderProperty" />
        <field name="Alpha Property" type="Ref" template="NiAlphaProperty" />
        <field name="Meshes" type="BSMeshArray" length="4" />
    </niobject>
	 */

	public short UnknownShort1;

	public short UnknownShort2;

	public int UnknownInt1;
	public int UnknownInt2;
	public int UnknownInt3;
	public NifRef Data;

	public boolean worldSpace;

	public int numModifiers;

	public NifRef[] modifiers;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (nifVer.LOAD_USER_VER >= 12 && !nifVer.isBP())
		{
			UnknownShort1 = ByteConvert.readShort(stream);
			UnknownShort2 = ByteConvert.readShort(stream);
			UnknownInt1 = ByteConvert.readInt(stream);
		}

		if (nifVer.BS_GTE_SSE())
		{
			UnknownInt2 = ByteConvert.readInt(stream);
			UnknownInt3 = ByteConvert.readInt(stream);
			Data = new NifRef(NiPSysData.class, stream);
		}

		//crazy black prophecy stuff
		if (nifVer.isBP()){
			if (nifVer.LOAD_USER_VER == 9){
				ByteConvert.readBytes(6, stream);
			}else if (nifVer.LOAD_USER_VER == 12){
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