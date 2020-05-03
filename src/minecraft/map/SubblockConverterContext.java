package minecraft.map;

import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import basic.Value;
import minecraft.Position;
import periphery.SourceGame;
import source.Material;
import source.addable.Addable;
import source.addable.AddableManager;
import source.addable.addable.Debug;
import source.addable.addable.DetailBlock;
import vmfWriter.Cuboid;
import vmfWriter.Free8Point;
import vmfWriter.Orientation;
import vmfWriter.Ramp;
import vmfWriter.entity.pointEntity.PointEntity;

public class SubblockConverterContext extends ConverterContext {

	private static final List<Integer> RAMPS_EAST = Arrays.asList(Material._RAMP_EAST, Material._RAMP_NORTH_EAST,
			Material._RAMP_SOUTH_EAST);

	private static final Position STEP_NORTH = new Position(0, 0, 1);
	private static final Position STEP_SOUTH = new Position(0, 0, -1);
	private static final Position STEP_WEST = new Position(1, 0, 0);
	private static final Position STEP_EAST = new Position(-1, 0, 0);

	private SortedMap<Position, Value<Integer>> subBlocks;

	private class RampAddableZ extends Addable {
		Orientation orientation;
		int mStart;
		int mEnd;

		public RampAddableZ(Orientation orientation, int mStart, int mEnd) {
			this.orientation = orientation;
			this.mStart = mStart;
			this.mEnd = mEnd;
		}

		@Override
		public void add(Position start, int material) {
			Position end = this.cuboidFinder.getBestZ(start, material);
			Cuboid cuboid = this.map.createCuboid(start, end, material);
			if (this.map.hasOrHadMaterial(Position.add(end, STEP_NORTH), this.mEnd)) {
				cuboid.extend(this.orientation, Orientation.NORTH);
			}
			if (this.map.hasOrHadMaterial(Position.add(start, STEP_SOUTH), this.mStart)) {
				cuboid.extend(this.orientation, Orientation.SOUTH);
			}
			Ramp ramp = new Ramp(cuboid, this.orientation);
			this.map.addSolid(ramp);
			this.map.markAsConverted(start, end);
		}

	}

	private class RampAddableX extends Addable {
		Orientation orientation;
		int mStart;
		int mEnd;

		public RampAddableX(Orientation orientation, int mStart, int mEnd) {
			this.orientation = orientation;
			this.mStart = mStart;
			this.mEnd = mEnd;
		}

		@Override
		public void add(Position start, int material) {
			Position end = this.cuboidFinder.getBestX(start, material);
			Cuboid cuboid = this.map.createCuboid(start, end, material);
			if (this.map.hasOrHadMaterial(Position.add(end, STEP_WEST), this.mEnd)) {
				cuboid.extend(this.orientation, Orientation.WEST);
			}
			if (this.map.hasOrHadMaterial(Position.add(start, STEP_EAST), this.mStart)) {
				cuboid.extend(this.orientation, Orientation.EAST);
			}
			Ramp ramp = new Ramp(cuboid, this.orientation);
			this.map.addSolid(ramp);
			this.map.markAsConverted(start, end);
		}

	}

	public SubblockConverterContext(ExtendedSourceMap target, int originalScale) {
		AddableManager addableManager = new AddableManager().setConverterContext(this)
				.setDefaultAddable(new DetailBlock())
				.setAddable(Material._RAMP_EAST,
						new RampAddableZ(Orientation.EAST, Material._RAMP_SOUTH_EAST, Material._RAMP_NORTH_EAST))
				.setAddable(Material._RAMP_WEST,
						new RampAddableZ(Orientation.WEST, Material._RAMP_SOUTH_WEST, Material._RAMP_NORTH_WEST))
				.setAddable(Material._RAMP_NORTH,
						new RampAddableX(Orientation.NORTH, Material._RAMP_NORTH_EAST, Material._RAMP_NORTH_WEST))
				.setAddable(Material._RAMP_SOUTH,
						new RampAddableX(Orientation.SOUTH, Material._RAMP_SOUTH_EAST, Material._RAMP_SOUTH_WEST))
				.setAddable(Arrays.asList(Material._RAMP_NORTH_EAST, Material._RAMP_NORTH_WEST,
						Material._RAMP_SOUTH_EAST, Material._RAMP_SOUTH_WEST), new Debug());
		addableManager.finishSetup();
		this.setAddableManager(addableManager);
		this.setTarget(target);
		this.subBlocks = new TreeMap<>();
		this.setScale(originalScale / 2);
	}

	@Override
	public void addObjects(SourceMap target, SourceGame game) {
		this.subBlocks.forEach((position, materialInteger) -> {
			this.addableManager.add(position, materialInteger.get()
					.intValue());
		});
	}

	@Override
	public int getMaterial(Position position) {
		Value<Integer> value = this.subBlocks.get(position);
		if (value == null) {
			return 0;
		}
		Integer presentMaterial = value.get();
		if (presentMaterial == null) {
			return 0;
		}
		return presentMaterial.intValue();
	}

	@Override
	public boolean isAirBlock(Position position) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAirBlockInitiate(Position position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNextToAir(Position position) {
		return true;
	}

	@Override
	public void markAsConverted(Position position) {
		int material = this.subBlocks.get(position)
				.get();
		this.subBlocks.get(position)
				.set(-material);
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
	public void setMaterial(Position position, int material) {
		this.subBlocks.put(position, new Value<Integer>().set(material));
	}

	@Override
	public Free8Point createFree8Point(Position start, Position end, int parts, Position[] offset, boolean align,
			int material) {
		// not supported
		return null;
	}

	@Override
	public Ramp createRampCuttet(Cuboid cuboid, Orientation orientation, Orientation cut1, Orientation cut2) {
		// not supported
		return null;
	}

	@Override
	public void addPointEntitys(Position position, Position end, int space, PointEntity type) {
		// not supported
	}

}
