package minecraft.reader.nbt;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

import basic.IOConsumer;
import basic.Loggger;
import basic.RunnableThrowing;

public abstract class NbtReader {

	public static final int SHORT_LENGTH = 2;
	public static final int INT_LENGTH = 4;
	public static final int LONG_LENGTH = 8;

	private DataInputStream stream;

	public NbtReader(DataInputStream stream) {
		this.stream = stream;
	}

	public int readTag() throws IOException {
		return this.stream.readUnsignedByte();
	}

	public int readByte() throws IOException {
		return this.stream.readUnsignedByte();
	}

	public int readInt() throws IOException {
		return this.stream.readInt();
	}

	public double readDouble() throws IOException {
		return this.stream.readDouble();
	}

	public void readForLength(int length) throws IOException {
		for (int i = 0; i < length; i++) {
			this.stream.readByte();
		}
	}

	/**
	 * Reads tags without saving. Returns false if a TAG_END occurs.
	 *
	 * @return false if TAG_END occurred.
	 * @throws IOException
	 */
	public boolean skipTag() throws IOException {
		int tag = this.stream.readUnsignedByte();
		return this.skipTag(tag);
	}

	/**
	 * Reads tags without saving. Returns false if a TAG_END occurs
	 */
	public boolean skipTag(int tag) throws IOException {
		if (tag == NbtTag.END) {
			return false;
		}
		this.readTitle();
		this.skipTagAfterTitle(tag);
		return true;
	}

	/**
	 * Reads tags in a TagList meaning skipping header
	 */
	public boolean skipTagInTagList(int tag) throws IOException {
		if (tag == NbtTag.END) {
			Loggger.log("File corrupted: unexpected END_TAG in TagList");
			return false;
		}
		// tags are nameless
		this.skipTagAfterTitle(tag);
		return true;
	}

	public void skipTagAfterTitle(int tag) throws IOException {
		if (tag == NbtTag.BYTE) {
			this.readByte();
		} else if (tag == NbtTag.SHORT) {
			this.readForLength(SHORT_LENGTH);
		} else if (tag == NbtTag.INT || tag == NbtTag.FLOAT) {
			this.readForLength(INT_LENGTH);
		} else if (tag == NbtTag.LONG || tag == NbtTag.DOUBLE) {
			this.readForLength(LONG_LENGTH);
		} else if (tag == NbtTag.BYTE_ARRAY) {
			this.skipByteArray();
		} else if (tag == NbtTag.STRING) {
			this.skipString();
		} else if (tag == NbtTag.LIST) {
			this.skipList();
		} else if (tag == NbtTag.COMPOUND) {
			this.skipCompound();
		} else if (tag == NbtTag.INT_ARRAY) {
			this.skipIntArray();
		} else if (tag == NbtTag.LONG_ARRAY) {
			this.skipLongArray();
		} else {
			Loggger.warn("File corrupted: Unkown tag occured " + tag + ".");
			throw new IOException("File corrupted: Unkown tag occured " + tag + ".");
		}
	}

	public void skipByteArray() throws IOException {
		int length = this.readLength();
		this.readForLength(length);
	}

	public void skipLongArray() throws IOException {
		int length = this.stream.readInt();
		for (int i = 0; i < length; i++) {
			this.stream.readLong();
		}
	}

	public void skipIntArray() throws IOException {
		int length = this.stream.readInt();
		for (int i = 0; i < length; i++) {
			this.stream.readInt();
		}
	}

	public int readLength() throws IOException {
		return this.stream.readInt();
	}

	public void skipCompound() throws IOException {
		while (this.skipTag() == true) {
			// repeat
		}
	}

	/**
	 * Inside compound, ready to read first tag
	 *
	 * @param onNamedTags
	 * @throws IOException
	 */
	public void doCompound(Map<NamedTag, RunnableThrowing> onNamedTags) throws IOException {
		int tag = this.stream.readUnsignedByte();
		while (tag != NbtTag.END) {
			String title = this.readTitle();
			RunnableThrowing action = onNamedTags.get(new NamedTag(tag, title));
			if (action == null) {
				this.skipTagAfterTitle(tag);
			} else {
				action.run();
			}
			tag = this.readTag();
		}
	}

	public void doCompound(NbtTasks onNamedTags) throws IOException {
		int tag = this.stream.readUnsignedByte();
		while (tag != NbtTag.END) {
			String title = this.readTitle();
			RunnableThrowing action = onNamedTags.get(new NamedTag(tag, title));
			if (action == null) {
				this.skipTagAfterTitle(tag);
			} else {
				action.run();
			}
			tag = this.readTag();
		}
	}

	public void doCompond(IOConsumer<String> action) throws IOException {
		int tag = this.stream.readUnsignedByte(); // readTag
		while (tag != NbtTag.END) {
			String title = this.readTitle();
			action.run(title);
			tag = this.readTag();
		}
	}

	public void doListOfCompounds(RunnableThrowing action) throws IOException {
		this.doListOfCompounds(i -> action.run());
	}

	public void doListOfCompounds(IOConsumer<Integer> action) throws IOException {
		int tagId = this.readTag();
		if (tagId == NbtTag.COMPOUND) {
			int listLength = this.readLength();
			for (int i = 0; i < listLength; i++) {
				action.run(Integer.valueOf(i));
			}
		} else {
			Loggger.warn("File corrupted: Unexpected Tag.");
		}
	}

	public void skipList() throws IOException {
		int listTag = this.readTag();
		this.skipListOf(listTag);
	}

	public void skipListOf(int listTag) throws IOException {
		int listLength = this.readLength();
		if (listTag != NbtTag.END) {
			for (int i = 0; i < listLength; i++) {
				this.skipTagInTagList(listTag);
			}
		}
	}

	public void skipString() throws IOException {
		int length = this.stream.readUnsignedShort();
		this.readForLength(length);
	}

	public String readString() throws IOException {
		int length = this.stream.readUnsignedShort();
		String title = "";
		for (int i = 0; i < length; i++) {
			Byte b = this.stream.readByte();
			char c = (char) (b & 0xFF);
			title = title + c;
		}
		return title;
	}

	public String readTitle() throws IOException {
		return this.readString();
	}

	public long readLong() throws IOException {
		return this.stream.readLong();
	}

}
