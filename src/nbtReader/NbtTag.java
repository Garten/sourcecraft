package nbtReader;

public class NbtTag {

	public static final int END = 0;
	public static final int BYTE = 1;
	public static final int SHORT = 2; // two bytes
	public static final int INT = 3;
	public static final int LONG = 4;
	public static final int FLOAT = 5;
	public static final int DOUBLE = 6;
	public static final int BYTE_ARRAY = 7;
	public static final int STRING = 8;
	public static final int LIST = 9;
	public static final int COMPOUND = 10; // for whole list
	public static final int INT_ARRAY = 11;
	public static final int LONG_ARRAY = 12;

	public static String get(int tag) {
		switch (tag) {
		case 0:
			return "END";
		case 1:
			return "BYTE";
		case 2:
			return "SHORT";
		case 3:
			return "INT";
		case 4:
			return "LONG";
		case 5:
			return "FLOAT";
		case 6:
			return "DOUBLE";
		case 7:
			return "BYTE_ARRAY";
		case 8:
			return "STRING";
		case 9:
			return "LIST";
		case 10:
			return "COMPOUND";
		case 11:
			return "INT_ARRAY";
		case 12:
			return "LONG_ARRAY";
		default:
			return "UNKOWN_TAG";
		}
	}
}
