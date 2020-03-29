package cuboidFinder;

import minecraft.Position;
import minecraft.map.DefaultMinecraftMapConverter;

public class SubBlockMapCuboidFinder extends AbstractCuboidFinder {

	public SubBlockMapCuboidFinder(DefaultMinecraftMapConverter map) {
		super(map);
	}

	@Override
	protected boolean blockNotValid(int xTest, int yTest, int zTest, int... materials) {
		return (!this.map.hasSubMaterial(new Position(xTest, yTest, zTest), materials));
	}

}
