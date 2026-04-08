package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.niobject.NiExtraData;

/**
     <niobject name="SkinAttach" inherit="NiExtraData" module="BSMain" versions="#STF#">
        <field name="Num Bones" type="uint" />
        <field name="Bones" type="SizedString" length="Num Bones" />
    </niobject>
 */
public class SkinAttach extends NiExtraData {

	public int			NumBones;
	public String[]		Bones;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException {
		boolean success = super.readFromStream(stream, nifVer);
		NumBones = ByteConvert.readInt(stream);
		Bones = new String[NumBones];
		for (int i = 0; i < NumBones; i++) {
			Bones[i] = ByteConvert.readSizedString(stream);
		}
		 
		return success;
	}
}
