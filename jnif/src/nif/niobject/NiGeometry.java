package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.compound.NifVector3;
import nif.niobject.bs.BSSkin;

public abstract class NiGeometry extends NiAVObject
{
	/**
	 
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
	
	 */

	public NifRef data;

	public NifRef skin;

	public int numMaterials;

	public String[] materialNames;

	public int[] materialExtraData;

	public int activeMaterial;

	public boolean hasShader;

	public String shaderName;

	public int unknownInteger;

	public boolean dirtyFlag;

	//FO4 onwards
	public NifVector3 center;

	//FO4 onwards
	public float radius;
 

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		if (!(nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER == 12 && nifVer.BS_Version >= 130))
		{
			data = new NifRef(NiObject.class, stream);

			skin = new NifRef(NiSkinInstance.class, stream);

			// record for data to know when loading later, skinned or controlled things must have a seperate coords channel
			if (skin.ref != -1 || this.controller.ref != -1)
				nifVer.niGeometryDataToLoadMorphably.add(new Integer(data.ref));

			if (nifVer.LOAD_VER >= NifVer.VER_10_1_0_101 && nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
			{
				if (this.extraDataList.length > 0)
					nifVer.niGeometryDataExtraDataArriving.add(new Integer(data.ref));
			}

			if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7)
			{
				numMaterials = ByteConvert.readInt(stream);

				materialNames = new String[numMaterials];
				materialExtraData = new int[numMaterials];
				for (int i = 0; i < numMaterials; i++)
				{
					materialNames[i] = ByteConvert.readIndexString(stream, nifVer);
					materialExtraData[i] = ByteConvert.readInt(stream);
				}

				activeMaterial = ByteConvert.readInt(stream);
				dirtyFlag = ByteConvert.readBool(stream, nifVer);
			}
		}

		if (nifVer.LOAD_VER >= NifVer.VER_10_0_1_0 && nifVer.LOAD_VER <= NifVer.VER_20_1_0_3)
		{
			hasShader = ByteConvert.readBool(stream, nifVer);
			if (hasShader)
			{
				shaderName = ByteConvert.readIndexString(stream, nifVer);
				unknownInteger = ByteConvert.readInt(stream);
			}
		}

		if (nifVer.BS_GTE_SSE()) {
			center = new NifVector3(stream);// should be NiBound
			radius = ByteConvert.readFloat(stream);
		}
		
		if(nifVer.BS_F76()) {
			float[] BoundMinMax = ByteConvert.readFloats(6, stream);
		}
			
		if (nifVer.BS_GTE_SSE())
			skin = new NifRef(BSSkin.Instance.class, stream);


		if ((nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER == 12) && !nifVer.isBP())
		{
			// should be shader and alpha properties, but I'm not sure if anything else writes to this array of refs?
			numProperties = 2;
			properties = new NifRef[numProperties];
			properties[0] = new NifRef(NiObject.class, stream);
			properties[1] = new NifRef(NiObject.class, stream);
		}

		return success;
	}
}