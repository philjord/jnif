package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

public class bhkLiquidAction extends bhkSerializable
{
	/**
	 
	 <niobject name="bhkLiquidAction" inherit="bhkSerializable">

	 Bethesda-specific node.
	 
	 <add name="Unknown Int 1" type="int">Unknown Flag</add>
	 <add name="Unknown Int 2" type="int">Unknown Flag</add>
	 <add name="Unknown Int 3" type="int">Unknown Flag</add>
	 <add name="Unknown Float 1" type="float">Unknown Flag</add>
	 <add name="Unknown Float 2" type="float">Unknown Flag</add>
	 <add name="Unknown Float 3" type="float">Unknown Flag</add>
	 <add name="Unknown Float 4" type="float">Unknown Flag</add>
	 </niobject>
	 */

	public int unknownInt1;

	public int unknownInt2;

	public int unknownInt3;

	public float unknownFloat1;

	public float unknownFloat2;

	public float unknownFloat3;

	public float unknownFloat4;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		unknownInt1 = ByteConvert.readInt(stream);
		unknownInt2 = ByteConvert.readInt(stream);
		unknownInt3 = ByteConvert.readInt(stream);

		unknownFloat1 = ByteConvert.readFloat(stream);
		unknownFloat2 = ByteConvert.readFloat(stream);
		unknownFloat3 = ByteConvert.readFloat(stream);
		unknownFloat4 = ByteConvert.readFloat(stream);

		return success;
	}
}