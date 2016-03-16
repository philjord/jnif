package nif.niobject.bgsm;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.compound.NifColor3;

public class ShaderMaterial extends BSMaterial
{
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

	public ShaderMaterial(String file)
	{
		super(file);
	}

	public void readFile(ByteBuffer stream) throws IOException
	{
		//MUST have read off first 4 bytes by now!!
		super.readFile(stream);

		for (int i = 0; i < 9; i++)
		{
			String tex = ByteConvert.readSizedString(stream);
			tex = tex.replace("/", "\\");
			tex = tex.trim();
			textureList.add(tex);
		}

		bEnableEditorAlphaRef = ByteConvert.readByte(stream);
		bRimLighting = ByteConvert.readByte(stream);
		fRimPower = ByteConvert.readFloat(stream);
		fBacklightPower = ByteConvert.readFloat(stream);
		bSubsurfaceLighting = ByteConvert.readByte(stream);
		fSubsurfaceLightingRolloff = ByteConvert.readFloat(stream);
		bSpecularEnabled = ByteConvert.readByte(stream);

		cSpecularColor = new NifColor3(stream);
		fSpecularMult = ByteConvert.readFloat(stream);
		fSmoothness = ByteConvert.readFloat(stream);
		fFresnelPower = ByteConvert.readFloat(stream);
		fWetnessControl_SpecScale = ByteConvert.readFloat(stream);
		fWetnessControl_SpecPowerScale = ByteConvert.readFloat(stream);
		fWetnessControl_SpecMinvar = ByteConvert.readFloat(stream);
		fWetnessControl_EnvMapScale = ByteConvert.readFloat(stream);
		fWetnessControl_FresnelPower = ByteConvert.readFloat(stream);
		fWetnessControl_Metalness = ByteConvert.readFloat(stream);

		sRootMaterialPath = ByteConvert.readSizedString(stream);

		bAnisoLighting = ByteConvert.readByte(stream);
		bEmitEnabled = ByteConvert.readByte(stream);

		if (bEmitEnabled != 0)
			cEmittanceColor = new NifColor3(stream);

		fEmittanceMult = ByteConvert.readFloat(stream);
		bModelSpaceNormals = ByteConvert.readByte(stream);
		bExternalEmittance = ByteConvert.readByte(stream);
		bBackLighting = ByteConvert.readByte(stream);
		bReceiveShadows = ByteConvert.readByte(stream);
		bHideSecret = ByteConvert.readByte(stream);
		bCastShadows = ByteConvert.readByte(stream);
		bDissolveFade = ByteConvert.readByte(stream);
		bAssumeShadowmask = ByteConvert.readByte(stream);
		bGlowmap = ByteConvert.readByte(stream);
		bEnvironmentMappingWindow = ByteConvert.readByte(stream);
		bEnvironmentMappingEye = ByteConvert.readByte(stream);
		bHair = ByteConvert.readByte(stream);
		cHairTintColor = new NifColor3(stream);

		bTree = ByteConvert.readByte(stream);
		bFacegen = ByteConvert.readByte(stream);
		bSkinTint = ByteConvert.readByte(stream);
		bTessellate = ByteConvert.readByte(stream);
		fDisplacementTextureBias = ByteConvert.readFloat(stream);
		fDisplacementTextureScale = ByteConvert.readFloat(stream);
		fTessellationPnScale = ByteConvert.readFloat(stream);
		fTessellationBaseFactor = ByteConvert.readFloat(stream);
		fTessellationFadeDistance = ByteConvert.readFloat(stream);
		fGrayscaleToPaletteScale = ByteConvert.readFloat(stream);
		bSkewSpecularAlpha = ByteConvert.readByte(stream);
	}
}
