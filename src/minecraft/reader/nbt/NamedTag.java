package minecraft.reader.nbt;

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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + this.tag;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof NamedTag)) {
			return false;
		}
		NamedTag other = (NamedTag) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.tag != other.tag) {
			return false;
		}
		return true;
	}
}
