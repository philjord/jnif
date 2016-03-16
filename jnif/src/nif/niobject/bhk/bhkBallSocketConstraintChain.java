package nif.niobject.bhk;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifPtr;
import nif.compound.NifVector4;
import nif.niobject.NiObject;

public class bhkBallSocketConstraintChain extends bhkSerializable
{

	/**
	    <niobject name="bhkBallSocketConstraintChain" abstract="0" inherit="bhkSerializable">
	        A Ball and Socket Constraint chain.
	        <add name="Num Floats" type="uint">Unknown</add>
	        <add name="Floats 1" type="Vector4" arr1="Num Floats">Unknown</add>
	        <add name="Unknown Float 1" type="float">Unknown</add>
	        <add name="Unknown Float 2" type="float">Unknown</add>
	        <add name="Unknown Int 1" type="uint">Unknown</add>
	        <add name="Unknown Int 2" type="uint">Unknown</add>
	        <add name="Num Links" type="uint">Number of links in the chain</add>
	        <add name="Links" type="Ptr" template="NiObject" arr1="Num Links" cond="Num Links >= 1">Unknown</add>
	        <add name="Num Links 2" type="uint">Number of links in the chain</add>
	        <add name="Links 2" type="Ptr" template="NiObject" arr1="Num Links 2" cond="Num Links 2 >= 1">Unknown</add>
	        <add name="Unknown Int 3" type="uint">Unknown</add>
	    </niobject>
	    */

	public int NumFloats;

	public NifVector4[] Floats1;

	public float UnknownFloat1;

	public float UnknownFloat2;

	public int UnknownInt1;

	public int UnknownInt2;

	public int NumLinks;

	public NifPtr[] Links;

	public int NumLinks2;

	public NifPtr[] Links2;

	public int UnknownInt3;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		NumFloats = ByteConvert.readInt(stream);
		Floats1 = new NifVector4[NumFloats];
		for (int i = 0; i < NumFloats; i++)
		{
			Floats1[i] = new NifVector4(stream);
		}
		UnknownFloat1 = ByteConvert.readFloat(stream);

		UnknownFloat2 = ByteConvert.readFloat(stream);
		UnknownInt1 = ByteConvert.readInt(stream);

		UnknownInt2 = ByteConvert.readInt(stream);

		NumLinks = ByteConvert.readInt(stream);

		Links = new NifPtr[NumLinks];
		for (int i = 0; i < NumLinks; i++)
		{
			Links[i] = new NifPtr(NiObject.class, stream);
		}

		NumLinks2 = ByteConvert.readInt(stream);

		Links2 = new NifPtr[NumLinks2];
		for (int i = 0; i < NumLinks2; i++)
		{
			Links2[i] = new NifPtr(NiObject.class, stream);
		}

		UnknownInt3 = ByteConvert.readInt(stream);

		return success;
	}
}
