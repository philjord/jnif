package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifCapsuleBV
{
	/**
	 <compound name="CapsuleBV">

	 Capsule Bounding Volume
	 
	 <add name="Center" type="Vector3">Center</add>
	 <add name="Origin" type="Vector3">Origin</add>
	 <!--
	 <add name="Direction" type="Vector3">Direction</add>
	 <add name="Radius" type="float">Radius</add>
	 -->
	 <!--
	 direction and radius seem not present in ffvt3r 10.1.0.0 nifs 
	 instead, there's two floats 
	 -->
	 <add name="Unknown Float 1" type="float">Unknown.</add>
	 <add name="Unknown Float 2" type="float">Unknown.</add>
	 </compound>			
	 */

	public NifVector3 center;

	public NifVector3 origin;

	public float unknownFloat1;

	public float unknownFloat2;

	public NifCapsuleBV(ByteBuffer stream) throws IOException
	{
		center = new NifVector3(stream);
		origin = new NifVector3(stream);
		unknownFloat1 = ByteConvert.readFloat(stream);
		unknownFloat2 = ByteConvert.readFloat(stream);
	}
}
