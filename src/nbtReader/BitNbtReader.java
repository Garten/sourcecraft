package nbtReader;

import java.io.IOException;

public class BitNbtReader {

	private static final int LONG_END = 64;

	private NbtReader source;
	private long amount;
	private long currentByte;
	private long currentPos = LONG_END;

	public BitNbtReader(NbtReader source, int amount) {
		this.source = source;
		this.amount = amount;
	}

	public int readBits() throws IOException {
		long result = 0;
		long resultPos = 0;
		for (int i = 0; i < this.amount; i++) {
			if (this.currentPos == LONG_END) {
				this.currentPos = 0;
				this.currentByte = this.source.readLong();
			}
			long bit = (this.currentByte >> this.currentPos) & 1L;
			long mask = (bit << resultPos);
			result = result | mask;
			resultPos++;
			this.currentPos++;
		}
		return (int) result;
	}
}