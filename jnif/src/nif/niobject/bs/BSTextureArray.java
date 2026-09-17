package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;

/**
   <struct name="BSTextureArray" module="BSMain" versions="#F76#">
    <field name="Texture Array Width" type="uint" />
    <field name="Texture Array" type="SizedString" length="Texture Array Width" />
</struct>

 */
public class BSTextureArray {
	public int		TextureArrayWidth;
	public String[]	TextureArray;

	public BSTextureArray(ByteBuffer stream, NifVer nifVer) throws IOException {

		TextureArrayWidth = ByteConvert.readInt(stream);
		if (TextureArrayWidth > 0) {
			TextureArray = new String[TextureArrayWidth];
			for (int i = 0; i < TextureArrayWidth; i++) {
				TextureArray[i] = ByteConvert.readSizedString(stream);
			}
		}
	}

}