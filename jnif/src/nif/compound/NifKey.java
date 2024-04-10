package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.enums.KeyType;

public abstract class NifKey
{
	/**
	 
	 Key <T> :: (None)
	 
	 T can be
	 byte
	 float
	 NPColor4
	 NPVector3
	 NPString
	 
	 <compound name="Key" niflibtype="Key" istemplate="1">

	 A generic key with support for interpolation. Type 1 is normal linear interpolation, type 2 has forward and backward tangents, 
	 and type 3 has tension, bias and continuity arguments. Note that color4 and byte always seem to be of type 1.
	 
	 <add name="Time" type="float">Time of the key.</add>
	 <add name="Value" type="TEMPLATE">The key value.</add>
	 <add name="Forward" type="TEMPLATE" cond="ARG == 2">Key forward tangent.</add>
	 <add name="Backward" type="TEMPLATE" cond="ARG == 2">The key backward tangent.</add>
	 <add name="TBC" type="TBC" cond="ARG == 3">The key's TBC.</add>
	 </compound>
	 */

	public KeyType type;

	public float time;

	public NifTBC tBC;

	/**
	 * 
	 * @param stream
	 * @param nifVer TODO
	 * @param ptrType
	 * @throws IOException
	 */

/*	public NifKey(KeyType type, Class<?> T, ByteBuffer stream, NifVer nifVer) throws IOException
	{
		this.type = type;
		this.T = T;
		time = ByteConvert.readFloat(stream);
		value = readObj(T, stream, nifVer);

		if (type.type == 2)
		{
			forward = readObj(T, stream, nifVer);
			backward = readObj(T, stream, nifVer);
		}
		if (type.type == 3)
		{
			tBC = new NifTBC(stream);
		}
	}*/

	/**
	 
	 T can be
	 byte
	 float
	 NPColor4
	 NPVector3
	 NPString	*/
/*	private static Object readObj(Class<?> T, ByteBuffer stream, NifVer nifVer) throws IOException
	{
		if (T.equals(Byte.class))
		{
			return new Byte(ByteConvert.readByte(stream));
		}
		else if (T.equals(Float.class))
		{
			return new Float(ByteConvert.readFloat(stream));
		}
		else if (T.equals(NifColor4.class))
		{
			return new NifColor4(stream);
		}
		else if (T.equals(NifVector3.class))
		{
			return new NifVector3(stream);
		}
		else if (T.equals(String.class))
		{
			return ByteConvert.readIndexString(stream, nifVer);
		}
		System.out.println("bad T man " + T);
		return null;
	}*/

	public static class NifKeyByte extends NifKey {
		
		public byte value;

		public byte forward;

		public byte backward;
		
		public NifKeyByte(KeyType type, ByteBuffer stream, NifVer nifVer) throws IOException
		{
			this.type = type;
			time = ByteConvert.readFloat(stream);
			value = ByteConvert.readByte(stream);
	
			if (type.type == 2)
			{
				forward = ByteConvert.readByte(stream);
				backward = ByteConvert.readByte(stream);
			}
			if (type.type == 3)
			{
				tBC = new NifTBC(stream);
			}
		}
	}
	
	public static class NifKeyFloat extends NifKey {
		
		public float value;

		public float forward;

		public float backward;
		
		public NifKeyFloat(KeyType type, ByteBuffer stream, NifVer nifVer) throws IOException
		{
			this.type = type;
			time = ByteConvert.readFloat(stream);
			value = ByteConvert.readFloat(stream);
	
			if (type.type == 2)
			{
				forward = ByteConvert.readFloat(stream);
				backward = ByteConvert.readFloat(stream);
			}
			if (type.type == 3)
			{
				tBC = new NifTBC(stream);
			}
		}
	}
	
	public static class NifKeyNifColor4 extends NifKey {
	
		public NifColor4 value;

		public NifColor4 forward;

		public NifColor4 backward;
		
		public NifKeyNifColor4(KeyType type, ByteBuffer stream, NifVer nifVer) throws IOException
		{
			this.type = type;
			time = ByteConvert.readFloat(stream);
			value = new NifColor4(stream);
	
			if (type.type == 2)
			{
				forward = new NifColor4(stream);
				backward = new NifColor4(stream);
			}
			if (type.type == 3)
			{
				tBC = new NifTBC(stream);
			}
		}
	}
	
	
	public static class NifKeyNifVector3 extends NifKey {
		
		public NifVector3 value;

		public NifVector3 forward;

		public NifVector3 backward;
		
		public NifKeyNifVector3(KeyType type, ByteBuffer stream, NifVer nifVer) throws IOException
		{
			this.type = type;
			time = ByteConvert.readFloat(stream);
			value = new NifVector3(stream);
	
			if (type.type == 2)
			{
				forward = new NifVector3(stream);
				backward = new NifVector3(stream);
			}
			if (type.type == 3)
			{
				tBC = new NifTBC(stream);
			}
		}
	}
	

	public static class NifKeyString extends NifKey {
		
		public String value;

		public String forward;

		public String backward;
		
		public NifKeyString(KeyType type, ByteBuffer stream, NifVer nifVer) throws IOException
		{
			this.type = type;
			time = ByteConvert.readFloat(stream);
			value = ByteConvert.readIndexString(stream, nifVer);
	
			if (type.type == 2)
			{
				forward = ByteConvert.readIndexString(stream, nifVer);
				backward = ByteConvert.readIndexString(stream, nifVer);
			}
			if (type.type == 3)
			{
				tBC = new NifTBC(stream);
			}
		}
	}
	
	
}
