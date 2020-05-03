package minecraft.map;

import java.io.File;
import java.io.IOException;

import basic.Loggger;
import basic.RunnableWith;
import basic.Tuple;
import main.ConverterData;
import minecraft.Area;
import minecraft.ConvertingReport;
import minecraft.Position;
import minecraft.SubBlockPosition;
import periphery.ConvertOption;
import periphery.SourceGame;
import source.Material;
import source.SkinManager;
import source.addable.CustomAddableManager;
import vmfWriter.Cuboid;
import vmfWriter.Free8Point;
import vmfWriter.Orientation;
import vmfWriter.Ramp;
import vmfWriter.Skin;
import vmfWriter.entity.pointEntity.PointEntity;
import vmfWriter.entity.pointEntity.pointEntity.LightEnvironment;
import vmfWriter.entity.pointEntity.pointEntity.ShadowControl;

public class BlockConverterContext extends ConverterContext {

	private final Area bound;

	private final Position arraySize;

	private int[][][] materialField;
	private boolean[][][] isNextToAir;

	private boolean isAirBlock[][][];
	private final ConvertOption convertOption;

	private SubblockConverterContext subBlocks;

	public BlockConverterContext(ConverterData converterData, ExtendedSourceMap target) {
		this.setTarget(target);

		this.convertOption = converterData.getOption();
		super.target.setSkyTexture(this.convertOption.getSkyTexture());

		this.setScale(this.convertOption.getScale());

		this.setAddableManager(new CustomAddableManager(this, this.convertOption.getAddablesAsStrings()));

		this.bound = converterData.getPlace()
				.createBound();

		Position start = converterData.getPlace()
				.getStart();
		Position end = converterData.getPlace()
				.getEnd();

		this.arraySize = new Position(3 + end.getX() - start.getX(), 3 + end.getY() - start.getY(),
				3 + end.getZ() - start.getZ());

		this.isNextToAir = this.createBooleanArray(this.arraySize);
		this.isAirBlock = this.createBooleanArray(this.arraySize);

		this.materialField = this.createIntArray(this.arraySize);

		this.forAllPositions(p -> {
			this.materialField[p.x][p.y][p.z] = Material._UNSET;
			this.isNextToAir[p.x][p.y][p.z] = true;
		});

		this.subBlocks = new SubblockConverterContext(target, this.getScale());
	}

	private boolean[][][] createBooleanArray(Position size) {
		return new boolean[size.getX()][size.getY()][size.getZ()];
	}

	private int[][][] createIntArray(Position size) {
		return new int[size.getX()][size.getY()][size.getZ()];
	}

	public void markBorder() {
		// top + bottom
		for (int x = 0; x < this.arraySize.getX(); x++) {
			for (int z = 0; z < this.arraySize.getZ(); z++) {
				this.isNextToAir[x][0][z] = true;
				this.isNextToAir[x][this.arraySize.getY() - 1][z] = true;
			}
		}
		// sides
		for (int y = 1; y < this.arraySize.getY() - 2; y++) {
			for (int x = 0; x < this.arraySize.getX(); x++) {
				this.isNextToAir[x][y][0] = true;
				this.isNextToAir[x][y][this.arraySize.getZ() - 1] = true;
			}
			for (int z = 1; z < this.arraySize.getZ() - 1; z++) {
				this.isNextToAir[0][y][z] = true;
				this.isNextToAir[this.arraySize.getX() - 1][y][z] = true;
			}
		}
	}

	public void forAllPositions(RunnableWith<Position> execute) {
		this.forPositions(new Position(1, 1, 1), this.arraySize.getOffset(-2, -2, -2), execute);
	}

	public void forPositions(Position start, Position end, RunnableWith<Position> execute) {
		for (int y = start.y; y <= end.y; y++) {
			for (int x = start.x; x <= end.x; x++) {
				for (int z = start.z; z <= end.z; z++) {
					execute.run(new Position(x, y, z));
				}
			}
		}
	}

	@Override
	public void addObjects(SourceMap target, SourceGame game) {
		this.setAirBlocks();
		Loggger.log("run");
		this.markBorder(); // temp fix
		this.forAllPositions(position -> {
			if (this.isNextToAir(position) == true) {
				this.addableManager.add(position, this.getMaterial(position));
			}
		});
		this.subBlocks.addObjects(target, game);
		this.addSkyShell();
		this.addSpawnPoint(game);
	}

	/**
	 * Adds a spawn point in the middle of the map
	 */
	private void addSpawnPoint(SourceGame game) {
		Position middle = Position.getMean(new Position(1, 1, 1), this.arraySize.getOffset(-1, -1, -1));
		Position middleX = middle.getOffset(-1, 0, 0);
		Position middleZ = middle.getOffset(0, 0, -1);
		Position middleXZ = middle.getOffset(-1, 0, -1);
		int y = this.arraySize.getY() - 2;
		for (; y > 1; y--) {
			middle.setY(y);
			middleX.setY(y);
			middleZ.setY(y);
			middleXZ.setY(y);
			if (!(this.isAir(middle) && this.isAir(middleX) && this.isAir(middleZ) && this.isAir(middleXZ))) {
				break;
			}
		}
		middle.setY(y + 1);
		Position converted = this.convert(middle);
		converted.move(0, 0, 10);
		this.addPointEntity(game.getSpawnEntity(), converted);
		this.setCameraPosition(converted.move(0, 0, 72));
		this.setCameraLook(new Position(0, 0, 0));
	}

	private void setAirBlocks() {
		this.forAllPositions(position -> {
			if (this.isAirBlockInitiate(position)) {
				this.setAirBlock(position, true);
				this.markNeighbors(position);
			} else {
				this.setAirBlock(position, false);
			}
		});
		this.markBorder();
	}

	// marks neighbor blocks and itself to be needed
	public void markNeighbors(Position position) {
		int x = position.getX();
		int y = position.getY();
		int z = position.getZ();
		this.isNextToAir[x][y][z] = true;
		this.isNextToAir[x + 1][y][z] = true;
		this.isNextToAir[x - 1][y][z] = true;
		this.isNextToAir[x][y + 1][z] = true;
		this.isNextToAir[x][y - 1][z] = true;
		this.isNextToAir[x][y][z + 1] = true;
		this.isNextToAir[x][y][z - 1] = true;
	}

	@Override
	public boolean isAirBlock(Position position) {
		return this.isAirBlock[position.getX()][position.getY()][position.getZ()];
	}

	private void setAirBlock(Position position, boolean value) {
		this.isAirBlock[position.x][position.y][position.z] = value;
	}

	@Override
	public void markAsConverted(Position start, Position end) {
		this.forPositions(start, end, this::markAsConverted);
	}

	@Override
	public void markAsConverted(Position p) {
		this.materialField[p.x][p.y][p.z] = -this.materialField[p.x][p.y][p.z];
	}

	@Override
	public int getMaterial(Position p) {
		return this.materialField[p.getX()][p.getY()][p.getZ()];
	}

	@Override
	public boolean isNextToAir(Position p) {
		return this.isNextToAir[p.getX()][p.getY()][p.getZ()];
	}

	@Override
	public void addSubBlock(Position position, SubBlockPosition pos, int materialInt) {
		int x = position.getX();
		int y = position.getY();
		int z = position.getZ();
		// TODO check if already set
		Integer material = Integer.valueOf(materialInt);
		x = x * 2 + 1;
		y = y * 2 + 1;
		z = z * 2 + 1;
		Position point;
		switch (pos) {
		case BOTTOM_EAST_SOUTH:
			point = new Position(x, y - 1, z);
			break;
		case BOTTOM_EAST_NORTH:
			point = new Position(x, y - 1, z - 1);
			break;
		case BOTTOM_WEST_SOUTH:
			point = new Position(x - 1, y - 1, z);
			break;
		case BOTTOM_WEST_NORTH:
			point = new Position(x - 1, y - 1, z - 1);
			break;
		case TOP_EAST_SOUTH:
			point = new Position(x, y, z);
			break;
		case TOP_EAST_NORTH:
			point = new Position(x, y, z - 1);
			break;
		case TOP_WEST_SOUTH:
			point = new Position(x - 1, y, z);
			break;
		case TOP_WEST_NORTH:
			point = new Position(x - 1, y, z - 1);
			break;
		default:
			point = new Position(1, 1, 1);
			Loggger.warn("default case of subBlocks occured");
			break;
		}
		this.subBlocks.setMaterial(point, material);
	}

	public ConvertingReport write(File file, SourceGame game) throws IOException {
		this.addObjects(this.target, game);
		this.target.write(file, game);
		return null;
	}

	/**
	 * adds skybox, light_environment and shadow_control
	 */
	public void addSkyShell() {
		int verticalHeight = this.bound.getHigher()
				.getY()
				- this.bound.getLower()
						.getY()
				+ 3;

		Position aPoint = new Position(0, -this.arraySize.getZ(), 0);
		Position hollow = new Position(1, -this.arraySize.getZ() + 1, 1);
		Position gPoint = new Position(this.arraySize.getX(), 0, verticalHeight + 2);
		aPoint.scale(this.getScale());
		hollow.scale(this.getScale());
		gPoint.scale(this.getScale());
		this.createHollow(aPoint, hollow, gPoint, SkinManager.SKYBOX);

		aPoint = new Position(-this.getScale() * 4, 0, (verticalHeight + 2) * this.getScale());
		hollow = new Position(-this.getScale() * 3, this.getScale() * 1, (verticalHeight + 2 + 1) * this.getScale());
		gPoint = new Position(0, this.getScale() * 4, (verticalHeight + 2 + 4) * this.getScale());
		this.createHollow(aPoint, hollow, gPoint, SkinManager.SKYBOX);

		// addSun
		Position p = new Position(-this.getScale() * 2, this.getScale() * 2, (verticalHeight + 4) * this.getScale());
		this.target.addPointEntity(p,
				new LightEnvironment(p, this.convertOption.getSunLight(), this.convertOption.getSunAmbient()));
		p.move(this.getScale() / 2, 0, 0);
		this.target.addPointEntity(p, new ShadowControl(p, this.convertOption.getSunShadow()));
	}

	private void createHollow(Position aPoint, Position hollow, Position gPoint, Skin skin) {
		int hollowX = hollow.getX() - aPoint.getX();
		int hollowY = hollow.getY() - aPoint.getY();
		int hollowZ = hollow.getZ() - aPoint.getZ();
		// bottom
		this.target.addSolid(new Cuboid(
				new Tuple<>(aPoint, new Position(gPoint.getX(), gPoint.getY(), aPoint.getZ() + hollowZ)), skin));
		// top
		this.target.addSolid(new Cuboid(
				new Tuple<>(new Position(aPoint.getX(), aPoint.getY(), gPoint.getZ() - hollowZ), gPoint), skin));

		// x side
		this.target.addSolid(new Cuboid(new Tuple<>(aPoint.getOffset(0, 0, hollowZ),
				new Position(aPoint.getX() + hollowX, gPoint.getY(), gPoint.getZ() - hollowZ)), skin));
		this.target.addSolid(
				new Cuboid(new Tuple<>(new Position(gPoint.getX() - hollowX, aPoint.getY(), aPoint.getZ() + hollowZ),
						gPoint.getOffset(0, 0, -hollowZ)), skin));
		// y side
		this.target.addSolid(new Cuboid(
				new Tuple<>(aPoint.getOffset(hollowX, 0, hollowZ),
						new Position(gPoint.getX() - hollowX, aPoint.getY() + hollowY, gPoint.getZ() - hollowZ)),
				skin));
		this.target.addSolid(new Cuboid(
				new Tuple<>(new Position(aPoint.getX() + hollowX, gPoint.getY() - hollowY, aPoint.getZ() + hollowZ),
						gPoint.getOffset(-hollowX, 0, -hollowZ)),
				skin));
	}

	@Override
	public void addPointEntitys(Position start, Position end, int space, PointEntity type) {
		Tuple<Position, Position> conveted = this.convertPositions(start, end);
		super.target.addPointEntitys(conveted.getFirst(), conveted.getSecond(), space, type);
	}

	@Override
	public void setMaterial(Position p, int material) {
		this.materialField[p.getX()][p.getY()][p.getZ()] = material;
	}

	@Override
	public Free8Point createFree8Point(Position start, Position end, int parts, Position[] offset, boolean align,
			int material) {
		double grid = ((double) (this.getScale())) / (double) (parts);
		Position[] p = new Position[8];
		for (int i = 0; i < 8; i++) {
			int newX = (int) (start.x * this.getScale() + offset[i].x * grid);
			int newY = (int) -(start.z * this.getScale() + offset[i].z * grid);
			int newZ = (int) ((start.y + end.y - start.y) * this.getScale() + offset[i].y * grid);
			p[i] = new Position(newX, newY, newZ);
		}
		Skin skin = SkinManager.INSTANCE.getSkin(material);
		Free8Point shape = new Free8Point(p, skin, align);
		return shape;
	}

	@Override
	public Ramp createRampCuttet(Cuboid cuboid, Orientation orientation, Orientation cut1, Orientation cut2) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setIsNextToAir(Position position, boolean value) {
		this.isNextToAir[position.x][position.y][position.z] = value;
	}

}
