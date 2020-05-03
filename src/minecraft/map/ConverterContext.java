package minecraft.map;

import basic.Tuple;
import minecraft.Position;
import minecraft.SubBlockPosition;
import periphery.SourceGame;
import source.Material;
import source.MaterialFilter;
import source.SkinManager;
import source.addable.Addable;
import source.addable.AddableManager;
import vmfWriter.Cuboid;
import vmfWriter.Orientation;
import vmfWriter.Ramp;

public abstract class ConverterContext extends SourceMapper {

	protected AddableManager addableManager;
	private int scale;

	public ConverterContext() {
	}

	public ConverterContext setAddableManager(AddableManager addableManager) {
		this.addableManager = addableManager;
		return this;
	}

	public abstract void addObjects(SourceMap target, SourceGame game);

	public void addSubBlock(Position position, SubBlockPosition pos, int materialInt) {
		throw new UnsupportedOperationException();
	}

	public boolean isAir(Position position) {
		int material = this.getMaterial(position);
		return material == Material.AIR || material == Material.VOID_AIR || material == Material.CAVE_AIR
				|| material == Material._UNSET;
	}

	@Override
	public boolean isAirBlockInitiate(Position position) {
		int material = this.getMaterial(position);
		Addable addable = this.addableManager.getAddable(material);
		if (addable != null) {
			return addable.isAirBlock();
		}
		return true;
	}

	public ConverterContext setScale(int scale) {
		this.scale = scale;
		return this;
	}

	@Override
	public int getScale() {
		return this.scale;
	}

	@Override
	public void movePointInGridDimension(double x, double y, double z) {
		// ugly
		super.target.movePointInGridDimension(x * this.scale, -z * this.scale, y * this.scale);
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
	public Position getMovedPointInGridDimension(Position p, double x, double y, double z) {
		int xDistance = (int) (x * this.scale);
		int yDistance = (int) (-z * this.scale);
		int zDistance = (int) (y * this.scale);
		return p.move(xDistance, yDistance, zDistance);
	}

	@Override
	public void setPointToGrid(Position position) {
		this.target.setPointToGrid(this.convert(position));
	}

	@Override
	public boolean hasOrHadMaterial(Position position, int... materials) {
		for (int material : materials) {
			if (this.getMaterial(position) == material || this.getMaterial(position) == -material) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasOrHadMaterial(Position position, MaterialFilter filter) {
		return filter.filter(this.getMaterial(position));
	}

	@Override
	public Cuboid createCuboid(Position start, Position end, int material) {
		return new Cuboid(this.convertPositions(start, end), SkinManager.INSTANCE.getSkin(material));
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
			int material) {
		double grid = (double) (this.getScale()) / (double) (parts);
		Position startNew = new Position(start.x * this.getScale() + offset.x * grid,
				(-end.z - 1) * this.getScale() + negativeOffset.z * grid, start.y * this.getScale() + offset.y * grid);
		Position endNew = new Position((end.x + 1) * this.getScale() - negativeOffset.x * grid,
				-start.z * this.getScale() - offset.z * grid, (end.y + 1) * this.getScale() - negativeOffset.y * grid);
		return new Cuboid(new Tuple<>(startNew, endNew), SkinManager.INSTANCE.getSkin(material));
	}

	@Override
	public Ramp createRamp(Cuboid cuboid, Orientation orientation) {
		return new Ramp(cuboid, orientation);
	}

	@Override
	public boolean hasMaterial(Position position, int... materials) {
		for (int material : materials) {
			if (this.getMaterial(position) == material) {
				return true;
			}
		}
		return false;
	}
}
