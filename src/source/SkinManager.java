package source;

import java.util.function.Supplier;

import basic.Loggger;
import minecraft.Block;
import minecraft.Blocks;
import minecraft.Material;
import minecraft.Texture;
import periphery.TexturePack;
import source.addable.BlockMap;
import vmfWriter.Orientation;
import vmfWriter.Skin;

public class SkinManager {

	public static SkinManager INSTANCE;

	public static final void init(TexturePack texturePack, int optionScale) {
		String textureFolder = texturePack.getName();
		int textureSize = texturePack.getTextureSize();
		INSTANCE = new SkinManager(textureFolder, textureSize, optionScale);
	}

	public static final Skin NODRAW = new Skin("tools/toolsnodraw", 0.25);
	public static final Skin TRIGGER = new Skin("tools/toolstrigger", 0.25);
	public static final Skin SKYBOX = new Skin("tools/toolsskybox", 0.25);
	private static final Skin PLAYER_CLIP = new Skin("tools/toolsplayerclip", 0.25);

	private static final String NODRAW_TEXTURE = "tools/toolsnodraw";
	public static final String DEFAULT_TEXTURE = "dev/dev_measurecrate02";

	private double textureScale;
	private Skin[] skinLegacy;

	private String folder;

	public Skin setSourceSkin(String texture) {
		return new Skin(texture, this.textureScale);
	}

	public Skin createSkin(String texture) {
		return new Skin(this.folder + texture, this.textureScale);
	}

	public Skin createSkin(String main, String topBottom) {
		return new Skin(this.folder + main, this.folder + topBottom, this.textureScale);
	}

	private Skin createSkinTopBottom(String main, String top, String bottom) {
		return new Skin(this.folder + main, this.folder + top, this.folder + main, this.folder + bottom,
				Orientation.NORTH, this.textureScale);
	}

	public Skin createSkinTopFront(String main, String top, String front, Orientation orientation) {
		return new Skin(this.folder + main, this.folder + top, this.folder + front, orientation, this.textureScale);
	}

	public Skin createSkinTopFrontBottom(String main, String top, String front, String bottom) {
		return new Skin(this.folder + main, this.folder + top, this.folder + front, this.folder + bottom,
				Orientation.NORTH, this.textureScale);
	}

	public Skin createSkinTopFrontBottom(String main, String top, String front, String bottom,
			Orientation orientation) {
		return new Skin(this.folder + main, this.folder + top, this.folder + front, this.folder + bottom, orientation,
				this.textureScale);
	}

	//

	public void setSourceSkinOld(int material, String texture) {
		this.skinLegacy[material] = new Skin(texture, this.textureScale);
	}

	public void setSkinOld(int material, String texture) {
		this.skinLegacy[material] = new Skin(this.folder + texture, this.textureScale);
	}

	public void setSkinOld(int material, String main, String topBottom) {
		this.skinLegacy[material] = new Skin(this.folder + main, this.folder + topBottom, this.textureScale);
	}

	private void setSkinTopBottomOld(int material, String main, String top, String bottom) {
		this.skinLegacy[material] = new Skin(this.folder + main, this.folder + top, this.folder + main,
				this.folder + bottom, Orientation.NORTH, this.textureScale);
	}

	public void setSkinTopFrontOld(int material, String main, String top, String front, Orientation orientation) {
		this.skinLegacy[material] = new Skin(this.folder + main, this.folder + top, this.folder + front, orientation,
				this.textureScale);
	}

	public void setSkinTopFrontBottomOld(int material, String main, String top, String front, String bottom) {
		this.skinLegacy[material] = new Skin(this.folder + main, this.folder + top, this.folder + front,
				this.folder + bottom, Orientation.NORTH, this.textureScale);
	}

	public void setSkinTopFrontBottomOld(int material, String main, String top, String front, String bottom,
			Orientation orientation) {
		this.skinLegacy[material] = new Skin(this.folder + main, this.folder + top, this.folder + front,
				this.folder + bottom, orientation, this.textureScale);
	}

	public SkinManager(String folder, int textureSizeNew, int scale) {
//		this.initLegacy(folder, textureSizeNew, scale);
		this.init(folder, textureSizeNew, scale);
	}

	private BlockMap<Skin> skins;

	/**
	 * Return whethe
	 *
	 * @param suffix
	 * @return
	 */
	private boolean putPrefixMadeSuffix(Material material, Material suffix) {
		String name = material.name();
		String _suf = suffix.name();
		if (name.endsWith(_suf)) { // except dark oak
			String suf_ = _suf.substring(1) + "_";
			String textureName = suf_ + name.substring(0, name.length() - suf_.length());
			this.put(material, textureName);
			return true;
		}
		return false;
	}

	public void init(String folder, int textureSizeNew, int scale) {
		this.textureScale = ((double) scale) / ((double) textureSizeNew);
		this.folder = folder + "/";
		this.skins = new BlockMap<Skin>().setDefault(new Skin(DEFAULT_TEXTURE, this.textureScale));
		for (Texture texture : Texture.values()) {
			this.put(texture);
			String name = texture.name();
		}
		for (Material material : Material.values()) {
			String name = material.name();
			if (name.endsWith("_log")) { // except dark oak
				String textureName = "log_" + name.substring(0, name.length() - "_log".length()) + "_top";
				this.put(material, textureName);
			} else if (this.putPrefixMadeSuffix(material, Material._leaves)) {

			} else if (this.putPrefixMadeSuffix(material, Material._planks)) {

			} else if (this.putPrefixMadeSuffix(material, Material._wool)) {

			} else if (this.putPrefixMadeSuffix(material, Material._stained_glass)) {

			}
		}
		this.put(Material.andesite, Texture.stone_andesite);
		this.put(Material.diorite, Texture.stone_diorite);
		this.put(Material.granite, Texture.stone_granite);
		this.skins.put(Material.sandstone,
				this.createSkinTopBottom("sandstone_normal", "sandstone_top", "sandstone_normal"));
		this.put(Material.sandstone, Texture.sandstone_normal);
		this.skins.put(Material.red_sandstone,
				this.createSkinTopBottom("red_sandstone_normal", "red_sandstone_top", "red_sandstone_normal"));
		this.put(Material.red_sandstone_wall, Texture.red_sandstone_normal);
		this.put(Material.end_stone_brick_, Texture.end_bricks);
		this.put(Material.mossy_stone_brick_, Texture.stonebrick_mossy);
		this.put(Material.mossy_cobblestone, Texture.cobblestone_mossy);
		this.put(Material.prismarine, Texture.prismarine_rough);
		this.put(Material.prismarine_brick_, Texture.prismarine_bricks);
		this.put(Material.nether_brick_, Texture.nether_brick);
		this.put(Material.red_nether_brick_, Texture.red_nether_brick);
		this.put(Material.stone_bricks, Texture.stonebrick);
		this.put(Material.stone_brick_, Texture.stonebrick);
		this.put(Material.oak_, Texture.planks_oak); // except oak_log, door
		this.put(Material.dark_oak_, Texture.planks_big_oak); // except log, door, ..
		this.put(Material.spruce_, Texture.planks_spruce);
		this.put(Material.acacia_, Texture.planks_acacia);
		// exceptsion wood

		// special
		this.skins.put(Blocks.get("sourcecraft:ramp"), PLAYER_CLIP);
//		this.setSourceSkinOld(MaterialLegacy._PLAYER_CLIP, PLAYER_CLIP);
//		this.setSourceSkinOld(MaterialLegacy._RAMP_NORTH, PLAYER_CLIP);
//		this.setSourceSkinOld(MaterialLegacy._RAMP_EAST, PLAYER_CLIP);
//		this.setSourceSkinOld(MaterialLegacy._RAMP_SOUTH, PLAYER_CLIP);
//		this.setSourceSkinOld(MaterialLegacy._RAMP_WEST, PLAYER_CLIP);
//		this.setSourceSkinOld(MaterialLegacy._RAMP_NORTH_EAST, PLAYER_CLIP);
//		this.setSourceSkinOld(MaterialLegacy._RAMP_NORTH_WEST, PLAYER_CLIP);
//		this.setSourceSkinOld(MaterialLegacy._RAMP_SOUTH_EAST, PLAYER_CLIP);
//		this.setSourceSkinOld(MaterialLegacy._RAMP_SOUTH_WEST, PLAYER_CLIP);
//		this.setSourceSkinOld(MaterialLegacy._UNKOWN, DEFAULT_TEXTURE);
	}

	private void put(Texture block) {
		this.put(block, block);
	}

	private void put(Supplier<Block> block, Texture texture) {
		this.put(block, texture.name());
	}

	private void put(Supplier<Block> block, String name) {
		this.skins.put(block, this.createSkin(name));
	}

	public void initLegacy(String folder, int textureSizeNew, int scale) {
		this.textureScale = ((double) scale) / ((double) textureSizeNew);
		folder = folder + "/";
		this.folder = folder;

		this.skinLegacy = new Skin[MaterialLegacy.__LENGTH];
		for (int material = 0; material < MaterialLegacy.__LENGTH; material++) {
			this.setSkinOld(material, MaterialLegacy.getName(material));
		}

		for (int id = 1; id < MaterialLegacy.__LENGTH_USEFUL; id++) {
			String name = MaterialLegacy.getName(id);

			if (name.endsWith("_log")) { // except dark oak
				String textureName = "log_" + name.substring(0, name.length() - "_log".length());
				this.setSkinOld(id, textureName, textureName + "_top");
			} else if (name.endsWith("_fence")) {
				String textureName = name.substring(0, name.length() - "_fence".length());
				this.setSkinOld(id, "planks_" + textureName);
			} else if (name.endsWith("_leaves")) {
				String textureName = "leaves_" + name.substring(0, name.length() - "_leaves".length());
				this.setSkinOld(id, textureName);
			} else if (name.endsWith("_planks")) {
				String textureName = name.substring(0, name.length() - "_planks".length());
				this.setSkinOld(id, "planks_" + textureName);
			} else if (name.endsWith("_wool")) {
				String textureName = name.substring(0, name.length() - "_wool".length());
				this.setSkinOld(id, "wool_colored_" + textureName);
			} else if (name.endsWith("_stained_glass")) {
				String textureName = name.substring(0, name.length() - "_stained_glass".length());
				this.setSkinOld(id, "glass_" + textureName);
			}
		}
		// exceptions
		this.setSkinOld(MaterialLegacy.NETHER_BRICK_FENCE, "nether_brick");

		this.setSkinOld(MaterialLegacy.DARK_OAK_LOG, "log_big_oak", "log_big_oak_top");
		this.setSkinOld(MaterialLegacy.DARK_OAK_LEAVES, "leaves_big_oak");
		this.setSkinOld(MaterialLegacy.DARK_OAK_PLANKS, "planks_big_oak");

		//
		// other
		this.setSkinTopBottomOld(MaterialLegacy.GRASS_BLOCK, "grass_side", "grass_top", "dirt");
		this.setSkinTopBottomOld(MaterialLegacy.GRASS_PATH, "grass_path_side", "grass_path_top", "dirt");
		this.setSkinTopBottomOld(MaterialLegacy.PODZOL, "dirt_podzol_side", "dirt_podzol_top", "dirt");
		this.setSkinTopBottomOld(MaterialLegacy.MYCELIUM, "mycelium_side", "mycelium_top", "dirt");

		this.setSkinOld(MaterialLegacy.PACKED_ICE, "ice_packed");
		this.setSkinOld(MaterialLegacy.SNOW_BLOCK, "snow");

		this.setSkinOld(MaterialLegacy.CHISELED_STONE_BRICKS, "stonebrick_carved");
		this.setSkinOld(MaterialLegacy.CRACKED_STONE_BRICKS, "stonebrick_cracked");

		this.setSkinOld(MaterialLegacy.ANDESITE, "stone_andesite");
		this.setSkinOld(MaterialLegacy.POLISHED_ANDESITE, "stone_andesite_smooth");
		this.setSkinOld(MaterialLegacy.DIORITE, "stone_diorite");
		this.setSkinOld(MaterialLegacy.POLISHED_DIORITE, "stone_diorite_smooth");
		this.setSkinOld(MaterialLegacy.GRANITE, "stone_granite");
		this.setSkinOld(MaterialLegacy.POLISHED_GRANITE, "stone_granite_smooth");

		this.setSkinOld(MaterialLegacy.CUT_SANDSTONE, "sandstone_smooth", "sandstone_top");
		this.setSkinOld(MaterialLegacy.CHISELED_SANDSTONE, "sandstone_carved", "sandstone_top");
		this.setSkinOld(MaterialLegacy.SMOOTH_SANDSTONE, "sandstone_smooth", "sandstone_top");

		this.setSkinOld(MaterialLegacy.RED_SANDSTONE, "red_sandstone_normal", "red_sandstone_top");
		this.setSkinOld(MaterialLegacy.CUT_RED_SANDSTONE, "red_sandstone_smooth", "red_sandstone_top");
		this.setSkinOld(MaterialLegacy.CHISELED_RED_SANDSTONE, "red_sandstone_carved", "red_sandstone_top");
		this.setSkinOld(MaterialLegacy.SMOOTH_RED_SANDSTONE, "red_sandstone_smooth", "sandstone_top");

		this.setSkinOld(MaterialLegacy.PURPUR_PILLAR, "purpur_pillar", "purpur_pillar_top");

		this.setSkinOld(MaterialLegacy.BONE_BLOCK, "bone_block_side", "bone_block_top");

		this.setSkinOld(MaterialLegacy.NETHER_QUARTZ_ORE, "quartz_ore");
		this.setSkinTopBottomOld(MaterialLegacy.QUARTZ_BLOCK, "quartz_block_side", "quartz_block_top",
				"quartz_block_bottom");
		this.setSkinOld(MaterialLegacy.CHISELED_QUARTZ_BLOCK, "quartz_block_chiseled", "quartz_block_chiseled_top");
		this.setSkinOld(MaterialLegacy.QUARTZ_PILLAR, "quartz_block_lines", "quartz_block_lines_top");
		this.setSkinOld(MaterialLegacy.SMOOTH_QUARTZ, "quartz_block_bottom");

		this.setSkinTopBottomOld(MaterialLegacy.TNT, "tnt_side", "tnt_top", "tnt_bottom");

		for (int material : new int[] { MaterialLegacy.WATER, MaterialLegacy.SEAGRASS, MaterialLegacy.TALL_SEAGRASS,
				MaterialLegacy.KELP, MaterialLegacy.KELP_PLANT }) {
			this.setWaterTexture(material);
		}

		this.setSkinOld(MaterialLegacy.LAVA, "lava_still");
		this.setSkinOld(MaterialLegacy.MAGMA_BLOCK, "magma");

		this.setSkinOld(MaterialLegacy.SPAWNER, "mob_spawner");
		this.setSkinOld(MaterialLegacy.MOSSY_COBBLESTONE, "cobblestone_mossy");

		this.setSkinOld(MaterialLegacy.NOTE_BLOCK, "jukebox_side");
		this.setSkinTopBottomOld(MaterialLegacy.JUKEBOX, "jukebox_side", "jukebox_top", "jukebox_side");
		this.setSkinOld(MaterialLegacy.WET_SPONGE, "sponge_wet");

		this.setSkinOld(MaterialLegacy.MELON, "melon", "melon_top");
		this.setSkinTopFrontOld(MaterialLegacy.CARVED_PUMPKIN, "pumpkin_side", "pumpkin_top", "pumpkin_face_off",
				Orientation.NORTH);
		this.setSkinTopFrontOld(MaterialLegacy.JACK_O_LANTERN, "pumpkin_side", "pumpkin_top", "pumpkin_face_on",
				Orientation.NORTH);
		this.setSkinOld(MaterialLegacy.PUMPKIN, "pumpkin_side", "pumpkin_top");

		this.setSkinTopFrontOld(MaterialLegacy.DISPENSER, "furnace_side", "furnace_top", "dispenser_front_horizontal",
				Orientation.NORTH);

		this.setSkinOld(MaterialLegacy.TORCH, "magma"); // temporary fixes
		this.setSkinOld(MaterialLegacy.WALL_TORCH$NORTH, "magma");
		this.setSkinOld(MaterialLegacy.WALL_TORCH$EAST, "magma");
		this.setSkinOld(MaterialLegacy.WALL_TORCH$SOUTH, "magma");
		this.setSkinOld(MaterialLegacy.WALL_TORCH$WEST, "magma");

		this.setSkinOld(MaterialLegacy.CACTUS, "cactus_side", "cactus_top");
		this.setSkinOld(MaterialLegacy.SLIME_BLOCK, "slime");
		this.setSkinTopFrontOld(MaterialLegacy.CRAFTING_TABLE, "crafting_table_side", "crafting_table_top",
				"crafting_table_front", Orientation.NORTH);
		this.setSkinTopFrontOld(MaterialLegacy.FURNACE, "furnace_side", "furnace_top", "furnace_front_on",
				Orientation.NORTH);

		this.setSkinTopBottomOld(MaterialLegacy.END_PORTAL_FRAME, "endframe_side", "endframe_top", "end_stone");
		this.setSkinOld(MaterialLegacy.REDSTONE_LAMP, "lamp_on");

		this.setSkinOld(MaterialLegacy.SPRUCE_TRAPDOOR, "door_spruce_upper");

		// temporary fixes
		this.setSkinOld(MaterialLegacy.CAMPFIRE, "magma");
		this.setSkinOld(MaterialLegacy.LANTERN, "magma");
		this.setSkinOld(MaterialLegacy.BLAST_FURNACE, "furnace_front_on");

	}

	private void setWaterTexture(int material) {
		this.skinLegacy[material] = new Skin(NODRAW_TEXTURE, this.folder + "water_still", NODRAW_TEXTURE,
				NODRAW_TEXTURE, this.textureScale);
	}

	// temp
	public Skin getSkin(Block block) {
		return this.skins.getFallBackToPrefix(block);
//		return this.getSkin(MaterialLegacy.get(block));
	}

	public Skin getSkin(int material) {
		if (0 <= material && material < this.skinLegacy.length) {
			return this.skinLegacy[material];
		} else {
			Loggger.log("unkown material " + material);
			return this.skinLegacy[MaterialLegacy._UNKOWN];
		}
	}
}
