package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class BSSubIndexTriShape extends BSTriShape
{
	public int numTriangles2;
	public int numA;
	public int numB;
	public BSSITSSegment[] SubIndexPart1;
	public SubIndexPart2 SubIndexPart2;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (dataSize > 0)
		{
			numTriangles2 = ByteConvert.readInt(stream);
			numA = ByteConvert.readInt(stream);
			numB = ByteConvert.readInt(stream);

			SubIndexPart1 = new BSSITSSegment[numA];
			for (int v = 0; v < numA; v++)
			{
				SubIndexPart1[v] = new BSSITSSegment(stream);
			}

			if (numA < numB)
			{
				SubIndexPart2 = new SubIndexPart2(stream);
			}
		}
		return success;
	}

	public static class BSSITSSubSegment
	{
		public int TriangleOffset;
		public int TriangleCount;
		public int SegmentOffset;
		public int UnknownInt1;

		public BSSITSSubSegment(ByteBuffer stream) throws IOException
		{
			TriangleOffset = ByteConvert.readInt(stream);
			TriangleCount = ByteConvert.readInt(stream);
			SegmentOffset = ByteConvert.readInt(stream);
			UnknownInt1 = ByteConvert.readInt(stream);
		}
	}

	public static class BSSITSSegment
	{
		public int TriangleOffset;
		public int TriangleCount;
		public int UnknownHash;
		public int NumRecords;
		public BSSITSSubSegment[] SubIndexRecord;

		public BSSITSSegment(ByteBuffer stream) throws IOException
		{
			TriangleOffset = ByteConvert.readInt(stream);
			TriangleCount = ByteConvert.readInt(stream);
			UnknownHash = ByteConvert.readInt(stream);
			NumRecords = ByteConvert.readInt(stream);
			SubIndexRecord = new BSSITSSubSegment[NumRecords];
			for (int i = 0; i < NumRecords; i++)
			{
				SubIndexRecord[i] = new BSSITSSubSegment(stream);
			}
		}
	}

	public static class SubIndexRecordB
	{
		public int UnknownInt1;
		public int UnknownInt2;
		public int NumData;
		public float[] ExtraData;

		public SubIndexRecordB(ByteBuffer stream) throws IOException
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

		public SubIndexPart2(ByteBuffer stream) throws IOException
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