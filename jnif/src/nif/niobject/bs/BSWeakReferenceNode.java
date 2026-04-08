package nif.niobject.bs;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.compound.NifMatrix44;
import nif.niobject.NiNode;

/**
 *
    <niobject name="BSWeakReferenceNode" inherit="NiNode" module="BSMain" versions="#STF#">
        <field name="Num Refs" type="uint" />
        <field name="Ref" type="BSWeakReference" length="Num Refs" />
        <field name="Unknown Int 1" type="uint" />
        <field name="Num Unknown 1" type="uint" />
        <field name="Unknown Structs" type="UnknownWeakReferenceStruct" length="Num Unknown 1" />
    </niobject>
 */

public class BSWeakReferenceNode extends NiNode {

	int NumRefs;
	BSWeakReference[] Ref;
	int UnknownInt1;
    int NumUnknown1;
    UnknownWeakReferenceStruct[] UnknownStructs;
	@Override
	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException {
		boolean success = super.readFromStream(stream, nifVer);
		NumRefs = ByteConvert.readInt(stream);
		Ref = new BSWeakReference[NumRefs];
		for (int i = 0; i < NumRefs; i++)
			Ref[i] = new BSWeakReference(stream);
		UnknownInt1 = ByteConvert.readInt(stream);
		NumUnknown1 = ByteConvert.readInt(stream);
		UnknownStructs = new UnknownWeakReferenceStruct[NumUnknown1];
		for (int i = 0; i < NumUnknown1; i++)
			UnknownStructs[i] = new UnknownWeakReferenceStruct(stream);
	
		return success;
	}
	/**	 
	    <struct name="BSWeakReference" module="BSMain" versions="#STF#">
	        <field name="Resource ID" type="BSResourceID" />
	        <field name="Num Transforms" type="uint" />
	        <field name="Transforms" type="Matrix44" length="Num Transforms" />
	        <field name="Unknown Int 1" type="uint" />
	        <field name="Unknown Material Struct" type="UnknownMaterialStruct" length="Unknown Int 1" />
	    </struct>	    
	 */
	
	public static class BSWeakReference {
		BSResourceID ResourceID;
		int NumTransforms;
		NifMatrix44[] Transforms;
		int NumUnknown1;
		UnknownMaterialStruct[] UnknownMaterialStructs;
		
		public BSWeakReference(ByteBuffer stream) throws IOException {
			ResourceID = new BSResourceID(stream);
			NumTransforms = ByteConvert.readInt(stream);
			Transforms = new NifMatrix44[NumTransforms];
			for (int i = 0; i < NumTransforms; i++)
				Transforms[i] = new NifMatrix44(stream);
			NumUnknown1 = ByteConvert.readInt(stream);
			UnknownMaterialStructs = new UnknownMaterialStruct[NumUnknown1];
			for (int i = 0; i < NumUnknown1; i++)
				UnknownMaterialStructs[i] = new UnknownMaterialStruct(stream);

		}
	}
	/**
	  <struct name="UnknownMaterialStruct" module="BSMain" versions="#STF#">
	        <field name="Unknown Int 1" type="uint" />
	        <field name="Directory Hash" type="uint" />
	        <field name="File Hash" type="uint" />
	        <field name="Material Extension" type="char" length="4" />
	    </struct>	   
	 */

	public static class UnknownMaterialStruct {
		int UnknownInt1;
		BSResourceID ResourceID;
		public UnknownMaterialStruct(ByteBuffer stream) throws IOException {
			UnknownInt1 = ByteConvert.readInt(stream);
			//TODO: what the hell, isn't this nearly a ResourceID?
			//Note order!
			long dir = ByteConvert.readInt(stream) & 0xffffffffL;
			long file = ByteConvert.readInt(stream) & 0xffffffffL;
			long ext = ByteConvert.readInt(stream) & 0xffffffffL;			
			ResourceID = new BSResourceID(file, ext, dir); 
		}
	}
	/**
	    <struct name="UnknownWeakReferenceStruct" module="BSMain" versions="#STF#">
	        <field name="Transform" type="Matrix44" />
	        <field name="Resource ID" type="BSResourceID" />
	        <field name="Unknown Int 1" type="uint" />
	        <field name="Material" type="SizedString" />
	    </struct>
	 */
	public static class UnknownWeakReferenceStruct {
		NifMatrix44 Transform;
		BSResourceID ResourceID;		
		int NumUnknown1;
		String Material;
		
		public UnknownWeakReferenceStruct(ByteBuffer stream) throws IOException {
			Transform = new NifMatrix44(stream);
			ResourceID = new BSResourceID(stream);
			NumUnknown1 = ByteConvert.readInt(stream);
			Material = ByteConvert.readSizedString(stream);
		}
	}
}
