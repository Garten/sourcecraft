package converter.mapper;

import java.util.function.Consumer;

import basic.Loggger;
import basic.Tuple;
import converter.SkinManager;
import converter.actions.Action;
import converter.actions.CustomActionManager;
import main.ConvertTask;
import minecraft.Area;
import minecraft.Block;
import minecraft.Blocks;
import minecraft.Position;
import minecraft.SubBlockPosition;
import periphery.ConvertOption;
import periphery.SourceGame;
import vmfWriter.Cuboid;
import vmfWriter.Free8Point;
import vmfWriter.Skin;
import vmfWriter.SourceMap;
import vmfWriter.entity.pointEntity.PointEntity;
import vmfWriter.entity.pointEntity.pointEntity.LightEnvironment;
import vmfWriter.entity.pointEntity.pointEntity.ShadowControl;

public class BlockMapper extends Mapper {

	private final Area bound;

	private final Position arraySize;

	private boolean[][][] isConverted;
	private Block[][][] materialField;
	private boolean[][][] isNextToAir;

	private boolean isAirBlock[][][];
	private final ConvertOption convertOption;

	private SubblockMapper subBlocks;

	public BlockMapper(ConvertTask converterData, SourceMap target) {
		this.setTarget(target);

		this.convertOption = converterData.getOption();
		this.setSkyTexture(this.convertOption.getSkyTexture());

		this.setScale(this.convertOption.getScale());

		this.convertOption.getAddablesAsStrings();
		this.setAddableManager(new CustomActionManager(this, null).setDefaults());

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
		this.isConverted = this.createBooleanArray(this.arraySize);
		this.materialField = this.createBlockArray(this.arraySize);

		this.forAllPositions(p -> {
			this.materialField[p.x][p.y][p.z] = Blocks._UNSET;
			this.isNextToAir[p.x][p.y][p.z] = true;
		});

		this.subBlocks = new SubblockMapper(target, this.getScale());
	}

	private boolean[][][] createBooleanArray(Position size) {
		return new boolean[size.getX()][size.getY()][size.getZ()];
	}

	private Block[][][] createBlockArray(Position size) {
		return new Block[size.getX()][size.getY()][size.getZ()];
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

	public void forAllPositions(Consumer<Position> execute) {
		this.forPositions(new Position(1, 1, 1), this.arraySize.getOffset(-2, -2, -2), execute);
	}

	public void forPositions(Position start, Position end, Consumer<Position> execute) {
		for (int y = start.y; y <= end.y; y++) {
			for (int x = start.x; x <= end.x; x++) {
				for (int z = start.z; z <= end.z; z++) {
					execute.accept(new Position(x, y, z));
				}
			}
		}
	}

	@Override
	public void addObjects(SourceGame game) {
		this.setAirBlocks();
		Loggger.log("run");
		this.markBorder(); // temp fix
		this.forAllPositions(position -> {
			if (this.isNextToAir(position) && (!this.isConverted(position))) {
				this.convertActions.add(this, position, this.getBlock(position));
			}
		});
		this.subBlocks.addObjects(game);
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
			if (!(this.isAirBlock(middle) && this.isAirBlock(middleX) && this.isAirBlock(middleZ)
					&& this.isAirBlock(middleXZ))) {
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
			Block block = this.getBlock(position);
			Action action = this.convertActions.getAction(block);
			if (action.isAirBlock()) {
				this.setAirBlock(position, true);
				this.markNeighbors(position);
			} else {
				this.setAirBlock(position, false);
			}
		});
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
		this.isConverted[p.x][p.y][p.z] = true;
	}

	@Override
	public Block getBlock(Position p) {
		return this.materialField[p.getX()][p.getY()][p.getZ()];
	}

	@Override
	public boolean isNextToAir(Position p) {
		return this.isNextToAir[p.getX()][p.getY()][p.getZ()];
	}

	@Override
	public void addSubBlock(Position position, SubBlockPosition pos, Block block) {
		int x = position.getX();
		int y = position.getY();
		int z = position.getZ();
		x = x * 2 + 1;
		y = y * 2 + 1;
		z = z * 2 + 1;
		Position point = null;
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
			break;
		}
		this.subBlocks.setMaterial(point, block);
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
		this.addPointEntity(p,
				new LightEnvironment(p, this.convertOption.getSunLight(), this.convertOption.getSunAmbient()));
		p.move(this.getScale() / 2, 0, 0);
		this.addPointEntity(p, new ShadowControl(p, this.convertOption.getSunShadow()));
	}

	private void createHollow(Position aPoint, Position hollow, Position gPoint, Skin skin) {
		int hollowX = hollow.getX() - aPoint.getX();
		int hollowY = hollow.getY() - aPoint.getY();
		int hollowZ = hollow.getZ() - aPoint.getZ();
		// bottom
		this.addSolid(new Cuboid(
				new Tuple<>(aPoint, new Position(gPoint.getX(), gPoint.getY(), aPoint.getZ() + hollowZ)), skin));
		// top
		this.addSolid(new Cuboid(
				new Tuple<>(new Position(aPoint.getX(), aPoint.getY(), gPoint.getZ() - hollowZ), gPoint), skin));

		// x side
		this.addSolid(new Cuboid(new Tuple<>(aPoint.getOffset(0, 0, hollowZ),
				new Position(aPoint.getX() + hollowX, gPoint.getY(), gPoint.getZ() - hollowZ)), skin));
		this.addSolid(
				new Cuboid(new Tuple<>(new Position(gPoint.getX() - hollowX, aPoint.getY(), aPoint.getZ() + hollowZ),
						gPoint.getOffset(0, 0, -hollowZ)), skin));
		// y side
		this.addSolid(new Cuboid(
				new Tuple<>(aPoint.getOffset(hollowX, 0, hollowZ),
						new Position(gPoint.getX() - hollowX, aPoint.getY() + hollowY, gPoint.getZ() - hollowZ)),
				skin));
		this.addSolid(new Cuboid(
				new Tuple<>(new Position(aPoint.getX() + hollowX, gPoint.getY() - hollowY, aPoint.getZ() + hollowZ),
						gPoint.getOffset(-hollowX, 0, -hollowZ)),
				skin));
	}

	@Override
	public void addPointEntitys(Position start, Position end, int space, PointEntity type) {
		Tuple<Position, Position> conveted = this.convertPositions(start, end);
		this.addPointEntitys(conveted.getFirst(), conveted.getSecond(), space, type);
	}

	@Override
	public void setMaterial(Position p, Block block) {
		if (block != null) {
			this.materialField[p.getX()][p.getY()][p.getZ()] = block;
		}
	}

	@Override
	public Free8Point createFree8Point(Position start, Position end, int parts, Position[] offset, boolean align,
			Block material) {
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

	public void setIsNextToAir(Position position, boolean value) {
		this.isNextToAir[position.x][position.y][position.z] = value;
	}

	@Override
	protected boolean isConverted(Position p) {
		return this.isConverted[p.x][p.y][p.z];
	}
}
