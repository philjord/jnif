package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.enums.KeyType;

public abstract class NifKeyGroup
{
	/**
	 The serialization of these values is not good for loading they are terrible memory burners
	 So instead we just load them into arrys of primitives, which cna then be copied or worked on in 
	with the known format
	  
	 
	 KeyGroup <T> :: (None)
	 
	 T can be
	 byte
	 float
	 NPColor4
	 NPVector3
	 NPString
	 
	 <compound name="KeyGroup" istemplate="1">

	 Array of vector keys (anything that can be interpolated, except rotations).
	 
	 <add name="Num Keys" type="uint">Number of keys in the array.</add>
	 <add name="Interpolation" type="KeyType" cond="Num Keys != 0">The key type.</add>
	 <add name="Keys" type="Key" arg="Interpolation" template="TEMPLATE" arr1="Num Keys">The keys.</add>
	 </compound>
	 */

	public int numKeys;

	public KeyType interpolation;

	public static class NifKeyGroupByte extends NifKeyGroup {
		//public NifKeyByte[] keys;
		public float[] time;
		public NifTBC[] tBC;
		public byte[] value;
		public byte[] forward;
		public byte[] backward;
	
		public NifKeyGroupByte(ByteBuffer stream, NifVer nifVer) throws IOException {
			numKeys = ByteConvert.readInt(stream);
			if (numKeys != 0) {
				interpolation = new KeyType(stream);
				//keys = new NifKeyByte[numKeys];
				time = new float[numKeys];
				tBC = new NifTBC[numKeys];
				value = new byte[numKeys];
				forward = new byte[numKeys];
				backward = new byte[numKeys];
			
				for (int i = 0; i < numKeys; i++) {
					//keys[i] = new NifKeyByte(interpolation, stream, nifVer);
					time[i] = ByteConvert.readFloat(stream);
					value[i] = ByteConvert.readByte(stream);
			
					if (interpolation.type == 2)
					{
						forward[i] = ByteConvert.readByte(stream);
						backward[i] = ByteConvert.readByte(stream);
					}
					if (interpolation.type == 3)
					{
						tBC[i] = new NifTBC(stream);
					}
				}
			}
		}
	}
	public static class NifKeyGroupFloat extends NifKeyGroup {
		//public NifKeyFloat[] keys;
		public float[] time;
		public NifTBC[] tBC;
		public float[] value;
		public float[] forward;
		public float[] backward;
	
		public NifKeyGroupFloat(ByteBuffer stream, NifVer nifVer) throws IOException {
			numKeys = ByteConvert.readInt(stream);
			if (numKeys != 0) {
				interpolation = new KeyType(stream);
				//keys = new NifKeyFloat[numKeys];
				time = new float[numKeys];
				tBC = new NifTBC[numKeys];
				value = new float[numKeys];
				forward = new float[numKeys];
				backward = new float[numKeys];
				for (int i = 0; i < numKeys; i++) {
					//keys[i] = new NifKeyFloat(interpolation, stream, nifVer);
					time[i] = ByteConvert.readFloat(stream);
					value[i] = ByteConvert.readFloat(stream);
			
					if (interpolation.type == 2)
					{
						forward[i] = ByteConvert.readFloat(stream);
						backward[i] = ByteConvert.readFloat(stream);
					}
					if (interpolation.type == 3)
					{
						tBC[i] = new NifTBC(stream);
					}
				}
			}
		}
	}
	public static class NifKeyGroupNifColor4 extends NifKeyGroup {
		//public NifKeyNifColor4[] keys;
		public float[] time;
		public NifTBC[] tBC;
		public NifColor4[] value;
		public NifColor4[] forward;
		public NifColor4[] backward;
	
		public NifKeyGroupNifColor4(ByteBuffer stream, NifVer nifVer) throws IOException {
			numKeys = ByteConvert.readInt(stream);
			if (numKeys != 0) {
				interpolation = new KeyType(stream);
				//keys = new NifKeyNifColor4[numKeys];
				time = new float[numKeys];
				tBC = new NifTBC[numKeys];
				value = new NifColor4[numKeys];
				forward = new NifColor4[numKeys];
				backward = new NifColor4[numKeys];
				for (int i = 0; i < numKeys; i++) {
					//keys[i] = new NifKeyNifColor4(interpolation, stream, nifVer);
					time[i] = ByteConvert.readFloat(stream);
					value[i] = new NifColor4(stream);
			
					if (interpolation.type == 2)
					{
						forward[i] = new NifColor4(stream);
						backward[i] = new NifColor4(stream);
					}
					if (interpolation.type == 3)
					{
						tBC[i] = new NifTBC(stream);
					}
				}
			}
		}
	}
	public static class NifKeyGroupNifVector3 extends NifKeyGroup {
		//public NifKeyNifVector3[] keys;
		public float[] time;
		public NifTBC[] tBC;
		public NifVector3[] value;
		public NifVector3[] forward;
		public NifVector3[] backward;
	
		public NifKeyGroupNifVector3(ByteBuffer stream, NifVer nifVer) throws IOException {
			numKeys = ByteConvert.readInt(stream);
			if (numKeys != 0) {
				interpolation = new KeyType(stream);
				//keys = new NifKeyNifVector3[numKeys];
				time = new float[numKeys];
				tBC = new NifTBC[numKeys];
				value = new NifVector3[numKeys];
				forward = new NifVector3[numKeys];
				backward = new NifVector3[numKeys];
				for (int i = 0; i < numKeys; i++) {
					//keys[i] = new NifKeyNifVector3(interpolation, stream, nifVer);
					time[i] = ByteConvert.readFloat(stream);
					value[i] = new NifVector3(stream);
			
					if (interpolation.type == 2)
					{
						forward[i] = new NifVector3(stream);
						backward[i] = new NifVector3(stream);
					}
					if (interpolation.type == 3)
					{
						tBC[i] = new NifTBC(stream);
					}
				}
			}
		}
	}
	public static class NifKeyGroupString extends NifKeyGroup {
		//public NifKeyString[] keys;
		public float[] time;
		public NifTBC[] tBC;
		public String[] value;
		public String[] forward;
		public String[] backward;
	
		public NifKeyGroupString(ByteBuffer stream, NifVer nifVer) throws IOException {
			numKeys = ByteConvert.readInt(stream);
			if (numKeys != 0) {
				interpolation = new KeyType(stream);
				//keys = new NifKeyString[numKeys];
				time = new float[numKeys];
				tBC = new NifTBC[numKeys];
				value = new String[numKeys];
				forward = new String[numKeys];
				backward = new String[numKeys];
				for (int i = 0; i < numKeys; i++) {
					//keys[i] = new NifKeyString(interpolation, stream, nifVer);
					time[i] = ByteConvert.readFloat(stream);
					value[i] = ByteConvert.readIndexString(stream, nifVer);
			
					if (interpolation.type == 2)
					{
						forward[i] = ByteConvert.readIndexString(stream, nifVer);
						backward[i] = ByteConvert.readIndexString(stream, nifVer);
					}
					if (interpolation.type == 3)
					{
						tBC[i] = new NifTBC(stream);
					}
				}
			}
		}
	}
}
