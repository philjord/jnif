package nif.compound;

import java.io.IOException;
import java.nio.ByteBuffer;

import nif.ByteConvert;

public class NifQuaternionXYZW {
	public static final NifQuaternionXYZW	Identity	= new NifQuaternionXYZW(0f, 0f, 0f, 1f);

	/**
	 <compound name="QuaternionXYZW" nifskopetype="quaternion_xyzw">
	
	 A quaternion as it appears in the havok objects.
	 
	 <add name="x" type="float">The x-coordinate.</add>
	 <add name="y" type="float">The y-coordinate.</add>
	 <add name="z" type="float">The z-coordinate.</add>
	 <add name="w" type="float">The w-coordinate.</add>
	 </compound>
	 */

	public float							x;

	public float							y;

	public float							z;

	public float							w;

	public NifQuaternionXYZW(ByteBuffer stream) throws IOException {
		x = ByteConvert.readFloat(stream);
		y = ByteConvert.readFloat(stream);
		z = ByteConvert.readFloat(stream);
		w = ByteConvert.readFloat(stream);
	}

	public NifQuaternionXYZW(ByteBuffer stream, int offset) {
		x = stream.getFloat(offset + 0);
		y = stream.getFloat(offset + 4);
		z = stream.getFloat(offset + 8);
		w = stream.getFloat(offset + 12);
	}

	public NifQuaternionXYZW(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof NifQuaternionXYZW) {
			NifQuaternionXYZW q2 = (NifQuaternionXYZW)o;
			return q2.w == w && q2.x == x && q2.y == y && q2.z == z;
		}
		return false;
	}

	@Override
	public String toString() {
		return "NPQuaternionXYZW " + x + " " + y + " " + z + " " + w;
	}

	// for interpolation
	//https://stackoverflow.com/questions/9027201/how-to-apply-a-scalar-multiplication-to-a-quaternion
	public NifQuaternionXYZW set(NifQuaternionXYZW q) {
		this.x = q.x;
		this.y = q.y;
		this.z = q.z;
		this.w = q.w;
		return this;
	}

	public NifQuaternionXYZW set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	public NifQuaternionXYZW mul(float f) {
		this.x *= f;
		this.y *= f;
		this.z *= f;
		this.w *= f;
		return this;
	}

	public NifQuaternionXYZW add(NifQuaternionXYZW q) {
		this.x += q.x;
		this.y += q.y;
		this.z += q.z;
		this.w += q.w;
		return this;
	}

	final static double	EPS		= 0.000001;
	final static double	EPS2	= 1.0e-30;
	final static double	PIO2	= 1.57079632679;

	/**
	 *  Performs a great circle interpolation between this quaternion
	 *  and the quaternion parameter and places the result into this
	 *  quaternion.
	 *  @param q1  the other quaternion
	 *  @param alpha  the alpha interpolation parameter
	 */
	public final void interpolate(NifQuaternionXYZW q1, float alpha) {
		// From "Advanced Animation and Rendering Techniques"
		// by Watt and Watt pg. 364, function as implemented appeared to be
		// incorrect.  Fails to choose the same quaternion for the double
		// covering. Resulting in change of direction for rotations.
		// Fixed function to negate the first quaternion in the case that the
		// dot product of q1 and this is negative. Second case was not needed.

		double dot, s1, s2, om, sinom;

		dot = x * q1.x + y * q1.y + z * q1.z + w * q1.w;

		if (dot < 0) {
			// negate quaternion
			q1.x = -q1.x;
			q1.y = -q1.y;
			q1.z = -q1.z;
			q1.w = -q1.w;
			dot = -dot;
		}

		if ((1.0 - dot) > EPS) {
			om = Math.acos(dot);
			sinom = Math.sin(om);
			s1 = Math.sin((1.0 - alpha) * om) / sinom;
			s2 = Math.sin(alpha * om) / sinom;
		} else {
			s1 = 1.0 - alpha;
			s2 = alpha;
		}

		w = (float)(s1 * w + s2 * q1.w);
		x = (float)(s1 * x + s2 * q1.x);
		y = (float)(s1 * y + s2 * q1.y);
		z = (float)(s1 * z + s2 * q1.z);
	}

	/**
	  *  Performs a great circle interpolation between quaternion q1
	  *  and quaternion q2 and places the result into this quaternion.
	  *  @param q1  the first quaternion
	  *  @param q2  the second quaternion
	  *  @param alpha  the alpha interpolation parameter
	  */
	public final void interpolate(NifQuaternionXYZW q1, NifQuaternion q2, float alpha) {
		// From "Advanced Animation and Rendering Techniques"
		// by Watt and Watt pg. 364, function as implemented appeared to be
		// incorrect.  Fails to choose the same quaternion for the double
		// covering. Resulting in change of direction for rotations.
		// Fixed function to negate the first quaternion in the case that the
		// dot product of q1 and this is negative. Second case was not needed.

		double dot, s1, s2, om, sinom;

		dot = q2.x * q1.x + q2.y * q1.y + q2.z * q1.z + q2.w * q1.w;

		if (dot < 0) {
			// negate quaternion
			q1.x = -q1.x;
			q1.y = -q1.y;
			q1.z = -q1.z;
			q1.w = -q1.w;
			dot = -dot;
		}

		if ((1.0 - dot) > EPS) {
			om = Math.acos(dot);
			sinom = Math.sin(om);
			s1 = Math.sin((1.0 - alpha) * om) / sinom;
			s2 = Math.sin(alpha * om) / sinom;
		} else {
			s1 = 1.0 - alpha;
			s2 = alpha;
		}
		w = (float)(s1 * q1.w + s2 * q2.w);
		x = (float)(s1 * q1.x + s2 * q2.x);
		y = (float)(s1 * q1.y + s2 * q2.y);
		z = (float)(s1 * q1.z + s2 * q2.z);
	}
}
