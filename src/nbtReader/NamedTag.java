package nbtReader;

import java.util.Objects;

public class NamedTag {

	private int tag;
	private String name;

	public NamedTag(int tag, String name) {
		this.tag = tag;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getTag() {
		return this.tag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, tag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof NamedTag)) {
			return false;
		}
		NamedTag other = (NamedTag) obj;
		return Objects.equals(name, other.name) && tag == other.tag;
	}
}
