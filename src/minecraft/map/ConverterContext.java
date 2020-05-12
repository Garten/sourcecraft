package minecraft.map;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableSet;

import basic.Tuple;
import cuboidFinder.CuboidFinder;
import cuboidFinder.DefaultCuboidFinder;
import minecraft.Block;
import minecraft.ConvertingReport;
import minecraft.Material;
import minecraft.Position;
import minecraft.SubBlockPosition;
import periphery.SourceGame;
import source.SkinManager;
import source.addable.ConvertAction;
import source.addable.ConvertActions;
import vmfWriter.Cuboid;

public abstract class ConverterContext extends SourceMapper {

	private CuboidFinder cuboidFinder = new DefaultCuboidFinder(this);
	protected ConvertActions convertActions;
	private int scale;

	public ConverterContext() {
	}

	public CuboidFinder getCuboidFinder() {
		return this.cuboidFinder;
	}

	public ConverterContext setAddableManager(ConvertActions addableManager) {
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

	public boolean isAirLegacy(Position position) {
		Block block = this.getMaterial(position);
		return ImmutableSet.of(Material.air.getName(), Material.void_air.getName(), Material.cave_air.getName())
				.contains(block.getName());
	}

	@Override
	public boolean isAir(Position position) {
		Block block = this.getMaterial(position);
		ConvertAction action = this.convertActions.getAction(block);
		if (action != null) {
			return action.isAirBlock();
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

	public boolean hasOrHadMaterial(Position position, Block block) {
		return this.hasOrHadMaterial(position, found -> found.equals(block));
	}

	public boolean hasOrHadMaterial(Position position, Collection<Block> container) {
		return this.hasOrHadMaterial(position, block -> container.contains(block));
	}

	@Override
	public boolean hasOrHadMaterial(Position position, Predicate<Block> container) {
		return container.test(this.getMaterial(position));
	}

	public boolean hasMaterial(Position position, Block block) {
		return this.hasMaterial(position, Arrays.asList(block));
	}

	public boolean hasMaterial(Position position, Collection<Block> blocks) {
		return (!this.isConverted(position)) && this.hasOrHadMaterial(position, blocks);
	}

	@Override
	public boolean hasMaterial(Position position, Predicate<Block> container) {
		return (!this.isConverted(position)) && this.hasOrHadMaterial(position, container);
	}

	@Override
	public Cuboid createCuboid(Position start, Position end, Block material) {
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
			Block material) {
		double grid = (double) (this.getScale()) / (double) (parts);
		Position startNew = new Position(start.x * this.getScale() + offset.x * grid,
				(-end.z - 1) * this.getScale() + negativeOffset.z * grid, start.y * this.getScale() + offset.y * grid);
		Position endNew = new Position((end.x + 1) * this.getScale() - negativeOffset.x * grid,
				-start.z * this.getScale() - offset.z * grid, (end.y + 1) * this.getScale() - negativeOffset.y * grid);
		return new Cuboid(new Tuple<>(startNew, endNew), SkinManager.INSTANCE.getSkin(material));
	}

	protected abstract boolean isConverted(Position position);

	public ConvertActions getActions() {
		return this.convertActions;
	}
}
