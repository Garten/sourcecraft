package source.addable;

import java.util.LinkedList;
import java.util.List;

import basic.Loggger;
import minecraft.Block;
import minecraft.Position;
import minecraft.map.ConverterContext;
import vmfWriter.Orientation;
import vmfWriter.entity.pointEntity.RotateablePointEntity;

public abstract class ConvertAction {

	protected int[] materialUsedFor = {};

	public ConvertAction() {
	}

	public Iterable<ConvertAction> getInstances() {
		List<ConvertAction> list = new LinkedList<>();
		try {
			ConvertAction a = (ConvertAction) this.getClass()
					.getConstructors()[0].newInstance();
			list.add(a);
		} catch (Exception ex) {
			Loggger.warn("Addable " + this.getClass()
					.getSimpleName() + " does not have a suitable constructor (InvocationTargetException)");
		}
		return list;
	}

	public String getName() {
		return this.getClass()
				.getSimpleName();
	}

	/**
	 * Returns whether the added blocks are air.
	 */
	public boolean isAirBlock() {
		return true;
	}

	public boolean hasWall(Orientation orientation) {
		return false;
	}

	@Deprecated
	public void setMaterialUsedFor(int[] material) {
		this.materialUsedFor = material;
	}

	public void setMaterialUsedFor(int material) {
		this.materialUsedFor = new int[1];
		this.materialUsedFor[0] = material;
	}

	/**
	 * For a given position with given material, this method adds solids and
	 * entities to the resulting Source map.
	 *
	 * @param position
	 * @param material
	 */
	public void add(ConverterContext context, Position position, Block material) {

	}

	protected void addDebugMarker(ConverterContext context, Position position, Block material) {
		context.setPointToGrid(position);
		context.movePointInGridDimension(0.5, 0, 0.5);
		int verticalAngle = (int) (Math.random() * 360);
		context.addPointEntity(new RotateablePointEntity().setName(material.getName())
				.setRotation(verticalAngle));
		context.markAsConverted(position);
	}
}
