package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiExtraData;

public class BSConnectPoint
{
	/*	<compound name="BSConnectPoint">
	<add name="Root" type="SizedString" />
	<add name="Variable Name" type="SizedString" />
	<add name="Unknown Float 1" type="float" />
	<add name="Unknown 6 Shorts" type="ushort" arr1="6" />
	<add name="Unknown 4 Floats" type="float" arr1="4" />
	</compound>
	
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

	public static class BSConnectPointData
	{
		public String Root;
		public String VariableName;
		float UnknownFloat1;
		short[] Unknown6Shorts;
		float[] Unknown4Floats;

		public BSConnectPointData(InputStream stream) throws IOException
		{
			Root = ByteConvert.readSizedString(stream);
			VariableName = ByteConvert.readSizedString(stream);
			UnknownFloat1 = ByteConvert.readFloat(stream);
			Unknown6Shorts = ByteConvert.readShorts(6, stream);
			Unknown4Floats = ByteConvert.readFloats(4, stream);
		}
	}

}
