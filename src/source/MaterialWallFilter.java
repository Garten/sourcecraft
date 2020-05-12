package source;

import java.util.function.Predicate;

import minecraft.Block;
import source.addable.ConvertAction;
import source.addable.ConvertActions;
import vmfWriter.Orientation;

public class MaterialWallFilter implements Predicate<Block> {

	private ConvertActions manager;
	private Orientation orientation;

	public MaterialWallFilter(ConvertActions manager, Orientation orientation) {
		this.manager = manager;
		this.orientation = orientation;
	}

	@Override
	public boolean test(Block material) {
		ConvertAction addable = this.manager.getAction(material);
		if (addable != null) {
			return this.manager.getAction(material)
					.hasWall(this.orientation);
		} else {
			return false;
		}
	}
}
