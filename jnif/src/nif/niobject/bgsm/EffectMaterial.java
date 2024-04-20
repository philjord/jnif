package nif.niobject.bgsm;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.compound.NifColor3;

public class EffectMaterial extends BSMaterial
{
	public String BaseTexture;
	public String GrayscaleTexture;
	public String EnvmapTexture;
	public String NormalTexture;
	public String EnvmapMaskTexture;
	public String SpecularTexture;
	public String LightingTexture;
	public String GlowTexture;
	public byte EnvironmentMapping;
	public float EnvironmentMappingMaskScale;
	public byte bBloodEnabled;
	public byte bEffectLightingEnabled;
	public byte bFalloffEnabled;
	public byte bFalloffColorEnabled;
	public byte bGrayscaleToPaletteAlpha;
	public byte bSoftEnabled;
	public NifColor3 cBaseColor;
	public float fBaseColorScale;
	public float fFalloffStartAngle;
	public float fFalloffStopAngle;
	public float fFalloffStartOpacity;
	public float fFalloffStopOpacity;
	public float fLightingInfluence;
	public int iEnvmapMinLOD;
	public float fSoftDepth;

	public NifColor3 EmittanceColor;
	public float AdaptativeEmissive_ExposureOffset;
	public float AdaptativeEmissive_FinalExposureMin;
	public float AdaptativeEmissive_FinalExposureMax;
	public byte Glowmap;
	public byte EffectPbrSpecular;


	public EffectMaterial(String file)
	{
		super(file);
	}

	@Override
	public void readFile(ByteBuffer stream) throws IOException
	{
		//MUST have read off first 4 bytes by now!!
		super.readFile(stream);
		
		BaseTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		GrayscaleTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		EnvmapTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		NormalTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		EnvmapMaskTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();

		if (Version >= 11) {
			SpecularTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			LightingTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			GlowTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		}

		if (Version >= 10) {
			EnvironmentMapping = ByteConvert.readByte(stream);
			EnvironmentMappingMaskScale = ByteConvert.readFloat(stream);
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
		iEnvmapMinLOD = ByteConvert.readUnsignedByte(stream);
		fSoftDepth = ByteConvert.readFloat(stream);
		
		
		if (Version >= 11) {
			EmittanceColor = new NifColor3(stream);
		}

		if (Version >= 15) {
			AdaptativeEmissive_ExposureOffset = ByteConvert.readFloat(stream);
			AdaptativeEmissive_FinalExposureMin = ByteConvert.readFloat(stream);
			AdaptativeEmissive_FinalExposureMax = ByteConvert.readFloat(stream);
		}

		if (Version >= 16) {
			Glowmap = ByteConvert.readByte(stream);
		}

		if (Version >= 20) {
			EffectPbrSpecular = ByteConvert.readByte(stream);
		}
	}
}
