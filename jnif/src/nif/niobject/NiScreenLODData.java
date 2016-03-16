package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector3;

public class NiScreenLODData extends NiLODData
{
	/**
	 
	 <niobject name="NiScreenLODData" abstract="0" inherit="NiLODData" ver1="10.1.0.0">

	 Describes levels of detail based on size of object on screen?
	 
	 <add name="Bound Center" type="Vector3">The center of the bounding sphere?</add>
	 <add name="Bound Radius" type="float">The radius of the bounding sphere?</add>
	 <add name="World Center" type="Vector3">The center of the bounding sphere in world space?</add>
	 <add name="World Radius" type="float">The radius of the bounding sphere in world space?</add>
	 <add name="Proportion Count" type="uint">The number of screen size based LOD levels.</add>
	 <add name="Proportion Levels" type="float" arr1="Proportion Count">The LOD levels based on proportion of screen size?</add>
	 </niobject>
	 
	 */

	public NifVector3 boundCenter;

	public float boundRadius;

	public NifVector3 worldCenter;

	public float worldRadius;

	public int proportionCount;

	public float[] proportionLevels;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		boundCenter = new NifVector3(stream);
		boundRadius = ByteConvert.readFloat(stream);
		worldCenter = new NifVector3(stream);
		worldRadius = ByteConvert.readFloat(stream);

		proportionCount = ByteConvert.readInt(stream);
		proportionLevels = new float[proportionCount];
		for (int i = 0; i < proportionCount; i++)
		{
			proportionLevels[i] = ByteConvert.readFloat(stream);
		}

		return success;
	}
}