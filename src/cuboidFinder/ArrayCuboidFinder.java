package cuboidFinder;

import minecraft.Position;
import minecraft.map.DefaultMinecraftMapConverter;

public class ArrayCuboidFinder extends AbstractCuboidFinder {

	public ArrayCuboidFinder(DefaultMinecraftMapConverter map) {
		super(map);
	}

	@Override
	protected boolean blockNotValid(int xTest, int yTest, int zTest, int... material) {
		Position test = new Position(xTest, yTest, zTest);
		return (this.map.hasMaterial(test, material) == false || this.map.isNextToAir(test) == false);
	}

}
