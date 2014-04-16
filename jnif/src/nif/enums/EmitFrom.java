package nif.enums;

import java.io.IOException;
import java.io.InputStream;

import nif.ByteConvert;

public class EmitFrom
{
	/**
	 * Controls which parts of the mesh that the particles are emitted from.
	 */
	public static final int EMIT_FROM_VERTICES = 0;// Emit particles from the vertices of the mesh.</option>

	public static final int EMIT_FROM_FACE_CENTER = 1;// Emit particles from the center of the faces of the mesh.

	public static final int EMIT_FROM_EDGE_CENTER = 2;// Emit particles from the center of the edges of the mesh.

	public static final int EMIT_FROM_FACE_SURFACE = 3;// Perhaps randomly emit particles from anywhere on the faces of the mesh?

	public static final int EMIT_FROM_EDGE_SURFACE = 4;// Perhaps randomly emit particles from anywhere on the edges of the mesh?

	public int from;

	public EmitFrom(InputStream stream) throws IOException
	{
		from = ByteConvert.readInt(stream);
	}
}
