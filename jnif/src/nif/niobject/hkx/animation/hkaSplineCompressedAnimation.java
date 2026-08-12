package nif.niobject.hkx.animation;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import nif.compound.NifQuaternion;
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

	public int						numFrames;				// frames are knots, so max knots inside a track but each track differs, each track is a bone
	public int						numBlocks;				// more than 1 if numFrame greater than 256 (last one is modulo 256 frames)
	public int						maxFramesPerBlock;		// always 256
	public int						maskAndQuantizationSize;
	public float					blockDuration;			// max duration of a block, but last block can be less based on frame count
	public float					blockInverseDuration;
	public float					frameDuration;			// how long 1 frame is so duration/numFrames
	public int[]					blockOffsets;			// where is block is in the data
	public int[]					floatBlockOffsets;		// where scalar data is in a given block in the data
	public int[]					transformOffsets;		// where rotation data is in the data
	public int[]					floatOffsets;			// float tracks are for non boned things, pure lists of floats for animation of other things
	public byte[]					data;
	public int						endian;

	public boolean					is64bit	= true;
	public List<TransformTrack[]>	blockTransformTracks;

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

		blockTransformTracks = hkaSplineCompressedAnimation.ReadSplineCompressedAnimByteBlock(data,
				numberOfTransformTracks, numBlocks, is64bit);

		System.out.println("decodedData " + blockTransformTracks.size());

		// now I dump the data? it's decoded now
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

	static NifQuaternion ReadQuatPOLAR32(ByteBuffer br) {
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

		NifQuaternion retVal = new NifQuaternion(R, //
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

	static NifQuaternion ReadQuatTHREECOMP48(ByteBuffer br) {
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

		return new NifQuaternion(retval[0], retval[1], retval[2], retval[3]);
	}

	static long Read40BitValue(ByteBuffer br) {
		byte[] bytes = new byte[5];
		for (int i = 0; i < bytes.length; i++)
			bytes[i] = br.get();
		return toLong(bytes);
	}

	// Converts an array of bytes into a long.  
	public static long toLong(byte[] buf) {
		if (buf == null) {
			throw new RuntimeException("no good at all");
		}

		// but for variable len
		/*    long l = ((buf[0] & 0xFFL) << 56) |
		         ((buf[1] & 0xFFL) << 48) |
		         ((buf[2] & 0xFFL) << 40) |
		         ((buf[3] & 0xFFL) << 32) |
		         ((buf[4] & 0xFFL) << 24) |
		         ((buf[5] & 0xFFL) << 16) |
		         ((buf[6] & 0xFFL) <<  8) |
		         ((buf[7] & 0xFFL) <<  0) ;*/

		int shift = 0;
		long ret = 0;
		int start = Math.min(buf.length - 1, 7);
		//assumes a less than 8 byte long loses the higher values, it would be crazy otherwise
		// java primitives are always big enddian
		for (int i = start; i >= 0; i--) {
			ret = ret | ((buf[i] & 0xFFL) << shift);
			shift += 8;
		}

		return ret;
	}

	static NifQuaternion ReadQuatTHREECOMP40(ByteBuffer br) {
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

		//TODO: check if is wxyz or xyzw, presumably wxyz
		return new NifQuaternion(retval[0], retval[1], retval[2], retval[3]);

	}

	static NifQuaternion ReadQuantizedQuaternion(ByteBuffer br, RotationQuantizationType type) {
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
				return new NifQuaternion(br.getFloat(), br.getFloat(), br.getFloat(), br.getFloat());
			default:
				return NifQuaternion.Identity;
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

	//Basis_ITS1, GetPoint_NR1, TIME-EFFICIENT NURBS CURVE EVALUATION ALGORITHMS, pages 64 & 65
	static float GetSinglePoint(int knotSpanIndex, int degree, float frame, short[] knots, Float[] cPoints) {
		float[] N = {1, 0, 0, 0, 0};

		for (int i = 1; i <= degree; i++)
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

		float retVal = 0.0f;

		for (int i = 0; i <= degree; i++)
			retVal += cPoints[knotSpanIndex - i] * N[i];

		return retVal;
	}

	//Basis_ITS1, GetPoint_NR1, TIME-EFFICIENT NURBS CURVE EVALUATION ALGORITHMS, pages 64 & 65
	static NifQuaternion GetSinglePoint(int knotSpanIndex, int degree, float frame, short[] knots,
										NifQuaternion[] cPoints) {
		float[] N = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f};

		for (int i = 1; i <= degree; i++)
			for (int j = i - 1; j >= 0; j--) {
				float A = (frame - knots[knotSpanIndex - j])
							/ (knots[knotSpanIndex + i - j] - knots[knotSpanIndex - j]);
				float tmp = N[j] * A;
				N[j + 1] += N[j] - tmp;
				N[j] = tmp;
			}

		NifQuaternion retVal = new NifQuaternion(0.0f, 0.0f, 0.0f, 0.0f);

		// looks like an interpolation of several weights of quat 
		for (int i = 0; i <= degree; i++)
			retVal.add(cPoints[knotSpanIndex - i].mul(N[i]));

		return retVal;
	}

	public static class SplineChannel<T> {
		public boolean	IsDynamic	= true;
		public T[]		Values;				//FIXME, this can be 1 length arrays, and it's now Float not float that is primitive wrapper
	}

	public static class SplineTrackQuaternion {
		public SplineChannel<NifQuaternion>	Channel;
		public short[]						Knots;
		public byte							Degree;

		SplineTrackQuaternion(ByteBuffer br, RotationQuantizationType quantizationType) {
			short numItems = br.getShort();
			Degree = br.get();
			int knotCount = numItems + Degree + 2;
			Knots = new short[knotCount];
			for (int i = 0; i < knotCount; i++) {
				Knots[i] = (short)(br.get() & 0xff);
			}

			Align(GetRotationAlign(quantizationType), br);

			Channel = new SplineChannel<NifQuaternion>();
			Channel.Values = new NifQuaternion[numItems + 1];
			for (int i = 0; i < numItems + 1; i++) {
				Channel.Values[i] = ReadQuantizedQuaternion(br, quantizationType);
			}
		}

		public NifQuaternion GetValue(float frame) {
			int knotspan = FindKnotSpan(Degree, frame, Channel.Values.length, Knots);
			return GetSinglePoint(knotspan, Degree, frame, Knots, Channel.Values);
		}
	}

	public static class SplineTrackVector3 {
		public SplineChannel<Float>	ChannelX;
		public SplineChannel<Float>	ChannelY;
		public SplineChannel<Float>	ChannelZ;
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

			ChannelX = new SplineChannel<Float>();
			ChannelY = new SplineChannel<Float>();
			ChannelZ = new SplineChannel<Float>();

			if (channelTypes.contains(FlagOffset.SplineX)) {
				BoundsXMin = br.getFloat();
				BoundsXMax = br.getFloat();
			} else if (channelTypes.contains(FlagOffset.StaticX)) {
				ChannelX.Values = new Float[] {br.getFloat()};
				ChannelX.IsDynamic = false;
			} else {
				ChannelX = null;
			}

			if (channelTypes.contains(FlagOffset.SplineY)) {
				BoundsYMin = br.getFloat();
				BoundsYMax = br.getFloat();
			} else if (channelTypes.contains(FlagOffset.StaticY)) {
				ChannelY.Values = new Float[] {br.getFloat()};
				ChannelY.IsDynamic = false;
			} else {
				ChannelY = null;
			}

			if (channelTypes.contains(FlagOffset.SplineZ)) {
				BoundsZMin = br.getFloat();
				BoundsZMax = br.getFloat();
			} else if (channelTypes.contains(FlagOffset.StaticZ)) {
				ChannelZ.Values = new Float[] {br.getFloat()};
				ChannelZ.IsDynamic = false;
			} else {
				ChannelZ = null;
			}

			if (channelTypes.contains(FlagOffset.SplineX))
				ChannelX.Values = new Float[numItems + 1];
			if (channelTypes.contains(FlagOffset.SplineY))
				ChannelY.Values = new Float[numItems + 1];
			if (channelTypes.contains(FlagOffset.SplineZ))
				ChannelZ.Values = new Float[numItems + 1];

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

		public Float GetValueX(float frame) {
			if (ChannelX == null)
				return null;

			if (ChannelX.Values.length == 1)
				return ChannelX.Values[0];
			int knotspan = FindKnotSpan(Degree, frame, ChannelX.Values.length, Knots);
			return GetSinglePoint(knotspan, Degree, frame, Knots, ChannelX.Values);
		}

		public Float GetValueY(float frame) {
			if (ChannelY == null)
				return null;

			if (ChannelY.Values.length == 1)
				return ChannelY.Values[0];
			int knotspan = FindKnotSpan(Degree, frame, ChannelY.Values.length, Knots);
			return GetSinglePoint(knotspan, Degree, frame, Knots, ChannelY.Values);
		}

		public Float GetValueZ(float frame) {
			if (ChannelZ == null)
				return null;

			if (ChannelZ.Values.length == 1)
				return ChannelZ.Values[0];
			int knotspan = FindKnotSpan(Degree, frame, ChannelZ.Values.length, Knots);
			return GetSinglePoint(knotspan, Degree, frame, Knots, ChannelZ.Values);
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

	public static class TransformTrack {
		public TransformMask			Mask;

		public boolean					HasSplinePosition;
		public boolean					HasSplineRotation;
		public boolean					HasSplineScale;

		public boolean					HasStaticRotation;

		public NifVector3				StaticPosition	= new NifVector3(0, 0, 0);
		public NifQuaternion			StaticRotation	= NifQuaternion.Identity;
		public NifVector3				StaticScale		= new NifVector3(1f, 1f, 1f);
		public SplineTrackVector3		SplinePosition	= null;
		public SplineTrackQuaternion	SplineRotation	= null;
		public SplineTrackVector3		SplineScale		= null;
	}

	//https://github.com/Meowmaritus/MVDX2/blob/master/MVDX2/Havok/SplineCompressedAnimation.cs
	public static List<TransformTrack[]> ReadSplineCompressedAnimByteBlock(	byte[] animationData, int numTransformTracks,
																			int numBlocks, boolean is64bit) {
		List<TransformTrack[]> blocks = new ArrayList<TransformTrack[]>();

		ByteBuffer br = ByteBuffer.wrap(animationData).order(ByteOrder.LITTLE_ENDIAN);

		for (int blockIndex = 0; blockIndex < numBlocks; blockIndex++) {
			TransformTrack[] TransformTracks = new TransformTrack[numTransformTracks];

			for (int i = 0; i < numTransformTracks; i++) {
				TransformTracks[i] = new TransformTrack();
				TransformTracks[i].Mask = new TransformMask(br);
			}

			// this is not true ata ll but let's see where we get to, I have a 3,3,3,3 after my masks
			if (is64bit)
				Align(4, br);
			else
				Align(8, br);

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
						track.StaticRotation = ReadQuantizedQuaternion(br, m.rotationQuantizationType); //br.ReadBytes(GetRotationByteCount(m.RotationQuantizationType));
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

			// 32bit has 4 floats of 1.0 
			if (!is64bit) {
				br.getFloat();
				br.getFloat();
				br.getFloat();
				br.getFloat();
			}

			Align(16, br);

			blocks.add(TransformTracks);
		}

		return blocks;
	}
	/*
		//https://github.com/CucFlavius/Zee-010-Templates/blob/4a8975338ae7cd6df6c52aaff1d33de05462d57f/HKSplineCompressedAnimation.bt#L333
		private void decodeData(byte[] data) {
	
			ByteBuffer bb = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
	
			System.out.println("getting TransformMask " + numberOfTransformTracks);
	
			TransformMask2[] masks = new TransformMask2[numberOfTransformTracks];
			for (int i = 0; i < numberOfTransformTracks; i++) {
				masks[i] = new TransformMask2(bb.get(), bb.get(), bb.get(), bb.get());
			}
			System.out.println("got TransformMasks");
	
			System.out.println("getting tracks " + numberOfTransformTracks);
	
			SplineDynamicTrackVector[] positions = new SplineDynamicTrackVector[numberOfTransformTracks];
			float[] positionFs = new float[numberOfTransformTracks * 3];
	
			SplineDynamicTrackQuat[] rotations = new SplineDynamicTrackQuat[numberOfTransformTracks];
			Quat[] rotationsQ = new Quat[numberOfTransformTracks];
	
			SplineDynamicTrackVector[] scales = new SplineDynamicTrackVector[numberOfTransformTracks];
			float[] scaleFs = new float[numberOfTransformTracks * 3];
	
			for (int i = 0; i < numberOfTransformTracks; i++) {
				if ((masks[i].positionTypes & 0x10) != 0	|| (masks[i].positionTypes & 0x20) != 0
					|| (masks[i].positionTypes & 0x40) != 0) {
					positions[i] = new SplineDynamicTrackVector(masks[i], true, bb);
					Align(4, bb);
				} else if ((masks[i].positionTypes & 0x1) != 0	|| (masks[i].positionTypes & 0x2) != 0
							|| (masks[i].positionTypes & 0x4) != 0) {
					if ((masks[i].positionTypes & 0x1) != 0) {
						positionFs[i * 3 + 0] = bb.getFloat();
					}
					if ((masks[i].positionTypes & 0x2) != 0) {
						positionFs[i * 3 + 1] = bb.getFloat();
					}
					if ((masks[i].positionTypes & 0x4) != 0) {
						positionFs[i * 3 + 2] = bb.getFloat();
					}
					Align(4, bb);
				}
	
				if ((masks[i].rotationTypes & 0xf0) != 0) {
					rotations[i] = new SplineDynamicTrackQuat(masks[i].rotQuantizationType, bb);
					Align(4, bb);
				} else if ((masks[i].rotationTypes & 0x0f) != 0) {
					rotationsQ[i] = Quat(masks[i].rotQuantizationType, bb);
					Align(4, bb);
				}
	
				if ((masks[i].scaleTypes & 0x10) != 0	|| (masks[i].scaleTypes & 0x20) != 0
					|| (masks[i].scaleTypes & 0x40) != 0) {
					scales[i] = new SplineDynamicTrackVector(masks[i], false, bb);
					Align(4, bb);
				} else if ((masks[i].scaleTypes & 0x1) != 0 || (masks[i].scaleTypes & 0x2) != 0
							|| (masks[i].scaleTypes & 0x4) != 0) {
					if ((masks[i].scaleTypes & 0x1) != 0)
						scaleFs[i * 3 + 0] = bb.getFloat();
					if ((masks[i].scaleTypes & 0x2) != 0)
						scaleFs[i * 3 + 1] = bb.getFloat();
					if ((masks[i].scaleTypes & 0x4) != 0)
						scaleFs[i * 3 + 2] = bb.getFloat();
					Align(4, bb);
				}
	
			}
	
			System.out.println("hkaSplineCompressedAnimation decode with " + bb.remaining() + " remaining");
		}
	
		enum QuantizationType {
			QT_8bit, QT_16bit, QT_32bit, QT_40bit, QT_48bit,
		};
	
		enum FlagOffset2 {
			staticX, staticY, staticZ, staticW, splineX, splineY, splineZ, splineW
		};
	
		enum SplineTrackType {
			STT_DYNAMIC, STT_STATIC, STT_IDENTITY
		};
	
		enum TransformType {
			ttPosX, ttPosY, ttPosZ, ttRotation, ttScaleX, ttScaleY, ttScaleZ
		};
	
		static class TransformMask2 {
	
			int					quantizationTypes;		//unsigned byte quantizationTypes;
	
			//example of use:
			// if (masks[i].positionTypes & 0x10 || masks[i].positionTypes & 0x20 || masks[i].positionTypes & 0x40)
			//if (mask.positionTypes & 0x10) TrackBBOX boxX; else if (mask.positionTypes & 0x1) float staticX;
			int					positionTypes;			//FlagOffset positionTypes;
	
			int					rotationTypes;			//unsigned byte rotationTypes;
			int					scaleTypes;				//FlagOffset scaleTypes;
	
			QuantizationType	posQuantizationType;
			QuantizationType	rotQuantizationType;
			QuantizationType	scaleQuantizationType;
	
			public TransformMask2(byte b1, byte b2, byte b3, byte b4) {
				quantizationTypes = b1 & 0xff;
				positionTypes = b2 & 0xff;
				rotationTypes = b3 & 0xff;
				scaleTypes = b4 & 0xff; //bit mask of flags
	
				posQuantizationType = QuantizationType.values()[(quantizationTypes) & 3];
				rotQuantizationType = QuantizationType.values()[((quantizationTypes >> 2) & 0xf) + 2];
				scaleQuantizationType = QuantizationType.values()[(quantizationTypes >> 6) & 3];
	
			}
	
		};
	
		static class TrackBBOX {
			float	min;
			float	max;
	
			public TrackBBOX(ByteBuffer bb) {
				min = bb.getFloat();
				max = bb.getFloat();
			}
		};
	
		public static float POINT8(ByteBuffer bb) {
			int val = bb.get() & 0xff;
	
			float fractal = 1.0f / 255.0f;
			float dVar = val * fractal;
			// extremes[id].min + (extremes[id].max - extremes[id].min) * dVar;
			return dVar;
		}
	
		static class Point8XYZ {
			float	x;
			float	y;
			float	z;
	
			//getPos means  get positions otherwise get scales
			public Point8XYZ(TransformMask2 mask, boolean getPos, ByteBuffer bb) {
	
				if (getPos) {
					if ((mask.positionTypes & 0x10) != 0)
						x = POINT8(bb);
	
					if ((mask.positionTypes & 0x20) != 0)
						y = POINT8(bb);
	
					if ((mask.positionTypes & 0x40) != 0)
						z = POINT8(bb);
				} else {
					if ((mask.scaleTypes & 0x10) != 0)
						x = POINT8(bb);
	
					if ((mask.scaleTypes & 0x20) != 0)
						y = POINT8(bb);
	
					if ((mask.scaleTypes & 0x40) != 0)
						z = POINT8(bb);
				}
			}
		};
	
		public static float POINT16(ByteBuffer bb) {
	
			float fractal = 1.0f / 0xffff;
			int val = bb.getShort() & 0xff;
			float dVar = val * fractal;
			// extremes[id].min + (extremes[id].max - extremes[id].min) * dVar;
			return dVar;
		}
	
		static class Point16XYZ {
			float	x;
			float	y;
			float	z;
	
			//getPos means  get positions otherwise get scales
			public Point16XYZ(TransformMask2 mask, boolean getPos, ByteBuffer bb) {
				if (getPos) {
					if ((mask.positionTypes & 0x10) != 0)
						x = POINT16(bb);
	
					if ((mask.positionTypes & 0x20) != 0)
						y = POINT16(bb);
	
					if ((mask.positionTypes & 0x40) != 0)
						z = POINT16(bb);
				} else {
					if ((mask.scaleTypes & 0x10) != 0)
						x = POINT16(bb);
	
					if ((mask.scaleTypes & 0x20) != 0)
						y = POINT16(bb);
	
					if ((mask.scaleTypes & 0x40) != 0)
						z = POINT16(bb);
				}
			}
		};
	
		static class SplineDynamicTrackVector {
			int				numItems;
			byte			degree;
			int[]			knots;
			TrackBBOX		boxX;
			float			staticX;
			TrackBBOX		boxY;
			float			staticY;
			TrackBBOX		boxZ;
			float			staticZ;
	
			Point8XYZ[]		points;		 
			Point16XYZ[]	points2;
	
			public SplineDynamicTrackVector(TransformMask2 mask, boolean getPos, ByteBuffer bb) {
				// Spline
				numItems = bb.getShort();
				degree = bb.get();
				int knotCount = numItems + degree + 2;
				knots = new int[knotCount];
				for (int i = 0; i < knotCount; i++) {
					knots[i] = bb.get() & 0xff;
				}
				Align(4, bb);
	
				if (getPos) {
					if ((mask.positionTypes & 0x10) != 0)
						boxX = new TrackBBOX(bb);
					else if ((mask.positionTypes & 0x1) != 0)
						staticX = bb.getFloat();
	
					if ((mask.positionTypes & 0x20) != 0)
						boxY = new TrackBBOX(bb);
					else if ((mask.positionTypes & 0x2) != 0)
						staticY = bb.getFloat();
	
					if ((mask.positionTypes & 0x40) != 0)
						boxZ = new TrackBBOX(bb);
					else if ((mask.positionTypes & 0x4) != 0)
						staticZ = bb.getFloat();
	
					if (mask.posQuantizationType == QuantizationType.QT_8bit) {
						points = new Point8XYZ[numItems + 1];
						for (int i = 0; i < numItems + 1; i++)
							points[i] = new Point8XYZ(mask, getPos, bb);
					} else {
						points2 = new Point16XYZ[numItems + 1];
						for (int i = 0; i < numItems + 1; i++)
							points2[i] = new Point16XYZ(mask, getPos, bb);
					}
				} else {
					if ((mask.scaleTypes & 0x10) != 0)
						boxX = new TrackBBOX(bb);
					else if ((mask.positionTypes & 0x1) != 0)
						staticX = FP16.toFloat(bb.getShort());
	
					if ((mask.scaleTypes & 0x20) != 0)
						boxY = new TrackBBOX(bb);
					else if ((mask.positionTypes & 0x2) != 0)
						staticY = FP16.toFloat(bb.getShort());
	
					if ((mask.scaleTypes & 0x40) != 0)
						boxZ = new TrackBBOX(bb);
					else if ((mask.positionTypes & 0x4) != 0)
						staticZ = FP16.toFloat(bb.getShort());
	
					if (mask.scaleQuantizationType == QuantizationType.QT_8bit) {
						points = new Point8XYZ[numItems + 1];
						for (int i = 0; i < numItems + 1; i++)
							points[i] = new Point8XYZ(mask, getPos, bb);
					} else {
						points2 = new Point16XYZ[numItems + 1];
						for (int i = 0; i < numItems + 1; i++)
							points2[i] = new Point16XYZ(mask, getPos, bb);
					}
				}
			}
	
		};
	
		public static class Quat32 extends Quat {
			byte[] compressed = new byte[4];
	
			public Quat32(ByteBuffer bb) {
	
				compressed[0] = bb.get();
				compressed[1] = bb.get();
				compressed[2] = bb.get();
				compressed[3] = bb.get();
				// Too lazy to write dequantization code here
			}
		};
	
		public static class Quat40 extends Quat {
			byte[] compressed = new byte[5];
	
			public Quat40(ByteBuffer bb) {
				compressed[0] = bb.get();
				compressed[1] = bb.get();
				compressed[2] = bb.get();
				compressed[3] = bb.get();
				compressed[4] = bb.get();
	
				// Too lazy to write dequantization code here
				//byte compressed[5];
				/*
				local float fractal = 0.000345436f;
				unsigned long cVal0;
				FSkip(-3);
				
				local uint cVal1 = (uint)(cVal0 >> 24);
				
				local ushort x = (ushort)((cVal0 * (1 << 20)) >> 20);
				local ushort y = (ushort)((cVal0 * (1 << 8)) >> 20);
				local ushort z = (ushort)((cVal1 * (1 << 20)) >> 20);
				
				local short x1 = (short)(x - (1 << 11) - 1);
				local short y1 = (short)(y - (1 << 11) - 1);
				local short z1 = (short)(z - (1 << 11) - 1);
				
				//BitConverter.ToSingle(BitConverter.GetBytes(x1), 0)
				
				local float x2 = (float)(x1 * fractal);
				local float y2 = (float)(y1 * fractal);
				local float z2 = (float)(z1 * fractal);
				
				if(x2 * x2 + y2 * y2 + z2 * z2 > 1)
				    local float w2 = 0;
				else
				    local float w2 = Sqrt(1 - (x2 * x2 + y2 * y2 + z2 * z2));
				
				//if ((cVal0 >> 38) & 1)
				//    w2 = -w2;
				*/
	//local ulong resultShift = (cVal0 >> 36) & 3;
	/*
	Quaternion retVal = new Quaternion(x2, y2, z2, w2);
	
	switch (resultShift)
	{
	    case 0:
	        return new Quaternion(retVal[2], retVal[1], retVal[0], retVal[3]);
	    case 1:
	        return new Quaternion(retVal[2], retVal[1], retVal[3], retVal[0]);
	    case 2:
	        return new Quaternion(retVal[2], retVal[3], retVal[1], retVal[0]);
	    default:
	        return retVal;
	}
	
	
	}
	}
	
	public static class Quat48 extends Quat {
	
	byte[] compressed = new byte[6];
	
	public Quat48(ByteBuffer bb) {
	compressed[0] = bb.get();
	compressed[1] = bb.get();
	compressed[2] = bb.get();
	compressed[3] = bb.get();
	compressed[4] = bb.get();
	compressed[5] = bb.get();
	
	// Too lazy to write dequantization code here
	}
	};
	
	public static class Quat {
	
	}
	
	public static Quat Quat(QuantizationType quantization, ByteBuffer bb) {
	Quat rotation = null;
	switch (quantization) {
	case QT_32bit:
		rotation = new Quat32(bb);
		break;
	case QT_40bit:
		rotation = new Quat40(bb);
		break;
	case QT_48bit:
		rotation = new Quat48(bb);
		break;
	default:
		System.err.println("Wrong rotation quantization");
		break;
	}
	return rotation;
	};
	
	public static class SplineDynamicTrackQuat {
	int		numItems;
	byte	degree;
	int[]	knots;
	Quat[]	quaternions;
	
	public SplineDynamicTrackQuat(QuantizationType quantization, ByteBuffer bb) {
	numItems = bb.getShort();
	degree = bb.get();
	int knotCount = numItems + degree + 2;
	knots = new int[knotCount];
	for (int i = 0; i < knotCount; i++) {
		knots[i] = bb.get() & 0xff;
		//System.out.print(" knot " +knots[i] );
	}
	
	if (quantization == QuantizationType.QT_48bit)
		Align(2, bb);
	else if (quantization == QuantizationType.QT_32bit)
		Align(4, bb);
	
	quaternions = new Quat[numItems + 1];
	for (int i = 0; i < numItems + 1; i++) {
		quaternions[i] = Quat(quantization, bb);
		//System.out.print(" quaternions " +quaternions[i] );
	}
	}
	};*/
}
