package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifMatrix44;

public class BSDistantObjectInstancedNode extends BSMultiBoundNode {
	/**
	<niobject name="BSDistantObjectInstancedNode" inherit="BSMultiBoundNode" module="BSMain" versions="#F76#">
	    <field name="Num Instances" type="uint" />
	    <field name="Instances" type="BSDistantObjectInstance" length="Num Instances" />
	    <field name="Texture Arrays" type="BSShaderTextureArray" length="3" />
	</niobject>
	 */

	public int							NumInstances;
	public BSDistantObjectInstance[]	Instances;
	public BSShaderTextureArray[]		TextureArrays	= new BSShaderTextureArray[3];

	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException {
		boolean success = super.readFromStream(stream, nifVer);
		NumInstances = ByteConvert.readInt(stream);
		if (NumInstances > 0) {
			Instances = new BSDistantObjectInstance[NumInstances];
			for (int i = 0; i < NumInstances; i++) {
				Instances[i] = new BSDistantObjectInstance(stream, nifVer);
			}
		}

		for (int i = 0; i < 3; i++) {
			TextureArrays[i] = new BSShaderTextureArray(stream, nifVer);
		}

		return success;
	}

	/**
	<struct name="BSDistantObjectUnknown" module="BSMain" size="12" versions="#F76#">
	    <field name="Unknown 1" type="uint64" />
	    <field name="Unknown 2" type="uint" />
	</struct>
	*/
	public static class BSDistantObjectUnknown {

		public long	Unknown1;
		public int	Unknown2;

		public BSDistantObjectUnknown(ByteBuffer stream, NifVer nifVer) throws IOException {
			Unknown1 = ByteConvert.readLong(stream);
			Unknown2 = ByteConvert.readInt(stream);
		}

	}

	/**
	<struct name="BSDistantObjectInstance" module="BSMain" versions="#F76#">
	    <field name="Resource ID" type="BSResourceID" />
	    <field name="Num Unknown Data" type="uint" />
	    <field name="Unknown Data" type="BSDistantObjectUnknown" length="Num Unknown Data" />
	    <field name="Num Transforms" type="uint" />
	    <field name="Transforms" type="Matrix44" length="Num Transforms" />
	</struct>
	*/
	public static class BSDistantObjectInstance {
		public BSResourceID				ResourceID;
		public int						NumUnknownData;
		public BSDistantObjectUnknown[]	UnknownData;
		public int						NumTransforms;
		public NifMatrix44[]			Transforms;

		public BSDistantObjectInstance(ByteBuffer stream, NifVer nifVer) throws IOException {
			ResourceID = new BSResourceID(stream);
			NumUnknownData = ByteConvert.readInt(stream);
			if (NumUnknownData > 0) {
				UnknownData = new BSDistantObjectUnknown[NumUnknownData];
				for (int i = 0; i < NumUnknownData; i++) {
					UnknownData[i] = new BSDistantObjectUnknown(stream, nifVer);
				}
			}
			NumTransforms = ByteConvert.readInt(stream);
			if (NumTransforms > 0) {
				Transforms = new NifMatrix44[NumTransforms];
				for (int i = 0; i < NumTransforms; i++) {
					Transforms[i] = new NifMatrix44(stream);
				}
			}
		}

	}
}