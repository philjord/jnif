package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

public class NifHalfSpaceBV
{
	/**
	 <compound name="HalfSpaceBV">
	 <add name="Normal" type="Vector3">Normal</add>
	 <add name="Center" type="Vector3">Center</add>
	 </compound>		
	 */
	public NifVector3 normal;

	public NifVector3 center;

	public NifHalfSpaceBV(ByteBuffer stream) throws IOException
	{
		normal = new NifVector3(stream);
		center = new NifVector3(stream);
	}
}
