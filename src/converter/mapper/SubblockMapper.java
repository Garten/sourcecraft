package converter.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import basic.Value;
import converter.actions.Action;
import converter.actions.ActionManager;
import converter.actions.actions.DetailBlock;
import minecraft.Block;
import minecraft.Blocks;
import minecraft.Position;
import periphery.SourceGame;
import vmfWriter.Cuboid;
import vmfWriter.Free8Point;
import vmfWriter.Orientation;
import vmfWriter.Ramp;
import vmfWriter.SourceMap;

public class SubblockMapper extends Mapper {

	private static final Position STEP_NORTH = new Position(0, 0, 1);
	private static final Position STEP_SOUTH = new Position(0, 0, -1);
	private static final Position STEP_WEST = new Position(1, 0, 0);
	private static final Position STEP_EAST = new Position(-1, 0, 0);

	private Set<Position> isConverted = new HashSet<>();
	private SortedMap<Position, Value<Block>> subBlocks;

	private class RampAddableZ extends Action {
		private Orientation orientation;
		private Block mStart;
		private Block mEnd;

		public RampAddableZ(Orientation orientation, Block mStart, Block mEnd) {
			this.orientation = orientation;
			this.mStart = mStart;
			this.mEnd = mEnd;
		}

		@Override
		public void add(Mapper context, Position start, Block material) {
			Position end = context.getCuboidFinder()
					.getBestZ(start, material);
			Cuboid cuboid = context.createCuboid(start, end, material);
			if (context.hasBlock(Position.add(end, STEP_NORTH), this.mEnd)) {
				cuboid.extend(this.orientation, Orientation.NORTH);
			}
			if (context.hasBlock(Position.add(start, STEP_SOUTH), this.mStart)) {
				cuboid.extend(this.orientation, Orientation.SOUTH);
			}
			Ramp ramp = new Ramp(cuboid, this.orientation);
			context.addSolid(ramp);
			context.markAsConverted(start, end);
		}

	}

	private class RampAddableX extends Action {
		private Orientation orientation;
		private Block mStart;
		private Block mEnd;

		public RampAddableX(Orientation orientation, Block mStart, Block mEnd) {
			this.orientation = orientation;
			this.mStart = mStart;
			this.mEnd = mEnd;
		}

		@Override
		public void add(Mapper mapper, Position start, Block material) {
			Position end = mapper.getCuboidFinder()
					.getBestX(start, material);
			Cuboid cuboid = mapper.createCuboid(start, end, material);
			if (mapper.hasBlock(Position.add(end, STEP_WEST), this.mEnd)) {
				cuboid.extend(this.orientation, Orientation.WEST);
			}
			if (mapper.hasBlock(Position.add(start, STEP_EAST), this.mStart)) {
				cuboid.extend(this.orientation, Orientation.EAST);
			}
			Ramp ramp = new Ramp(cuboid, this.orientation);
			mapper.addSolid(ramp);
			mapper.markAsConverted(start, end);
		}
	}

	public SubblockMapper(SourceMap target, int originalScale) {
		ActionManager addableManager = new ActionManager(new DetailBlock())
				.setAction(Blocks._RAMP_EAST,
						new RampAddableZ(Orientation.EAST, Blocks._RAMP_SOUTH_EAST, Blocks._RAMP_NORTH_EAST))
				.setAction(Blocks._RAMP_WEST,
						new RampAddableZ(Orientation.WEST, Blocks._RAMP_SOUTH_WEST, Blocks._RAMP_NORTH_WEST))
				.setAction(Blocks._RAMP_NORTH,
						new RampAddableX(Orientation.NORTH, Blocks._RAMP_NORTH_EAST, Blocks._RAMP_NORTH_WEST))
				.setAction(Blocks._RAMP_SOUTH,
						new RampAddableX(Orientation.SOUTH, Blocks._RAMP_SOUTH_EAST, Blocks._RAMP_SOUTH_WEST))
//				.setAddable(Arrays.asList(Material._RAMP_NORTH_EAST, Material._RAMP_NORTH_WEST,
//						Material._RAMP_SOUTH_EAST, Material._RAMP_SOUTH_WEST), new Debug())
		;
		this.setAddableManager(addableManager);
		this.setTarget(target);
		this.subBlocks = new TreeMap<>();
		this.setScale(originalScale / 2);
	}

	@Override
	public void addObjects(SourceGame game) {
		this.subBlocks.forEach((position, block) -> {
			if (this.needsConversion(position)) {
				this.convertActions.add(this, position, block.get());
			}
		});
	}

	@Override
	public Block getBlock(Position position) {
		Value<Block> value = this.subBlocks.get(position);
		if (value == null) {
			return Blocks._UNSET;
		}
		return value.get();
	}

	@Override
	public void markAsConverted(Position position) {
		this.isConverted.add(position);
	}

	@Override
	public void markAsConverted(Position start, Position end) {
		for (int xMark = start.getX(); xMark <= end.getX(); xMark++) {
			for (int yMark = start.getY(); yMark <= end.getY(); yMark++) {
				for (int zMark = start.getZ(); zMark <= end.getZ(); zMark++) {
					this.markAsConverted(new Position(xMark, yMark, zMark));
				}
			}
		}
	}

	@Override
	public void setBlock(Position position, Block material) {
		this.subBlocks.put(position, new Value<Block>().set(material));
	}

	@Override
	public Free8Point createFree8Point(Position start, Position end, int parts, Position[] offset, boolean align,
			Block material) {
		// not supported
		return null;
	}

	@Override
	public boolean needsConversion(Position position) {
		return !this.isConverted.contains(position);
	}
}
