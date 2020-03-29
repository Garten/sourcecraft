package source;

import source.addable.Addable;
import source.addable.AddableManager;
import vmfWriter.Orientation;

public class MaterialWallFilter implements MaterialFilter {

	private AddableManager manager;
	private Orientation orientation;

	public MaterialWallFilter(AddableManager manager, Orientation orientation) {
		this.manager = manager;
		this.orientation = orientation;
	}

	@Override
	public boolean filter(int material) {
		Addable addable = this.manager.getAddable(material);
		if (addable != null) {
			return this.manager.getAddable(material)
					.hasWall(this.orientation);
		} else {
			return false;
		}
	}
}
