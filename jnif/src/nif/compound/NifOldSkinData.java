package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifOldSkinData
{
	/**
	 <compound name="OldSkinData">

	 Used to store skin weights in NiTriShapeSkinController.
	 
	 <add name="Vertex Weight" type="float">The amount that this bone affects the vertex.</add>
	 <add name="Vertex Index" type="ushort">
	 The index of the vertex that this weight applies to.
	 </add>
	 <add name="Unknown Vector" type="Vector3">Unknown.  Perhaps some sort of offset?</add>
	 </compound>
	 */

	public float vertexWeight;

	public short vertexIndex;

	public NifVector3 unknownVector;

	public NifOldSkinData(ByteBuffer stream) throws IOException
	{
		vertexWeight = ByteConvert.readFloat(stream);
		vertexIndex = ByteConvert.readShort(stream);
		unknownVector = new NifVector3(stream);
	}
}
