package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifVector3;
import nif.niobject.NiExtraData;

/**


    <niobject name="BoneTranslations" inherit="NiExtraData" module="BSMain" versions="#STF#">
        <field name="Num Translations" type="uint" />
        <field name="Translations" type="BoneTranslation" length="Num Translations" />
    </niobject>
 */
public class BoneTranslations extends NiExtraData {

	public int					NumTranslations;
	public BoneTranslation[]	Translations;

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException {
		boolean success = super.readFromStream(stream, nifVer);
		NumTranslations = ByteConvert.readInt(stream);
		Translations = new BoneTranslation[NumTranslations];
		for (int i = 0; i < NumTranslations; i++) {
			Translations[i] = new BoneTranslation(stream);
		}

		return success;
	}
	/**
	  <struct name="BoneTranslation" module="BSMain" versions="#STF#">
	        <field name="Bone Name" type="SizedString" />
	        <field name="Translation" type="Vector3" />
	    </struct>
	 */
	
	public static class BoneTranslation {
		public String		BoneName;
		public NifVector3	Translation;

		public BoneTranslation(ByteBuffer stream) throws IOException {
			BoneName = ByteConvert.readSizedString(stream);
			Translation =  new NifVector3(stream);			 
		}
	}
}
