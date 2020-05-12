package source.addable;

import java.util.Collection;
import java.util.Stack;

import basic.Loggger;
import minecraft.Blocks;
import minecraft.Material;
import minecraft.Property;
import minecraft.map.BlockConverterContext;
import source.addable.addable.Cactus;
import source.addable.addable.CenteredPointEntity;
import source.addable.addable.CssLamp;
import source.addable.addable.Debug;
import source.addable.addable.DetailBlock;
import source.addable.addable.EndPortalFrame;
import source.addable.addable.Fence;
import source.addable.addable.Fire;
import source.addable.addable.Liquid;
import source.addable.addable.NoAction;
import source.addable.addable.Pane;
import source.addable.addable.PlayerSpawnCss;
import source.addable.addable.SlabBottom;
import source.addable.addable.SlabTop;
import source.addable.addable.SnowBlock;
import source.addable.addable.Solid;
import source.addable.addable.Stairs;
import source.addable.addable.tf2.LilypadTf2;
import source.addable.addable.tf2.PlayerSpawnTf2;
import source.addable.addable.tf2.SupplyTf2;
import source.addable.addable.tf2.TallGrassTf2;
import source.addable.addable.torch.Torch;
import source.addable.addable.torch.TorchEast;
import source.addable.addable.torch.TorchNorth;
import source.addable.addable.torch.TorchSouth;
import source.addable.addable.torch.TorchWest;
import source.addable.addable.vines.VinesEast;
import source.addable.addable.vines.VinesNorth;
import source.addable.addable.vines.VinesSouth;
import source.addable.addable.vines.VinesWest;
import vmfWriter.entity.pointEntity.pointEntity.InfoPlayerCT;
import vmfWriter.entity.pointEntity.pointEntity.InfoPlayerT;

public class CustomAddableManager extends ConvertActions {

	public CustomAddableManager(BlockConverterContext map, Collection<ConvertEntity> converters) {
		super(Solid.INSTANCE);

		// temp
//		LinkedList<ConvertEntity> links = new LinkedList<>();
//		links.add(new ConvertEntity(Material.cactus, new Cactus()));
//		links.add(new ConvertEntity(Material.water, new Liquid()));
//		this.loadAddables(links);
	}

	public CustomAddableManager setDefaults() {
		this.actions.put(Blocks._UNSET, NoAction.INSTANCE);
		this.actions.put(Material.air, NoAction.INSTANCE);
		this.actions.put(Material.cave_air, NoAction.INSTANCE);
		this.actions.put(Material.void_air, NoAction.INSTANCE);
		this.actions.put(Material._ore, new Cactus());
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
	private CustomAddableManager loadAddables(Collection<ConvertEntity> converters) {
		Stack<ConvertAction> loadedAddables = new Stack<>();
		ConvertAction[] availables = { new CssLamp(), new Fence(), new Fire(), new Liquid(), new Pane(),
				new SlabBottom(), new SnowBlock(), new LilypadTf2(), new TallGrassTf2(), new DetailBlock(),
				new EndPortalFrame(), new VinesEast(), new VinesNorth(), new VinesSouth(), new VinesWest(),
				new TorchNorth(), new TorchSouth(), new TorchEast(), new TorchWest(), new Torch(), new PlayerSpawnCss(),
				new PlayerSpawnTf2(), new SupplyTf2(), new Debug(), new SlabTop() };
		for (ConvertAction addable : availables) {
			Loggger.log("available \t" + addable.getName());
		}
		for (ConvertEntity converter : converters) {
			for (ConvertAction available : availables) {
				if (available.getName()
						.equals(converter.getAction()
								.getName())) {
					for (ConvertAction a : available.getInstances()) {
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
