package converter;

import java.util.ArrayList;
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

    private void multiPut(Material[] materials, Texture texture) {
        for (Material material : materials) {
            this.put(material, texture);
        }
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
        this.put(Material.dark_oak_button, Texture.planks_big_oak);
        this.put(Material.dark_oak_fence, Texture.planks_big_oak);
        this.put(Material.dark_oak_fence_gate, Texture.planks_big_oak);
        this.put(Material.dark_oak_pressure_plate, Texture.planks_big_oak);
        this.put(Material.dark_oak_sapling, Texture.planks_big_oak);
        this.put(Material.dark_oak_sign, Texture.planks_big_oak);
        this.put(Material.dark_oak_slab, Texture.planks_big_oak);
        this.put(Material.dark_oak_trapdoor, Texture.planks_big_oak);
        this.put(Material.dark_oak_wall_sign, Texture.planks_big_oak);
        this.put(Material.dark_oak_wood, Texture.planks_big_oak);

        // Stairs and Slabs
        this.multiPut(new Material[]{Material.acacia_stairs, Material.acacia_slab}, Texture.planks_acacia);
        this.multiPut(new Material[]{Material.andesite_stairs, Material.andesite_slab}, Texture.stone_andesite);
        this.multiPut(new Material[]{Material.birch_stairs, Material.birch_slab}, Texture.planks_birch);
        this.multiPut(new Material[]{Material.brick_stairs, Material.brick_slab}, Texture.brick);
        this.multiPut(new Material[]{Material.cobblestone_stairs, Material.cobblestone_slab}, Texture.cobblestone);
        this.multiPut(new Material[]{Material.dark_oak_stairs, Material.dark_oak_slab}, Texture.planks_big_oak);
        this.multiPut(new Material[]{Material.dark_prismarine_stairs, Material.dark_prismarine_slab}, Texture.prismarine_dark);
        this.multiPut(new Material[]{Material.diorite_stairs, Material.diorite_slab}, Texture.stone_diorite);
        this.multiPut(new Material[]{Material.end_stone_brick_stairs, Material.end_stone_brick_slab}, Texture.end_bricks);
        this.multiPut(new Material[]{Material.granite_stairs, Material.granite_slab}, Texture.stone_granite);
        this.multiPut(new Material[]{Material.jungle_stairs, Material.jungle_slab}, Texture.planks_jungle);
        this.multiPut(new Material[]{Material.mossy_cobblestone_stairs, Material.mossy_cobblestone_slab}, Texture.cobblestone_mossy);
        this.multiPut(new Material[]{Material.mossy_stone_brick_stairs, Material.mossy_stone_brick_slab}, Texture.stonebrick_mossy);
        this.multiPut(new Material[]{Material.nether_brick_stairs, Material.nether_brick_slab}, Texture.nether_brick);
        this.multiPut(new Material[]{Material.oak_stairs, Material.oak_slab}, Texture.planks_oak);
        this.multiPut(new Material[]{Material.polished_andesite_stairs, Material.polished_andesite_slab}, Texture.stone_andesite_smooth);
        this.multiPut(new Material[]{Material.polished_diorite_stairs, Material.polished_diorite_slab}, Texture.stone_diorite_smooth);
        this.multiPut(new Material[]{Material.polished_granite_stairs, Material.polished_granite_slab}, Texture.stone_granite_smooth);
        this.multiPut(new Material[]{Material.prismarine_brick_stairs, Material.prismarine_brick_slab}, Texture.prismarine_bricks);
        this.multiPut(new Material[]{Material.prismarine_stairs, Material.prismarine_slab}, Texture.prismarine_rough);
        this.multiPut(new Material[]{Material.purpur_stairs, Material.purpur_slab}, Texture.purpur_pillar);
        this.multiPut(new Material[]{Material.quartz_stairs, Material.quartz_slab}, Texture.quartz_block_side);
        this.multiPut(new Material[]{Material.red_nether_brick_stairs, Material.red_nether_brick_slab}, Texture.red_nether_brick);
        this.multiPut(new Material[]{Material.red_sandstone_stairs, Material.red_sandstone_slab}, Texture.red_sandstone_normal);
        this.multiPut(new Material[]{Material.sandstone_stairs, Material.sandstone_slab}, Texture.sandstone_normal);
        this.multiPut(new Material[]{Material.smooth_quartz_stairs, Material.smooth_quartz_slab}, Texture.quartz_block_side);
        this.multiPut(new Material[]{Material.smooth_red_sandstone_stairs, Material.smooth_red_sandstone_slab}, Texture.red_sandstone_smooth);
        this.multiPut(new Material[]{Material.smooth_sandstone_stairs, Material.smooth_sandstone_slab}, Texture.sandstone_smooth);
        this.multiPut(new Material[]{Material.spruce_stairs, Material.spruce_slab}, Texture.planks_spruce);
        this.multiPut(new Material[]{Material.stone_brick_stairs, Material.stone_brick_slab}, Texture.stonebrick);
        this.multiPut(new Material[]{Material.stone_stairs, Material.stone_slab}, Texture.stone);

        // Colored glass

        this.multiPut(new Material[]{
                        Material.blue_stained_glass,
                        Material.blue_stained_glass_pane,
                        Material.brown_stained_glass,
                        Material.brown_stained_glass_pane,
                        Material.cyan_stained_glass,
                        Material.cyan_stained_glass_pane,
                        Material.gray_stained_glass,
                        Material.gray_stained_glass_pane,
                        Material.green_stained_glass,
                        Material.green_stained_glass_pane,
                        Material.light_blue_stained_glass,
                        Material.light_blue_stained_glass_pane,
                        Material.light_gray_stained_glass,
                        Material.light_gray_stained_glass_pane,
                        Material.lime_stained_glass,
                        Material.lime_stained_glass_pane,
                        Material.magenta_stained_glass,
                        Material.magenta_stained_glass_pane,
                        Material.orange_stained_glass,
                        Material.orange_stained_glass_pane,
                        Material.pink_stained_glass,
                        Material.pink_stained_glass_pane,
                        Material.purple_stained_glass,
                        Material.purple_stained_glass_pane,
                        Material.red_stained_glass,
                        Material.red_stained_glass_pane,
                        Material.white_stained_glass,
                        Material.white_stained_glass_pane,
                        Material.yellow_stained_glass,
                        Material.yellow_stained_glass_pane,},
                Texture.glass);


        // Colored deco blocks
        this.multiPut(new Material[]{
                Material.blue_banner,
                Material.blue_bed,
                Material.blue_carpet,
                Material.blue_concrete,
                Material.blue_concrete_powder,
                Material.blue_glazed_terracotta,
                Material.blue_shulker_box,
                Material.blue_terracotta,
                Material.blue_wall_banner,
                Material.blue_wool,
                Material.blue_,
                Material.blue_stained_,
        }, Texture.wool_colored_blue);

        this.multiPut(new Material[]{
                Material.brown_banner,
                Material.brown_bed,
                Material.brown_carpet,
                Material.brown_concrete,
                Material.brown_concrete_powder,
                Material.brown_glazed_terracotta,
                Material.brown_shulker_box,
                Material.brown_terracotta,
                Material.brown_wall_banner,
                Material.brown_wool,
                Material.brown_,
                Material.brown_stained_,
        }, Texture.wool_colored_brown);

        this.multiPut(new Material[]{
                Material.cyan_banner,
                Material.cyan_bed,
                Material.cyan_carpet,
                Material.cyan_concrete,
                Material.cyan_concrete_powder,
                Material.cyan_glazed_terracotta,
                Material.cyan_shulker_box,
                Material.cyan_terracotta,
                Material.cyan_wall_banner,
                Material.cyan_wool,
                Material.cyan_,
                Material.cyan_stained_,
        }, Texture.wool_colored_cyan);

        this.multiPut(new Material[]{
                Material.gray_banner,
                Material.gray_bed,
                Material.gray_carpet,
                Material.gray_concrete,
                Material.gray_concrete_powder,
                Material.gray_glazed_terracotta,
                Material.gray_shulker_box,
                Material.gray_terracotta,
                Material.gray_wall_banner,
                Material.gray_wool,
                Material.gray_,
                Material.gray_stained_,
        }, Texture.wool_colored_gray);

        this.multiPut(new Material[]{
                Material.green_banner,
                Material.green_bed,
                Material.green_carpet,
                Material.green_concrete,
                Material.green_concrete_powder,
                Material.green_glazed_terracotta,
                Material.green_shulker_box,
                Material.green_terracotta,
                Material.green_wall_banner,
                Material.green_wool,
                Material.green_,
                Material.green_stained_,
        }, Texture.wool_colored_green);

        this.multiPut(new Material[]{
                Material.light_blue_banner,
                Material.light_blue_bed,
                Material.light_blue_carpet,
                Material.light_blue_concrete,
                Material.light_blue_concrete_powder,
                Material.light_blue_glazed_terracotta,
                Material.light_blue_shulker_box,
                Material.light_blue_terracotta,
                Material.light_blue_wall_banner,
                Material.light_blue_wool,
                Material.light_blue_,
                Material.light_blue_stained_,
        }, Texture.wool_colored_light_blue);

        this.multiPut(new Material[]{
                Material.light_gray_banner,
                Material.light_gray_bed,
                Material.light_gray_carpet,
                Material.light_gray_concrete,
                Material.light_gray_concrete_powder,
                Material.light_gray_glazed_terracotta,
                Material.light_gray_shulker_box,
                Material.light_gray_terracotta,
                Material.light_gray_wall_banner,
                Material.light_gray_wool,
                Material.light_gray_,
                Material.light_gray_stained_,
        }, Texture.wool_colored_gray);

        this.multiPut(new Material[]{
                Material.lime_banner,
                Material.lime_bed,
                Material.lime_carpet,
                Material.lime_concrete,
                Material.lime_concrete_powder,
                Material.lime_glazed_terracotta,
                Material.lime_shulker_box,
                Material.lime_terracotta,
                Material.lime_wall_banner,
                Material.lime_wool,
                Material.lime_,
                Material.lime_stained_,
        }, Texture.wool_colored_lime);

        this.multiPut(new Material[]{
                Material.magenta_banner,
                Material.magenta_bed,
                Material.magenta_carpet,
                Material.magenta_concrete,
                Material.magenta_concrete_powder,
                Material.magenta_glazed_terracotta,
                Material.magenta_shulker_box,
                Material.magenta_terracotta,
                Material.magenta_wall_banner,
                Material.magenta_wool,
                Material.magenta_,
                Material.magenta_stained_,
        }, Texture.wool_colored_magenta);

        this.multiPut(new Material[]{
                Material.orange_banner,
                Material.orange_bed,
                Material.orange_carpet,
                Material.orange_concrete,
                Material.orange_concrete_powder,
                Material.orange_glazed_terracotta,
                Material.orange_shulker_box,
                Material.orange_terracotta,
                Material.orange_wall_banner,
                Material.orange_wool,
                Material.orange_,
                Material.orange_stained_,
        }, Texture.wool_colored_orange);

        this.multiPut(new Material[]{
                Material.pink_banner,
                Material.pink_bed,
                Material.pink_carpet,
                Material.pink_concrete,
                Material.pink_concrete_powder,
                Material.pink_glazed_terracotta,
                Material.pink_shulker_box,
                Material.pink_terracotta,
                Material.pink_wall_banner,
                Material.pink_wool,
                Material.pink_,
                Material.pink_stained_,
        }, Texture.wool_colored_pink);

        this.multiPut(new Material[]{
                Material.purple_banner,
                Material.purple_bed,
                Material.purple_carpet,
                Material.purple_concrete,
                Material.purple_concrete_powder,
                Material.purple_glazed_terracotta,
                Material.purple_shulker_box,
                Material.purple_terracotta,
                Material.purple_wall_banner,
                Material.purple_wool,
                Material.purple_,
                Material.purple_stained_,
        }, Texture.wool_colored_purple);

        this.multiPut(new Material[]{
                Material.red_banner,
                Material.red_bed,
                Material.red_carpet,
                Material.red_concrete,
                Material.red_concrete_powder,
                Material.red_glazed_terracotta,
                Material.red_shulker_box,
                Material.red_terracotta,
                Material.red_wall_banner,
                Material.red_wool,
                Material.red_,
                Material.red_stained_,
        }, Texture.wool_colored_red);

        this.multiPut(new Material[]{
                Material.white_banner,
                Material.white_bed,
                Material.white_carpet,
                Material.white_concrete,
                Material.white_concrete_powder,
                Material.white_glazed_terracotta,
                Material.white_shulker_box,
                Material.white_terracotta,
                Material.white_wall_banner,
                Material.white_wool,
                Material.white_,
                Material.white_stained_,
        }, Texture.wool_colored_white);

        this.multiPut(new Material[]{
                Material.yellow_banner,
                Material.yellow_bed,
                Material.yellow_carpet,
                Material.yellow_concrete,
                Material.yellow_concrete_powder,
                Material.yellow_glazed_terracotta,
                Material.yellow_shulker_box,
                Material.yellow_terracotta,
                Material.yellow_wall_banner,
                Material.yellow_wool,
                Material.yellow_,
                Material.yellow_stained_,
        }, Texture.wool_colored_yellow);

        this.multiPut(new Material[]{
                Material.black_banner,
                Material.black_bed,
                Material.black_carpet,
                Material.black_concrete,
                Material.black_concrete_powder,
                Material.black_glazed_terracotta,
                Material.black_shulker_box,
                Material.black_terracotta,
                Material.black_wall_banner,
                Material.black_wool,
                Material.black_,
                Material.black_stained_,
        }, Texture.wool_colored_black);

        // Terracotta

        // material-prefixe
        this.put(Material.smooth_stone, Texture.stone);
        this.put(Material.smooth_stone_slab, Texture.stone);

        this.put(Material.andesite, Texture.stone_andesite);
        this.put(Material.diorite, Texture.stone_diorite);
        this.put(Material.granite, Texture.stone_granite);
        this.skins.put(Material.sandstone,
                this.createSkinTopBottom(Texture.sandstone_normal, Texture.sandstone_top, Texture.sandstone_normal)
        );
        this.put(Material.sandstone, Texture.sandstone_normal);
        this.skins.put(Material.red_sandstone,
                this.createSkinTopBottom(Texture.red_sandstone_normal, Texture.red_sandstone_top, Texture.red_sandstone_normal)
        );
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
        for (Material m : new Material[]{Material.water, Material.seagrass, Material.tall_seagrass, Material.kelp,
                Material.kelp_plant}) {
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
