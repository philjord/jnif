package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifRef;
import nif.basic.NifStringOffset;
import nif.niobject.NiObject;
import nif.niobject.NiStringPalette;
import nif.niobject.controller.NiTimeController;
import nif.niobject.interpolator.NiInterpolator;

public class NifControllerLink
{
	/**
	 In a .kf file, this links to a controllable object, via its name (or for version 10.2.0.0 and up, a link and offset to a NiStringPalette 
	 that contains the name), and a sequence of interpolators that apply to this controllable object, via links.
	 
	 <add name="Target Name" type="string" ver2="10.1.0.0">Name of a controllable object in another NIF file.</add>
	 <add name="Controller" type="Ref" template="NiTimeController" ver2="10.1.0.0">Link to a controller.</add>
	 <add name="Interpolator" type="Ref" template="NiInterpolator" ver1="10.1.0.106">Link to an interpolator.</add>
	 <add name="Controller" type="Ref" template="NiTimeController" ver1="10.1.0.106">Unknown link. Usually -1.</add>
	 <add name="Unknown Link 2" type="Ref" template="NiObject" ver1="10.1.0.106" ver2="10.1.0.106">Unknown.</add>
	 <add name="Unknown Short 0" type="ushort" ver1="10.1.0.106" ver2="10.1.0.106">Unknown.</add>
	 <add name="Priority" type="byte" ver1="10.1.0.106" vercond="(User Version == 10) || (User Version == 11)">
	 Idle animations tend to have low values for this, and NIF objects that have high values tend to correspond with the important parts of the 
	 animation.
	 </add>
	 <add name="String Palette" type="Ref" template="NiStringPalette" ver1="10.2.0.0" ver2="20.0.0.5">
	 Refers to the NiStringPalette which contains the name of the controlled NIF object.
	 </add>
	 <add name="Node Name" type="string" ver1="10.1.0.106" ver2="10.1.0.106">The name of the animated node.</add>
	 <add name="Node Name" type="string" ver1="20.1.0.3">The name of the animated node.</add>
	 <add name="Node Name Offset" type="StringOffset" ver1="10.2.0.0" ver2="20.0.0.5">
	 Offset in the string palette where the name of the controlled node (NiNode, NiTriShape, ...) starts.
	 </add>
	 <add name="Property Type" type="string" ver1="10.1.0.106" ver2="10.1.0.106">
	 Name of the property (NiMaterialProperty, ...), if this controller controls a property.
	 </add>
	 <add name="Property Type" type="string" ver1="20.1.0.3">
	 Name of the property (NiMaterialProperty, ...), if this controller controls a property.
	 </add>
	 <add name="Property Type Offset" type="StringOffset" ver1="10.2.0.0" ver2="20.0.0.5">
	 Offset in the string palette where the property (NiMaterialProperty, ...) starts, if this controller controls a property. Otherwise, -1.
	 </add>
	 <add name="Controller Type" type="string" ver1="10.1.0.106" ver2="10.1.0.106">
	 Probably the object type name of the controller in the NIF file that is child of the controlled object.
	 </add>
	 <add name="Controller Type" type="string" ver1="20.1.0.3">
	 Probably the object type name of the controller in the NIF file that is child of the controlled object.
	 </add>
	 <add name="Controller Type Offset" type="StringOffset" ver1="10.2.0.0" ver2="20.0.0.5">
	 Apparently the offset in the string palette of some type of controller related to Interpolator (for example, a 'NiTransformInterpolator' will
	 have here a 'NiTransformController', etc.). Sometimes the type of controller that links to the interpolator. Probably it refers to the 
	 controller in the NIF file that is child of the controlled object, via its type name.
	 </add>
	 <add name="Variable 1" type="string" ver1="10.1.0.106" ver2="10.1.0.106">
	 Some variable string (such as 'SELF_ILLUM', '0-0-TT_TRANSLATE_U', 'tongue_out', etc.).
	 </add>
	 <add name="Variable 1" type="string" ver1="20.1.0.3">
	 Some variable string (such as 'SELF_ILLUM', '0-0-TT_TRANSLATE_U', 'tongue_out', etc.).
	 </add>
	 <add name="Variable 1 Offset" type="StringOffset" ver1="10.2.0.0" ver2="20.0.0.5">
	 Offset in the string palette where some variable string starts (such as 'SELF_ILLUM', '0-0-TT_TRANSLATE_U', 'tongue_out', etc.). Usually, -1.
	 </add>
	 <add name="Variable 2" type="string" ver1="10.1.0.106" ver2="10.1.0.106">
	 Another variable string, apparently used for particle system controllers.
	 </add>
	 <add name="Variable 2" type="string" ver1="20.1.0.3">
	 Another variable string, apparently used for particle system controllers.
	 </add>
	 <add name="Variable 2 Offset" type="StringOffset" ver1="10.2.0.0" ver2="20.0.0.5">
	 Offset in the string palette where some variable string starts (so far only 'EmitterActive' and 'BirthRate' have been observed in official files,
	 used for particle system controllers). Usually, -1.
	 </add>
	 </compound>
	 */

	public NifRef interpolator;

	public NifRef controller;

	public int priority;

	public NifRef stringPalette;

	public String nodeName;

	public NifStringOffset nodeNameOffset;

	public String propertyType;

	public NifStringOffset propertyTypeOffset;

	public String controllerType;

	public NifStringOffset controllerTypeOffset;

	public String variable1;

	public NifStringOffset variable1Offset;

	public String variable2;

	public NifStringOffset variable2Offset;

	public NifControllerLink(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		interpolator = new NifRef(NiInterpolator.class, stream);
		controller = new NifRef(NiTimeController.class, stream);
		if (nifVer.LOAD_VER == NifVer.VER_10_1_0_106)
		{
			new NifRef(NiObject.class, stream);
			ByteConvert.readShort(stream);
		}
		if ((nifVer.LOAD_USER_VER == 10 || (nifVer.LOAD_USER_VER == 11 || nifVer.LOAD_USER_VER == 12))
				&& !nifVer.isBP())
		{
			priority = ByteConvert.readUnsignedByte(stream);
		}

		if (nifVer.LOAD_VER >= NifVer.VER_10_2_0_0 && nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
			stringPalette = new NifRef(NiStringPalette.class, stream);

		if (nifVer.LOAD_VER == NifVer.VER_10_1_0_106 || nifVer.LOAD_VER >= NifVer.VER_20_1_0_3)
			nodeName = ByteConvert.readIndexString(stream, nifVer);

		if (nifVer.LOAD_VER >= NifVer.VER_10_2_0_0 && nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
			nodeNameOffset = new NifStringOffset(stream);

		if (nifVer.LOAD_VER == NifVer.VER_10_1_0_106 || nifVer.LOAD_VER >= NifVer.VER_20_1_0_3)
			propertyType = ByteConvert.readIndexString(stream, nifVer);

		if (nifVer.LOAD_VER >= NifVer.VER_10_2_0_0 && nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
			propertyTypeOffset = new NifStringOffset(stream);

		if (nifVer.LOAD_VER == NifVer.VER_10_1_0_106 || nifVer.LOAD_VER >= NifVer.VER_20_1_0_3)
			controllerType = ByteConvert.readIndexString(stream, nifVer);

		if (nifVer.LOAD_VER >= NifVer.VER_10_2_0_0 && nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
			controllerTypeOffset = new NifStringOffset(stream);

		if (nifVer.LOAD_VER == NifVer.VER_10_1_0_106 || nifVer.LOAD_VER >= NifVer.VER_20_1_0_3)
			variable1 = ByteConvert.readIndexString(stream, nifVer);

		if (nifVer.LOAD_VER >= NifVer.VER_10_2_0_0 && nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
			variable1Offset = new NifStringOffset(stream);

		if (nifVer.LOAD_VER == NifVer.VER_10_1_0_106 || nifVer.LOAD_VER >= NifVer.VER_20_1_0_3)
			variable2 = ByteConvert.readIndexString(stream, nifVer);

		if (nifVer.LOAD_VER >= NifVer.VER_10_2_0_0 && nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
			variable2Offset = new NifStringOffset(stream);
	}
}
