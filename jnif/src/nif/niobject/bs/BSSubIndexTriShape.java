package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;

public class BSSubIndexTriShape extends BSTriShape
{
	public int numTriangles2;
	public int numA;
	public int numB;
	public SubIndexPart1[] SubIndexPart1;
	public SubIndexPart2 SubIndexPart2;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (dataSize > 0)
		{
			numTriangles2 = ByteConvert.readInt(stream);
			numA = ByteConvert.readInt(stream);
			numB = ByteConvert.readInt(stream);

			SubIndexPart1 = new SubIndexPart1[numA];
			for (int v = 0; v < numA; v++)
			{
				SubIndexPart1[v] = new SubIndexPart1(stream);
			}

			if (numA < numB)
			{
				SubIndexPart2 = new SubIndexPart2(stream);
			}
		}
		return success;
	}

	public static class SubIndexRecordA
	{
		public int UnknownInt1;
		public int UnknownInt2;
		public int UnknownInt3;
		public int UnknownInt4;

		public SubIndexRecordA(InputStream stream) throws IOException
		{
			UnknownInt1 = ByteConvert.readInt(stream);
			UnknownInt2 = ByteConvert.readInt(stream);
			UnknownInt3 = ByteConvert.readInt(stream);
			UnknownInt4 = ByteConvert.readInt(stream);
		}
	}

	public static class SubIndexPart1
	{
		public int UnknownInt1;
		public int UnknownInt2;
		public int UnknownInt3;
		public int NumRecords;
		public SubIndexRecordA[] SubIndexRecord;

		public SubIndexPart1(InputStream stream) throws IOException
		{
			UnknownInt1 = ByteConvert.readInt(stream);
			UnknownInt2 = ByteConvert.readInt(stream);
			UnknownInt3 = ByteConvert.readInt(stream);
			NumRecords = ByteConvert.readInt(stream);
			SubIndexRecord = new SubIndexRecordA[NumRecords];
			for (int i = 0; i < NumRecords; i++)
			{
				SubIndexRecord[i] = new SubIndexRecordA(stream);
			}
		}
	}

	public static class SubIndexRecordB
	{
		public int UnknownInt1;
		public int UnknownInt2;
		public int NumData;
		public float[] ExtraData;

		public SubIndexRecordB(InputStream stream) throws IOException
		{
			UnknownInt1 = ByteConvert.readInt(stream);
			UnknownInt2 = ByteConvert.readInt(stream);
			NumData = ByteConvert.readInt(stream);
			ExtraData = ByteConvert.readFloats(NumData, stream);
		}
	}

	public static class SubIndexPart2
	{
		public int NumA2;
		public int NumB2;
		public int[] Sequence;
		public SubIndexRecordB[] SubIndexRecord;
		public String SSFFile;

		public SubIndexPart2(InputStream stream) throws IOException
		{
			NumA2 = ByteConvert.readInt(stream);
			NumB2 = ByteConvert.readInt(stream);
			Sequence = ByteConvert.readInts(NumA2, stream);
			SubIndexRecord = new SubIndexRecordB[NumB2];
			for (int i = 0; i < NumB2; i++)
			{
				SubIndexRecord[i] = new SubIndexRecordB(stream);
			}

			SSFFile = ByteConvert.readRealShortString(stream);
		}
	}

}