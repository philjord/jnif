package nif.niobject.bgsm;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
/**
 * some cs exmaple for deserialiaze here
 * https://github.com/ousnius/Material-Editor/blob/master/BaseMaterialFile.cs
 */
public abstract class BSMaterial
{
	public String localPath;

	// BGSM & BGEM shared variables

	public int Version = 0;
	public int tileFlags = 0;
	public boolean bTileU = false;
	public boolean bTileV = false;
	public float fUOffset;
	public float fVOffset;
	public float fUScale;
	public float fVScale;
	public float fAlpha;
	public byte bAlphaBlend;
	public int iAlphaSrc;
	public int iAlphaDst;
	public int iAlphaTestRef;
	public byte bAlphaTest;
	public short bZBufferWrite;
	public byte bZBufferTest;
	public byte bScreenSpaceReflections;
	public byte bWetnessControl_ScreenSpaceReflections;
	public byte bDecal;
	public byte bTwoSided;
	public byte bDecalNoFade;
	public byte bNonOccluder;
	public byte bRefraction;
	public byte bRefractionFalloff;
	public float fRefractionPower;
	public byte bEnvironmentMapping;
	public float fEnvironmentMappingMaskScale;
	public byte bDepthBias;
	public byte bGrayscaleToPaletteColor;

	public byte MaskWrites;

	public BSMaterial(String file)
	{
		this.localPath = file;
	}

	public void readFile(ByteBuffer stream) throws IOException
	{
		Version = ByteConvert.readInt(stream);
		tileFlags = ByteConvert.readInt(stream);

		bTileU = (tileFlags & 0x2) != 0;
		bTileV = (tileFlags & 0x1) != 0;

		fUOffset = ByteConvert.readFloat(stream);
		fVOffset = ByteConvert.readFloat(stream);
		fUScale = ByteConvert.readFloat(stream);
		fVScale = ByteConvert.readFloat(stream);

		fAlpha = ByteConvert.readFloat(stream);
		bAlphaBlend = ByteConvert.readByte(stream);
		iAlphaSrc = ByteConvert.readInt(stream);
		iAlphaDst = ByteConvert.readInt(stream);
		iAlphaTestRef = ByteConvert.readUnsignedByte(stream);
		bAlphaTest = ByteConvert.readByte(stream);
		bZBufferWrite = ByteConvert.readByte(stream);
		bZBufferTest = ByteConvert.readByte(stream);
		bScreenSpaceReflections = ByteConvert.readByte(stream);
		bWetnessControl_ScreenSpaceReflections = ByteConvert.readByte(stream);
		bDecal = ByteConvert.readByte(stream);
		bTwoSided = ByteConvert.readByte(stream);
		bDecalNoFade = ByteConvert.readByte(stream);
		bNonOccluder = ByteConvert.readByte(stream);
		bRefraction = ByteConvert.readByte(stream);
		bRefractionFalloff = ByteConvert.readByte(stream);
		fRefractionPower = ByteConvert.readFloat(stream);
		
		if (Version < 10) {
			bEnvironmentMapping = ByteConvert.readByte(stream);
			fEnvironmentMappingMaskScale = ByteConvert.readFloat(stream);             
        } else {
            bDepthBias = ByteConvert.readByte(stream);
        }
		
		bGrayscaleToPaletteColor = ByteConvert.readByte(stream);
		
		if (Version >= 6) {
			MaskWrites = ByteConvert.readByte(stream);
		}
	}

}
