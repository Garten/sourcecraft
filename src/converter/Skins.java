package converter;

import java.util.function.Supplier;

import basic.NameSupplier;
import converter.actions.BlockMap;
import minecraft.Block;
import minecraft.Blocks;
import minecraft.Material;
import minecraft.Texture;
import periphery.TexturePack;
import vmfWriter.Orientation;
import vmfWriter.Skin;

public class Skins {

	private static final double DEFAULT_SCALE = 0.25;
	public static Skins INSTANCE;

	public static final void init(TexturePack texturePack, int optionScale) {
		INSTANCE = new Skins(texturePack.getName(), texturePack.getTextureSize(), optionScale);
	}

	private static final String NODRAW_TEXTURE = "tools/toolsnodraw";

	public static final Skin NODRAW = new Skin(NODRAW_TEXTURE, DEFAULT_SCALE);
	public static final Skin TRIGGER = new Skin("tools/toolstrigger", DEFAULT_SCALE);
	public static final Skin SKYBOX = new Skin("tools/toolsskybox", DEFAULT_SCALE);
	private static final Skin PLAYER_CLIP = new Skin("tools/toolsplayerclip", DEFAULT_SCALE);

	public static final String DEFAULT_TEXTURE = "dev/dev_measurecrate02";

	private double textureScale;
	private Skin[] skinLegacy;

	private String folder;

	public Skin setSourceSkin(String texture) {
		return new Skin(texture, this.textureScale);
	}

	public Skin createSkin(String main) {
		return new Skin(this.folder + main, this.textureScale);
	}

	public Skin createSkin(NameSupplier main, NameSupplier topBottom) {
		return new Skin(this.folder + main.getName(), this.folder + topBottom.getName(), this.textureScale);
	}

	private Skin createSkinTopBottom(NameSupplier side, NameSupplier top, NameSupplier bottom) {
		return new Skin(this.folder + side.getName(), this.folder + top.getName(), this.folder + side.getName(),
				this.folder + bottom.getName(), Orientation.NORTH, this.textureScale);
	}

	public Skin createSkinTopFront(NameSupplier main, NameSupplier top, NameSupplier front, Orientation orientation) {
		return new Skin(this.folder + main.getName(), this.folder + top.getName(), this.folder + front.getName(),
				orientation, this.textureScale);
	}

	public Skin createSkinTopFrontBottom(NameSupplier main, NameSupplier top, NameSupplier front, NameSupplier bottom) {
		return new Skin(this.folder + main.getName(), this.folder + top.getName(), this.folder + front.getName(),
				this.folder + bottom.getName(), Orientation.NORTH, this.textureScale);
	}

	public Skin createSkinTopFrontBottom(NameSupplier main, NameSupplier top, NameSupplier front, NameSupplier bottom,
			Orientation orientation) {
		return new Skin(this.folder + main.getName(), this.folder + top.getName(), this.folder + front.getName(),
				this.folder + bottom.getName(), orientation, this.textureScale);
	}

	public Skins(String folder, int textureSizeNew, int scale) {
		this.init(folder, textureSizeNew, scale);
	}

	private BlockMap<Skin> skins;

	/**
	 * Returns also whether given material has suffix
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
		// set based on matching texture name
		for (Texture texture : Texture.values()) {
			this.put(texture);
		}

		// set based on common
		for (Material material : Material.values()) {
			String name = material.name();
			if (name.endsWith("_log")) { // except dark oak
				String textureName = "log_" + name.substring(0, name.length() - "_log".length());
				this.put(material, this.createSkin(() -> textureName, () -> textureName + "_top"));
			} else if (this.putPrefixMadeSuffix(material, Material._leaves)) {
			} else if (this.putPrefixMadeSuffix(material, Material._planks)) {
			} else if (this.putPrefixMadeSuffix(material, Material._wool)) {
			} else if (this.putPrefixMadeSuffix(material, Material._stained_glass)) {
			}
		}
		// exceptions
		this.put(Material.dark_oak_log, this.createSkin(Texture.log_big_oak, Texture.log_big_oak_top));
		this.put(Material.dark_oak_leaves, Texture.leaves_big_oak);
		this.put(Material.dark_oak_planks, Texture.planks_big_oak);

		// material-prefixe
		this.put(Material.andesite, Texture.stone_andesite);
		this.put(Material.diorite, Texture.stone_diorite);
		this.put(Material.granite, Texture.stone_granite);
		this.skins.put(Material.sandstone,
				this.createSkinTopBottom(Texture.sandstone_normal, Texture.sandstone_top, Texture.sandstone_normal));
		this.put(Material.sandstone, Texture.sandstone_normal);
		this.skins.put(Material.red_sandstone, this.createSkinTopBottom(Texture.red_sandstone_normal,
				Texture.red_sandstone_top, Texture.red_sandstone_normal));
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
		this.put(Material.oak_, Texture.planks_oak);
		this.put(Material.dark_oak_, Texture.planks_big_oak);
		this.put(Material.spruce_, Texture.planks_spruce);
		this.put(Material.acacia_, Texture.planks_acacia);
		// exceptions

		// other material
		this.put(Material.grass_block, this.createSkinTopBottom(Texture.grass_side, Texture.grass_top, Texture.dirt));
		this.put(Material.podzol,
				this.createSkinTopBottom(Texture.dirt_podzol_side, Texture.dirt_podzol_top, Texture.dirt));
		this.put(Material.mycelium,
				this.createSkinTopBottom(Texture.mycelium_side, Texture.mycelium_top, Texture.dirt));
//
		this.put(Material.packed_ice, Texture.ice_packed);
		this.put(Material.snow_block, Texture.snow);

		this.put(Material.chiseled_stone_bricks, Texture.stonebrick_carved);
		this.put(Material.cracked_stone_bricks, Texture.stonebrick_cracked);

		this.put(Material.polished_andesite, Texture.stone_andesite_smooth);
		this.put(Material.polished_diorite, Texture.stone_diorite_smooth);
		this.put(Material.polished_granite, Texture.stone_granite_smooth);

		this.put(Material.cut_sandstone, this.createSkin(Texture.sandstone_smooth, Texture.sandstone_top));
		this.put(Material.chiseled_sandstone, this.createSkin(Texture.sandstone_carved, Texture.sandstone_top));
		this.put(Material.smooth_sandstone, this.createSkin(Texture.sandstone_smooth, Texture.sandstone_top));

		this.put(Material.red_sandstone, this.createSkin(Texture.red_sandstone_normal, Texture.red_sandstone_top));
		this.put(Material.cut_red_sandstone, this.createSkin(Texture.red_sandstone_smooth, Texture.red_sandstone_top));
		this.put(Material.chiseled_red_sandstone,
				this.createSkin(Texture.red_sandstone_carved, Texture.red_sandstone_top));
		this.put(Material.smooth_red_sandstone, this.createSkin(Texture.red_sandstone_smooth, Texture.sandstone_top));

		this.put(Material.purpur_pillar, this.createSkin(Texture.purpur_pillar, Texture.purpur_pillar_top));

		this.put(Material.bone_block, this.createSkin(Texture.bone_block_side, Texture.bone_block_top));

		this.put(Material.nether_quartz_ore, Texture.quartz_ore);
		this.put(Material.quartz_block, this.createSkinTopBottom(Texture.quartz_block_side, Texture.quartz_block_top,
				Texture.quartz_block_bottom));
		this.put(Material.chiseled_quartz_block,
				this.createSkin(Texture.quartz_block_chiseled, Texture.quartz_block_chiseled_top));
		this.put(Material.quartz_pillar, this.createSkin(Texture.quartz_block_lines, Texture.quartz_block_lines_top));
		this.put(Material.smooth_quartz, Texture.quartz_block_bottom);

		this.put(Material.tnt, this.createSkinTopBottom(Texture.tnt_side, Texture.tnt_top, Texture.tnt_bottom));

		Skin waterSkin = new Skin(NODRAW_TEXTURE, this.folder + "water_still", NODRAW_TEXTURE, NODRAW_TEXTURE,
				this.textureScale);
		for (Material m : new Material[] { Material.water, Material.seagrass, Material.tall_seagrass, Material.kelp,
				Material.kelp_plant }) {
			this.put(m, waterSkin);
		}

		this.put(Material.lava, Texture.lava_still);
		this.put(Material.magma_block, Texture.magma);

		this.put(Material.spawner, Texture.mob_spawner);
		this.put(Material.mossy_cobblestone, Texture.cobblestone_mossy);

		this.put(Material.note_block, Texture.jukebox_side);
		this.put(Material.jukebox,
				this.createSkinTopBottom(Texture.jukebox_side, Texture.jukebox_top, Texture.jukebox_side));
		this.put(Material.wet_sponge, Texture.sponge_wet);

		this.put(Material.melon, this.createSkin(Texture.melon_side, Texture.melon_top));
		this.put(Material.carved_pumpkin, this.createSkinTopFront(Texture.pumpkin_side, Texture.pumpkin_top,
				Texture.pumpkin_face_off, Orientation.NORTH));
		this.put(Material.jack_o_lantern, this.createSkinTopFront(Texture.pumpkin_side, Texture.pumpkin_top,
				Texture.pumpkin_face_on, Orientation.NORTH));
		this.put(Material.pumpkin, this.createSkin(Texture.pumpkin_side, Texture.pumpkin_top));

		this.put(Material.dispenser, this.createSkinTopFront(Texture.furnace_side, Texture.furnace_top,
				Texture.dispenser_front_horizontal, Orientation.NORTH));

		this.put(Material.cactus, this.createSkin(Texture.cactus_side, Texture.cactus_top));
		this.put(Material.slime_block, Texture.slime);
		this.put(Material.crafting_table, this.createSkinTopFront(Texture.crafting_table_side,
				Texture.crafting_table_top, Texture.crafting_table_front, Orientation.NORTH));
		this.put(Material.furnace, this.createSkinTopFront(Texture.furnace_side, Texture.furnace_top,
				Texture.furnace_front_on, Orientation.NORTH));

		this.put(Material.end_portal_frame,
				this.createSkinTopBottom(Texture.endframe_side, Texture.endframe_top, Texture.end_stone));
		this.put(Material.redstone_lamp, Texture.redstone_lamp_on);
		this.put(Material.spruce_trapdoor, Texture.door_spruce_upper);
		this.put(Material.wall_torch, Texture.torch_on);

		// temporary fixes
		this.put(Material.campfire, Texture.magma);
		this.put(Material.lantern, Texture.magma);
		this.put(Material.blast_furnace, Texture.furnace_front_on);
		this.put(Material.torch, Texture.magma);

		// special
		this.skins.put(Blocks.get("sourcecraft:ramp"), PLAYER_CLIP);
	}

	private void put(Texture block) {
		this.put(block, block);
	}

	private void put(Supplier<Block> block, Texture texture) {
		this.put(block, this.createSkin(texture.name()));
	}

	private void put(Supplier<Block> block, String texture) {
		this.put(block, this.createSkin(texture));
	}

	private void put(Supplier<Block> block, Skin skin) {
		this.skins.put(block, skin);
	}

	public Skin getSkin(Block block) {
		return this.skins.getFallBackToPrefix(block);
	}
}
