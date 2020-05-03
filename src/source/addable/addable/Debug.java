package source.addable.addable;

import minecraft.Position;
import source.addable.Addable;

public class Debug extends Addable {

	public Debug() {
		super.setMaterialUsedFor(new int[] {});
	}

	@Override
	public void add(Position position, int material) {
		this.addDebugMarker(position, material);
	}

}
