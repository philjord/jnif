package nif.niobject.bgsm;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.compound.NifColor3;

public class EffectMaterial extends BSMaterial
{
	public byte bBloodEnabled;
	public byte bEffectLightingEnabled;
	public byte bFalloffEnabled;
	public byte bFalloffColorEnabled;
	public byte bGrayscaleToPaletteAlpha;
	public byte bSoftEnabled;
	public float baseR = 0, baseG = 0, baseB = 0;
	public NifColor3 cBaseColor;
	public float fBaseColorScale;
	public float fFalloffStartAngle;
	public float fFalloffStopAngle;
	public float fFalloffStartOpacity;
	public float fFalloffStopOpacity;
	public float fLightingInfluence;
	public byte iEnvmapMinLOD;
	public float fSoftDepth;

	public EffectMaterial(String file)
	{
		super(file);
	}

	public void readFile(InputStream stream) throws IOException
	{
		//MUST have read off first 4 bytes by now!!
		super.readFile(stream);

		for (int i = 0; i < 5; i++)
		{
			String tex = ByteConvert.readSizedString(stream);
			tex = tex.replace("/", "\\");
			tex = tex.trim();
			textureList.add(tex);
		}

		bBloodEnabled = ByteConvert.readByte(stream);
		bEffectLightingEnabled = ByteConvert.readByte(stream);
		bFalloffEnabled = ByteConvert.readByte(stream);
		bFalloffColorEnabled = ByteConvert.readByte(stream);
		bGrayscaleToPaletteAlpha = ByteConvert.readByte(stream);
		bSoftEnabled = ByteConvert.readByte(stream);
		cBaseColor = new NifColor3(stream);
		fBaseColorScale = ByteConvert.readFloat(stream);
		fFalloffStartAngle = ByteConvert.readFloat(stream);
		fFalloffStopAngle = ByteConvert.readFloat(stream);
		fFalloffStartOpacity = ByteConvert.readFloat(stream);
		fFalloffStopOpacity = ByteConvert.readFloat(stream);
		fLightingInfluence = ByteConvert.readFloat(stream);
		iEnvmapMinLOD = ByteConvert.readByte(stream);
		fSoftDepth = ByteConvert.readFloat(stream);
	}
}
