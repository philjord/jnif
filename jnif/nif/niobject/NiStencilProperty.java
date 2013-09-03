package nif.niobject;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;
import nif.NifVer;
import nif.basic.NifFlags;
import nif.enums.CompareMode;
import nif.enums.FaceDrawMode;
import nif.enums.StencilAction;

public class NiStencilProperty extends NiProperty
{
	/**
	 <niobject name="NiStencilProperty" abstract="0" inherit="NiProperty" ver1="4.1.0.12">

	 Allows control of stencil testing.
	 
	 <add name="Flags" type="Flags" ver2="10.0.1.2">Property flags.</add>
	 <add name="Stencil Enabled" type="byte" ver2="20.0.0.5">Enables or disables the stencil test.</add>
	 <add name="Stencil Function" type="CompareMode" ver2="20.0.0.5">Selects the compare mode function.</add>
	 <add name="Stencil Ref" type="uint" ver2="20.0.0.5">Unknown.  Default is 0.</add>
	 <add name="Stencil Mask" type="uint" default="4294967295" ver2="20.0.0.5">A bit mask. The default is 0xffffffff.</add>
	 <add name="Fail Action" type="StencilAction" ver2="20.0.0.5"/>
	 <add name="Z Fail Action" type="StencilAction" ver2="20.0.0.5"/>
	 <add name="Pass Action" type="StencilAction" ver2="20.0.0.5"/>
	 <add name="Draw Mode" default="3" type="FaceDrawMode" ver2="20.0.0.5">
	 Used to enabled double sided faces. Default is 3 (DRAW_BOTH).
	 </add>
	 <add name="Flags" type="Flags" ver1="20.1.0.3">

	 Property flags:
	 0.. 0(?) = Stencil Enable
	 1.. 3 = Fail Action
	 4.. 6 = Z Fail Action
	 7.. 9 = Pass Action
	 10..11 = Draw Mode
	 12..14 = Test Func
	 
	 </add>
	 <add name="Stencil Ref" type="uint" ver1="20.1.0.3">Unknown.  Default is 0.</add>
	 <add name="Stencil Mask" type="uint" default="4294967295" ver1="20.1.0.3">A bit mask. The default is 0xffffffff.</add>
	 </niobject>
	 */

	private boolean stencilEnabled;

	private CompareMode stencilFunction;

	public int stencilRef;

	public int stencilMask;

	private StencilAction failAction;

	private StencilAction zFailAction;

	private StencilAction passAction;

	private FaceDrawMode drawMode;

	private NifFlags flags;

	public boolean readFromStream(InputStream stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);
		if (nifVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			stencilEnabled = ByteConvert.readBool(stream, nifVer);
			stencilFunction = new CompareMode(stream);
			stencilRef = ByteConvert.readInt(stream);
			stencilMask = ByteConvert.readInt(stream);
			failAction = new StencilAction(stream);
			zFailAction = new StencilAction(stream);
			passAction = new StencilAction(stream);
			drawMode = new FaceDrawMode(stream);
		}
		else if (nifVer.LOAD_VER >= NifVer.VER_20_1_0_3)
		{
			flags = new NifFlags(stream);
			stencilRef = ByteConvert.readInt(stream);
			stencilMask = ByteConvert.readInt(stream);
		}

		return success;
	}

	public boolean isStencilEnable()
	{
		boolean ret = false; //default	
		if (nVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			ret = stencilEnabled;
		}
		else if (nVer.LOAD_VER >= NifVer.VER_20_1_0_3)
		{
			ret = (flags.flags & 0x0001) != 0;
		}
		return ret;
	}

	public int stencilFunction()
	{
		int ret = -1;
		if (nVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			ret = stencilFunction.mode;
		}
		else if (nVer.LOAD_VER >= NifVer.VER_20_1_0_3)
		{
			ret = ((flags.flags >> 12) & 0x007);
		}
		return ret;
	}

	public int failAction()
	{
		int ret = -1;
		if (nVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			ret = failAction.action;
		}
		else if (nVer.LOAD_VER >= NifVer.VER_20_1_0_3)
		{
			ret = ((flags.flags >> 1) & 0x007);
		}
		return ret;
	}

	public int zFailAction()
	{
		int ret = -1;
		if (nVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			ret = zFailAction.action;
		}
		else if (nVer.LOAD_VER >= NifVer.VER_20_1_0_3)
		{
			ret = ((flags.flags >> 4) & 0x007);
		}
		return ret;
	}

	public int passAction()
	{
		int ret = -1;
		if (nVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			ret = passAction.action;
		}
		else if (nVer.LOAD_VER >= NifVer.VER_20_1_0_3)
		{
			ret = ((flags.flags >> 7) & 0x007);
		}
		return ret;
	}

	public int getDrawMode()
	{
		int ret = 3; //default	
		if (nVer.LOAD_VER <= NifVer.VER_20_0_0_5)
		{
			ret = drawMode.mode;
		}
		else if (nVer.LOAD_VER >= NifVer.VER_20_1_0_3)
		{
			ret = ((flags.flags >> 10) & 0x003);
		}
		return ret;
	}

}