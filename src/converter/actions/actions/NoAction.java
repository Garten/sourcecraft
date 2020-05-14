package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.MaterialLegacy;
import minecraft.Position;

public class NoAction extends Action {

	public static final NoAction INSTANCE = new NoAction();

	public NoAction() {
		int[] temp = { MaterialLegacy.FERN, MaterialLegacy.GRASS, MaterialLegacy.DANDELION, MaterialLegacy.POPPY,
				MaterialLegacy.BROWN_MUSHROOM, MaterialLegacy.RED_MUSHROOM, MaterialLegacy.REDSTONE_DUST,
				MaterialLegacy.WHEAT, MaterialLegacy.OAK_DOOR, MaterialLegacy.LADDER, MaterialLegacy.RAIL,
				MaterialLegacy.OAK_WALL_SIGN, MaterialLegacy.LEVER, MaterialLegacy.STONE_PRESSURE_PLATE,
				MaterialLegacy.IRON_DOOR, MaterialLegacy.OAK_PRESSURE_PLATE, MaterialLegacy.SUGAR_CANE,
				MaterialLegacy.SUNFLOWER, MaterialLegacy.COBWEB, MaterialLegacy.DETECTOR_RAIL,
				MaterialLegacy.DETECTOR_RAIL, MaterialLegacy.FIRE, MaterialLegacy.REDSTONE_WALL_TORCH,
				MaterialLegacy.REDSTONE_TORCH, MaterialLegacy.STONE_BUTTON, MaterialLegacy.TALL_GRASS,
				MaterialLegacy.TALL_GRASS, MaterialLegacy.LARGE_FERN, MaterialLegacy.BLUE_ORCHID, MaterialLegacy.ALLIUM,
				MaterialLegacy.AZURE_BLUET, MaterialLegacy.RED_TULIP, MaterialLegacy.ORANGE_TULIP,
				MaterialLegacy.WHITE_TULIP, MaterialLegacy.PINK_TULIP, MaterialLegacy.OXEYE_DAISY,
				MaterialLegacy.CORNFLOWER, MaterialLegacy.LILY_OF_THE_VALLEY, MaterialLegacy.WITHER_ROSE,
				MaterialLegacy.LILAC, MaterialLegacy.ROSE_BUSH, MaterialLegacy.PEONY, MaterialLegacy.SUGAR_CANE,
				MaterialLegacy.SEAGRASS, MaterialLegacy.TALL_SEAGRASS, MaterialLegacy.SWEET_BERRY_BUSH };
		super.setMaterialUsedFor(temp);
	}

	@Override
	public void add(Mapper context, Position position, Block material) {
		context.markAsConverted(position);
//		Loggger.log("no action");
		// do nothing
	}

}
