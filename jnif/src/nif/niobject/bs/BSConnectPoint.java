package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifQuaternion;
import nif.compound.NifVector3;
import nif.niobject.NiExtraData;

public class BSConnectPoint
{

	/*
	<niobject name="BSConnectPoint::Parents" inherit="NiExtraData">
	Fallout 4 Item Slot Parent
	<add name="Num Connect Points" type="uint" />
	<add name="Connect Points" type="BSConnectPoint" arr1="Num Connect Points" />
	</niobject>
	
	<niobject name="BSConnectPoint::Children" inherit="NiExtraData">
	Fallout 4 Item Slot Child
	<add name="Unknown Byte" type="byte" />
	<add name="Num Targets" type="int" />
	<add name="Target" type="SizedString" arr1="Num Targets" />
	</niobject>
	*/

	public static class Parents extends NiExtraData
	{
		public int NumConnectPoints;
		public BSConnectPointData[] ConnectPoints;

		public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
		{
			boolean success = super.readFromStream(stream, nifVer);
			NumConnectPoints = ByteConvert.readInt(stream);
			ConnectPoints = new BSConnectPointData[NumConnectPoints];
			for (int v = 0; v < NumConnectPoints; v++)
			{
				ConnectPoints[v] = new BSConnectPointData(stream);
			}
			return success;
		}
	}

	public static class Children extends NiExtraData
	{
		public byte unknownByte;
		public int NumTargets;
		public String[] Target;

		public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
		{
			boolean success = super.readFromStream(stream, nifVer);
			unknownByte = ByteConvert.readByte(stream);
			NumTargets = ByteConvert.readInt(stream);
			Target = new String[NumTargets];
			for (int v = 0; v < NumTargets; v++)
			{
				Target[v] = ByteConvert.readSizedString(stream);
			}
			return success;
		}
	}

	/*	<compound name="BSConnectPoint">
	<add name="Root" type="SizedString" />
	<add name="Variable Name" type="SizedString" />
	<add name="Rotation" type="Quaternion" />
	<add name="Translation" type="Vector3" />
	<add name="Scale" type="float" />
	</compound>
	*/
	public static class BSConnectPointData
	{
		public String Root;
		public String VariableName;
		NifQuaternion Rotation;
		NifVector3 Translation;
		float Scale;

		public BSConnectPointData(InputStream stream) throws IOException
		{
			Root = ByteConvert.readSizedString(stream);
			VariableName = ByteConvert.readSizedString(stream);
			Rotation = new NifQuaternion(stream);
			Translation = new NifVector3(stream);
			Scale = ByteConvert.readFloat(stream);
		}
	}

}
