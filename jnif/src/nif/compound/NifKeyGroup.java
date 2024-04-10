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

		public float[] time;
		public NifTBC[] tBC;
		public byte[] value;
		public byte[] forward;
		public byte[] backward;
	
		public NifKeyGroupByte(ByteBuffer stream, NifVer nifVer) throws IOException {
			numKeys = ByteConvert.readInt(stream);
			if (numKeys != 0) {
				interpolation = new KeyType(stream);
				time = new float[numKeys];				
				value = new byte[numKeys];
				if (interpolation.type == 2) {
					forward = new byte[numKeys];
					backward = new byte[numKeys];
				} else if (interpolation.type == 3) {
					tBC = new NifTBC[numKeys];
				}				
			
				for (int i = 0; i < numKeys; i++) {
					//keys[i] = new NifKeyByte(interpolation, stream, nifVer);
					time[i] = ByteConvert.readFloat(stream);
					value[i] = ByteConvert.readByte(stream);
			
					if (interpolation.type == 2) {
						forward[i] = ByteConvert.readByte(stream);
						backward[i] = ByteConvert.readByte(stream);
					} else if (interpolation.type == 3) {
						tBC[i] = new NifTBC(stream);
					}
				}
			}
		}
	}
	public static class NifKeyGroupFloat extends NifKeyGroup {

		public float[] time;
		public NifTBC[] tBC;
		public float[] value;
		public float[] forward;
		public float[] backward;
	
		public NifKeyGroupFloat(ByteBuffer stream, NifVer nifVer) throws IOException {
			numKeys = ByteConvert.readInt(stream);
			if (numKeys != 0) {
				interpolation = new KeyType(stream);
				time = new float[numKeys];				
				value = new float[numKeys];
				if (interpolation.type == 2) {
					forward = new float[numKeys];
					backward = new float[numKeys];
				} else if (interpolation.type == 3) {
					tBC = new NifTBC[numKeys];
				}
				for (int i = 0; i < numKeys; i++) {
					time[i] = ByteConvert.readFloat(stream);
					value[i] = ByteConvert.readFloat(stream);
			
					if (interpolation.type == 2) {
						forward[i] = ByteConvert.readFloat(stream);
						backward[i] = ByteConvert.readFloat(stream);
					} else if (interpolation.type == 3) {
						tBC[i] = new NifTBC(stream);
					}
				}
			}
		}
	}
	public static class NifKeyGroupNifColor4 extends NifKeyGroup {

		public float[] time;
		public NifTBC[] tBC;
		public float[] value; //NifColor4
		public float[] forward; //NifColor4
		public float[] backward; //NifColor4
	
		public NifKeyGroupNifColor4(ByteBuffer stream, NifVer nifVer) throws IOException {
			numKeys = ByteConvert.readInt(stream);
			if (numKeys != 0) {
				interpolation = new KeyType(stream);
				time = new float[numKeys];				
				value = new float[numKeys*4];
				if (interpolation.type == 2) {
					forward = new float[numKeys*4];
					backward = new float[numKeys*4];
				} else if (interpolation.type == 3) {
					tBC = new NifTBC[numKeys];
				}
				for (int i = 0; i < numKeys; i++) {
					time[i] = ByteConvert.readFloat(stream);
					value[i*4+0] = ByteConvert.readFloat(stream);//r
					value[i*4+1] = ByteConvert.readFloat(stream);//g
					value[i*4+2] = ByteConvert.readFloat(stream);//b
					value[i*4+3] = ByteConvert.readFloat(stream);//a
					  
			
					if (interpolation.type == 2) {
						forward[i*4+0] = ByteConvert.readFloat(stream);//r
						forward[i*4+1] = ByteConvert.readFloat(stream);//g
						forward[i*4+2] = ByteConvert.readFloat(stream);//b
						forward[i*4+3] = ByteConvert.readFloat(stream);//a
						
						backward[i*4+0] = ByteConvert.readFloat(stream);//r
						backward[i*4+1] = ByteConvert.readFloat(stream);//g
						backward[i*4+2] = ByteConvert.readFloat(stream);//b
						backward[i*4+3] = ByteConvert.readFloat(stream);//a
					} else if (interpolation.type == 3) {
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
		public float[] value; //NifVector3
		public float[] forward;//NifVector3
		public float[] backward;//NifVector3
	
		public NifKeyGroupNifVector3(ByteBuffer stream, NifVer nifVer) throws IOException {
			numKeys = ByteConvert.readInt(stream);
			if (numKeys != 0) {
				interpolation = new KeyType(stream);
				time = new float[numKeys];				
				value = new float[numKeys*3];
				if (interpolation.type == 2) {
					forward = new float[numKeys*3];
					backward = new float[numKeys*3];
				} else if (interpolation.type == 3) {
					tBC = new NifTBC[numKeys];
				}
				for (int i = 0; i < numKeys; i++) {
					//keys[i] = new NifKeyNifVector3(interpolation, stream, nifVer);
					time[i] = ByteConvert.readFloat(stream);

					value[i*3+0] = ByteConvert.readFloat(stream);//x
					value[i*3+1] = ByteConvert.readFloat(stream);//y
					value[i*3+2] = ByteConvert.readFloat(stream);//z

					if (Float.isNaN(value[i*3+0]) || Float.isNaN(value[i*3+1]) || Float.isNaN(value[i*3+2])) {
						value[i*3+0] = 0;
						value[i*3+1] = 0;
						value[i*3+2] = 0;
					}
					if (interpolation.type == 2) {
						forward[i*3+0] = ByteConvert.readFloat(stream);//x
						forward[i*3+1] = ByteConvert.readFloat(stream);//y
						forward[i*3+2] = ByteConvert.readFloat(stream);//z

						if (Float.isNaN(forward[i*3+0]) || Float.isNaN(forward[i*3+1]) || Float.isNaN(forward[i*3+2])) {
							forward[i*3+0] = 0;
							forward[i*3+1] = 0;
							forward[i*3+2] = 0;
						}
						backward[i*3+0] = ByteConvert.readFloat(stream);//x
						backward[i*3+1] = ByteConvert.readFloat(stream);//y
						backward[i*3+2] = ByteConvert.readFloat(stream);//z

						if (Float.isNaN(backward[i*3+0]) || Float.isNaN(backward[i*3+1]) || Float.isNaN(backward[i*3+2])) {
							backward[i*3+0] = 0;
							backward[i*3+1] = 0;
							backward[i*3+2] = 0;
						}
					} else if (interpolation.type == 3) {
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
				value = new String[numKeys];
				if (interpolation.type == 2) {
					forward = new String[numKeys];
					backward = new String[numKeys];
				} else if (interpolation.type == 3) {
					tBC = new NifTBC[numKeys];
				}
				for (int i = 0; i < numKeys; i++) {
					time[i] = ByteConvert.readFloat(stream);
					value[i] = ByteConvert.readIndexString(stream, nifVer);			
					if (interpolation.type == 2) {
						forward[i] = ByteConvert.readIndexString(stream, nifVer);
						backward[i] = ByteConvert.readIndexString(stream, nifVer);
					} else if (interpolation.type == 3) {
						tBC[i] = new NifTBC(stream);
					}
				}
			}
		}
	}
}
