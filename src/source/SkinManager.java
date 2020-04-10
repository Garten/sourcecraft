package source;

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
	private static final String DEFAULT_TEXTURE = "dev/dev_measuregeneric01b";

	double textureScale;
	private Skin[] skins;
	TextureType[] materialTextureType;
	double[] materialTextureScale;
	String[] materialTexture;
	String[] materialTextureTop; // additional if type = 1 or 2 or 3
	String[] materialTextureFront; // additional if type = 2 or 3
	String[] materialTextureBottom; // additional if type = 3
	Orientation[] materialOrientation;

	private String folder;

	public void setSkin(int material, String texture) {
		this.materialTexture[material] = this.folder + texture;
	}

	public void setSkin(int material, String main, String topBottom) {
		this.materialTextureType[material] = TextureType.TOPBOTTOM_EXTRA;
		this.materialTexture[material] = this.folder + main;
		this.materialTextureTop[material] = this.folder + topBottom;
	}

	private void setSkinTopBottom(int material, String main, String top, String bottom) {
		this.materialTextureType[material] = TextureType.TOP_FRONT_BOTTOM_EXTRA;
		this.materialTexture[material] = this.folder + main;
		this.materialTextureTop[material] = this.folder + top;
		this.materialTextureFront[material] = this.folder + main;
		this.materialTextureBottom[material] = this.folder + bottom;
	}

	public void setSkinTopFront(int material, String main, String top, String front, Orientation orientation) {
		this.materialTextureType[material] = TextureType.TOP_FRONT_EXTRA;
		this.materialTexture[material] = this.folder + main;
		this.materialTextureTop[material] = this.folder + top;
		this.materialTextureFront[material] = this.folder + front;
		this.materialOrientation[material] = orientation;
	}

	public void setSkinTopFrontBottom(int material, String main, String top, String front, String bottom) {
		this.materialTextureType[material] = TextureType.TOP_FRONT_BOTTOM_EXTRA;
		this.materialTexture[material] = this.folder + main;
		this.materialTextureTop[material] = this.folder + top;
		this.materialTextureFront[material] = this.folder + front;
		this.materialTextureBottom[material] = this.folder + bottom;
	}

	public void setSkinTopFrontBottom(int material, String main, String top, String front, String bottom, Orientation orientation) {
		this.materialTextureType[material] = TextureType.TOP_FRONT_BOTTOM_EXTRA;
		this.materialTexture[material] = this.folder + main;
		this.materialTextureTop[material] = this.folder + top;
		this.materialTextureFront[material] = this.folder + front;
		this.materialTextureBottom[material] = this.folder + bottom;
		this.materialOrientation[material] = orientation;
	}

	public SkinManager(String folder, int textureSizeNew, int scale) {
		this.init(folder, textureSizeNew, scale);
	}

	public void init(String folder, int textureSizeNew, int scale) {
		this.textureScale = ((double) scale) / ((double) textureSizeNew);
		folder = folder + "/";
		this.folder = folder;

		this.skins = new Skin[Material.__LENGTH];

		this.materialTextureType = new TextureType[Material.__LENGTH];
		this.materialTextureScale = new double[Material.__LENGTH];
		this.materialTexture = new String[Material.__LENGTH];
		this.materialTextureTop = new String[Material.__LENGTH];
		this.materialTextureFront = new String[Material.__LENGTH];
		this.materialTextureBottom = new String[Material.__LENGTH];
		this.materialOrientation = new Orientation[Material.__LENGTH];
		for (int i = 0; i < Material.__LENGTH; i++) {
			this.materialTextureType[i] = TextureType.SINGLE;
			this.materialTextureScale[i] = this.textureScale;
			this.materialTexture[i] = folder + Material.getName(i);

			this.materialTextureTop[i] = DEFAULT_TEXTURE;
			this.materialTextureFront[i] = DEFAULT_TEXTURE;
			this.materialTextureBottom[i] = DEFAULT_TEXTURE;
			this.materialOrientation[i] = Orientation.NORTH;
		}

		this.setSkinTopBottom(Material.GRASS_BLOCK, "grass_side", "grass_top", "dirt");
		this.setSkinTopBottom(Material.GRASS_PATH, "grass_path_side", "grass_path_top", "dirt");
		this.setSkinTopBottom(Material.PODZOL, "dirt_podzol_side", "dirt_podzol_top", "dirt");
		this.setSkinTopBottom(Material.MYCELIUM, "mycelium_side", "mycelium_top", "dirt");

		this.materialTexture[Material.DIRT] = folder + "dirt";

		for (int id = 1; id < Material.__LENGTH_USEFUL; id++) {
			String name = Material.getName(id);
			if (name.endsWith("_log")) { // except dark oak
				String textureName = "log_" + name.substring(0, name.length() - "_log".length());
				this.setSkin(id, textureName, textureName + "_top");
			} else if (name.endsWith("_fence")) {
				String textureName = name.substring(0, name.length() - "_fence".length());
				this.setSkin(id, "planks_" + textureName);
			} else if (name.endsWith("_leaves")) {
				String textureName = "leaves_" + name.substring(0, name.length() - 7);
				this.setSkin(id, textureName);
			} else if (name.endsWith("_slab")) {
				String textureName = name.substring(0, name.length() - 5);
				this.setSkin(id, textureName);
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

		// other
		this.setSkin(Material.PACKED_ICE, "ice_packed");
		this.setSkin(Material.SNOW_BLOCK, "snow");

		this.setSkin(Material.BRICKS, "brick");
		this.setSkin(Material.STONE_BRICKS, "stonebrick");
		this.setSkin(Material.CHISELED_STONE_BRICKS, "stonebrick_carved");
		this.setSkin(Material.CRACKED_STONE_BRICKS, "stonebrick_cracked");
		this.setSkin(Material.MOSSY_STONE_BRICKS, "stonebrick_mossy");
		this.setSkin(Material.MOSSY_STONE_BRICK_SLAB, "stonebrick_mossy");

		this.setSkin(Material.NETHER_BRICKS, "nether_brick");
		this.setSkin(Material.END_STONE_BRICKS, "end_bricks"); // this time its bricks ;)

		this.setSkin(Material.ANDESITE, "stone_andesite");
		this.setSkin(Material.POLISHED_ANDESITE, "stone_andesite_smooth");
		this.setSkin(Material.DIORITE, "stone_diorite");
		this.setSkin(Material.POLISHED_DIORITE, "stone_diorite_smooth");
		this.setSkin(Material.GRANITE, "stone_granite");
		this.setSkin(Material.POLISHED_GRANITE, "stone_granite_smooth");

		this.setSkin(Material.SANDSTONE, "sandstone_normal", "sandstone_top");
		this.setSkin(Material.CUT_SANDSTONE, "sandstone_smooth", "sandstone_top");
		this.setSkin(Material.CHISELED_SANDSTONE, "sandstone_carved", "sandstone_top");
		this.setSkin(Material.SMOOTH_SANDSTONE, "sandstone_smooth", "sandstone_top");

		this.setSkin(Material.RED_SANDSTONE, "red_sandstone_normal", "red_sandstone_top");
		this.setSkin(Material.CUT_RED_SANDSTONE, "red_sandstone_smooth", "red_sandstone_top");
		this.setSkin(Material.CHISELED_RED_SANDSTONE, "red_sandstone_carved", "red_sandstone_top");
		this.setSkin(Material.SMOOTH_RED_SANDSTONE, "red_sandstone_smooth", "sandstone_top");

		this.setSkin(Material.PRISMARINE, "prismarine_rough");
		this.setSkin(Material.PRISMARINE_SLAB, "prismarine_rough");
		this.setSkin(Material.DARK_PRISMARINE, "prismarine_dark");
		this.setSkin(Material.DARK_PRISMARINE_SLAB, "prismareine_dark");

		this.setSkin(Material.PURPUR_PILLAR, "purpur_pillar", "purpur_pillar_top");

		this.setSkin(Material.BONE_BLOCK, "bone_block_side", "bone_block_top");

		this.setSkin(Material.NETHER_QUARTZ_ORE, "quartz_ore");
		this.setSkinTopBottom(Material.QUARTZ_BLOCK, "quartz_block_side", "quartz_block_top", "quartz_block_bottom");
		this.setSkin(Material.CHISELED_QUARTZ_BLOCK, "quartz_block_chiseled", "quartz_block_chiseled_top");
		this.setSkin(Material.QUARTZ_PILLAR, "quartz_block_lines", "quartz_block_lines_top");
		this.setSkin(Material.SMOOTH_QUARTZ, "quartz_block_bottom");

		this.setSkinTopBottom(Material.TNT, "tnt_side", "tnt_top", "tnt_bottom");

		for (int material : new int[] { Material.WATER, Material.SEAGRASS, Material.TALL_SEAGRASS, Material.KELP, Material.KELP_PLANT }) {
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
		this.setSkinTopFront(Material.CARVED_PUMPKIN, "pumpkin_side", "pumpkin_top", "pumpkin_face_off", Orientation.NORTH);
		this.setSkinTopFront(Material.JACK_O_LANTERN, "pumpkin_side", "pumpkin_top", "pumpkin_face_on", Orientation.NORTH);
		this.setSkin(Material.PUMPKIN, "pumpkin_side", "pumpkin_top");
		this.skins[Material.PUMPKIN] = new Skin(folder + "pumpkin side", folder + "pumpkin top", folder + "pumpkin front", folder + "pumpkin bottom",
				Orientation.SOUTH, this.textureScale);

		this.setSkinTopFront(Material.DISPENSER, "furnace_side", "furnace_top", "dispenser_front_horizontal", Orientation.NORTH);

		this.materialTextureType[Material.STONE_SLAB] = TextureType.TOPBOTTOM_EXTRA; // double stone slab
		this.materialTextureTop[Material.STONE_SLAB] = folder + "stone slab top";

		this.materialTextureType[Material.TORCH] = TextureType.TOP_FRONT_BOTTOM_EXTRA; // torch
		this.materialTexture[Material.TORCH] = folder + "torch";
		this.materialTextureFront[Material.TORCH] = folder + "torch";
		this.materialTextureTop[Material.TORCH] = folder + "torch fit top";
		this.materialTextureBottom[Material.TORCH] = folder + "torch fit bottom";

		this.materialTextureType[Material.CHEST_NORTH] = TextureType.TOP_FRONT_EXTRA;
		this.materialTexture[Material.CHEST_NORTH] = folder + "chest side";
		this.materialTextureFront[Material.CHEST_NORTH] = folder + "chest front";
		this.materialTextureTop[Material.CHEST_NORTH] = folder + "chest top";

		this.setSkin(Material.CACTUS, "cactus_side", "cactus_top");
		this.setSkin(Material.SLIME_BLOCK, "slime");
		this.setSkinTopFront(Material.CRAFTING_TABLE, "crafting_table_side", "crafting_table_top", "crafting_table_front", Orientation.NORTH);
		this.setSkinTopFront(Material.FURNACE, "furnace_side", "furnace_top", "furnace_front_on", Orientation.NORTH);

		this.materialTextureType[Material.END_PORTAL_FRAME] = TextureType.TOP_FRONT_BOTTOM_EXTRA;
		this.materialTexture[Material.END_PORTAL_FRAME] = folder + "end portal frame side";
		this.materialTextureTop[Material.END_PORTAL_FRAME] = folder + "end portal frame top";
		this.materialTextureFront[Material.END_PORTAL_FRAME] = folder + "end portal frame side";
		this.materialTextureBottom[Material.END_PORTAL_FRAME] = folder + "end stone";

		this.materialTexture[Material.REDSTONE_LAMP] = folder + "lamp_off";

		this.materialTexture[Material._PLAYER_CLIP] = "tools/toolsplayerclip"; // player clip

		// red nether brick is colored?
	}

	private void setWaterTexture(int material) {
		this.materialTextureType[material] = TextureType.TOP_FRONT_BOTTOM_EXTRA;
		this.materialTextureTop[material] = folder + "water_still";
		this.materialTexture[material] = NODRAW_TEXTURE;
		this.materialTextureFront[material] = NODRAW_TEXTURE;
		this.materialTextureBottom[material] = NODRAW_TEXTURE;
		this.materialTextureType[material] = TextureType.TOP_FRONT_BOTTOM_EXTRA;
	}

	public Skin getSkin(int m) {
		if (this.skins[m] != null) {
			return this.skins[m];
		} else {
			String texture = this.materialTexture[m];
			String textureTop = this.materialTextureTop[m];
			TextureType type = this.materialTextureType[m];
			double s = this.materialTextureScale[m];
			Orientation o = this.materialOrientation[m];
			if (type == TextureType.SINGLE) {
				return new Skin(texture, s);
			} else if (type == TextureType.TOPBOTTOM_EXTRA) {
				return new Skin(texture, textureTop, s);
			} else if (type == TextureType.TOP_FRONT_EXTRA) {
				return new Skin(texture, textureTop, this.materialTextureFront[m], o, s);
			} else {
				return new Skin(texture, textureTop, this.materialTextureFront[m], this.materialTextureBottom[m], o, s);
			}
		}
	}

//	public void removeData(DefaultMinecraftMapConverter map) {
//		map.forAllPositions(position -> removeData(map, position));
//	}
//
//	/**
//	 * Removes data and gives data relevant blocks different materials. Also makes
//	 * grass under snow or a snow block to a snowy grass
//	 *
//	 * @param map
//	 * @param position
//	 */
//	private void removeData(DefaultMinecraftMapConverter map, Position position) {
//		int data = map.getData(position);
//		switch (map.getMaterial(new Position(position))) {
//		case Material.GRASS_BLOCK:
//			Position above = new Position(position.x, position.y + 1, position.z);
//			if (map.hasMaterial(above, Material.SNOW) || map.hasMaterial(above, Material.SNOW_BLOCK)) { // above
//				map.setMaterial(position, Material.SNOW_GRASS_BLOCK);
//				// snow grass block
//			}
//		case Material.OAK_LOG:
//			if (map.getData(position) == 1) { // redwood wood
//				map.setMaterial(position, Material.SPRUCE_LOG);
//			} else if (map.getData(position) == 2) { // pine wood
//				map.setMaterial(position, Material.BIRCH_LOG);
//			} else if (map.getData(position) == 3) { // jungle wood
//				map.setMaterial(position, Material.JUNGLE_LOG);
//			}
//			break;
//		case Material.OAK_LEAVES:
//			if (map.getData(position) == 1 || map.getData(position) == 5 || map.getData(position) == 9 || map.getData(position) == 13) {
//				map.setMaterial(position, 259); // redwood leaves
//			} else if (map.getData(position) == 2 || map.getData(position) == 6 || map.getData(position) == 10 || map.getData(position) == 14) {
//				map.setMaterial(position, 260); // pine leaves
//			} else if (map.getData(position) == 3 || map.getData(position) == 7 || map.getData(position) == 11 || map.getData(position) == 15) {
//				map.setMaterial(position, Material.JUNGLE_LEAVES); // jungle
//				// leaves
//			}
//			break;
//		case Material.WOOL: // wool
//			if (map.getData(position) != 0) {
//				map.setMaterial(position, 260 + map.getData(position));
//			} else if (map.getData(position) == 2) {
//				map.setMaterial(position, 262);
//			} else if (map.getData(position) == 3) {
//				map.setMaterial(position, 263);
//			} else if (map.getData(position) == 4) {
//				map.setMaterial(position, 264);
//			} else if (map.getData(position) == 5) {
//				map.setMaterial(position, 265);
//			} else if (map.getData(position) == 6) {
//				map.setMaterial(position, 266);
//			} else if (map.getData(position) == 7) {
//				map.setMaterial(position, 267);
//			} else if (map.getData(position) == 8) {
//				map.setMaterial(position, 268);
//			} else if (map.getData(position) == 9) {
//				map.setMaterial(position, 269);
//			} else if (map.getData(position) == 10) {
//				map.setMaterial(position, 270);
//			} else if (map.getData(position) == 11) {
//				map.setMaterial(position, 271);
//			} else if (map.getData(position) == 12) {
//				map.setMaterial(position, 272);
//			} else if (map.getData(position) == 13) {
//				map.setMaterial(position, 273);
//			} else if (map.getData(position) == 14) {
//				map.setMaterial(position, 274);
//			} else if (map.getData(position) == 15) {
//				map.setMaterial(position, 275);
//			}
//			break;
//		case Material.FARMLAND:
//			if (map.getData(position) != 0) { // dry farmland
//				map.setMaterial(position, Material.DRY_FARMLAND);
//			}
//			break;
//		case Material.STONE_BRICKS:
//			if (map.getData(position) == 1) { // mossy stone brick
//				map.setMaterial(position, Material.MOSSY_STONE_BRICKS);
//			} else if (map.getData(position) == 2) { // cracked stone brick
//				map.setMaterial(position, Material.CRACKED_STONE_BRICKS);
//			} else if (map.getData(position) == 3) { // circular stone brick
//				map.setMaterial(position, Material.CHISELED_STONE_BRICKS);
//			}
//			break;
//		case Material.STONE_SLAB:
//			if (data != 0) {
//				map.setMaterial(position, 295 + data);
//			}
//			break;
//		case Material.INFESTED_STONE: // silverfish
//			if (map.getData(position) == 1) { // silverfish cobblestone
//				map.setMaterial(position, 311);
//			} else if (map.getData(position) == 2) { // silberfish stone brick
//				map.setMaterial(position, 312);
//			}
//			break;
//		// 313 player clip texture
//		case 53: // wooden stairs
//			int newMaterial = Material.WOODEN_STAIRS_EAST;
//			switch (data) {
//			case 1:
//				newMaterial = Material.WOODEN_STAIRS_WEST;
//				break;
//			case 2:
//				newMaterial = Material.WOODEN_STAIRS_SOUTH;
//				break;
//			case 3:
//				newMaterial = Material.WOODEN_STAIRS_NORTH;
//				break;
//			case 4:
//				newMaterial = Material.WOODEN_STAIRS_HIGH_EAST;
//				break;
//			case 5:
//				newMaterial = Material.WOODEN_STAIRS_HIGH_WEST;
//				break;
//			case 6:
//				newMaterial = Material.WOODEN_STAIRS_HIGH_SOUTH;
//				break;
//			case 7:
//				newMaterial = Material.WOODEN_STAIRS_HIGH_NORTH;
//				break;
//			}
//			map.setMaterial(position, newMaterial);
//			break;
//		case 67: // cobblestone stairs
//			newMaterial = Material.COBBLESTONE_STAIRS_EAST;
//			switch (data) {
//			case 1:
//				newMaterial = Material.COBBLESTONE_STAIRS_WEST;
//				break;
//			case 2:
//				newMaterial = Material.COBBLESTONE_STAIRS_SOUTH;
//				break;
//			case 3:
//				newMaterial = Material.COBBLESTONE_STAIRS_NORTH;
//				break;
//			case 4:
//				newMaterial = Material.COBBLESTONE_STAIRS_HIGH_EAST;
//				break;
//			case 5:
//				newMaterial = Material.COBBLESTONE_STAIRS_HIGH_WEST;
//				break;
//			case 6:
//				newMaterial = Material.COBBLESTONE_STAIRS_HIGH_SOUTH;
//				break;
//			case 7:
//				newMaterial = Material.COBBLESTONE_STAIRS_HIGH_NORTH;
//				break;
//			}
//			map.setMaterial(position, newMaterial);
//			break;
//		case 108: // brick stairs
//			newMaterial = Material.BRICK_STAIRS_EAST;
//			switch (data) {
//			case 1:
//				newMaterial = Material.BRICK_STAIRS_WEST;
//				break;
//			case 2:
//				newMaterial = Material.BRICK_STAIRS_SOUTH;
//				break;
//			case 3:
//				newMaterial = Material.BRICK_STAIRS_NORTH;
//				break;
//			case 4:
//				newMaterial = Material.BRICK_STAIRS_HIGH_EAST;
//				break;
//			case 5:
//				newMaterial = Material.BRICK_STAIRS_HIGH_WEST;
//				break;
//			case 6:
//				newMaterial = Material.BRICK_STAIRS_HIGH_SOUTH;
//				break;
//			case 7:
//				newMaterial = Material.BRICK_STAIRS_HIGH_NORTH;
//				break;
//			}
//			map.setMaterial(position, newMaterial);
//			break;
//		case 109: // stone brick stairs
//			newMaterial = Material.STONE_BRICK_STAIRS_EAST;
//			switch (data) {
//			case 1:
//				newMaterial = Material.STONE_BRICK_STAIRS_WEST;
//				break;
//			case 2:
//				newMaterial = Material.STONE_BRICK_STAIRS_SOUTH;
//				break;
//			case 3:
//				newMaterial = Material.STONE_BRICK_STAIRS_NORTH;
//				break;
//			case 4:
//				newMaterial = Material.STONE_BRICK_STAIRS_HIGH_EAST;
//				break;
//			case 5:
//				newMaterial = Material.STONE_BRICK_STAIRS_HIGH_WEST;
//				break;
//			case 6:
//				newMaterial = Material.STONE_BRICK_STAIRS_HIGH_SOUTH;
//				break;
//			case 7:
//				newMaterial = Material.STONE_BRICK_STAIRS_HIGH_NORTH;
//				break;
//			}
//			map.setMaterial(position, newMaterial);
//			break;
//		case Material.NETHER_BRICK_STAIRS_EAST: // brick stairs
//			switch (data) {
//			case 1:
//				map.setMaterial(position, Material.NETHER_BRICK_STAIRS_WEST);
//				break;
//			case 2:
//				map.setMaterial(position, Material.NETHER_BRICK_STAIRS_SOUTH);
//				break;
//			case 3:
//				map.setMaterial(position, Material.NETHER_BRICK_STAIRS_NORTH);
//				break;
//			case 4:
//				map.setMaterial(position, Material.NETHER_BRICK_STAIRS_HIGH_EAST);
//				break;
//			case 5:
//				map.setMaterial(position, Material.NETHER_BRICK_STAIRS_HIGH_WEST);
//				break;
//			case 6:
//				map.setMaterial(position, Material.NETHER_BRICK_STAIRS_HIGH_SOUTH);
//				break;
//			case 7:
//				map.setMaterial(position, Material.NETHER_BRICK_STAIRS_HIGH_NORTH);
//				break;
//			}
//			break;
//		case Material.FURNACE_NORTH:
//			if (map.getData(position) == 3) {
//				map.setMaterial(position, Material.FURNACE_SOUTH);
//			} else if (map.getData(position) == 4) {
//				map.setMaterial(position, Material.FURNACE_WEST);
//			} else if (map.getData(position) == 5) {
//				map.setMaterial(position, Material.FURNACE_EAST);
//			}
//			break;
//		case Material.CHEST_NORTH:
//			if (map.getData(position) == 3) {
//				map.setMaterial(position, Material.CHEST_SOUTH);
//			} else if (map.getData(position) == 4) {
//				map.setMaterial(position, Material.CHEST_WEST);
//			} else if (map.getData(position) == 5) {
//				map.setMaterial(position, Material.CHEST_EAST);
//			}
//			break;
//		case Material.DISPENSER_NORTH:
//			if (map.getData(position) == 3) {
//				map.setMaterial(position, Material.DISPENSER_SOUTH);
//			} else if (map.getData(position) == 4) {
//				map.setMaterial(position, Material.DISPENSER_WEST);
//			} else if (map.getData(position) == 5) {
//				map.setMaterial(position, Material.DISPENSER_EAST);
//			}
//			break;
//		case Material.TORCH:
//			if (map.getData(position) == 1) {
//				map.setMaterial(position, Material.WALL_TORCH_EAST);
//			} else if (map.getData(position) == 2) {
//				map.setMaterial(position, Material.WALL_TORCH_WEST);
//			} else if (map.getData(position) == 3) {
//				map.setMaterial(position, Material.WALL_TORCH_SOUTH);
//			} else if (map.getData(position) == 4) {
//				map.setMaterial(position, Material.WALL_TORCH_NORTH);
//			}
//			break;
//		case Material.VINES:
//			if (data != 0) {
//				map.setMaterial(position, 346 + data);
//				// map.printMaterial( position );
//			}
//			break;
//		case Material.OAK_PLANKS:
//			if (map.getData(position) == 1) {
//				map.setMaterial(position, 362);
//			}
//			if (map.getData(position) == 2) {
//				map.setMaterial(position, 363);
//			}
//			if (map.getData(position) == 3) {
//				map.setMaterial(position, 364);
//			}
//			break;
//		case 26: // bed
//			// already used:
//			// if( map.getData( position )!=1 ){
//			// map.setMaterial( position, 367+map.getData( position ) ); //368 -
//			// 382
//			// }
//			break;
//		case Material.PINE_WOOD_STAIRS_EAST:
//			newMaterial = Material.PINE_WOOD_STAIRS_EAST;
//			switch (data) {
//			case 1:
//				newMaterial = Material.PINE_WOOD_STAIRS_WEST;
//				break;
//			case 2:
//				newMaterial = Material.PINE_WOOD_STAIRS_SOUTH;
//				break;
//			case 3:
//				newMaterial = Material.PINE_WOOD_STAIRS_NORTH;
//				break;
//			case 4:
//				newMaterial = Material.PINE_WOOD_STAIRS_HIGH_EAST;
//				break;
//			case 5:
//				newMaterial = Material.PINE_WOOD_STAIRS_HIGH_WEST;
//				break;
//			case 6:
//				newMaterial = Material.PINE_WOOD_STAIRS_HIGH_SOUTH;
//				break;
//			case 7:
//				newMaterial = Material.PINE_WOOD_STAIRS_HIGH_NORTH;
//				break;
//			}
//			map.setMaterial(position, newMaterial);
//			break;
//		case Material.OAK_WOOD_SLAB:
//			newMaterial = Material.OAK_WOOD_SLAB;
//			switch (data) {
//			case 1:
//				newMaterial = Material.PINE_WOOD_SLAB;
//				break;
//			case 2:
//				newMaterial = Material.BIRCH_WOOD_SLAB;
//				break;
//			case 3:
//				newMaterial = Material.JUNGLE_WOOD_SLAB;
//				break;
//			case 8:
//				newMaterial = Material.OAK_WOOD_HIGH_SLAB;
//				break;
//			case 9:
//				newMaterial = Material.PINE_WOOD_HIGH_SLAB;
//				break;
//			case 10:
//				newMaterial = Material.BIRCH_WOOD_HIGH_SLAB;
//				break;
//			case 11:
//				newMaterial = Material.JUNGLE_WOOD_HIGH_SLAB;
//				break;
//			}
//			map.setMaterial(position, newMaterial);
//			break;
//		case Material.BIRCH_WOOD_STAIRS_EAST:
//			newMaterial = Material.BIRCH_WOOD_STAIRS_EAST;
//			switch (data) {
//			case 1:
//				newMaterial = Material.BIRCH_WOOD_STAIRS_WEST;
//				break;
//			case 2:
//				newMaterial = Material.BIRCH_WOOD_STAIRS_SOUTH;
//				break;
//			case 3:
//				newMaterial = Material.BIRCH_WOOD_STAIRS_NORTH;
//				break;
//			case 4:
//				newMaterial = Material.BIRCH_WOOD_STAIRS_HIGH_EAST;
//				break;
//			case 5:
//				newMaterial = Material.BIRCH_WOOD_STAIRS_HIGH_WEST;
//				break;
//			case 6:
//				newMaterial = Material.BIRCH_WOOD_STAIRS_HIGH_SOUTH;
//				break;
//			case 7:
//				newMaterial = Material.BIRCH_WOOD_STAIRS_HIGH_NORTH;
//				break;
//			}
//			map.setMaterial(position, newMaterial);
//			break;
//		case Material.JUNGLE_WOOD_STAIRS_EAST:
//			newMaterial = Material.JUNGLE_WOOD_STAIRS_EAST;
//			switch (data) {
//			case 1:
//				newMaterial = Material.JUNGLE_WOOD_STAIRS_WEST;
//				break;
//			case 2:
//				newMaterial = Material.JUNGLE_WOOD_STAIRS_SOUTH;
//				break;
//			case 3:
//				newMaterial = Material.JUNGLE_WOOD_STAIRS_NORTH;
//				break;
//			case 4:
//				newMaterial = Material.JUNGLE_WOOD_STAIRS_HIGH_EAST;
//				break;
//			case 5:
//				newMaterial = Material.JUNGLE_WOOD_STAIRS_HIGH_WEST;
//				break;
//			case 6:
//				newMaterial = Material.JUNGLE_WOOD_STAIRS_HIGH_SOUTH;
//				break;
//			case 7:
//				newMaterial = Material.JUNGLE_WOOD_STAIRS_HIGH_NORTH;
//				break;
//			}
//			map.setMaterial(position, newMaterial);
//			break;
//		case Material.SANDSTONE_STAIRS_EAST:
//			newMaterial = Material.SANDSTONE_STAIRS_EAST;
//			switch (data) {
//			case 1:
//				newMaterial = Material.SANDSTONE_STAIRS_WEST;
//				break;
//			case 2:
//				newMaterial = Material.SANDSTONE_STAIRS_SOUTH;
//				break;
//			case 3:
//				newMaterial = Material.SANDSTONE_STAIRS_NORTH;
//				break;
//			case 4:
//				newMaterial = Material.SANDSTONE_STAIRS_HIGH_EAST;
//				break;
//			case 5:
//				newMaterial = Material.SANDSTONE_STAIRS_HIGH_WEST;
//				break;
//			case 6:
//				newMaterial = Material.SANDSTONE_STAIRS_HIGH_SOUTH;
//				break;
//			case 7:
//				newMaterial = Material.SANDSTONE_STAIRS_HIGH_NORTH;
//				break;
//			}
//			map.setMaterial(position, newMaterial);
//			break;
//		}
//	}
}
