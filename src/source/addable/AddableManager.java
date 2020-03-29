package source.addable;

import java.util.Arrays;
import java.util.Stack;

import cuboidFinder.ArrayCuboidFinder;
import cuboidFinder.CuboidFinder;
import minecraft.Position;
import minecraft.map.DefaultMinecraftMapConverter;
import periphery.SourceGame;
import source.Material;
import source.addable.addable.Block;
import source.addable.addable.Cactus;
import source.addable.addable.CssLamp;
import source.addable.addable.EmptyAddable;
import source.addable.addable.EndPortalFrame;
import source.addable.addable.Fence;
import source.addable.addable.Fire;
import source.addable.addable.Liquid;
import source.addable.addable.Pane;
import source.addable.addable.PlayerSpawnCss;
import source.addable.addable.Slab;
import source.addable.addable.SnowBlock;
import source.addable.addable.TransparentBlock;
import source.addable.addable.stairs.StairsEast;
import source.addable.addable.stairs.StairsHighEast;
import source.addable.addable.stairs.StairsHighNorth;
import source.addable.addable.stairs.StairsHighSouth;
import source.addable.addable.stairs.StairsHighWest;
import source.addable.addable.stairs.StairsNorth;
import source.addable.addable.stairs.StairsSouth;
import source.addable.addable.stairs.StairsWest;
import source.addable.addable.tf2.LilypadTf2;
import source.addable.addable.tf2.PlayerSpawnTf2;
import source.addable.addable.tf2.SupplyTf2;
import source.addable.addable.tf2.TallGrassTf2;
import source.addable.addable.torch.Torch;
import source.addable.addable.torch.TorchEast;
import source.addable.addable.torch.TorchNorth;
import source.addable.addable.torch.TorchSouth;
import source.addable.addable.torch.TorchWest;
import source.addable.addable.vines.VinesEast;
import source.addable.addable.vines.VinesNorth;
import source.addable.addable.vines.VinesSouth;
import source.addable.addable.vines.VinesWest;

public class AddableManager {

	private static final Addable DEFAULT_ADDABLE = new Block();

	private DefaultMinecraftMapConverter map;
	private CuboidFinder cuboidFinder;
	private Addable[] materialToAddable;

	public AddableManager(DefaultMinecraftMapConverter newMine, SourceGame game, String[] addablesString) {
		this.map = newMine;
		this.cuboidFinder = new ArrayCuboidFinder(newMine);
		this.materialToAddable = new Addable[Material.__LENGTH];

		this.setAddables(this.loadAddables(addablesString));
	}

//	TODO remove Slab High from addable lists in config
	private Stack<Addable> loadAddables(String[] addablesString) {
		Addable[] addablesPool = { new Block(), new Cactus(), new CssLamp(), new Fence(), new Fire(), new Liquid(), new Pane(), new Slab(), new SnowBlock(),
				new StairsEast(), new StairsNorth(), new StairsSouth(), new StairsWest(), new LilypadTf2(), new TallGrassTf2(), new TransparentBlock(),
				new EndPortalFrame(), new VinesEast(), new VinesNorth(), new VinesSouth(), new VinesWest(), new TorchNorth(), new TorchSouth(), new TorchEast(),
				new TorchWest(), new Torch(), new StairsHighEast(), new StairsHighNorth(), new StairsHighSouth(), new StairsHighWest(), new PlayerSpawnCss(),
				new PlayerSpawnTf2(), new SupplyTf2() };
		Stack<Addable> loadedAddables = new Stack<>();
		for (Addable potentialAddable : addablesPool) {
			for (String toBeAdded : addablesString) {
				if (potentialAddable.getName()
						.equals(toBeAdded)) {
					for (Addable a : potentialAddable.getInstances()) {
						// Loggger.log("adding " + a.getName());
						loadedAddables.push(a);
					}
				}
			}
		}
		return loadedAddables;
	}

	private void setAddables(Stack<Addable> addables) {
		Arrays.fill(this.materialToAddable, DEFAULT_ADDABLE);
		for (Addable a : this.materialToAddable) {
			a.setAccess(this.cuboidFinder, this.map, this);
		}
		this.materialToAddable[Material.AIR] = null;
		this.materialToAddable[Material.CAVE_AIR] = null;

		this.setAddable(new EmptyAddable());

		for (Addable addable : addables) {
			this.setAddable(addable);
		}
	}

	private void setAddable(Addable a) {
		a.setAccess(this.cuboidFinder, this.map, this);
		int[] materialsUsed = a.getMaterialUsedFor();
		for (int material : materialsUsed) {
			// add all material that use addable
			this.materialToAddable[material] = a;
		}
	}

	public Addable getAddable(int material) {
		if (material >= 0 && material <= this.materialToAddable.length) {
			return this.materialToAddable[material];
		}
		return null;
	}

	public void add(Position position) {
		int material = this.map.getMaterial(position);
		if (material > 0 && material < this.materialToAddable.length) {
			if (this.materialToAddable[material] != null) {
				this.materialToAddable[material].add(position, material);
			}
		}
	}

	public boolean isAirMaterial(int material) {
		if (material > 0 && material < this.materialToAddable.length) {
			if (this.materialToAddable[material] != null) {
				return this.materialToAddable[material].isAirBlock();
			}
		}
		return true; // unknown/border block
	}

}
