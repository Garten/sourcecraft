package minecraft.map;

import minecraft.Position;
import vmfWriter.Solid;
import vmfWriter.entity.pointEntity.PointEntity;
import vmfWriter.entity.solidEntity.SolidEntity;

public abstract class MinecraftMapConverter implements MinecraftMap {

	protected ExtendedSourceMap target;

	public MinecraftMapConverter(ExtendedSourceMap target) {
		this.target = target;
	}

	@Override
	public void setSkyTexture(String skyTexture) {
		this.target.setSkyTexture(skyTexture);
	}

	@Override
	public void addSolid(Solid shape) {
		this.target.addSolid(shape);
	}

	@Override
	public void addDetail(Solid... solids) {
		this.target.addDetail(solids);
	}

	@Override
	public PointEntity addPointEntity(PointEntity position) {
		return this.target.addPointEntity(position);
	}

	@Override
	public PointEntity addPointEntity(PointEntity entity, Position position) {
		return this.target.addPointEntity(entity, position);
	}

	@Override
	public void addPointEntity(Position origin, PointEntity type) {
		this.target.addPointEntity(origin, type);
	}

	@Override
	public void addSolidEntity(SolidEntity solidEnttiy) {
		this.target.addSolidEntity(solidEnttiy);
	}

	// @Override
	// public Ramp createRamp(Cuboid cuboid, Orientation orientation) {
	// return new Ramp(cuboid, orientation);
	// }
	// @Override
	// public Ramp createRampCuttet(Cuboid cuboid, Orientation orientation,
	// Orientation cut1, Orientation cut2) {
	// return this.target.createRampCuttet(cuboid, orientation, cut1, cut2);
	// }
	//
	@Override
	public void setCameraPosition(Position origin) {
		this.target.setCameraPosition(origin);
	}

	@Override
	public void setCameraLook(Position position) {
		this.target.setCameraLook(position);
	}

}
