package source.addable;

import java.util.Arrays;
import java.util.Stack;

import basic.Loggger;
import cuboidFinder.CuboidFinder;
import cuboidFinder.DefaultCuboidFinder;
import minecraft.Position;
import minecraft.map.ConverterContext;
import source.Material;
import source.addable.addable.Block;
import source.addable.addable.Nothing;

public class AddableManager {

	private final Addable DEFAULT_ADDABLE = new Block();
	private static final Addable NOTHING = new Nothing();

	private ConverterContext map;
	private CuboidFinder cuboidFinder;
	private Addable[] materialToAddable;

	public AddableManager() {
		this.materialToAddable = new Addable[Material.__LENGTH];
		this.setDefaultAddable(this.DEFAULT_ADDABLE);
		this.materialToAddable[Material.AIR] = NOTHING;
		this.materialToAddable[Material.CAVE_AIR] = NOTHING;

		this.setAddable(new Nothing());
	}

	public AddableManager setDefaultAddable(Addable addable) {
		Arrays.fill(this.materialToAddable, addable);
		return this;
	}

	public AddableManager setConverterContext(ConverterContext map) {
		this.map = map;
		this.cuboidFinder = new DefaultCuboidFinder(this.map);
		return this;
	}

	public AddableManager setAddable(int material, Addable addable) {
		this.materialToAddable[material] = addable;
		return this;
	}

	public AddableManager setAddable(Iterable<Integer> materials, Addable addable) {
		materials.forEach(m -> this.materialToAddable[m] = addable);
		return this;
	}

	public AddableManager setAddable(Addable addable) {
		int[] materialsUsed = addable.getMaterialUsedFor();
		for (int material : materialsUsed) {
			this.materialToAddable[material] = addable;
		}
		return this;
	}

	public AddableManager setAddables(Stack<Addable> addables) {
		for (Addable addable : addables) {
			this.setAddable(addable);
		}
		return this;
	}

	public void finishSetup() {
		for (Addable addable : this.materialToAddable) {
			addable.setAccess(this.cuboidFinder, this.map, this);
		}
	}

	public Addable getAddable(int material) {
		if (material >= 0 && material <= this.materialToAddable.length) {
			return this.materialToAddable[material];
		}
		return null;
	}

	public void add(Position position, int material) {
		if (material == Material.WALL_TORCH$EAST || material == Material.WALL_TORCH$NORTH) {
			Loggger.log("break");
		}
		if (0 < material && material < this.materialToAddable.length) {
			if (this.materialToAddable[material] != null) {
				this.materialToAddable[material].add(position, material);
			}
		}
	}

}
