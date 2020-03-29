package minecraft.map;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

import basic.Loggger;
import minecraft.ConvertingReport;
import minecraft.Position;
import periphery.TexturePack;
import vmfWriter.Solid;
import vmfWriter.ValveElement;
import vmfWriter.ValveWriter;
import vmfWriter.entity.pointEntity.PointEntity;
import vmfWriter.entity.pointEntity.pointEntity.CustomPointEntity;
import vmfWriter.entity.solidEntity.FuncDetail;
import vmfWriter.entity.solidEntity.SolidEntity;

public class DefaultSourceMap implements ExtendedSourceMap {

	private Position cameraPosition = new Position(0, 0, 0);
	private Position cameraLook = new Position(10, -10, 0);

	private String SKY_TEXTURE = "sky_day02_09";
	private int hammerGridSize;

	private Stack<Solid> solids;
	private Stack<PointEntity> pointEntities;
	private Stack<SolidEntity> solidEntities;
	private int scale;

	public Position localPoint;

	public DefaultSourceMap(TexturePack texturePack, int optionScale) {
		this.localPoint = new Position();
		this.solids = new Stack<>();
		this.pointEntities = new Stack<>();
		this.solidEntities = new Stack<>();

		this.scale = optionScale;
		this.hammerGridSize = optionScale;
	}

	@Override
	public void setSkyTexture(String skyTexture) {
		this.SKY_TEXTURE = skyTexture;
	}

	@Override
	public void addSolid(Solid solid) {
		this.solids.push(solid);
	}

	@Override
	public void addDetail(Solid... solids) {
		this.solidEntities.push(new FuncDetail(solids));
	}

	// @Override
	// public Ramp createRamp(Cuboid cuboid, Orientation orientation) {
	// return new Ramp(cuboid, orientation);
	// }
	//
	// @Override
	// public Ramp createRampCuttet(Cuboid cuboid, Orientation orientation,
	// Orientation cut1, Orientation cut2) {
	// Ramp result = new Ramp(cuboid, orientation);
	// result.cut(cut1);
	// result.cut(cut2);
	// return result;
	// }

	@Override
	public void addSolidEntity(SolidEntity solidEnttiy) {
		this.solidEntities.add(solidEnttiy);
	}

	@Override
	public PointEntity addPointEntity(PointEntity type) {
		PointEntity finalObject = type.create(this.localPoint);
		this.pointEntities.push(finalObject);
		return finalObject;
	}

	@Override
	public PointEntity addPointEntity(PointEntity type, Position position) {
		PointEntity finalObject = type.create(position);
		this.pointEntities.push(finalObject);
		return finalObject;
	}

	@Override
	public void addPointEntity(Position origin, PointEntity type) {
		this.pointEntities.push(type.create(origin));
	}

	@Override
	public void addPointEntitys(Position start, Position end, int space, PointEntity type) {
		Loggger.log("Adding point entities of type " + type.getName() + " from positions " + start.toString() + " to " + end.toString() + ".");

		Position point = new Position(start);
		point.move(space / 2, space / 2, space / 2);
		while (point.getX() <= end.getX()) {
			int distance = 0;
			while (point.getY() <= end.getY()) {
				this.addPointEntity(point, type);
				point.move(0, space, 0);
				distance++;
			}
			point.move(space, -distance * space, 0);
		}
	}

	@Override
	public ConvertingReport write(File file) throws IOException {
		if (!file.getParentFile()
				.exists()) {
			if (file.getParentFile()
					.mkdirs()) {
				Loggger.log("Successfully created " + file.getParentFile());
			} else {
				Loggger.log("Failed to create " + file.getParentFile());
			}
		}

		ValveWriter w = new ValveWriter(file);

		Loggger.log("writing");
		this.writeHeader(w);

		int brushCount = 0;
		while (this.solids.empty() == false) {
			brushCount++;
			this.solids.pop()
					.writeVmf(w);
		}
		ConvertingReport report = new ConvertingReport();
		report.setBrushCount(brushCount);
		Loggger.log(brushCount + "/8192 brushes added"); // TODO
		w.close();
		this.write(this.solidEntities, w);
		this.write(this.pointEntities, w);
		this.writeTail(w);
		w.finish();
		return report;
	}

	private void write(Stack<? extends ValveElement> stack, ValveWriter w) throws IOException {
		while (stack.empty() == false) {
			stack.pop()
					.writeVmf(w);
		}
	}

	private void writeHeader(ValveWriter writer) throws IOException {
		writer.open("versioninfo")
				.put("editorversion", 400)
				.put("editorbuild", 6920)
				.put("mapversion", 1)
				.put("formatversion", 100)
				.put("prefab", 0)
				.close();
		writer.open("visgroups")
				.close();
		writer.open("viewsettings")
				.put("bSnapToGrid", true)
				.put("bShowGrid", true)
				.put("bShowLogicalGrid", false)
				.put("nGridSpacing", this.hammerGridSize)
				.put("bShow3DGrid", false)
				.close();
		writer.open("world")
				.put("id", 1)
				.put("mapversion", 1)
				.put("classname", "worldspawn")
				.put("skyname", this.SKY_TEXTURE)
				.put("maxpropscreenwidth", -1)
				.put("detailvbsp", "detail.vbsp")
				.put("detailmaterial", "detail/detailsprites");
		// do not close
	}

	private void writeTail(ValveWriter w) throws IOException {
		w.open("cameras")
				.put("activecamera", 0)
				.open("camera")
				.put("position", this.cameraPosition.toBracketString())
				.put("look", this.cameraLook.toBracketString())
				.close()
				.close(); // close from header
		w.open("cordon")
				.put("mins", "(-1024 -1024 -1024)")
				.put("maxs", "(1024 1024 1024)")
				.put("active", 0)
				.close();
	}

	@Override
	public void setPointToGrid(Position position) {
		this.localPoint.setTo(position);
	}

	// decorated by DefaultMinecraftMap
	@Override
	public void movePointInGridDimension(double x, double y, double z) {
		this.localPoint.move((int) x, (int) y, (int) z);
	}

	// decorated by DefaultMinecraftMap
	@Override
	public void movePointExactly(Position offset) {
		this.localPoint.move(offset.x, offset.y, offset.z);
	}

	@Override
	public void setScale(int scale) {
		this.scale = scale;
	}

	@Override
	public int getScale() {
		return this.scale;
	}

	@Override
	public Position getLocalPoint() {
		return this.localPoint;
	}

	@Override
	public void addCustomPointEntity(Position p, String name) {
		this.pointEntities.push(new CustomPointEntity(p, name));
	}

	@Override
	public void setCameraPosition(Position origin) {
		this.cameraPosition = origin;
	}

	@Override
	public void setCameraLook(Position position) {
		this.cameraLook = position;
	}
}
