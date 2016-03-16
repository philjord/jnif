package nif.niobject.particle;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class NiPSysTrailEmitter extends NiPSysFieldModifier
{
	/**
	 <niobject name="NiPSysTrailEmitter" abstract="0" inherit="NiPSysEmitter">

	 Guild 2-Specific node
	 
	 <add name="Unknown Int 1" type="int">Unknown</add>
	 <add name="Unknown Float 1" type="float">Unknown</add>
	 <add name="Unknown Float 2" type="float">Unknown</add>
	 <add name="Unknown Float 3" type="float">Unknown</add>
	 <add name="Unknown Int 2" type="int">Unknown</add>
	 <add name="Unknown Float 4" type="float">Unknown</add>
	 <add name="Unknown Int 3" type="int">Unknown</add>
	 <add name="Unknown Float 5" type="float">Unknown</add>
	 <add name="Unknown Int 4" type="int">Unknown</add>
	 <add name="Unknown Float 6" type="float">Unknown</add>
	 <add name="Unknown Float 7" type="float">Unknown</add>
	 </niobject>
	 */

	public int unknownInt1;

	public float unknownFloat1b;

	public float unknownFloat2;

	public float unknownFloat3;

	public int unknownInt2;

	public float unknownFloat4;

	public int unknownInt3;

	public float unknownFloat5;

	public int unknownInt4;

	public float unknownFloat6;

	public float unknownFloat7;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		unknownInt1 = ByteConvert.readInt(stream);
		unknownFloat1b = ByteConvert.readFloat(stream);
		unknownFloat2 = ByteConvert.readFloat(stream);
		unknownFloat3 = ByteConvert.readFloat(stream);
		unknownInt2 = ByteConvert.readInt(stream);
		unknownFloat4 = ByteConvert.readFloat(stream);
		unknownInt3 = ByteConvert.readInt(stream);
		unknownFloat5 = ByteConvert.readFloat(stream);
		unknownInt4 = ByteConvert.readInt(stream);
		unknownFloat6 = ByteConvert.readFloat(stream);
		unknownFloat7 = ByteConvert.readFloat(stream);
		return success;
	}
}
