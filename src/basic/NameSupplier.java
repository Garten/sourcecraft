package basic;

import java.util.function.Supplier;

public interface NameSupplier extends Supplier<String> {

	public abstract String name();

	@Override
	default String get() {
		String name = this.name();
		if (name.endsWith("$")) {
			name.substring(0, name.length() - 1);
		}
		return name;
	}
}
