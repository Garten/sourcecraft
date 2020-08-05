package nbtReader;

import java.io.IOException;

public class BitNbtReader {

	static final int LONG_SIZE = 64;

	private NbtReader source;
	private long amount;
	private long currentLong;
	private long currentPos = LONG_SIZE;

	public BitNbtReader(NbtReader source, int amount) {
		this.source = source;
		this.amount = amount;
	}

	public int readBits() throws IOException {
		long result = 0;
		long resultPos = 0;
		if (this.currentPos + this.amount - 1 >= LONG_SIZE) {
			this.currentPos = 0;
			this.currentLong = this.source.readLong();
		}
		for (int i = 0; i < this.amount; i++) {
			long bit = (this.currentLong >> this.currentPos) & 1L;
			long mask = (bit << resultPos);
			result = result | mask;
			resultPos++;
			this.currentPos++;
		}
		return (int) result;
	}

	public int readBitsBeforeVersion16() throws IOException {
		long result = 0;
		long resultPos = 0;
		for (int i = 0; i < this.amount; i++) {
			if (this.currentPos == LONG_SIZE) {
				this.currentPos = 0;
				this.currentLong = this.source.readLong();
			}
			long bit = (this.currentLong >> this.currentPos) & 1L;
			long mask = (bit << resultPos);
			result = result | mask;
			resultPos++;
			this.currentPos++;
		}
		return (int) result;
	}
}