package converter.mapper;

import basic.Tuple;
import converter.ConvertingReport;
import converter.Skins;
import converter.actions.ActionManager;
import converter.cuboidFinder.CuboidFinder;
import converter.cuboidFinder.DefaultCuboidFinder;
import minecraft.Block;
import minecraft.Position;
import minecraft.SubBlockPosition;
import periphery.SourceGame;
import vmfWriter.Cuboid;
import vmfWriter.entity.pointEntity.PointEntity;

public abstract class Mapper extends SourceMapper {

	private CuboidFinder cuboidFinder = new DefaultCuboidFinder(this);
	protected ActionManager convertActions;
	private int scale;

	public Mapper() {
	}

	public CuboidFinder getCuboidFinder() {
		return this.cuboidFinder;
	}

	public Mapper setAddableManager(ActionManager addableManager) {
		this.convertActions = addableManager;
		return this;
	}

	@Override
	public ConvertingReport prepareWrite(SourceGame game) {
		this.addObjects(game);
		return null;
	}

	public abstract void addObjects(SourceGame game);

	public void addSubBlock(Position position, SubBlockPosition pos, Block materialInt) {
		throw new UnsupportedOperationException();
	}

	public Block getSubBlock(Position position, SubBlockPosition subpos) {
		throw new UnsupportedOperationException();
	}

	public Mapper setScale(int scale) {
		this.scale = scale;
		return this;
	}

	public int getScale() {
		return this.scale;
	}

	@Override
	public void movePointInGridDimension(double x, double y, double z) {
		// ugly
		this.target.movePointInGridDimension(x * this.scale, -z * this.scale, y * this.scale);
	}

	@Override
	public void movePointExactly(Position offset) {
		Position translated = new Position(offset.x, -offset.z, offset.y);
		this.target.movePointExactly(translated);
	}

	protected Position convert(Position p) {
		// z and y axis are switched
		// NORTH-SOUTH axis is flipped
		return new Position(p.getX() * this.scale, -p.getZ() * this.scale, p.getY() * this.scale);
	}

	@Override
	public void setPointToGrid(Position position) {
		this.target.setPointToGrid(this.convert(position));
	}

	@Override
	public Cuboid createCuboid(Position start, Position end, Block material) {
		return new Cuboid(this.convertPositions(start, end), Skins.INSTANCE.getSkin(material));
	}

	public Tuple<Position, Position> convertPositions(Position start, Position endIn) {
		Position end = endIn.getOffset(1, 1, 1);
		Position startConverted = this.convert(start);
		Position endConverted = this.convert(end);
		Position.bringInOrder(startConverted, endConverted);

		return new Tuple<>(startConverted, endConverted);
	}

	@Override
	public Cuboid createCuboid(Position start, Position end, int parts, Position offset, Position negativeOffset,
			Block material) {
		double grid = (double) (this.getScale()) / (double) (parts);
		Position startNew = new Position(start.x * this.getScale() + offset.x * grid,
				(-end.z - 1) * this.getScale() + negativeOffset.z * grid,
				start.y * this.getScale() + offset.y * grid);
		Position endNew = new Position((end.x + 1) * this.getScale() - negativeOffset.x * grid,
				-start.z * this.getScale() - offset.z * grid,
				(end.y + 1) * this.getScale() - negativeOffset.y * grid);
		return new Cuboid(new Tuple<>(startNew, endNew), Skins.INSTANCE.getSkin(material));
	}

	public ActionManager getActions() {
		return this.convertActions;
	}

	public boolean isAirBlock(Position position) {
		return this.getActions()
				.getAction(this.getBlock(position))
				.isAirBlock();
	}

	public boolean needsConversion(Position position) {
		return true; // overwrite me
	}

	public abstract void markAsConverted(Position position);

	public abstract void markAsConverted(Position position, Position end);

	@Override
	public void addPointEntitys(Position start, Position end, int space, PointEntity type) {
		Tuple<Position, Position> conveted = this.convertPositions(start, end);
		this.addPointEntitys(conveted.getFirst(), conveted.getSecond(), space, type);
	}

}
