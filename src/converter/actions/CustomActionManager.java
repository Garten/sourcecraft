package converter.actions;

import java.util.Collection;

import converter.actions.actions.Cactus;
import converter.actions.actions.CenteredPointEntity;
import converter.actions.actions.DetailBlock;
import converter.actions.actions.Fence;
import converter.actions.actions.Fire;
import converter.actions.actions.Liquid;
import converter.actions.actions.NoAction;
import converter.actions.actions.PlayerSpawnCss;
import converter.actions.actions.SlabBottom;
import converter.actions.actions.SlabTop;
import converter.actions.actions.Solid;
import converter.actions.actions.Stairs;
import converter.actions.actions.TallGrassTf2;
import converter.actions.actions.Torch;
import converter.mapper.Mapper;
import minecraft.Blocks;
import minecraft.Material;
import minecraft.Property;
import vmfWriter.entity.pointEntity.pointEntity.InfoPlayerCT;
import vmfWriter.entity.pointEntity.pointEntity.InfoPlayerT;

public class CustomActionManager extends ActionManager {

	public CustomActionManager(Mapper map, Collection<ConvertEntity> converters) {
		super(Solid.INSTANCE);
	}

	public CustomActionManager setDefaults() {
		this.actions.put(Blocks._UNSET, NoAction.INSTANCE);
		this.actions.put(Material.air, NoAction.INSTANCE);
		this.actions.put(Material.cave_air, NoAction.INSTANCE);
		this.actions.put(Material.void_air, NoAction.INSTANCE);

		for (Material m : new Material[] { Material.fern, Material.grass, Material.dandelion, Material.poppy,
				Material.brown_mushroom, Material.red_mushroom, Material.redstone_dust, Material.wheat,
				Material.oak_door, Material.ladder, Material.rail, Material.oak_wall_sign, Material.lever,
				Material.stone_pressure_plate, Material.iron_door, Material.oak_pressure_plate, Material.sugar_cane,
				Material.sunflower, Material.cobweb, Material.detector_rail, Material.detector_rail, Material.fire,
				Material.redstone_wall_torch, Material.redstone_torch, Material.stone_button, Material.tall_grass,
				Material.tall_grass, Material.large_fern, Material.blue_orchid, Material.allium, Material.azure_bluet,
				Material.red_tulip, Material.orange_tulip, Material.white_tulip, Material.pink_tulip,
				Material.oxeye_daisy, Material.cornflower, Material.lily_of_the_valley, Material.wither_rose,
				Material.lilac, Material.rose_bush, Material.peony, Material.sugar_cane, Material.seagrass,
				Material.tall_seagrass, Material.sweet_berry_bush }) {
			this.actions.put(m, NoAction.INSTANCE);
		}
		for (Material m : new Material[] { Material._leaves, Material.glass, Material.ice }) {
			this.actions.put(m, new DetailBlock());
		}
		for (Material m : new Material[] { Material.water, Material.lava, Material.seagrass, Material.tall_seagrass,
				Material.kelp, Material.kelp_plant }) {
			this.actions.put(m, new Liquid());
		}
		this.actions.put(Material._fence, new Fence());
		this.actions.put(Material._stairs, new Stairs());
		this.actions.put(Blocks.get(t -> t.setName(Material._slab)
				.addProperty(Property.half, Property.Half.top)
				.addProperty(Property.waterlogged, Property.Waterlogged.false$)), new SlabTop());
		this.actions.put(Blocks.get(t -> t.setName(Material._slab)
				.addProperty(Property.half, Property.Half.bottom)
				.addProperty(Property.waterlogged, Property.Waterlogged.false$)), new SlabBottom());
		this.actions.put(Material.torch, Torch.INSTANCE);
		this.actions.put(Material.wall_torch, Torch.INSTANCE);
		this.actions.put(Material.cactus, new Cactus());
		this.actions.put(Material.fire, new Fire());

		// tf2
		this.actions.put(Material.grass, new TallGrassTf2());

		// ttt
		this.actions.put(Material.zombie_head, new CenteredPointEntity("info_player_start"));
		this.actions.put(Material.fletching_table, new CenteredPointEntity("ttt_random_weapon"));
		this.actions.put(Material.grindstone, new CenteredPointEntity("ttt_random_ammo"));

		// css
//		this.actions.put(Material.torch, new CssLamp());
//		this.actions.put(Material.wall_torch, new CssLamp());
		this.actions.put(Material.end_portal_frame, new PlayerSpawnCss(new InfoPlayerT().setRotation(0), false));
		this.actions.put(Material.ender_chest, new PlayerSpawnCss(new InfoPlayerCT().setRotation(180), true));
		return this;
	}
}
