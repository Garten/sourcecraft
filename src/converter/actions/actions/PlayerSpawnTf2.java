package converter.actions.actions;

import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Position;
import vmfWriter.entity.pointEntity.RotateablePointEntity;
import vmfWriter.entity.solidEntity.Buyzone;

public class PlayerSpawnTf2 extends Action {

	private final static int SPACE = 50;

	private RotateablePointEntity type;
	private boolean police;

	public PlayerSpawnTf2() {

	}

//	public PlayerSpawnTf2(Block material, RotateablePointEntity type, boolean police) {
//		super.setMaterialUsedFor(material);
//		this.type = type;
//		this.police = police;
//	}
//
//	@Override
//	public Iterable<ConvertAction> getInstances() {
//		RotateablePointEntity redSpawn = new InfoPlayerTeamSpawn().setTeamNum(Tf2Team.RED)
//				.setRotation(0);
//		RotateablePointEntity blueSpawn = new InfoPlayerTeamSpawn().setTeamNum(Tf2Team.BLUE)
//				.setRotation(180);
//		LinkedList<ConvertAction> list = new LinkedList<>();
//		list.add(new PlayerSpawnTf2(MaterialLegacy.END_PORTAL_FRAME, redSpawn, false));
//		list.add(new PlayerSpawnTf2(MaterialLegacy.ENDER_CHEST$NORTH, blueSpawn, true));
//		list.add(new PlayerSpawnTf2(MaterialLegacy.ENDER_CHEST$EAST, blueSpawn, true));
//		list.add(new PlayerSpawnTf2(MaterialLegacy.ENDER_CHEST$SOUTH, blueSpawn, true));
//		list.add(new PlayerSpawnTf2(MaterialLegacy.ENDER_CHEST$WEST, blueSpawn, true));
//		return list;
//	}

	@Override
	public void add(Mapper context, Position p, Block material) {
		Position end = context.getCuboidFinder()
				.getBestXZ(p, material);
		context.addPointEntitys(p, end, PlayerSpawnTf2.SPACE, this.type);
		context.markAsConverted(p, end);
		end.move(0, 2, 0);
		context.addSolidEntity(new Buyzone(context.createCuboid(p, end, null), this.police));
	}
}
