package nif.niobject;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.NifVer;

public class NiEnvMappedTriShapeData extends NiTriShapeData
{
	/**
	 
	 <!--

	 <niobject name="NiEnvMappedTriShapeData" abstract="0" ver1="3.1" ver2="3.1" inherit="NiTriBasedGeomData">
	 Holds mesh data using a list of singular triangles.
	 <add name="Num Triangle Points" type="uint">Num Triangles times 3.</add>
	 <add name="Has Triangles" type="bool">Do we have triangle data?</add>
	 <add name="Triangles" type="Triangle" arr1="Num Triangles" cond="Has Triangles != 0">Triangle face data.</add>
	 <add name="Num Match Groups" type="ushort">Number of shared normals groups.</add>
	 <add name="Match Groups" type="MatchGroup" arr1="Num Match Groups">The shared normals.</add>
	 </niobject>
	 
	 -->

	 <niobject name="NiEnvMappedTriShapeData" abstract="0" ver1="3.1" ver2="3.1" inherit="NiTriShapeData">

	 Holds mesh data using a list of singular triangles.
	 
	 </niobject>
	 
	 */

	public boolean readFromStream(ByteBuffer stream, NifVer nifVer) throws IOException
	{
		boolean success = super.readFromStream(stream, nifVer);

		return success;
	}
}