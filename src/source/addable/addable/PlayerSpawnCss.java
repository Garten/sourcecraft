package source.addable.addable;

import minecraft.Block;
import minecraft.Position;
import minecraft.map.ConverterContext;
import source.addable.ConvertAction;
import vmfWriter.entity.pointEntity.RotateablePointEntity;
import vmfWriter.entity.solidEntity.Buyzone;

public class PlayerSpawnCss extends ConvertAction {

	private final static int SPACE = 40;

	private RotateablePointEntity type;
	private boolean police;

	public PlayerSpawnCss() {

	}

	public PlayerSpawnCss(RotateablePointEntity type, boolean police) {
		this.type = type;
		this.police = police;
	}

	@Override
	public void add(ConverterContext context, Position p, Block material) {
		Position end = context.getCuboidFinder()
				.getBestXZ(p, material);
		context.addPointEntitys(p, end, PlayerSpawnCss.SPACE, this.type);
		end.move(0, 2, 0);
		context.addSolidEntity(new Buyzone(context.createCuboid(p, end, null), this.police));
		context.markAsConverted(p, end);
	}
}
