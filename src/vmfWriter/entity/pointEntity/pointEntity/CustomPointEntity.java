package vmfWriter.entity.pointEntity.pointEntity;

import minecraft.Position;
import vmfWriter.entity.pointEntity.PointEntity;

public class CustomPointEntity extends PointEntity {

	private String name;

	public CustomPointEntity(Position origin, String name) {
		super(origin);
		this.name = name;
	}

	@Override
	public CustomPointEntity create(Position origin) {
		return new CustomPointEntity(origin, this.name);
	}

	@Override
	public String getName() {
		return this.name;
	}

}
