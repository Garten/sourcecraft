package source.addable.addable;

import minecraft.Block;
import minecraft.Position;
import minecraft.map.ConverterContext;
import source.addable.ConvertAction;

public class Debug extends ConvertAction {

	public Debug() {
		super.setMaterialUsedFor(new int[] {});
	}

	@Override
	public void add(ConverterContext context, Position position, Block block) {
		this.addDebugMarker(context, position, block);
	}

}
