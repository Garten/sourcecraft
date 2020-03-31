package source.addable.addable;

import minecraft.Position;
import source.Material;
import source.addable.Addable;

public class Nothing extends Addable {

	public Nothing() {
		int[] temp = { Material.FERN, Material.GRASS, Material.DANDELION, Material.POPPY, Material.BROWN_MUSHROOM, Material.RED_MUSHROOM,
				Material.REDSTONE_DUST, Material.WHEAT, Material.OAK_DOOR, Material.LADDER, Material.RAIL, Material.OAK_WALL_SIGN, Material.LEVER,
				Material.STONE_PRESSURE_PLATE, Material.IRON_DOOR, Material.OAK_PRESSURE_PLATE, Material.SUGAR_CANE, Material.SUNFLOWER, Material.COBWEB,
				Material.DETECTOR_RAIL, Material.DETECTOR_RAIL, Material.FIRE, Material.REDSTONE_WALL_TORCH, Material.REDSTONE_TORCH, Material.STONE_BUTTON,
				Material.TALL_GRASS, Material.TALL_GRASS, Material.LARGE_FERN, Material.BLUE_ORCHID, Material.ALLIUM, Material.AZURE_BLUET, Material.RED_TULIP,
				Material.ORANGE_TULIP, Material.WHITE_TULIP, Material.PINK_TULIP, Material.OXEYE_DAISY, Material.CORNFLOWER, Material.LILY_OF_THE_VALLEY,
				Material.WITHER_ROSE, Material.LILAC, Material.ROSE_BUSH, Material.PEONY, Material.SUGAR_CANE, Material.SEAGRASS, Material.TALL_SEAGRASS,
				Material.SWEET_BERRY_BUSH };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Position position, int material) {
		// do nothing
	}

}
