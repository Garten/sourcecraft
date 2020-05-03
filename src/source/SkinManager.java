package source;

import basic.Loggger;
import periphery.TexturePack;
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

	private static final String NODRAW_TEXTURE = "tools/toolsnodraw";
	private static final String PLAYER_CLIP = "tools/toolsplayerclip";
	public static final String DEFAULT_TEXTURE = "dev/dev_measurecrate02";

	private double textureScale;
	private Skin[] skin;

	private String folder;

	public void setSourceSkin(int material, String texture) {
		this.skin[material] = new Skin(texture, this.textureScale);
	}

	public void setSkin(int material, String texture) {
		this.skin[material] = new Skin(this.folder + texture, this.textureScale);
	}

	public void setSkin(int material, String main, String topBottom) {
		this.skin[material] = new Skin(this.folder + main, this.folder + topBottom, this.textureScale);
	}

	private void setSkinTopBottom(int material, String main, String top, String bottom) {
		this.skin[material] = new Skin(this.folder + main, this.folder + top, this.folder + main, this.folder + bottom,
				Orientation.NORTH, this.textureScale);
	}

	public void setSkinTopFront(int material, String main, String top, String front, Orientation orientation) {
		this.skin[material] = new Skin(this.folder + main, this.folder + top, this.folder + front, orientation,
				this.textureScale);
	}

	public void setSkinTopFrontBottom(int material, String main, String top, String front, String bottom) {
		this.skin[material] = new Skin(this.folder + main, this.folder + top, this.folder + front, this.folder + bottom,
				Orientation.NORTH, this.textureScale);
	}

	public void setSkinTopFrontBottom(int material, String main, String top, String front, String bottom,
			Orientation orientation) {
		this.skin[material] = new Skin(this.folder + main, this.folder + top, this.folder + front, this.folder + bottom,
				orientation, this.textureScale);
	}

	public SkinManager(String folder, int textureSizeNew, int scale) {
		this.init(folder, textureSizeNew, scale);
	}

	public void init(String folder, int textureSizeNew, int scale) {
		this.textureScale = ((double) scale) / ((double) textureSizeNew);
		folder = folder + "/";
		this.folder = folder;

		this.skin = new Skin[Material.__LENGTH];
		for (int material = 0; material < Material.__LENGTH; material++) {
			this.setSkin(material, Material.getName(material));
		}

		for (int id = 1; id < Material.__LENGTH_USEFUL; id++) {
			String name = Material.getName(id);
			if (name.startsWith("andesite")) {
				this.setSkin(id, "stone_andesite");
			} else if (name.startsWith("diorite")) {
				this.setSkin(id, "stone_diorite");
			} else if (name.startsWith("granite")) {
				this.setSkin(id, "stone_granite");
			} else if (name.startsWith("sandstone")) {
				if (name.equals("sandstone_wall")) {
					this.setSkin(id, "sandstone_normal");
				} else {
					this.setSkinTopBottom(id, "sandstone_normal", "sandstone_top", "sandstone_normal");
				}
			} else if (name.startsWith("red_sandstone")) {
				if (name.equals("red_sandstone_wall")) {
					this.setSkin(id, "red_sandstone_normal");
				} else {
					this.setSkinTopBottom(id, "red_sandstone_normal", "red_sandstone_top", "red_sandstone_normal");
				}
			} else if (name.startsWith("end_stone_brick")) {
				this.setSkin(id, "end_bricks"); // this time its bricks ;)
			} else if (name.startsWith("mossy_stone_brick")) {
				this.setSkin(id, "stonebrick_mossy");
			} else if (name.startsWith("mossy_cobblestone")) {
				this.setSkin(id, "cobblestone_mossy");
			} else if (name.startsWith("prismarine")) { // order crucial
				this.setSkin(id, "prismarine_rough");
			} else if (name.startsWith("prismarine_brick")) {
				this.setSkin(id, "prismarine_brick");
			} else if (name.startsWith("prismareine_dark")) {
				this.setSkin(id, "prismareine_dark");
			} else if (name.startsWith("nether_brick")) {
				this.setSkin(id, "nether_brick");
			} else if (name.startsWith("red_nether_brick")) {
				this.setSkin(id, "red_nether_brick");
			} else if (name.startsWith("stone_brick")) {
				this.setSkin(id, "stonebrick");
			} else if (name.startsWith("brick")) {
				this.setSkin(id, "brick");
			} else if (name.startsWith("cobblestone")) {
				this.setSkin(id, "cobblestone");
			} else if (name.startsWith("stone")) {
				this.setSkin(id, "stone");
			} else if (name.startsWith("oak")) {
				this.setSkin(id, "planks_oak"); // except oak_log, door
			} else if (name.startsWith("dark_oak")) {
				this.setSkin(id, "planks_big_oak"); // except log, door, ..
			} else if (name.startsWith("spruce")) {
				this.setSkin(id, "planks_spruce");
			} else if (name.startsWith("acacia")) {
				this.setSkin(id, "planks_acacia");
			}

			if (name.endsWith("_log")) { // except dark oak
				String textureName = "log_" + name.substring(0, name.length() - "_log".length());
				this.setSkin(id, textureName, textureName + "_top");
			} else if (name.endsWith("_fence")) {
				String textureName = name.substring(0, name.length() - "_fence".length());
				this.setSkin(id, "planks_" + textureName);
			} else if (name.endsWith("_leaves")) {
				String textureName = "leaves_" + name.substring(0, name.length() - "_leaves".length());
				this.setSkin(id, textureName);
//			} else if (name.endsWith("_slab")) {
//				String textureName = name.substring(0, name.length() - "_slab".length());
//				this.setSkin(id, textureName);
			} else if (name.endsWith("_planks")) {
				String textureName = name.substring(0, name.length() - "_planks".length());
				this.setSkin(id, "planks_" + textureName);
			} else if (name.endsWith("_wool")) {
				String textureName = name.substring(0, name.length() - "_wool".length());
				this.setSkin(id, "wool_colored_" + textureName);
			} else if (name.endsWith("_stained_glass")) {
				String textureName = name.substring(0, name.length() - "_stained_glass".length());
				this.setSkin(id, "glass_" + textureName);
			}
		}
		// exceptions
		this.setSkin(Material.NETHER_BRICK_FENCE, "nether_brick");

		this.setSkin(Material.DARK_OAK_LOG, "log_big_oak", "log_big_oak_top");
		this.setSkin(Material.DARK_OAK_LEAVES, "leaves_big_oak");
		this.setSkin(Material.DARK_OAK_PLANKS, "planks_big_oak");

		//
		// other
		this.setSkinTopBottom(Material.GRASS_BLOCK, "grass_side", "grass_top", "dirt");
		this.setSkinTopBottom(Material.GRASS_PATH, "grass_path_side", "grass_path_top", "dirt");
		this.setSkinTopBottom(Material.PODZOL, "dirt_podzol_side", "dirt_podzol_top", "dirt");
		this.setSkinTopBottom(Material.MYCELIUM, "mycelium_side", "mycelium_top", "dirt");

		this.setSkin(Material.PACKED_ICE, "ice_packed");
		this.setSkin(Material.SNOW_BLOCK, "snow");

		this.setSkin(Material.CHISELED_STONE_BRICKS, "stonebrick_carved");
		this.setSkin(Material.CRACKED_STONE_BRICKS, "stonebrick_cracked");

		this.setSkin(Material.ANDESITE, "stone_andesite");
		this.setSkin(Material.POLISHED_ANDESITE, "stone_andesite_smooth");
		this.setSkin(Material.DIORITE, "stone_diorite");
		this.setSkin(Material.POLISHED_DIORITE, "stone_diorite_smooth");
		this.setSkin(Material.GRANITE, "stone_granite");
		this.setSkin(Material.POLISHED_GRANITE, "stone_granite_smooth");

		this.setSkin(Material.CUT_SANDSTONE, "sandstone_smooth", "sandstone_top");
		this.setSkin(Material.CHISELED_SANDSTONE, "sandstone_carved", "sandstone_top");
		this.setSkin(Material.SMOOTH_SANDSTONE, "sandstone_smooth", "sandstone_top");

		this.setSkin(Material.RED_SANDSTONE, "red_sandstone_normal", "red_sandstone_top");
		this.setSkin(Material.CUT_RED_SANDSTONE, "red_sandstone_smooth", "red_sandstone_top");
		this.setSkin(Material.CHISELED_RED_SANDSTONE, "red_sandstone_carved", "red_sandstone_top");
		this.setSkin(Material.SMOOTH_RED_SANDSTONE, "red_sandstone_smooth", "sandstone_top");

		this.setSkin(Material.PURPUR_PILLAR, "purpur_pillar", "purpur_pillar_top");

		this.setSkin(Material.BONE_BLOCK, "bone_block_side", "bone_block_top");

		this.setSkin(Material.NETHER_QUARTZ_ORE, "quartz_ore");
		this.setSkinTopBottom(Material.QUARTZ_BLOCK, "quartz_block_side", "quartz_block_top", "quartz_block_bottom");
		this.setSkin(Material.CHISELED_QUARTZ_BLOCK, "quartz_block_chiseled", "quartz_block_chiseled_top");
		this.setSkin(Material.QUARTZ_PILLAR, "quartz_block_lines", "quartz_block_lines_top");
		this.setSkin(Material.SMOOTH_QUARTZ, "quartz_block_bottom");

		this.setSkinTopBottom(Material.TNT, "tnt_side", "tnt_top", "tnt_bottom");

		for (int material : new int[] { Material.WATER, Material.SEAGRASS, Material.TALL_SEAGRASS, Material.KELP,
				Material.KELP_PLANT }) {
			this.setWaterTexture(material);
		}

		this.setSkin(Material.LAVA, "lava_still");
		this.setSkin(Material.MAGMA_BLOCK, "magma");

		this.setSkin(Material.SPAWNER, "mob_spawner");
		this.setSkin(Material.MOSSY_COBBLESTONE, "cobblestone_mossy");

		this.setSkin(Material.NOTE_BLOCK, "jukebox_side");
		this.setSkinTopBottom(Material.JUKEBOX, "jukebox_side", "jukebox_top", "jukebox_side");
		this.setSkin(Material.WET_SPONGE, "sponge_wet");

		this.setSkin(Material.MELON, "melon", "melon_top");
		this.setSkinTopFront(Material.CARVED_PUMPKIN, "pumpkin_side", "pumpkin_top", "pumpkin_face_off",
				Orientation.NORTH);
		this.setSkinTopFront(Material.JACK_O_LANTERN, "pumpkin_side", "pumpkin_top", "pumpkin_face_on",
				Orientation.NORTH);
		this.setSkin(Material.PUMPKIN, "pumpkin_side", "pumpkin_top");

		this.setSkinTopFront(Material.DISPENSER, "furnace_side", "furnace_top", "dispenser_front_horizontal",
				Orientation.NORTH);

		this.setSkin(Material.TORCH, "magma"); // temporary fixes
		this.setSkin(Material.WALL_TORCH$NORTH, "magma");
		this.setSkin(Material.WALL_TORCH$EAST, "magma");
		this.setSkin(Material.WALL_TORCH$SOUTH, "magma");
		this.setSkin(Material.WALL_TORCH$WEST, "magma");

		this.setSkin(Material.CACTUS, "cactus_side", "cactus_top");
		this.setSkin(Material.SLIME_BLOCK, "slime");
		this.setSkinTopFront(Material.CRAFTING_TABLE, "crafting_table_side", "crafting_table_top",
				"crafting_table_front", Orientation.NORTH);
		this.setSkinTopFront(Material.FURNACE, "furnace_side", "furnace_top", "furnace_front_on", Orientation.NORTH);

		this.setSkinTopBottom(Material.END_PORTAL_FRAME, "endframe_side", "endframe_top", "end_stone");
		this.setSkin(Material.REDSTONE_LAMP, "lamp_on");

		this.setSkin(Material.SPRUCE_TRAPDOOR, "door_spruce_upper");

		// temporary fixes
		this.setSkin(Material.CAMPFIRE, "magma");
		this.setSkin(Material.LANTERN, "magma");
		this.setSkin(Material.BLAST_FURNACE, "furnace_front_on");

		// special
		this.setSourceSkin(Material._PLAYER_CLIP, PLAYER_CLIP);
		this.setSourceSkin(Material._RAMP_NORTH, PLAYER_CLIP);
		this.setSourceSkin(Material._RAMP_EAST, PLAYER_CLIP);
		this.setSourceSkin(Material._RAMP_SOUTH, PLAYER_CLIP);
		this.setSourceSkin(Material._RAMP_WEST, PLAYER_CLIP);
		this.setSourceSkin(Material._RAMP_NORTH_EAST, PLAYER_CLIP);
		this.setSourceSkin(Material._RAMP_NORTH_WEST, PLAYER_CLIP);
		this.setSourceSkin(Material._RAMP_SOUTH_EAST, PLAYER_CLIP);
		this.setSourceSkin(Material._RAMP_SOUTH_WEST, PLAYER_CLIP);
		this.setSourceSkin(Material._UNKOWN, DEFAULT_TEXTURE);
	}

	private void setWaterTexture(int material) {
		this.skin[material] = new Skin(NODRAW_TEXTURE, this.folder + "water_still", NODRAW_TEXTURE, NODRAW_TEXTURE,
				this.textureScale);
	}

	public Skin getSkin(int material) {
		if (0 <= material && material < this.skin.length) {
			return this.skin[material];
		} else {
			Loggger.log("unkown material " + material);
			return this.skin[Material._UNKOWN];
		}
	}
}
