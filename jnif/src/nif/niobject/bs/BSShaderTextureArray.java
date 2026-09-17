package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

/**
<struct name="BSShaderTextureArray" module="BSMain" versions="#F76#">
    <field name="Unknown Byte" type="byte" default="1" />
    <field name="Num Texture Arrays" type="uint" />
    <field name="Texture Arrays" type="BSTextureArray" length="Num Texture Arrays" />
</struct>
 */
public class BSShaderTextureArray {
	public byte				UnknownByte;
	public int				NumTextureArrays;
	public BSTextureArray[]	TextureArrays;

	public BSShaderTextureArray(ByteBuffer stream, NifVer nifVer) throws IOException {
		UnknownByte = ByteConvert.readByte(stream);
		NumTextureArrays = ByteConvert.readInt(stream);
		if (NumTextureArrays > 0) {
			TextureArrays = new BSTextureArray[NumTextureArrays];
			for (int i = 0; i < NumTextureArrays; i++) {
				TextureArrays[i] = new BSTextureArray(stream, nifVer);
			}
		}
	}

}