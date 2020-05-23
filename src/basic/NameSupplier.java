package basic;

public interface NameSupplier {

	public abstract String name();

	default String getName() {
		String name = this.name();
		if (name.endsWith("$")) {
			return name.substring(0, name.length() - 1);
		}
		return name;
	}
}
