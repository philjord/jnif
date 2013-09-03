package nif;

import nif.compound.NifFooter;
import nif.compound.NifHeader;

public class NifFile
{
	public NifHeader header;

	public NiObjectList blocks;

	public NifFooter footer;

	public NifFile(NifHeader header, NiObjectList blocks, NifFooter footer)
	{
		this.header = header;

		this.blocks = blocks;
		this.footer = footer;
	}
}
