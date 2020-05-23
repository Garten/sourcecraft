package minecraft;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class Block implements Supplier<Block>, Predicate<Block> {

	public abstract String getName();

	public abstract Map<String, String> getProperties();

	public String getProperty(Property property) {
		return this.getProperties()
				.get(property.name());
	}

	@Override
	public Block get() {
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getName(), this.getProperties());
	}

	@Override
	public boolean test(Block containee) {
		return this.equals(containee);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Block)) {
			return false;
		}
		Block other = (Block) obj;
		boolean result = Objects.equals(this.getName(), other.getName())
				&& Objects.equals(this.getProperties(), other.getProperties());
		return result;
	}

	@Override
	public String toString() {
		Map<String, String> properties = this.getProperties();
		if (properties == null) {
			return this.getName();
		}
		return this.getName() + properties.toString();
	}
}
