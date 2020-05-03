package minecraft.map;

import minecraft.Position;
import vmfWriter.Solid;
import vmfWriter.entity.pointEntity.PointEntity;
import vmfWriter.entity.solidEntity.SolidEntity;

public interface SourceMap {

	public void setSkyTexture(String skyTexture);

	public void addSolid(Solid solid);

	public void addDetail(Solid... solids);

	/**
	 * Adds a point entity and returns the just added point entity.
	 *
	 * @param pointEntity
	 * @return
	 */
	public PointEntity addPointEntity(PointEntity pointEntity);

	public PointEntity addPointEntity(PointEntity entity, Position position);

	public void addPointEntity(Position position, PointEntity type);

	public void addPointEntitys(Position position, Position end, int space, PointEntity type);

	public void addSolidEntity(SolidEntity solidEnttiy);

	public void setPointToGrid(Position position);

	public void movePointInGridDimension(double x, double y, double z);

	public void movePointExactly(Position offset);

	public void setCameraPosition(Position origin);

	public void setCameraLook(Position position);
}
