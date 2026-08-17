package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import nif.compound.NifQuaternionXYZW;
import nif.compound.NifVector3;
import nif.niobject.hkx.reader.DataInternal;
import nif.niobject.hkx.reader.HKXReader;
import nif.niobject.hkx.reader.HKXReaderConnector;
import nif.niobject.hkx.reader.InvalidPositionException;

/**
<class name='hkaSplineCompressedAnimation' version='0' signature='0x8c3b5f7e' parent='hkaAnimation'>
	<members>
		<member name='numFrames' type='hkInt32' offset='56' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='numBlocks' type='hkInt32' offset='60' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='maxFramesPerBlock' type='hkInt32' offset='64' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='maskAndQuantizationSize' type='hkInt32' offset='68' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='blockDuration' type='hkReal' offset='72' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='blockInverseDuration' type='hkReal' offset='76' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='frameDuration' type='hkReal' offset='80' vtype='TYPE_REAL' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
		<member name='blockOffsets' type='hkArray&lt;hkUint32&gt;' offset='88' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT32' arrsize='0' flags='FLAGS_NONE'/>
		<member name='floatBlockOffsets' type='hkArray&lt;hkUint32&gt;' offset='104' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT32' arrsize='0' flags='FLAGS_NONE'/>
		<member name='transformOffsets' type='hkArray&lt;hkUint32&gt;' offset='120' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT32' arrsize='0' flags='FLAGS_NONE'/>
		<member name='floatOffsets' type='hkArray&lt;hkUint32&gt;' offset='136' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT32' arrsize='0' flags='FLAGS_NONE'/>
		<member name='data' type='hkArray&lt;hkUint8&gt;' offset='152' vtype='TYPE_ARRAY' vsubtype='TYPE_UINT8' arrsize='0' flags='FLAGS_NONE'/>
		<member name='endian' type='hkInt32' offset='168' vtype='TYPE_INT32' vsubtype='TYPE_VOID' arrsize='0' flags='FLAGS_NONE'/>
	</members>
</class>

https://github.com/aerisarn/hkxlib/blob/master/src/main/java/org/tes/hkx/lib/ext/hkaSplineCompressedAnimation.java

*/

public class hkaSplineCompressedAnimation extends hkaAnimation {

	public int				numFrames;				// frames are knots, so max knots inside a track but each track differs, each track is a bone
	public int				numBlocks;				// more than 1 if numFrame greater than 256 (last one is modulo 256 frames)
	public int				maxFramesPerBlock;		// always 256
	public int				maskAndQuantizationSize;
	public float			blockDuration;			// max duration of a block, but last block can be less based on frame count
	public float			blockInverseDuration;
	public float			frameDuration;			// how long 1 frame is so duration/numFrames
	public int[]			blockOffsets;			// where is block is in the data
	public int[]			floatBlockOffsets;		// where scalar data is in a given block in the data
	public int[]			transformOffsets;		// where rotation data is in the data
	public int[]			floatOffsets;			// float tracks are for non boned things, pure lists of floats for animation of other things
	public byte[]			data;
	public int				endian;

	public boolean			is64bit	= true;
	public AnimationTracks	animationTracks;

	@Override
	public boolean readFromStream(HKXReaderConnector connector, ByteBuffer stream, int classOffset)
			throws IOException, InvalidPositionException {
		boolean success = super.readFromStream(connector, stream, classOffset);

		if (connector.header.is64bit) {
			numFrames = stream.getInt(classOffset + 56);
			numBlocks = stream.getInt(classOffset + 60);
			maxFramesPerBlock = stream.getInt(classOffset + 64);
			maskAndQuantizationSize = stream.getInt(classOffset + 68);
			blockDuration = stream.getFloat(classOffset + 72);
			blockInverseDuration = stream.getFloat(classOffset + 76);
			frameDuration = stream.getFloat(classOffset + 80);

			int arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 88));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 88;
				blockOffsets = new int[arrSize];
				for (int i = 0; i < arrSize; i++) {
					blockOffsets[i] = stream.getInt((int)arrValue.to + (i * 4));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 104));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 104;
				floatBlockOffsets = new int[arrSize];
				for (int i = 0; i < arrSize; i++) {
					floatBlockOffsets[i] = stream.getInt((int)arrValue.to + (i * 4));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 120));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 120;
				transformOffsets = new int[arrSize];
				for (int i = 0; i < arrSize; i++) {
					transformOffsets[i] = stream.getInt((int)arrValue.to + (i * 4));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 136));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 136;
				floatOffsets = new int[arrSize];
				for (int i = 0; i < arrSize; i++) {
					floatOffsets[i] = stream.getInt((int)arrValue.to + (i * 4));
				}
			}

			arrSize = HKXReader.getSizeComponent(connector.data.setup(classOffset + 152));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 152;
				ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
				data = new byte[arrSize];
				s2.get(data);

			}
			endian = stream.getInt(classOffset + 168);

		} else {
			this.is64bit = false;

			numFrames = stream.getInt(classOffset + 40);
			numBlocks = stream.getInt(classOffset + 44);
			maxFramesPerBlock = stream.getInt(classOffset + 48);
			maskAndQuantizationSize = stream.getInt(classOffset + 52);
			blockDuration = stream.getFloat(classOffset + 56);
			blockInverseDuration = stream.getFloat(classOffset + 60);
			frameDuration = stream.getFloat(classOffset + 64);

			int arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 68));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 68;
				blockOffsets = new int[arrSize];
				for (int i = 0; i < arrSize; i++) {
					blockOffsets[i] = stream.getInt((int)arrValue.to + (i * 4));
				}
			}

			arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 80));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 80;
				floatBlockOffsets = new int[arrSize];
				for (int i = 0; i < arrSize; i++) {
					floatBlockOffsets[i] = stream.getInt((int)arrValue.to + (i * 4));
				}
			}

			arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 92));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 92;
				transformOffsets = new int[arrSize];
				for (int i = 0; i < arrSize; i++) {
					transformOffsets[i] = stream.getInt((int)arrValue.to + (i * 4));
				}
			}

			arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 104));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 104;
				floatOffsets = new int[arrSize];
				for (int i = 0; i < arrSize; i++) {
					floatOffsets[i] = stream.getInt((int)arrValue.to + (i * 4));
				}
			}

			arrSize = HKXReader.getSizeComponent32(connector.data.setup(classOffset + 116));
			if (arrSize > 0) {
				DataInternal arrValue = connector.data1.readNext();
				assert arrValue.from == classOffset + 116;
				ByteBuffer s2 = connector.data.setup((int)arrValue.to).slice().order(ByteOrder.LITTLE_ENDIAN);
				data = new byte[arrSize];
				s2.get(data);
			}
			endian = stream.getInt(classOffset + 128);
		}

		animationTracks = hkaSplineCompressedAnimation.ReadSplineCompressedAnimByteBlock(data, numberOfTransformTracks,
				numBlocks, maskAndQuantizationSize, numberOfFloatTracks, is64bit);

		//System.out.println("decodedData " + blockTransformTracks.size());

		// now I dump the data. it's decoded now
		data = null;

		return success;
	}

	public void printDebug() {
		System.out.println("hkaSplineCompressedAnimation debug:");
		System.out.println("duration " + duration);
		System.out.println("numberOfTransformTracks " + numberOfTransformTracks);
		System.out.println("numberOfFloatTracks " + numberOfFloatTracks);
		System.out.println("extractedMotion " + extractedMotion);
		System.out.println("numFrames " + numFrames);
		System.out.println("numBlocks " + numBlocks);
		System.out.println("maxFramesPerBlock " + maxFramesPerBlock);
		System.out.println("maskAndQuantizationSize " + maskAndQuantizationSize);
		System.out.println("blockDuration " + blockDuration);
		System.out.println("blockInverseDuration " + blockInverseDuration);
		System.out.println("frameDuration " + frameDuration);
		System.out.println("blockOffsets " + (blockOffsets != null ? blockOffsets.length : null));
		System.out.println("floatBlockOffsets " + (floatBlockOffsets != null ? floatBlockOffsets.length : null));
		System.out.println("transformOffsets " + (transformOffsets != null ? transformOffsets.length : null));
		System.out.println("floatOffsets " + (floatOffsets != null ? floatOffsets.length : null));

	}

	public static void Align(int alignment, ByteBuffer bb) {
		int loc = bb.position();
		int pad = (alignment - (loc % alignment));
		if (pad != 0 && pad < alignment) {
			bb.position(bb.position() + pad);
		}
	};

	// from 
	//https://github.com/Meowmaritus/MVDX2/blob/master/MVDX2/Havok/SplineCompressedAnimation.cs

	public enum FlagOffset {
		//https://stackoverflow.com/questions/9048225/java-enum-confusion-with-creating-a-bitmask-and-checking-permissions
		StaticX(0b00000001), //
		StaticY(0b00000010), //
		StaticZ(0b00000100), //
		StaticW(0b00001000), //
		SplineX(0b00010000), //
		SplineY(0b00100000), //
		SplineZ(0b01000000), //
		SplineW(0b10000000);

		private int _val;

		FlagOffset(int val) {
			_val = val;
		}

		public int getValue() {
			return _val;
		}

		public static List<FlagOffset> parseFlagOffsets(int val) {
			List<FlagOffset> apList = new ArrayList<FlagOffset>();
			for (FlagOffset ap : values()) {
				if ((val & ap.getValue()) != 0)
					apList.add(ap);
			}
			return apList;
		}
	};

	static enum ScalarQuantizationType {
		BITS8, BITS16,
	};

	static enum RotationQuantizationType {
		POLAR32, //4 bytes long
		THREECOMP40, //5 bytes long
		THREECOMP48, //6 bytes long
		THREECOMP24, //3 bytes long
		STRAIGHT16, //2 bytes long
		UNCOMPRESSED, //16 bytes long
	}

	static int GetRotationAlign(RotationQuantizationType qt) {
		switch (qt) {
			case POLAR32:
				return 4;
			case THREECOMP40:
				return 1;
			case THREECOMP48:
				return 2;
			case THREECOMP24:
				return 1;
			case STRAIGHT16:
				return 2;
			case UNCOMPRESSED:
				return 4;
			default:
				System.err.println("Wrong rotation quantization");
		}
		return -1;
	}

	static int GetRotationByteCount(RotationQuantizationType qt) {
		switch (qt) {
			case POLAR32:
				return 4;
			case THREECOMP40:
				return 5;
			case THREECOMP48:
				return 6;
			case THREECOMP24:
				return 3;
			case STRAIGHT16:
				return 2;
			case UNCOMPRESSED:
				return 16;
			default:
				System.err.println("Bad RotationQuantizationType");
		}
		return -1;
	}

	static float ReadQuantizedFloat(ByteBuffer bb, float min, float max, ScalarQuantizationType type) {
		float ratio = -1;
		switch (type) {
			case BITS8:
				ratio = (bb.get() & 0xff) / 255.0f;
				break;
			case BITS16:
				ratio = (bb.getShort() & 0xff) / 65535.0f;
				break;
			default:
				System.err.println("Bad ScalarQuantizationType");
		}
		return min + ((max - min) * ratio);
	}

	static float CastToFloat(int src) {
		return Float.intBitsToFloat(src);
	}

	static NifQuaternionXYZW ReadQuatPOLAR32(ByteBuffer br) {
		long rMask = (1 << 10) - 1;
		float rFrac = 1.0f / rMask;
		float fPI = 3.14159265f;
		float fPI2 = 0.5f * fPI;
		float fPI4 = 0.5f * fPI2;
		float phiFrac = fPI2 / 511.0f;

		int cVal = br.getInt();

		float R = CastToFloat((cVal >> 18) & (int)(rMask & 0xFFFFFFFF)) * rFrac;
		R = 1.0f - (R * R);

		float phiTheta = ((cVal & 0x3FFFF));

		float phi = (float)Math.floor(Math.sqrt(phiTheta));
		float theta = 0;

		if (phi > 0.0f) {
			theta = fPI4 * (phiTheta - (phi * phi)) / phi;
			phi = phiFrac * phi;
		}

		float magnitude = (float)Math.sqrt(1.0f - R * R);

		NifQuaternionXYZW retVal = new NifQuaternionXYZW(R, //
				(float)(Math.sin(phi) * Math.cos(theta) * magnitude), //
				(float)(Math.sin(phi) * Math.sin(theta) * magnitude), //
				(float)(Math.cos(phi) * magnitude));

		if ((cVal & 0x10000000) > 0)
			retVal.x *= -1;

		if ((cVal & 0x20000000) > 0)
			retVal.y *= -1;

		if ((cVal & 0x40000000) > 0)
			retVal.z *= -1;

		if ((cVal & 0x80000000) > 0)
			retVal.w *= -1;

		return retVal;
	}

	static NifQuaternionXYZW ReadQuatTHREECOMP48(ByteBuffer br) {
		long mask = (1 << 15) - 1;
		float fractal = 0.000043161f;

		short x = br.getShort();
		short y = br.getShort();
		short z = br.getShort();

		char resultShift = (char)(((y >> 14) & 2) | ((x >> 15) & 1));
		boolean rSign = (z >> 15) != 0;

		x &= (short)mask;
		x -= (short)(mask >> 1);
		y &= (short)mask;
		y -= (short)(mask >> 1);
		z &= (short)mask;
		z -= (short)(mask >> 1);

		float[] tempValF = new float[3];
		tempValF[0] = x * fractal;
		tempValF[1] = y * fractal;
		tempValF[2] = z * fractal;

		float[] retval = new float[4];

		for (int i = 0; i < 4; i++) {
			if (i < resultShift)
				retval[i] = tempValF[i];
			else if (i > resultShift)
				retval[i] = tempValF[i - 1];
		}

		retval[resultShift] = 1.0f - tempValF[0] * tempValF[0] - tempValF[1] * tempValF[1] - tempValF[2] * tempValF[2];

		if (retval[resultShift] <= 0.0f)
			retval[resultShift] = 0.0f;
		else
			retval[resultShift] = (float)Math.sqrt(retval[resultShift]);

		if (rSign)
			retval[resultShift] *= -1;

		return new NifQuaternionXYZW(retval[0], retval[1], retval[2], retval[3]);
	}

	static long Read40BitValue(ByteBuffer br) {
		byte[] bytes = new byte[5];
		for (int i = 0; i < bytes.length; i++)
			bytes[i] = br.get();
		return toLong(bytes);
	}

	// Converts an array of bytes into a long.  
	static long toLong(byte[] buf) {
		if (buf == null) {
			throw new RuntimeException("no good at all");
		}

		// Empirically confirmed
		long ret = ((buf[4] & 0xFFL) << 32) | //
					((buf[3] & 0xFFL) << 24) | //
					((buf[2] & 0xFFL) << 16) | //
					((buf[1] & 0xFFL) << 8) | //
					((buf[0] & 0xFFL) << 0);

		// I'd like this to work on any len but n'ermind

		// the below might be right now, I've fixed it to match the above
		/*		int shift = 0;
				long ret = 0;
				int end = Math.min(buf.length , 8);
				for (int i = 0; i < end; i++) {
					ret = ret | ((buf[i] & 0xFFL) << shift);
					shift += 8;
				}*/

		return ret;
	}

	static NifQuaternionXYZW ReadQuatTHREECOMP40(ByteBuffer br) {
		long mask = (1 << 12) - 1;
		long positiveMask = mask >> 1;
		float fractal = 0.000345436f;
		// Read only the 5 bytes needed to prevent EndOfStreamException :fatcat:
		long cVal = Read40BitValue(br);

		int x = (int)(cVal & mask);
		int y = (int)((cVal >> 12) & mask);
		int z = (int)((cVal >> 24) & mask);

		int resultShift = (int)((cVal >> 36) & 3);

		x -= (int)positiveMask;
		y -= (int)positiveMask;
		z -= (int)positiveMask;

		float[] tempValF = new float[3];
		tempValF[0] = x * fractal;
		tempValF[1] = y * fractal;
		tempValF[2] = z * fractal;

		float[] retval = new float[4];

		for (int i = 0; i < 4; i++) {
			if (i < resultShift)
				retval[i] = tempValF[i];
			else if (i > resultShift)
				retval[i] = tempValF[i - 1];
		}

		retval[resultShift] = 1.0f - tempValF[0] * tempValF[0] - tempValF[1] * tempValF[1] - tempValF[2] * tempValF[2];

		if (retval[resultShift] <= 0.0f)
			retval[resultShift] = 0.0f;
		else
			retval[resultShift] = (float)Math.sqrt(retval[resultShift]);

		if (((cVal >> 38) & 1) > 0)
			retval[resultShift] *= -1;

		//havok is always XYZW
		return new NifQuaternionXYZW(retval[0], retval[1], retval[2], retval[3]);

	}

	static NifQuaternionXYZW ReadQuantizedQuaternion(ByteBuffer br, RotationQuantizationType type) {
		switch (type) {
			case POLAR32:
				return ReadQuatPOLAR32(br);
			case THREECOMP40:
				return ReadQuatTHREECOMP40(br);
			case THREECOMP48:
				return ReadQuatTHREECOMP48(br);
			case THREECOMP24:
			case STRAIGHT16:
				System.err.println("Wrong rotation quantization");
				return null;
			case UNCOMPRESSED:
				return new NifQuaternionXYZW(br.getFloat(), br.getFloat(), br.getFloat(), br.getFloat());
			default:
				return NifQuaternionXYZW.Identity;
		}
	}

	// Algorithm A2.1 The NURBS Book 2nd edition, page 68
	static int FindKnotSpan(int degree, float value, int cPointsSize, short[] knots) {
		if (value >= knots[cPointsSize])
			return cPointsSize - 1;

		int low = degree;
		int high = cPointsSize;
		int mid = (low + high) / 2;

		while (value < knots[mid] || value >= knots[mid + 1]) {
			if (value < knots[mid])
				high = mid;
			else
				low = mid;

			mid = (low + high) / 2;
		}

		return mid;
	}

	public static class SplineChannelQuaternion {
		public boolean				IsDynamic	= true;
		public NifQuaternionXYZW[]	Values;
	}

	public static class SplineTrackQuaternion {
		public SplineChannelQuaternion	Channel;
		public short[]					Knots;
		public byte						Degree;

		SplineTrackQuaternion(ByteBuffer br, RotationQuantizationType quantizationType) {
			short numItems = br.getShort();
			Degree = br.get();
			int knotCount = numItems + Degree + 2;
			Knots = new short[knotCount];
			for (int i = 0; i < knotCount; i++) {
				Knots[i] = (short)(br.get() & 0xff);
			}

			Align(GetRotationAlign(quantizationType), br);

			Channel = new SplineChannelQuaternion();
			Channel.Values = new NifQuaternionXYZW[numItems + 1];
			for (int i = 0; i < numItems + 1; i++) {
				Channel.Values[i] = ReadQuantizedQuaternion(br, quantizationType);
			}
		}

		public NifQuaternionXYZW GetValue(float frame) {
			int knotspan = FindKnotSpan(Degree, frame, Channel.Values.length, Knots);
			return GetSinglePoint(knotspan, Degree, frame, Knots, Channel.Values);
		}

		//deburner think about threads!
		NifQuaternionXYZW	retVal	= new NifQuaternionXYZW(0.0f, 0.0f, 0.0f, 0.0f);
		NifQuaternionXYZW	temp	= new NifQuaternionXYZW(0.0f, 0.0f, 0.0f, 0.0f);// so we don't alter the channels quats

		//Basis_ITS1, GetPoint_NR1, TIME-EFFICIENT NURBS CURVE EVALUATION ALGORITHMS, pages 64 & 65
		NifQuaternionXYZW GetSinglePoint(	int knotSpanIndex, int degree, float frame, short[] knots,
											NifQuaternionXYZW[] cPoints) {
			float[] N = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f};

			for (int i = 1; i <= degree; i++) {
				for (int j = i - 1; j >= 0; j--) {
					float A = (frame - knots[knotSpanIndex - j])
								/ (knots[knotSpanIndex + i - j] - knots[knotSpanIndex - j]);
					float tmp = N[j] * A;
					N[j + 1] += N[j] - tmp;
					N[j] = tmp;
				}
			}

			//reset deburners
			retVal.set(0f, 0f, 0f, 0f);
			temp.set(0f, 0f, 0f, 0f);

			// this is the overloaded * on a xna quat and a scalar, the source!
			//https://learn.microsoft.com/en-us/previous-versions/windows/silverlight/dotnet-windows-silverlight/bb198126(v=xnagamestudio.35)
			//xna is old and dead now, but here is an open source re-implementation
			//https://github.com/FNA-XNA/FNA/blob/a3b3abd913086d960a00e83f0610b38f78225568/src/Quaternion.cs#L831
			// which says just multiply!

			// an interpolation of several weights of quat spread out over the degree range
			for (int i = 0; i <= degree; i++) {
				temp.set(cPoints[knotSpanIndex - i]);
				temp.mul(N[i]);
				retVal.add(temp);
			}
			return retVal;
		}
	}

	public static class SplineChannelFloat {
		public boolean	IsDynamic	= true;
		public float[]	Values;
	}

	public static class SplineTrackVector3 {
		public SplineChannelFloat	ChannelX;
		public SplineChannelFloat	ChannelY;
		public SplineChannelFloat	ChannelZ;
		public short[]				Knots;
		public int					Degree;

		SplineTrackVector3(	ByteBuffer br, List<FlagOffset> channelTypes, ScalarQuantizationType quantizationType,
							boolean isPosition) {
			// long debug_StartOfThisSplineTrack = br.position();

			short numItems = br.getShort();
			Degree = br.get();
			int knotCount = numItems + Degree + 2;
			Knots = new short[knotCount];
			for (int i = 0; i < knotCount; i++) {
				Knots[i] = (short)(br.get() & 0xff);
			}

			Align(4, br);

			float BoundsXMin = 0;
			float BoundsXMax = 0;
			float BoundsYMin = 0;
			float BoundsYMax = 0;
			float BoundsZMin = 0;
			float BoundsZMax = 0;

			ChannelX = new SplineChannelFloat();
			ChannelY = new SplineChannelFloat();
			ChannelZ = new SplineChannelFloat();

			if (channelTypes.contains(FlagOffset.SplineX)) {
				BoundsXMin = br.getFloat();
				BoundsXMax = br.getFloat();
			} else if (channelTypes.contains(FlagOffset.StaticX)) {
				ChannelX.Values = new float[] {br.getFloat()};
				ChannelX.IsDynamic = false;
			} else {
				ChannelX = null;
			}

			if (channelTypes.contains(FlagOffset.SplineY)) {
				BoundsYMin = br.getFloat();
				BoundsYMax = br.getFloat();
			} else if (channelTypes.contains(FlagOffset.StaticY)) {
				ChannelY.Values = new float[] {br.getFloat()};
				ChannelY.IsDynamic = false;
			} else {
				ChannelY = null;
			}

			if (channelTypes.contains(FlagOffset.SplineZ)) {
				BoundsZMin = br.getFloat();
				BoundsZMax = br.getFloat();
			} else if (channelTypes.contains(FlagOffset.StaticZ)) {
				ChannelZ.Values = new float[] {br.getFloat()};
				ChannelZ.IsDynamic = false;
			} else {
				ChannelZ = null;
			}

			if (channelTypes.contains(FlagOffset.SplineX))
				ChannelX.Values = new float[numItems + 1];
			if (channelTypes.contains(FlagOffset.SplineY))
				ChannelY.Values = new float[numItems + 1];
			if (channelTypes.contains(FlagOffset.SplineZ))
				ChannelZ.Values = new float[numItems + 1];

			for (int i = 0; i < numItems + 1; i++) {
				if (channelTypes.contains(FlagOffset.SplineX)) {
					ChannelX.Values[i] = (ReadQuantizedFloat(br, BoundsXMin, BoundsXMax, quantizationType));
				}

				if (channelTypes.contains(FlagOffset.SplineY)) {
					ChannelY.Values[i] = (ReadQuantizedFloat(br, BoundsYMin, BoundsYMax, quantizationType));
				}

				if (channelTypes.contains(FlagOffset.SplineZ)) {
					ChannelZ.Values[i] = (ReadQuantizedFloat(br, BoundsZMin, BoundsZMax, quantizationType));
				}
			}
		}

		//Basis_ITS1, GetPoint_NR1, TIME-EFFICIENT NURBS CURVE EVALUATION ALGORITHMS, pages 64 & 65
		float GetSinglePoint(int knotSpanIndex, int degree, float frame, short[] knots, float[] cPoints) {
			float[] N = {1, 0, 0, 0, 0};

			for (int i = 1; i <= degree; i++) {
				for (int j = i - 1; j >= 0; j--) {

					float A = (frame - knots[knotSpanIndex - j])
								/ (knots[knotSpanIndex + i - j] - knots[knotSpanIndex - j]);
					// without multiplying A, model jitters slightly
					float tmp = N[j] * A;
					// without subtracting tmp, model flies away then resets to origin every few frames
					N[j + 1] += N[j] - tmp;
					// without setting to tmp, model either is moved from origin or grows very long limbs
					// depending on the animation
					N[j] = tmp;
				}
			}

			float retVal = 0.0f;

			for (int i = 0; i <= degree; i++)
				retVal += cPoints[knotSpanIndex - i] * N[i];

			return retVal;
		}

		public float GetValueX(float frame) {
			if (ChannelX == null)
				return Float.NaN;

			if (ChannelX.Values.length == 1)
				return ChannelX.Values[0];

			int knotspan = FindKnotSpan(Degree, frame, ChannelX.Values.length, Knots);
			return GetSinglePoint(knotspan, Degree, frame, Knots, ChannelX.Values);
		}

		public float GetValueY(float frame) {
			if (ChannelY == null)
				return Float.NaN;

			if (ChannelY.Values.length == 1)
				return ChannelY.Values[0];

			int knotspan = FindKnotSpan(Degree, frame, ChannelY.Values.length, Knots);
			return GetSinglePoint(knotspan, Degree, frame, Knots, ChannelY.Values);
		}

		public float GetValueZ(float frame) {
			if (ChannelZ == null)
				return Float.NaN;

			if (ChannelZ.Values.length == 1)
				return ChannelZ.Values[0];

			int knotspan = FindKnotSpan(Degree, frame, ChannelZ.Values.length, Knots);
			return GetSinglePoint(knotspan, Degree, frame, Knots, ChannelZ.Values);
		}

		public void GetValueXYZ(float frame, NifVector3 out) {

			if (ChannelX == null) {
				out.x = Float.NaN;
			} else {
				if (ChannelX.Values.length == 1) {
					out.x = ChannelX.Values[0];
				} else {
					int knotspan = FindKnotSpan(Degree, frame, ChannelX.Values.length, Knots);
					out.x = GetSinglePoint(knotspan, Degree, frame, Knots, ChannelX.Values);
				}
			}

			if (ChannelY == null) {
				out.y = Float.NaN;
			} else {
				if (ChannelY.Values.length == 1) {
					out.y = ChannelY.Values[0];
				} else {
					int knotspan = FindKnotSpan(Degree, frame, ChannelY.Values.length, Knots);
					out.y = GetSinglePoint(knotspan, Degree, frame, Knots, ChannelY.Values);
				}
			}

			if (ChannelZ == null) {
				out.z = Float.NaN;
			} else {
				if (ChannelZ.Values.length == 1) {
					out.z = ChannelZ.Values[0];
				} else {
					int knotspan = FindKnotSpan(Degree, frame, ChannelZ.Values.length, Knots);
					out.z = GetSinglePoint(knotspan, Degree, frame, Knots, ChannelZ.Values);
				}
			}
		}
	}

	public static class TransformMask {
		public ScalarQuantizationType	PositionQuantizationType;
		public RotationQuantizationType	rotationQuantizationType;
		public ScalarQuantizationType	ScaleQuantizationType;
		public List<FlagOffset>			PositionTypes;
		public List<FlagOffset>			RotationTypes;
		public List<FlagOffset>			ScaleTypes;

		TransformMask(ByteBuffer br) {
			PositionTypes = new ArrayList<FlagOffset>();
			RotationTypes = new ArrayList<FlagOffset>();
			ScaleTypes = new ArrayList<FlagOffset>();

			int byteQuantizationTypes = br.get() & 0xff;
			int bytePositionTypes = br.get() & 0xff;
			int byteRotationTypes = br.get() & 0xff;
			int byteScaleTypes = br.get() & 0xff;

			PositionQuantizationType = ScalarQuantizationType.values()[(byteQuantizationTypes & 3)];
			rotationQuantizationType = RotationQuantizationType.values()[((byteQuantizationTypes >> 2) & 0xF)];
			ScaleQuantizationType = ScalarQuantizationType.values()[((byteQuantizationTypes >> 6) & 3)];

			//  foreach (var flagOffset in (FlagOffset[])Enum.GetValues(typeof(FlagOffset)))
			/*	for (FlagOffset flagOffset : FlagOffset.values()) {
					if ((bytePositionTypes & flagOffset.getValue()) != 0)
						PositionTypes.add(flagOffset);
			
					if ((byteRotationTypes & flagOffset.getValue()) != 0)
						RotationTypes.add(flagOffset);
			
					if ((byteScaleTypes & flagOffset.getValue()) != 0)
						ScaleTypes.add(flagOffset);
				}*/
			PositionTypes = FlagOffset.parseFlagOffsets(bytePositionTypes);
			RotationTypes = FlagOffset.parseFlagOffsets(byteRotationTypes);
			ScaleTypes = FlagOffset.parseFlagOffsets(byteScaleTypes);
		}
	}

	public static class FloatTrack {
		public FloatMask	Mask;
		public float[]		floats;
	}

	public static class FloatMask {
		int mask;

		// I've seen a 3 which result in a 1 single float
		FloatMask(ByteBuffer br) {
			mask = br.get() & 0xff;
		}
	}

	public static class AnimationTracks {
		public List<TransformTrack[]>	transformBlocks;
		public List<FloatTrack[]>		floatBlocks;
	}

	public static class TransformTrack {
		public TransformMask			Mask;

		public boolean					HasSplinePosition;
		public boolean					HasSplineRotation;
		public boolean					HasSplineScale;

		public boolean					HasStaticRotation;

		public NifVector3				StaticPosition	= new NifVector3(0, 0, 0);
		public NifQuaternionXYZW		StaticRotation	= NifQuaternionXYZW.Identity;
		public NifVector3				StaticScale		= new NifVector3(1f, 1f, 1f);
		public SplineTrackVector3		SplinePosition	= null;
		public SplineTrackQuaternion	SplineRotation	= null;
		public SplineTrackVector3		SplineScale		= null;
	}

	//https://github.com/Meowmaritus/MVDX2/blob/master/MVDX2/Havok/SplineCompressedAnimation.cs
	public static AnimationTracks ReadSplineCompressedAnimByteBlock(byte[] animationData, int numTransformTracks,
																	int numBlocks, int maskAndQuantizationSize,
																	int numberOfFloatTracks, boolean is64bit) {
		// debug helper
		/*	for (int i = 0; i < animationData.length; i++) {
				System.out.print("" + (animationData[i] & 0xff) + " ");
			}
			System.out.println("");*/
		AnimationTracks animationTracks = new AnimationTracks();
		animationTracks.transformBlocks = new ArrayList<TransformTrack[]>();
		animationTracks.floatBlocks = new ArrayList<FloatTrack[]>();

		ByteBuffer br = ByteBuffer.wrap(animationData).order(ByteOrder.LITTLE_ENDIAN);

		for (int blockIndex = 0; blockIndex < numBlocks; blockIndex++) {
			TransformTrack[] TransformTracks = new TransformTrack[numTransformTracks];

			for (int i = 0; i < numTransformTracks; i++) {
				TransformTracks[i] = new TransformTrack();
				TransformTracks[i].Mask = new TransformMask(br);
			}

			FloatTrack[] floatTracks = new FloatTrack[numberOfFloatTracks];
			//FIXME: I shouldn't hang onto the mask, once loaded we are loaded
			for (int i = 0; i < numberOfFloatTracks; i++) {
				floatTracks[i] = new FloatTrack();
				floatTracks[i].Mask = new FloatMask(br);
			}

			Align(4, br);

			for (int i = 0; i < numTransformTracks; i++) {
				TransformTrack track = TransformTracks[i];
				TransformMask m = track.Mask;

				track.HasSplinePosition = m.PositionTypes.contains(FlagOffset.SplineX)
											|| m.PositionTypes.contains(FlagOffset.SplineY)
											|| m.PositionTypes.contains(FlagOffset.SplineZ);

				track.HasSplineRotation = m.RotationTypes.contains(FlagOffset.SplineX)
											|| m.RotationTypes.contains(FlagOffset.SplineY)
											|| m.RotationTypes.contains(FlagOffset.SplineZ)
											|| m.RotationTypes.contains(FlagOffset.SplineW);

				track.HasStaticRotation = m.RotationTypes.contains(FlagOffset.StaticX)
											|| m.RotationTypes.contains(FlagOffset.StaticY)
											|| m.RotationTypes.contains(FlagOffset.StaticZ)
											|| m.RotationTypes.contains(FlagOffset.StaticW);

				track.HasSplineScale = m.ScaleTypes.contains(FlagOffset.SplineX)
										|| m.ScaleTypes.contains(FlagOffset.SplineY)
										|| m.ScaleTypes.contains(FlagOffset.SplineZ);

				if (track.HasSplinePosition) {
					track.SplinePosition = new SplineTrackVector3(br, m.PositionTypes, m.PositionQuantizationType,
							true);
				} else {
					if (m.PositionTypes.contains(FlagOffset.StaticX)) {
						track.StaticPosition.x = br.getFloat();
					}

					if (m.PositionTypes.contains(FlagOffset.StaticY)) {
						track.StaticPosition.y = br.getFloat();
					}

					if (m.PositionTypes.contains(FlagOffset.StaticZ)) {
						track.StaticPosition.z = br.getFloat();
					}
				}

				Align(4, br);

				if (track.HasSplineRotation) {
					track.SplineRotation = new SplineTrackQuaternion(br, m.rotationQuantizationType);
				} else {
					if (track.HasStaticRotation) {
						Align(GetRotationAlign(m.rotationQuantizationType), br);
						track.StaticRotation = ReadQuantizedQuaternion(br, m.rotationQuantizationType);
					}
				}

				Align(4, br);

				if (track.HasSplineScale) {
					track.SplineScale = new SplineTrackVector3(br, m.ScaleTypes, m.ScaleQuantizationType, false);
				} else {
					if (m.ScaleTypes.contains(FlagOffset.StaticX)) {
						track.StaticScale.x = br.getFloat();
					}

					if (m.ScaleTypes.contains(FlagOffset.StaticY)) {
						track.StaticScale.y = br.getFloat();
					}

					if (m.ScaleTypes.contains(FlagOffset.StaticZ)) {
						track.StaticScale.z = br.getFloat();
					}
				}

				Align(4, br);
			}

			for (int i = 0; i < numberOfFloatTracks; i++) {
				FloatTrack track = floatTracks[i];
				FloatMask m = track.Mask;
				if (m.mask != 3) {
					System.err.println("float mask not the number 3 " + m.mask);
				} else {
					track.floats = new float[1];
					track.floats[0] = br.getFloat(); // I've seen 1.0 for 4 float tracks
				}
			}

			Align(16, br);

			animationTracks.transformBlocks.add(TransformTracks);
			animationTracks.floatBlocks.add(floatTracks);
		}

		return animationTracks;
	}
}
