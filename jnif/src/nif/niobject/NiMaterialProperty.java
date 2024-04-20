package nif.niobject;

import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifFlags;
import nif.compound.NifColor3;

public class NiMaterialProperty extends NiProperty
{

	/**
	 
	     <niobject name="NiMaterialProperty" abstract="0" inherit="NiProperty">
	    Describes the material shading properties.
	    <add name="Flags" type="Flags" ver1="3.0" ver2="10.0.1.2">Property flags.</add>
	    <add name="Ambient Color" type="Color3" vercond="!((Version == 20.2.0.7) &amp;&amp; (User Version >= 11) &amp;&amp; (User Version 2 > 21))">How much the material reflects ambient light.</add>
	    <add name="Diffuse Color" type="Color3" vercond="!((Version == 20.2.0.7) &amp;&amp; (User Version >= 11) &amp;&amp; (User Version 2 > 21))">How much the material reflects diffuse light.</add>
	    <add name="Specular Color" type="Color3">How much light the material reflects in a specular manner.</add>
	    <add name="Emissive Color" type="Color3">How much light the material emits.</add>
	    <add name="Glossiness" type="float">The material&#039;s glossiness.</add>
	    <add name="Alpha" type="float">The material transparency (1=non-transparant). Refer to a NiAlphaProperty object in this material&#039;s parent NiTriShape object, when alpha is not 1.</add>
	    <add name="Emit Multi" type="float" default="1.0" vercond="(Version == 20.2.0.7) &amp;&amp; (User Version >= 11) &amp;&amp; (User Version 2 > 21)">Unknown</add>
	</niobject>
	 */
	
	public NifFlags flags;
	
	public NifColor3 ambientColor;

	public NifColor3 diffuseColor;

	public NifColor3 specularColor;

	public NifColor3 emissiveColor;

	public float glossiness;

	public float alpha;

	public float emitMulti;

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws java.io.IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		
		if (nifVer.LOAD_VER <= NifVer.VER_10_0_1_2  )
		{
			flags = new NifFlags(stream);
		}
		
		if (!(nifVer.LOAD_VER == NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 11 && nifVer.BS_Version > 21))
		{
			ambientColor = new NifColor3(stream);
			diffuseColor = new NifColor3(stream);
		}
		specularColor = new NifColor3(stream);
		emissiveColor = new NifColor3(stream);
		glossiness = ByteConvert.readFloat(stream);
		//TODO: what the hell was I thining here??
		//System.out.println("glossiness " +glossiness);
	//	if (nifVer.LOAD_VER < NifVer.VER_20_2_0_7)
		{
			// it appears the older glossiness were the inverse of java3d
	//		glossiness = 128f - glossiness;
		}
		alpha = ByteConvert.readFloat(stream);
		if (nifVer.LOAD_VER == NifVer.VER_20_2_0_7 && nifVer.LOAD_USER_VER >= 11 && nifVer.BS_Version > 21)
		{
			emitMulti = ByteConvert.readFloat(stream);
		}

		return success;
	}
}