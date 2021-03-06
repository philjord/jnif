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
	 
	<niobject name="NiGeometry" abstract="1" inherit="NiAVObject">
	    Describes a visible scene element with vertices like a mesh, a particle system, lines, etc.
	    <add name="Data" type="Ref" template="NiGeometryData">Data index (NiTriShapeData/NiTriStripData).</add>
	    <add name="Skin Instance" type="Ref" template="NiSkinInstance" ver1="3.3.0.13">Skin instance index.</add>
	    <add name="Num Materials" type="uint" ver1="20.2.0.7">Num Materials</add>
	    <add name="Material Name" type="string" arr1="Num Materials" ver1="20.2.0.7">Unknown string.  Shader?</add>
	    <add name="Material Extra Data" type="int" arr1="Num Materials" ver1="20.2.0.7">Unknown integer; often -1. (Is this a link, array index?)</add>
	    <add name="Active Material" type="int" ver1="20.2.0.7" default="0">Active Material; often -1. (Is this a link, array index?)</add>
	    <add name="Has Shader" type="bool" ver1="10.0.1.0" ver2="20.1.0.3">Shader.</add>
	    <add name="Shader Name" type="string" cond="Has Shader" ver1="10.0.1.0" ver2="20.1.0.3">The shader name.</add>
	    <add name="Unknown Integer" type="int" cond="Has Shader" ver1="10.0.1.0" ver2="20.1.0.3">Unknown value, usually -1. (Not a link)</add>
	    <add name="Unknown Byte" type="byte" default="255" userver="1" vercond="(Version == 10.2.0.0) || ((Version != 20.0.0.5) &amp;&amp; (User Version 2 == 1))">Cyanide extension (only in version 10.2.0.0?).</add>
	    <add name="Unknown Integer 2" type="int" ver1="10.4.0.1" ver2="10.4.0.1">Unknown.</add>
	    <add name="Dirty Flag" type="bool" ver1="20.2.0.7">Dirty Flag?</add>
	    <add name="Properties" type="Ref" template="NiObject" arr1="2" ver1="20.2.0.7" userver="12">Two property links, used in Skyrim nifs.</add>
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

	//FO4 onwards
	public NifRef unknownRef;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		if (!(nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER == 12 && nifVer.LOAD_USER_VER2 == 130))
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

		if (nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER == 12 && nifVer.LOAD_USER_VER2 == 130)
		{
			//Super happy about these now!
			center = new NifVector3(stream);
			radius = ByteConvert.readFloat(stream);
			skin = new NifRef(BSSkin.Instance.class, stream);
		}

		if ((nifVer.LOAD_VER >= NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER == 12) && !nifVer.isBP())
		{
			numProperties = 2;
			properties = new NifRef[numProperties];
			properties[0] = new NifRef(NiObject.class, stream);
			properties[1] = new NifRef(NiObject.class, stream);
		}

		return success;
	}
}