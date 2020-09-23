package converter.actions;

import java.util.Collection;

import converter.actions.actions.Button;
import converter.actions.actions.Cactus;
import converter.actions.actions.Carpet;
import converter.actions.actions.Chest;
import converter.actions.actions.DetailBlock;
import converter.actions.actions.Fence;
import converter.actions.actions.Fire;
import converter.actions.actions.GrassPath;
import converter.actions.actions.Ladder;
import converter.actions.actions.Liquid;
import converter.actions.actions.NoAction;
import converter.actions.actions.Pane;
import converter.actions.actions.Slab;
import converter.actions.actions.Solid;
import converter.actions.actions.Stairs;
import converter.actions.actions.Torch;
import converter.actions.actions.Trapdoor;
import converter.mapper.Mapper;
import minecraft.Blocks;
import minecraft.Material;

public class CustomActionManager extends ActionManager {

	public CustomActionManager(Mapper map, Collection<ConvertEntity> converters) {
		super(Solid.INSTANCE);
		this.setDefaults();
		if (converters != null) {
			for (ConvertEntity converter : converters) {
				this.actions.put(converter.getBlock(), converter.getAction());
			}
		}
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
				Material.tall_seagrass, Material.sweet_berry_bush, Material._pressure_plate }) {
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
		this.actions.put(Material._slab, new Slab());
		this.actions.put(Material.torch, Torch.INSTANCE);
		this.actions.put(Material.wall_torch, Torch.INSTANCE);
		this.actions.put(Material.cactus, new Cactus());
		this.actions.put(Material.fire, new Fire());
		this.actions.put(Material._trapdoor, new Trapdoor());
		this.actions.put(Material.ladder, new Ladder());
		this.actions.put(Material._carpet, new Carpet());
		this.actions.put(Material._pane, new Pane());
		this.actions.put(Material.iron_bars, new Pane());
		this.actions.put(Material.chest, new Chest());
		this.actions.put(Material._button, new Button());
		this.actions.put(Material.grass_path, new GrassPath());

//		// tf2
//		this.actions.put(Material.grass, new TallGrassTf2());
//
//		// ttt
//		this.actions.put(Material.zombie_head, new CenteredPointEntity("info_player_start"));
//		this.actions.put(Material.fletching_table, new CenteredPointEntity("ttt_random_weapon"));
//		this.actions.put(Material.grindstone, new CenteredPointEntity("ttt_random_ammo"));
//
//		// css
////		this.actions.put(Material.torch, new CssLamp());
////		this.actions.put(Material.wall_torch, new CssLamp());
//		this.actions.put(Material.end_portal_frame,
//				new PlayerSpawnCss(new RotateablePointEntity().setName("info_player_terrorist")
//						.setRotation(0), false));
//		this.actions.put(Material.ender_chest,
//				new PlayerSpawnCss(new RotateablePointEntity().setName("info_player_counterterrorist")
//						.setRotation(180), true));
		return this;
	}
}
