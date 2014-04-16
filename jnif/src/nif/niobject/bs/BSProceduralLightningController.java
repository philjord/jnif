package nif.niobject.bs;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.niobject.controller.NiFloatInterpController;
import nif.niobject.interpolator.NiInterpolator;

public class BSProceduralLightningController extends NiFloatInterpController
{
	/**
	 * 	<niobject name="BSProceduralLightningController" abstract="0" inherit="NiFloatInterpController">
	    Skyrim, Paired with dummy TriShapes, this controller generates lightning shapes for special effects.
	    First interpolator controls Generation.
			<add name="Interpolator 2: Mutation" type="Ref" template="NiInterpolator">References interpolator for Mutation of strips</add>
	        <add name="Interpolator 3" type="Ref" template="NiInterpolator">Unknown</add>
	        <add name="Interpolator 4" type="Ref" template="NiInterpolator">Unknown</add>
	        <add name="Interpolator 5" type="Ref" template="NiInterpolator">Unknown</add>
	        <add name="Interpolator 6" type="Ref" template="NiInterpolator">Unknown</add>
	        <add name="Interpolator 7" type="Ref" template="NiInterpolator">Unknown</add>
	        <add name="Interpolator 8" type="Ref" template="NiInterpolator">Unknown</add>
	        <add name="Interpolator 9: Arc Offset" type="Ref" template="NiInterpolator">References interpolator for Amplitutde control. 0=straight, 50=wide</add>
	        <add name="Unknown Short 1" type="ushort">Unknown</add>
	        <add name="Unknown Short 2" type="ushort">Unknown</add>
	        <add name="Unknown Short 3" type="ushort">Unknown</add>
	        <add name="Distance Weight" type="float">How far lightning will stretch to.</add>
	        <add name="Float 2" type="float">Unknown</add>
	        <add name="Strip Width" type="float">How wide the bolt will be</add>
	        <add name="Fork" type="float">Influences forking behavior</add>
	        <add name="Float 5" type="float">Unknown</add>
	        <add name="Byte 1" type="byte">Unknown</add>
	        <add name="Byte 2" type="byte">Unknown</add>
	        <add name="Byte 3" type="byte">Unknown</add>
	        <add name="Interpolator 10?" type="Ref" template="NiInterpolator">Unknown, unsure if this is actually another interpolator link.</add>
		</niobject>
	 */

	public NifRef Interpolator2Mutation;

	public NifRef Interpolator3;

	public NifRef Interpolator4;

	public NifRef Interpolator5;

	public NifRef Interpolator6;

	public NifRef Interpolator7;

	public NifRef Interpolator8;

	public NifRef Interpolator9ArcOffset;

	public short UnknownShort1;

	public short UnknownShort2;

	public short UnknownShort3;

	public float DistanceWeight;

	public float Float2;

	public float StripWidth;

	public float Fork;

	public float Float5;

	public byte Byte1;

	public byte Byte2;

	public byte Byte3;

	public NifRef Interpolator10;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		Interpolator2Mutation = new NifRef(NiInterpolator.class, stream);

		Interpolator3 = new NifRef(NiInterpolator.class, stream);

		Interpolator4 = new NifRef(NiInterpolator.class, stream);

		Interpolator5 = new NifRef(NiInterpolator.class, stream);

		Interpolator6 = new NifRef(NiInterpolator.class, stream);

		Interpolator7 = new NifRef(NiInterpolator.class, stream);

		Interpolator8 = new NifRef(NiInterpolator.class, stream);

		Interpolator9ArcOffset = new NifRef(NiInterpolator.class, stream);

		UnknownShort1 = ByteConvert.readShort(stream);

		UnknownShort2 = ByteConvert.readShort(stream);

		UnknownShort3 = ByteConvert.readShort(stream);

		DistanceWeight = ByteConvert.readFloat(stream);

		Float2 = ByteConvert.readFloat(stream);

		StripWidth = ByteConvert.readFloat(stream);

		Fork = ByteConvert.readFloat(stream);

		Float5 = ByteConvert.readFloat(stream);

		Byte1 = ByteConvert.readByte(stream);

		Byte2 = ByteConvert.readByte(stream);

		Byte3 = ByteConvert.readByte(stream);

		Interpolator10 = new NifRef(NiInterpolator.class, stream);

		return success;
	}
}
