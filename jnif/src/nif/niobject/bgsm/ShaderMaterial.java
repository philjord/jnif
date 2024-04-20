package nif.niobject.bgsm;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.compound.NifColor3;

public class ShaderMaterial extends BSMaterial
{
	public String DiffuseTexture;
	public String NormalTexture;
	public String SmoothSpecTexture;
	public String GreyscaleTexture;
	public String GlowTexture;
	public String WrinklesTexture;
	public String SpecularTexture;
	public String LightingTexture;
	public String FlowTexture;
	public String DistanceFieldAlphaTexture;
	public String EnvmapTexture;
	public String InnerLayerTexture;
	public String DisplacementTexture;
	
	public byte bEnableEditorAlphaRef;
	public byte bRimLighting;
	public float fRimPower;
	public float fBacklightPower;
	public byte bSubsurfaceLighting;
	public float fSubsurfaceLightingRolloff;
	public byte bSpecularEnabled;
	public NifColor3 cSpecularColor;
	public float fSpecularMult;
	public float fSmoothness;
	public float fFresnelPower;
	public float fWetnessControl_SpecScale;
	public float fWetnessControl_SpecPowerScale;
	public float fWetnessControl_SpecMinvar;
	public float fWetnessControl_EnvMapScale;
	public float fWetnessControl_FresnelPower;
	public float fWetnessControl_Metalness;
	public String sRootMaterialPath;
	public byte bAnisoLighting;
	public byte bEmitEnabled;
	public NifColor3 cEmittanceColor;
	public float fEmittanceMult;
	public byte bModelSpaceNormals;
	public byte bExternalEmittance;
	public byte bBackLighting;
	public byte bReceiveShadows;
	public byte bHideSecret;
	public byte bCastShadows;
	public byte bDissolveFade;
	public byte bAssumeShadowmask;
	public byte bGlowmap;
	public byte bEnvironmentMappingWindow;
	public byte bEnvironmentMappingEye;
	public byte bHair;
	public NifColor3 cHairTintColor;
	public byte bTree;
	public byte bFacegen;
	public byte bSkinTint;
	public byte bTessellate;
	public float fDisplacementTextureBias;
	public float fDisplacementTextureScale;
	public float fTessellationPnScale;
	public float fTessellationBaseFactor;
	public float fTessellationFadeDistance;
	public float fGrayscaleToPaletteScale;
	public byte bSkewSpecularAlpha;

	public byte Translucency;
	public byte TranslucencyThickObject;
	public byte TranslucencyMixAlbedoWithSubsurfaceColor;
	public NifColor3 TranslucencySubsurfaceColor;
	public float TranslucencyTransmissiveScale;
	public float TranslucencyTurbulence;
	public byte PBR;
	public byte CustomPorosity;
	public float PorosityValue;
	public float LumEmittance;
	public byte UseAdaptativeEmissive;
	public float AdaptativeEmissive_ExposureOffset;
	public float AdaptativeEmissive_FinalExposureMin;
	public float AdaptativeEmissive_FinalExposureMax;
	public byte Terrain;
	public int UnkInt1;
	public float TerrainThresholdFalloff;
	public float TerrainTilingDistance;
	public float TerrainRotationAngle;

	public ShaderMaterial(String file)
	{
		super(file);
	}

	@Override
	public void readFile(ByteBuffer stream) throws IOException
	{
		//MUST have read off first 4 bytes by now!!
		super.readFile(stream);

		DiffuseTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		NormalTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		SmoothSpecTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		GreyscaleTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();

		if (Version > 2) {
			GlowTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			WrinklesTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			SpecularTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			LightingTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			FlowTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();

			if (Version >= 17) {
				DistanceFieldAlphaTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			}
		} else {
			EnvmapTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			GlowTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			InnerLayerTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			WrinklesTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
			DisplacementTexture = ByteConvert.readSizedString(stream).replace("/", "\\").trim();
		}

		bEnableEditorAlphaRef = ByteConvert.readByte(stream);
		
		if (Version >= 8) {
			Translucency = ByteConvert.readByte(stream);
			TranslucencyThickObject = ByteConvert.readByte(stream);
			TranslucencyMixAlbedoWithSubsurfaceColor = ByteConvert.readByte(stream);
			TranslucencySubsurfaceColor = new NifColor3(stream);
			TranslucencyTransmissiveScale = ByteConvert.readFloat(stream);
			TranslucencyTurbulence = ByteConvert.readFloat(stream);
		} else {
			bRimLighting = ByteConvert.readByte(stream);
			fRimPower = ByteConvert.readFloat(stream);
			fBacklightPower = ByteConvert.readFloat(stream);

			bSubsurfaceLighting = ByteConvert.readByte(stream);
			fSubsurfaceLightingRolloff = ByteConvert.readFloat(stream);
		}
		
		bSpecularEnabled = ByteConvert.readByte(stream);
		cSpecularColor = new NifColor3(stream);
		fSpecularMult = ByteConvert.readFloat(stream);
		fSmoothness = ByteConvert.readFloat(stream);
		
		fFresnelPower = ByteConvert.readFloat(stream);
		fWetnessControl_SpecScale = ByteConvert.readFloat(stream);
		fWetnessControl_SpecPowerScale = ByteConvert.readFloat(stream);
		fWetnessControl_SpecMinvar = ByteConvert.readFloat(stream);
		if (Version < 10) {
			fWetnessControl_EnvMapScale = ByteConvert.readFloat(stream);
		}
		fWetnessControl_FresnelPower = ByteConvert.readFloat(stream);
		fWetnessControl_Metalness = ByteConvert.readFloat(stream);

		if (Version > 2) {
			PBR = ByteConvert.readByte(stream);

			if (Version >= 9) {
				CustomPorosity = ByteConvert.readByte(stream);
				PorosityValue = ByteConvert.readFloat(stream);
			}
		}
		
		
		sRootMaterialPath = ByteConvert.readSizedString(stream);

		bAnisoLighting = ByteConvert.readByte(stream);
		bEmitEnabled = ByteConvert.readByte(stream);

		if (bEmitEnabled != 0)
			cEmittanceColor = new NifColor3(stream);

		fEmittanceMult = ByteConvert.readFloat(stream);
		bModelSpaceNormals = ByteConvert.readByte(stream);
		bExternalEmittance = ByteConvert.readByte(stream);
		if (Version >= 12) {
			LumEmittance = ByteConvert.readFloat(stream);
		}

		if (Version >= 13) {
			UseAdaptativeEmissive = ByteConvert.readByte(stream);
			AdaptativeEmissive_ExposureOffset = ByteConvert.readFloat(stream);
			AdaptativeEmissive_FinalExposureMin = ByteConvert.readFloat(stream);
			AdaptativeEmissive_FinalExposureMax = ByteConvert.readFloat(stream);
		}

		if (Version < 8) {
			bBackLighting = ByteConvert.readByte(stream);
		}
		
		bReceiveShadows = ByteConvert.readByte(stream);
		bHideSecret = ByteConvert.readByte(stream);
		bCastShadows = ByteConvert.readByte(stream);
		bDissolveFade = ByteConvert.readByte(stream);
		bAssumeShadowmask = ByteConvert.readByte(stream);
		
		bGlowmap = ByteConvert.readByte(stream);
		
		if (Version < 7) {
			bEnvironmentMappingWindow = ByteConvert.readByte(stream);
			bEnvironmentMappingEye = ByteConvert.readByte(stream);
		}
		
		bHair = ByteConvert.readByte(stream);
		cHairTintColor = new NifColor3(stream);

		bTree = ByteConvert.readByte(stream);
		bFacegen = ByteConvert.readByte(stream);
		bSkinTint = ByteConvert.readByte(stream);
		bTessellate = ByteConvert.readByte(stream);
		
		if (Version < 3) {
			fDisplacementTextureBias = ByteConvert.readFloat(stream);
			fDisplacementTextureScale = ByteConvert.readFloat(stream);
			fTessellationPnScale = ByteConvert.readFloat(stream);
			fTessellationBaseFactor = ByteConvert.readFloat(stream);
			fTessellationFadeDistance = ByteConvert.readFloat(stream);
		}
		
		fGrayscaleToPaletteScale = ByteConvert.readFloat(stream);
		
		if (Version >= 1) {
			bSkewSpecularAlpha = ByteConvert.readByte(stream);
		}
		
		if (Version >= 3) {
			Terrain = ByteConvert.readByte(stream);

			if (Terrain != 0) {
				if (Version == 3) {
					UnkInt1 = ByteConvert.readInt(stream);
				}

				TerrainThresholdFalloff = ByteConvert.readFloat(stream);
				TerrainTilingDistance = ByteConvert.readFloat(stream);
				TerrainRotationAngle = ByteConvert.readFloat(stream);
			}
		}
	}
}
