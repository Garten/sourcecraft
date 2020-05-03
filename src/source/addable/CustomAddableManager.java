package source.addable;

import java.util.Stack;

import basic.Loggger;
import minecraft.map.BlockConverterContext;
import source.addable.addable.Cactus;
import source.addable.addable.CssLamp;
import source.addable.addable.Debug;
import source.addable.addable.DetailBlock;
import source.addable.addable.EndPortalFrame;
import source.addable.addable.Fence;
import source.addable.addable.Fire;
import source.addable.addable.Liquid;
import source.addable.addable.Pane;
import source.addable.addable.PlayerSpawnCss;
import source.addable.addable.SnowBlock;
import source.addable.addable.StairsSimple;
import source.addable.addable.stairs.SlabBottom;
import source.addable.addable.stairs.SlabTop;
import source.addable.addable.tf2.LilypadTf2;
import source.addable.addable.tf2.PlayerSpawnTf2;
import source.addable.addable.tf2.SupplyTf2;
import source.addable.addable.tf2.TallGrassTf2;
import source.addable.addable.torch.Torch;
import source.addable.addable.torch.TorchEast;
import source.addable.addable.torch.TorchNorth;
import source.addable.addable.torch.TorchSouth;
import source.addable.addable.torch.TorchWest;
import source.addable.addable.ttt.TttEntites;
import source.addable.addable.vines.VinesEast;
import source.addable.addable.vines.VinesNorth;
import source.addable.addable.vines.VinesSouth;
import source.addable.addable.vines.VinesWest;

public class CustomAddableManager extends AddableManager {

	public CustomAddableManager(BlockConverterContext map, String[] addablesString) {
		this.setConverterContext(map);
		this.setAddables(this.loadAddables(addablesString));
		this.finishSetup();
	}

	private Stack<Addable> loadAddables(String[] addablesString) {
		Stack<Addable> loadedAddables = new Stack<>();
		if (addablesString == null) {
			return loadedAddables;
		}
		Addable[] available = { new Cactus(), new CssLamp(), new Fence(), new Fire(), new Liquid(), new Pane(),
				new SlabBottom(), new SnowBlock(), new LilypadTf2(), new TallGrassTf2(), new DetailBlock(),
				new EndPortalFrame(), new VinesEast(), new VinesNorth(), new VinesSouth(), new VinesWest(),
				new TorchNorth(), new TorchSouth(), new TorchEast(), new TorchWest(), new Torch(), new PlayerSpawnCss(),
				new PlayerSpawnTf2(), new SupplyTf2(), new Debug(), new TttEntites(), new SlabTop() };
		for (Addable addable : available) {
			Loggger.log("available \t" + addable.getName());
		}
		for (Addable potentialAddable : available) {
			for (String toBeAdded : addablesString) {
				if (potentialAddable.getName()
						.equals(toBeAdded)) {
					for (Addable a : potentialAddable.getInstances()) {
						Loggger.log("adding \t" + a.getName());
						loadedAddables.push(a);
					}
				}
			}
		}
		loadedAddables.push(new StairsSimple());
		return loadedAddables;
	}

}
