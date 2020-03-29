package vmfWriter.entity.pointEntity;

public abstract class NamedPointEntity extends PointEntity {

	private static int nameCount = 0;

	private String targetName;

	public NamedPointEntity() {
		this.createName();
	}

	private NamedPointEntity createName() {
		NamedPointEntity.nameCount++;
		this.targetName = Integer.toString(NamedPointEntity.nameCount);
		return this;
	}

	public String getTargetName() {
		return this.targetName;
	}

	public void setTargetName(String name) throws PointEntityNameException {
		if (NamedPointEntity.isNumber(name)) {
			throw new PointEntityNameException("To avoid name conflicts PointEntities cannot be named after a number.");
		} else {
			this.targetName = name;
		}
	}

	public NamedPointEntity createNamedPointEntity(NamedPointEntity result) {
		return result.createName();
	}

	private static boolean isNumber(String string) {
		for (char c : string.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}
}
