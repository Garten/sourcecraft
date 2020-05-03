package source.addable.addable.tf2;

import java.util.LinkedList;

import basic.Loggger;
import minecraft.Position;
import source.Material;
import source.addable.Addable;
import vmfWriter.Orientation;
import vmfWriter.Solid;
import vmfWriter.entity.Tf2Team;
import vmfWriter.entity.pointEntity.pointEntity.PropDynamic;
import vmfWriter.entity.solidEntity.FuncRegenerate;

/**
 *
 *
 */
public class SupplyTf2 extends Addable {

	private static final String MODEL = "models/props_gameplay/resupply_locker.mdl";
	private static final int DISTANCE_FROM_WALL = 16;

	private Orientation orientation;
	private PropDynamic prop;

	public SupplyTf2() {
		int[] temp = { Material.CHEST$NORTH, Material.CHEST$SOUTH, Material.CHEST$WEST, Material.CHEST$EAST };
		super.setMaterialUsedFor(temp);
	}

	public SupplyTf2(int material, Orientation orientation) {
		super.setMaterialUsedFor(material);
		this.setPropDynamic(orientation);
	}

	@Override
	public Iterable<Addable> getInstances() {
		LinkedList<Addable> list = new LinkedList<>();
		list.add(new SupplyTf2(Material.CHEST$NORTH, Orientation.NORTH));
		list.add(new SupplyTf2(Material.CHEST$SOUTH, Orientation.SOUTH));
		list.add(new SupplyTf2(Material.CHEST$EAST, Orientation.EAST));
		list.add(new SupplyTf2(Material.CHEST$WEST, Orientation.WEST));
		return list;
	}

	@Override
	public void add(Position p, int material) {
		Loggger.log(this.getName() + " at " + p.getString());
		Position end = this.cuboidFinder.getBestXZ(p, material);
		Position point = new Position(p);
		this.map.setPointToGrid(p);
		this.moveToMiddle(point, point); // TODO
		PropDynamic createdProp = (PropDynamic) this.map.addPointEntity(this.prop);
		this.map.addSolidEntity(new FuncRegenerate(this.createArea(p, end), Tf2Team.ANY, createdProp));
		this.map.markAsConverted(p, end);
	}

	private Solid createArea(Position p, Position end) {
		return this.map.createCuboid(p, end.getOffset(0, 2, 0), 0);
	}

	private void moveToMiddle(Position p, Position end) {
		switch (this.orientation) {
		case NORTH:
			this.map.movePointInGridDimension((end.x - p.x + 1) / 2, 0, 1);
			this.map.movePointExactly(new Position(0, 0, -SupplyTf2.DISTANCE_FROM_WALL));
			break;
		case SOUTH:
			this.map.movePointInGridDimension((end.x - p.x + 1) / 2, 0, 0);
			this.map.movePointExactly(new Position(0, 0, SupplyTf2.DISTANCE_FROM_WALL));
			break;
		case EAST:
			this.map.movePointInGridDimension(0, 0, (end.z - p.z + 1) / 2);
			this.map.movePointExactly(new Position(SupplyTf2.DISTANCE_FROM_WALL, 0, 0));
			break;
		case WEST:
			this.map.movePointInGridDimension(1, 0, (end.z - p.z + 1) / 2);
			this.map.movePointExactly(new Position(-SupplyTf2.DISTANCE_FROM_WALL, 0, 0));
			break;
		default:
			break;
		}
	}

	private void setPropDynamic(Orientation orientation) {
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
