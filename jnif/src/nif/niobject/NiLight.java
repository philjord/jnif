package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifColor3;

public abstract class NiLight extends NiDynamicEffect
{
	/**
	 <niobject name="NiLight" abstract="1" inherit="NiDynamicEffect">

	 Light source.
	 
	 <add name="Dimmer" type="float">Dimmer.</add>
	 <add name="Ambient Color" type="Color3">Ambient color.</add>
	 <add name="Diffuse Color" type="Color3">Diffuse color.</add>
	 <add name="Specular Color" type="Color3">Specular color.</add>
	 </niobject>
	 */

	public float dimmer;

	public NifColor3 ambientColor;

	public NifColor3 diffuseColor;

	public NifColor3 specularColor;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		dimmer = ByteConvert.readFloat(stream);
		ambientColor = new NifColor3(stream);
		diffuseColor = new NifColor3(stream);
		specularColor = new NifColor3(stream);

		return success;
	}
}