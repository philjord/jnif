package nif.niobject.hkx.reader;

/**
 * Classify the HKX types into "families" These families usually have an impact
 * on data reading and writing.
 */
public enum HKXTypeFamily {
	UNKNOWN, DIRECT, COMPLEX, ENUM, ARRAY, POINTER, STRING, OBJECT
}
