package nif.tools;


public class MiniFloat
{
	// ignores the higher 16 bits
	public static float toFloat(int hbits)
	{
		int mant = hbits & 0x03ff; // 10 bits mantissa
		int exp = hbits & 0x7c00; // 5 bits exponent
		if (exp == 0x7c00) // NaN/Inf
			exp = 0x3fc00; // -> NaN/Inf
		else if (exp != 0) // normalized value
		{
			exp += 0x1c000; // exp - 15 + 127
			if (mant == 0 && exp > 0x1c400) // smooth transition
				return Float.intBitsToFloat((hbits & 0x8000) << 16 | exp << 13 | 0x3ff);
		}
		else if (mant != 0) // && exp==0 -> subnormal
		{
			exp = 0x1c400; // make it normal
			do
			{
				mant <<= 1; // mantissa * 2
				exp -= 0x400; // decrease exp by 1
			}
			while ((mant & 0x400) == 0); // while not normal
			mant &= 0x3ff; // discard subnormal bit
		} // else +/-0 -> +/-0
		return Float.intBitsToFloat( // combine all parts
				(hbits & 0x8000) << 16 // sign  << ( 31 - 15 )
						| (exp | mant) << 13); // value << ( 23 - 10 )
	}

	// returns all higher 16 bits as 0 for all results
	public static int fromFloat(float fval)
	{
		int fbits = Float.floatToIntBits(fval);
		int sign = fbits >>> 16 & 0x8000; // sign only
		int val = (fbits & 0x7fffffff) + 0x1000; // rounded value

		if (val >= 0x47800000) // might be or become NaN/Inf
		{ // avoid Inf due to rounding
			if ((fbits & 0x7fffffff) >= 0x47800000)
			{ // is or must become NaN/Inf
				if (val < 0x7f800000) // was value but too large
					return sign | 0x7c00; // make it +/-Inf
				return sign | 0x7c00 | // remains +/-Inf or NaN
						(fbits & 0x007fffff) >>> 13; // keep NaN (and Inf) bits
			}
			return sign | 0x7bff; // unrounded not quite Inf
		}
		if (val >= 0x38800000) // remains normalized value
			return sign | val - 0x38000000 >>> 13; // exp - 127 + 15
		if (val < 0x33000000) // too small for subnormal
			return sign; // becomes +/-0
		val = (fbits & 0x7fffffff) >>> 23; // tmp exp for subnormal calc
		return sign | ((fbits & 0x7fffff | 0x800000) // add subnormal bit
				+ (0x800000 >>> val - 102) // round depending on cut off
		>>> 126 - val); // div by 2^(1-(exp-127+15)) and >> 13 | exp=0
	}
	
	
	//https://stackoverflow.com/questions/37105764/how-to-convert-to-from-an-8-bit-float-representation
	// Note I want unsigened float, so perhaps 4,4?
	//https://stackoverflow.com/questions/46003325/minifloat-format
	
	//FIXME:!!!!!!!!! NOT DONE!
		public static float toFloatU8(byte hbits)
		{
			int mant = hbits & 0b00000111; // 3 bits mantissa
			int exp = hbits & 0b01111000; // 4 bits exponent
			if (exp == 0b01111000) // NaN/Inf
				exp = 0x3fc00; // -> NaN/Inf
			else if (exp != 0) // normalized value
			{
				exp += 0x1c000; // exp - 15 + 127
				if (mant == 0 && exp > 0x1c400) // smooth transition
					return Float.intBitsToFloat((hbits & 0x8000) << 16 | exp << 13 | 0x3ff);
			}
			else if (mant != 0) // && exp==0 -> subnormal
			{
				exp = 0x1c400; // make it normal
				do
				{
					mant <<= 1; // mantissa * 2
					exp -= 0x400; // decrease exp by 1
				}
				while ((mant & 0x400) == 0); // while not normal
				mant &= 0x3ff; // discard subnormal bit
			} // else +/-0 -> +/-0
			return Float.intBitsToFloat( // combine all parts
					(hbits & 0x8000) << 16 // sign  << ( 31 - 15 )
							| (exp | mant) << 13); // value << ( 23 - 10 )
		}

		
		//Therefore final typical number is sign(+/- 1) * 2decoded exponent * 1.mantissa.
		//1 sign 8 exponent, 15 mantissa 21bit
		/*public static float float21bits(long raw, int shift)
		{
			//8,12 produces NaN
			//7,13 no Nan 1.9733078E-31, -1.0009764
			//6,14 no Nan 3.0094273E-36, -3.2313176E-27
			//5,15 no Nan 9.409052E-38, -3.0831292E-33
			
			
			//bias of 4bit exp is 7, 5bit is 15, 8bit is 127 
			//so 6bit = 31 and 7bit = 63  
			
			int base =  (int)(raw >> shift) & 0b111111111111111111111; // 21 bits masked
			System.out.println("bits21:" + String.format("%21s", Integer.toBinaryString(base)).replace(" ", "0"));
			int sign = (base >> 20);
			int exp = (base >> 14) & 0b111111;// 5 bits exponent
			if (exp != 0)
				exp += 91; // exp - 15 + 127 bias components 
			int mant = base & 0b11111111111111; // 15 bits mantissa
			
			int bits = (sign << 31) | exp << 23 | mant;
			return Float.intBitsToFloat(bits);
		}
		//1 sign 6 exponent, 16 mantissa 22bit
		public static float float22bits(long raw, int shift)
		{
			int base =  (int)(raw >> shift) & 0b1111111111111111111111; // 22 bits masked
			System.out.println("bits22:" + String.format("%22s", Integer.toBinaryString(base)).replace(" ", "0"));
			int sign = (base >> 21);
			int exp = (base >> 13) & 0b1111111;// 7 bits exponent
			if (exp != 0)
				exp += -63+127; // exp - 15 + 127 bias components 
			int mant = base & 0b1111111111111; // 13 bits mantissa
			
			int bits = (sign << 31) | exp << 23 | mant << (23-13);// mantissa shifted to the left of it's bitspace as it is a fraction
			return Float.intBitsToFloat(bits);
			
			//so 6 exp and 15 mant and bias = 118 is nearly about right maybe
			//5 and 16 and bias = 123 nearly as well
			 
		}*/
		
}
