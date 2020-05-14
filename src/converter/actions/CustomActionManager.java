package converter.actions;

import java.util.Collection;
import java.util.Stack;

import basic.Loggger;
import converter.actions.actions.Cactus;
import converter.actions.actions.CenteredPointEntity;
import converter.actions.actions.CssLamp;
import converter.actions.actions.Debug;
import converter.actions.actions.DetailBlock;
import converter.actions.actions.EndPortalFrame;
import converter.actions.actions.Fence;
import converter.actions.actions.Fire;
import converter.actions.actions.LilypadTf2;
import converter.actions.actions.Liquid;
import converter.actions.actions.NoAction;
import converter.actions.actions.Pane;
import converter.actions.actions.PlayerSpawnCss;
import converter.actions.actions.PlayerSpawnTf2;
import converter.actions.actions.SlabBottom;
import converter.actions.actions.SlabTop;
import converter.actions.actions.SnowBlock;
import converter.actions.actions.Solid;
import converter.actions.actions.Stairs;
import converter.actions.actions.SupplyTf2;
import converter.actions.actions.TallGrassTf2;
import converter.actions.actions.Torch;
import converter.actions.actions.TorchEast;
import converter.actions.actions.TorchNorth;
import converter.actions.actions.TorchSouth;
import converter.actions.actions.TorchWest;
import converter.actions.actions.VinesEast;
import converter.actions.actions.VinesNorth;
import converter.actions.actions.VinesSouth;
import converter.actions.actions.VinesWest;
import converter.mapper.BlockMapper;
import minecraft.Blocks;
import minecraft.Material;
import minecraft.Property;
import vmfWriter.entity.pointEntity.pointEntity.InfoPlayerCT;
import vmfWriter.entity.pointEntity.pointEntity.InfoPlayerT;

public class CustomActionManager extends ActionManager {

	public CustomActionManager(BlockMapper map, Collection<ConvertEntity> converters) {
		super(Solid.INSTANCE);
	}

	public CustomActionManager setDefaults() {
		this.actions.put(Blocks._UNSET, NoAction.INSTANCE);
		this.actions.put(Material.air, NoAction.INSTANCE);
		this.actions.put(Material.cave_air, NoAction.INSTANCE);
		this.actions.put(Material.void_air, NoAction.INSTANCE);
		this.actions.put(Material.water, new Liquid());
		this.actions.put(Material.cactus, new Cactus());
		this.actions.put(Blocks.get(t -> t.setName(Material._stairs)), new Stairs());
		this.actions.put(Blocks.get(t -> t.setName(Material._slab)
				.addProperty(Property.half, Property.Half.top)
				.addProperty(Property.waterlogged, Property.Waterlogged.false$)), new SlabTop());
		this.actions.put(Blocks.get(t -> t.setName(Material._slab)
				.addProperty(Property.half, Property.Half.top)
				.addProperty(Property.waterlogged, Property.Waterlogged.true$)), new SlabTop());
		// ttt
		this.actions.put(Material.zombie_head, new CenteredPointEntity("info_player_start"));
		this.actions.put(Material.fletching_table, new CenteredPointEntity("ttt_random_weapon"));
		this.actions.put(Material.grindstone, new CenteredPointEntity("ttt_random_ammo"));

		// css
		this.actions.put(Material.end_portal_frame, new PlayerSpawnCss(new InfoPlayerT().setRotation(0), false));
		this.actions.put(Material.ender_chest, new PlayerSpawnCss(new InfoPlayerCT().setRotation(180), true));
		return this;
	}

	// requires dummy convererEntites
	private CustomActionManager loadAddables(Collection<ConvertEntity> converters) {
		Stack<Action> loadedAddables = new Stack<>();
		Action[] availables = { new CssLamp(), new Fence(), new Fire(), new Liquid(), new Pane(), new SlabBottom(),
				new SnowBlock(), new LilypadTf2(), new TallGrassTf2(), new DetailBlock(), new EndPortalFrame(),
				new VinesEast(), new VinesNorth(), new VinesSouth(), new VinesWest(), new TorchNorth(),
				new TorchSouth(), new TorchEast(), new TorchWest(), new Torch(), new PlayerSpawnCss(),
				new PlayerSpawnTf2(), new SupplyTf2(), new Debug(), new SlabTop() };
		for (Action addable : availables) {
			Loggger.log("available \t" + addable.getName());
		}
		for (ConvertEntity converter : converters) {
			for (Action available : availables) {
				if (available.getName()
						.equals(converter.getAction()
								.getName())) {
					for (Action a : available.getInstances()) {
						this.setAction(converter.getBlock()
								.get(), available);
						Loggger.log("adding \t" + a.getName());
					}
				}
			}
		}
		return this;
//		loadedAddables.push(new StairsSimple());
	}

}
