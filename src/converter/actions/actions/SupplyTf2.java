package converter.actions.actions;

import basic.Loggger;
import converter.Orientation;
import converter.actions.Action;
import converter.mapper.Mapper;
import minecraft.Block;
import minecraft.Position;
import vmfWriter.Solid;
import vmfWriter.entity.Tf2Team;
import vmfWriter.entity.pointEntity.pointEntity.PropDynamic;
import vmfWriter.entity.solidEntity.FuncRegenerate;

/**
 *
 *
 */
public class SupplyTf2 extends Action {

	private static final String MODEL = "models/props_gameplay/resupply_locker.mdl";
	private static final int DISTANCE_FROM_WALL = 16;

	private Orientation orientation;
	private PropDynamic prop;

//	public SupplyTf2() {
//		int[] temp = { MaterialLegacy.CHEST$NORTH, MaterialLegacy.CHEST$SOUTH, MaterialLegacy.CHEST$WEST,
//				MaterialLegacy.CHEST$EAST };
//		super.setMaterialUsedFor(temp);
//	}

//	public SupplyTf2(Block material, Orientation orientation) {
//		super.setMaterialUsedFor(material);
//		this.setPropDynamic(orientation);
//	}
//
//	@Override
//	public Iterable<ConvertAction> getInstances() {
//		LinkedList<ConvertAction> list = new LinkedList<>();
////		list.add(new SupplyTf2(MaterialLegacy.CHEST$NORTH, Orientation.NORTH));
////		list.add(new SupplyTf2(MaterialLegacy.CHEST$SOUTH, Orientation.SOUTH));
////		list.add(new SupplyTf2(MaterialLegacy.CHEST$EAST, Orientation.EAST));
////		list.add(new SupplyTf2(MaterialLegacy.CHEST$WEST, Orientation.WEST));
//		return list;
//	}

	@Override
	public void add(Mapper context, Position p, Block material) {
		Loggger.log(this.getName() + " at " + p.getString());
		Position end = context.getCuboidFinder()
				.getBestXZ(p, material);
		Position point = new Position(p);
		context.setPointToGrid(p);
		this.moveToMiddle(context, point, point); // TODO
		PropDynamic createdProp = (PropDynamic) context.addPointEntity(this.prop);
		context.addSolidEntity(new FuncRegenerate(this.createArea(context, p, end), Tf2Team.ANY, createdProp));
		context.markAsConverted(p, end);
	}

	private Solid createArea(Mapper context, Position p, Position end) {
		return context.createCuboid(p, end.getOffset(0, 2, 0), null);
	}

	private void moveToMiddle(Mapper context, Position p, Position end) {
		switch (this.orientation) {
		case NORTH:
			context.movePointInGridDimension((end.x - p.x + 1) / 2, 0, 1);
			context.movePointExactly(new Position(0, 0, -SupplyTf2.DISTANCE_FROM_WALL));
			break;
		case SOUTH:
			context.movePointInGridDimension((end.x - p.x + 1) / 2, 0, 0);
			context.movePointExactly(new Position(0, 0, SupplyTf2.DISTANCE_FROM_WALL));
			break;
		case EAST:
			context.movePointInGridDimension(0, 0, (end.z - p.z + 1) / 2);
			context.movePointExactly(new Position(SupplyTf2.DISTANCE_FROM_WALL, 0, 0));
			break;
		case WEST:
			context.movePointInGridDimension(1, 0, (end.z - p.z + 1) / 2);
			context.movePointExactly(new Position(-SupplyTf2.DISTANCE_FROM_WALL, 0, 0));
			break;
		default:
			break;
		}
	}

	public void setPropDynamic(Orientation orientation) {
		this.orientation = orientation;
		this.prop = new PropDynamic(SupplyTf2.MODEL, 0, this.getAngleY(orientation), 0);
	}

	private int getAngleY(Orientation orientation) {
		switch (orientation) {
		case NORTH:
			return 90;
		case SOUTH:
			return 270;
		case EAST:
			return 0;
		case WEST:
			return 180;
		default:
			return 0;
		}
	}
}
